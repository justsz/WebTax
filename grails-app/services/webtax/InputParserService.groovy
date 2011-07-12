package webtax
import org.hibernate.SessionFactory;

class InputParserService {

	static transactional = false

	InputParserService() {
		blaster = new Blaster()
	}

	

	def userInput = new File("./userUpload/input.fasta")
	def blaster
	def sessionFactory
	def batchSize = 50

	//def headerPattern = />(.*)\|([0-9]*)\|([0-9]*)\|(.*)/ //Change for whatever information will be accepted from the header.
	def headerPattern = />(.+)_(?:.*)_MOTU(\d+)cutoff=(\d+)/		//>creer1_0bp_MOTU0893cutoff=0
	def sequencePattern = /([CGATNcgatn]*)/

	def counter = -1
	def runnedOnce = false //Used for resetting counter if user uploads multiple files

	def sites
	
	def ID
	def cutoff
	def sequence
		
	def progress


	def void parseAndAdd(Long ident) {

		def job = Job.get(ident)
		
		sites = []
		
		ID = []
		cutoff = []
		sequence = []
		def batch = []
		def start = System.currentTimeMillis()
		userInput.eachLine(parse)
		println "${ID.size()} MOTUs will be added."
		progress = 0
		def batchCount = 0

		

		for (i in 0..<ID.size()) {
			

			def motu = new Motu(seqID: "MOTU${ID[i]}bp${cutoff[i]}", cutoff: cutoff[i], site: sites[i], sequence: sequence[i])	
			batch.add(motu)


			if (batch.size() > batchSize) {
				
				

				//No rollback needed
				def batchAddStart = System.currentTimeMillis()
				for (Motu m: batch) {
					if (m.save()) {	//Check if MOTU is already in database.
						blaster.doBlast(m)
					} else {
						println "${m.seqID} already in database."
					}
				}
				
				
				


				batch.clear()
				
				//println "${progress as Integer}% done | ${(System.currentTimeMillis() - batchAddStart)/1000 as Integer}s for batch $batchCount"
				
				sessionFactory.getCurrentSession().flush()	//Save all
				sessionFactory.getCurrentSession().clear()	//Clear all
				
				
				batchCount++
				progress = batchCount * 100 * batchSize / ID.size()	//Progress in percents
				job.progress = progress
				job.save(flush:true)

			}



		}

		//Adds the rest of motus.
		for (Motu m: batch) {
			if (m.save()) {	//Check if MOTU is already in database.
				blaster.doBlast(m)
			} else {
				println "${m.seqID} already in database."
			}
		}
		
		//Cleanup of orphan blastHits. Ugh, have to be careful with this.
		//BlastHit.list().each { if (it.motus.size() == 0) {it.delete()} }		
		
		
		job.progress = 100
		job.save()
		runnedOnce = true
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

			sites[counter] = headerFilter[0][1]
			
			ID[counter] = headerFilter[0][2]
			cutoff[counter] = headerFilter[0][3]

			sequence[counter] = '' //Initialise sequence string so it doesn't start with 'null'

		} else if (sequenceFilter.matches()) {
			sequence[counter] += sequenceFilter[0][1]
		}
	}
}

