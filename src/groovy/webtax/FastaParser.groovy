package webtax

import java.security.Identity;

//This is a preliminary parser. It can only handle an input with one header and sequence. 
//Some fiddling required to extend it to handling an arbitrary number of sequences.
//(Stick in a counter that increases after each header and put parsed values into a list.)
//The things in the header are parsed but not currently used for anything. Implement later when know what to do with it.


class FastaParser {
	def userInput = new File("/home/justs/workspace/WebTax/userUpload/input.fasta")
	def blaster = new Blaster()

	def headerRule = />(.*)\|([0-9]*)\|([0-9]*)\|(.*)/
	def sequenceRule = /([CGAT]*)/
	
	def counter = -1
	
	def ID = []
	def cutoff = []
	def memberNum =[]  //Note: Rename to memberCount
	def sampleSite = []
	
	def sequence = []
	

	def parser = {
		def headerFilter = (it =~ headerRule)
		def sequenceFilter = (it =~ sequenceRule)
		
		if (headerFilter.matches()) {
			counter++
			//println headerFilter[0][1]
			ID[counter] = headerFilter[0][1]
			cutoff[counter] = headerFilter[0][2] as Integer
			memberNum[counter] = headerFilter[0][3] as Integer
			sampleSite[counter] = headerFilter[0][4]
			
			sequence[counter] = '' //Initialise sequence string to avoid null
						
		} else if (sequenceFilter.matches()) {
			sequence[counter] += sequenceFilter[0][1]
		} 
	}
	
	def void parseAndAdd() {
		userInput.eachLine(parser)
		//println sequence[0]
		
		for (i in 0..<ID.size()) {
			
			def motu = new Motu(seqID: parser.ID[i], sequence: parser.sequence[i])
			motu.save()
			blaster.doBlast(motu)
		}
		
	}
	
	
}
