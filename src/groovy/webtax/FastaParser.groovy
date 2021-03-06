package webtax

import java.security.Identity;
import org.codehaus.groovy.grails.commons.ApplicationHolder as AH


//The things in the header are parsed but not currently used for anything. Implement later when know what to do with it.


class FastaParser {
	def userInput = new File("/home/justs/workspace/WebTax/userUpload/input.fasta")
	def blaster = new Blaster()
	
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
	
	
	
	def ctx = AH.application.mainContext
	def sessionFactory = ctx.sessionFactory
	

	def void parseAndAdd() {
		println "POGO here."
		if (sessionFactory.getCurrentSession() != null)
		println "Service sessionFactory is ok."
		ID = []
		cutoff = []
		sequence = []
		def batch = []
		userInput.eachLine(parse)

		for (i in 0..<ID.size()) {

			def motu = new Motu(seqID: ID[i], sequence: sequence[i])
			batch.add(motu)

			if (batch.size() > 100) {
				Motu.withTransaction {
					for (Motu m: batch) {
						if (m.save()) {	//Check if MOTU is already in database.
							blaster.doBlast(m)
						} else {
							println "${m.seqID} already in database."
						}
					}
					batch.clear()
					sessionFactory.getCurrentSession().clear();
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
