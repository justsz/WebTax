package webtax
import org.hibernate.SessionFactory;
import org.codehaus.groovy.grails.commons.*

class InputParserService {

	static transactional = false
	

//	InputParserService() {
//		config = ConfigurationHolder.config
//		blaster = new Blaster(config.megablastPath, config.taxdumpPath, config.databasePath)
//		
//		println config.userInputPath
//	}

	

	//def userInput = new File("./userUpload/input.fasta")
	def config = ConfigurationHolder.config
	def blaster = new Blaster(config.megablastPath, config.taxdumpPath, config.databasePath, config.processorCores)
	def sessionFactory
	def batchSize = config.batchSize
	def ant = new AntBuilder()

	def headerPattern = />site=(.*?)motu=(.*?)cutoff=(\d+)freq=(\d+)/	//>site=creer1motu=MOTU0893cutoff=0freq=10
	def sequencePattern = /([CGATNcgatn]*)/

	def counter = -1
	def runnedOnce = false //Used for resetting counter if user uploads multiple files

	def sites
	
	def ID
	def motuIds
	def cutoff
	def sequence
	def freqs
	
	def progress


	def void parseAndAdd(Long ident, String datasetName, String db, String destination, String fileName) {
		
		def job = Job.get(ident)
		def dataset = Dataset.findByName(datasetName)
		blaster.setBlastDatabase(db)
		
		sites = []
		
		ID = []
		motuIds = []
		cutoff = []
		sequence = []
		freqs = []
		
		def batch = []
		
		def file = new File(destination, fileName)
		println file.getName()
		
		try {
		file.eachLine(parse)
		} catch(Exception e) {
			job.progress = -1
			if (file.isFile()) file.delete()
			else ant.delete(dir: "${destination}/${fileName}")
			
			def fileCount = 0
			new File(destination).eachFile {fileCount++}
			if (!fileCount) {
				ant.delete(dir: destination)
			}
			return
		}
			
		
		runnedOnce = true
		println "${ID.size()} MOTUs will be added."
		progress = 0
		job.progress = progress
		def batchCount = 0		

		for (i in 0..<ID.size()) {

			def motu = new Motu(seqID: ID[i], motuId: motuIds[i], cutoff: cutoff[i], site: sites[i], sequence: sequence[i], freq: freqs[i])	
			batch.add(motu)


			if (batch.size() > batchSize) {
				dataset.attach()
				dataset = dataset.merge()
				

				//No rollback needed
				
				for (Motu m: batch) {
					
//					if (m.save()) {	//Check if MOTU is already in database.
//						blaster.doBlast(m)
//						dataset.addToMotus(m)	//currently impossible to add same data to different datasets. have to change motu's name to do that.					
//					} 
					
					if (!dataset.motus*.seqID.contains(m.seqID)) {
						m.save()
						blaster.doBlast(m)
						dataset.addToMotus(m)
					}
					
					
				}
				
				batch.clear()				
				sessionFactory.getCurrentSession().flush()	//Save all
				sessionFactory.getCurrentSession().clear()	//Clear all				
				
				//Update progress.
				batchCount++
				progress = batchCount * 100 * batchSize / ID.size()	//Progress in percents
				job.progress = progress
				job.save(flush:true)

			}
		}

		//Adds the rest of motus.
		
		dataset.attach()
		dataset = dataset.merge()
		for (Motu m: batch) {
			
			if (!dataset.motus*.seqID.contains(m.seqID)) {
						m.save()
						blaster.doBlast(m)
						dataset.addToMotus(m)
					}			
		}
		
				
		job.progress = 100
		job.save(flush:true)
		file.delete()
		
		//Check if the directory of the dataset is empty. If it is, delete the directory.
		def fileCount = 0
		new File(destination).eachFile {fileCount++}
		if (!fileCount) {
			ant.delete(dir: destination)
		}
		
		
		
	}

	
	def parse = {
		if (runnedOnce) {
			counter = -1
			runnedOnce = false
		}

		def headerFilter = (it =~ headerPattern)
		def sequenceFilter = (it =~ sequencePattern)

		if (headerFilter.matches()) {
			counter++
			
			ID[counter] = headerFilter[0][0]
			sites[counter] = headerFilter[0][1]
			motuIds[counter] = headerFilter[0][2]
			cutoff[counter] = headerFilter[0][3]
			freqs[counter] = headerFilter[0][4]

			sequence[counter] = '' //Initialise sequence string so it doesn't start with 'null'

		} else if (sequenceFilter.matches()) {
			sequence[counter] += sequenceFilter[0][1]
		}
	}
}

