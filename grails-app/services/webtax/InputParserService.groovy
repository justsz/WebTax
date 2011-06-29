package webtax
import org.hibernate.SessionFactory;

class InputParserService {

    static transactional = false

	InputParserService() {
		blaster = new Blaster()
	}
	
	
	
	def userInput = new File("/home/justs/workspace/WebTax/userUpload/input.fasta")
	def blaster
	def sessionFactory

	//def headerPattern = />(.*)\|([0-9]*)\|([0-9]*)\|(.*)/ //Change for whatever information will be accepted from the header.
	def headerPattern = />(.*)cutoff=([0-9]*)/
	def sequencePattern = /([CGATcgat]*)/

	def counter = -1
	def runnedOnce = false //Used for resetting counter if user uploads multiple files

	def ID
	def cutoff
	//def memberCount =[]
	//def sampleSite = []
	def sequence


	def void parseAndAdd() {
		println "Service here."
		if (sessionFactory.getCurrentSession() != null)
			println "Service sessionFactory is ok."
		
		ID = []
		cutoff = []
		sequence = []
		def batch = []
		def start = System.currentTimeMillis()
		userInput.eachLine(parse)
		println "Input parsing time: ${(System.currentTimeMillis() - start) /1000}"
		println "transaction count at at first call of parseAndAdd: ${sessionFactory.getStatistics().getEntityInsertCount()}"
		
		for (i in 0..<ID.size()) {

			start = System.currentTimeMillis()
			def motu = new Motu(seqID: ID[i], sequence: sequence[i])
			batch.add(motu)
			

			if (batch.size() > 100) {
				println "Making a 100 motu in list time: ${(System.currentTimeMillis() - start) /1000}"
				Motu.withTransaction {
					for (Motu m: batch) {
						if (m.save()) {	//Check if MOTU is already in database.
							blaster.doBlast(m)
						} else {
							println "${m.seqID} already in database."
						}
					}
					
					batch.clear()
					println "transaction count before flush: ${sessionFactory.getStatistics().getEntityInsertCount()}"
					println "Clearing session!"
					sessionFactory.getCurrentSession().flush()
					sessionFactory.getCurrentSession().clear()
					println "transaction count after flush: ${sessionFactory.getStatistics().getEntityInsertCount()}"
				}
			}

			

		}

		for (Motu m: batch) {
			if (m.save()) {	//Check if MOTU is already in database.
				blaster.doBlast(m)
			} else {
				println "${m.seqID} already in database."
			}
		}
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
