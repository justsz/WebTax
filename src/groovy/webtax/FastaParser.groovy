package webtax

import java.security.Identity;


//The things in the header are parsed but not currently used for anything. Implement later when know what to do with it.


class FastaParser {
	def userInput = new File("/home/justs/workspace/WebTax/userUpload/input.fasta")
	def blaster = new Blaster()

	def headerPattern = />(.*)\|([0-9]*)\|([0-9]*)\|(.*)/ //Change for whatever information will be accepted from the header.
	def sequencePattern = /([CGAT]*)/

	def counter = -1
	def runnedOnce = false //Used for resetting counter if user uploads multiple files

	def ID = []
	def cutoff = []
	def memberCount =[]
	def sampleSite = []
	def sequence = []


	def void parseAndAdd() {
		userInput.eachLine(parse)

		println ID.size()

		for (i in 0..<ID.size()) {

			def motu = new Motu(seqID: ID[i], sequence: sequence[i])
			if (motu.save()) {	//Check if MOTU is already in database.
				blaster.doBlast(motu)
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
			//cutoff[counter] = headerFilter[0][2] as Integer		//Not used for anything right now.
			//memberCount[counter] = headerFilter[0][3] as Integer	//This one either
			sampleSite[counter] = headerFilter[0][4]

			sequence[counter] = '' //Initialise sequence string so it doesn't start with 'null'

		} else if (sequenceFilter.matches()) {
			sequence[counter] += sequenceFilter[0][1]
		}
	}


}
