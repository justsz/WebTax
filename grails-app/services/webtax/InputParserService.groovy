package webtax
import org.hibernate.SessionFactory;

class InputParserService {

	static transactional = false

	InputParserService() {
		blaster = new Blaster()
	}



	def userInput = new File("/Users/markb/Desktop/Justs\' folder/workspace2/WebTax/userUpload/input.fasta")
	def blaster
	def sessionFactory

	//def headerPattern = />(.*)\|([0-9]*)\|([0-9]*)\|(.*)/ //Change for whatever information will be accepted from the header.
	def headerPattern = />(.*)cutoff=([0-9]*)/
	def sequencePattern = /([CGATNcgatn]*)/

	def counter = -1
	def runnedOnce = false //Used for resetting counter if user uploads multiple files

	def ID
	def cutoff
	//def memberCount =[]
	//def sampleSite = []
	def sequence
	def progress


	def void parseAndAdd(Long ident) {

		def job = Job.get(ident)
		
		ID = []
		cutoff = []
		sequence = []
		def batch = []
		def start = System.currentTimeMillis()
		userInput.eachLine(parse)
		println "${ID.size()} MOTUs will be added."
		progress = 0
		def batchCount = 0

		//println "transaction count at at first call of parseAndAdd: ${sessionFactory.getStatistics().getEntityInsertCount()}"

		for (i in 0..<ID.size()) {
			

			def motu = new Motu(seqID: ID[i], sequence: sequence[i])	//No prob
			batch.add(motu)


			if (batch.size() > 50) {
				
				

				//Motu.withTransaction {	//No rollback needed
				def batchAddStart = System.currentTimeMillis()
				for (Motu m: batch) {
					if (m.save()) {	//Check if MOTU is already in database.
						blaster.doBlast(m)
					} else {
						println "${m.seqID} already in database."
					}
				}
				//100 motus with 10 blasts each
				
				


				batch.clear()
				//println "transaction count before flush: ${sessionFactory.getStatistics().getEntityInsertCount()}"
				//println "${progress as Integer}% done | ${(System.currentTimeMillis() - batchAddStart)/1000 as Integer}s for batch $batchCount"
				println "${(System.currentTimeMillis() - batchAddStart)/1000 as Integer}"
				sessionFactory.getCurrentSession().flush()	//Save all
				sessionFactory.getCurrentSession().clear()	//Clear all
				//println "transaction count after flush: ${sessionFactory.getStatistics().getEntityInsertCount()}"
				
				batchCount++
				progress = batchCount * 5000 / ID.size()	//Progress in percents
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
		
		println "Done."
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

			ID[counter] = headerFilter[0][1]
			cutoff[counter] = headerFilter[0][2]

			sequence[counter] = '' //Initialise sequence string so it doesn't start with 'null'

		} else if (sequenceFilter.matches()) {
			sequence[counter] += sequenceFilter[0][1]
		}
	}
}
