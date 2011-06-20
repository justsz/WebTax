package webtax

//This is a preliminary parser. It can only handle an input with one header and sequence. 
//Some fiddling required to extend it to handling an arbitrary number of sequences.


class FastaParser {
	def userInput = new File("/home/justs/workspace/WebTax/userUpload/input.fasta")

	def headerRule = />(.*)\|([0-9]*)\|([0-9]*)\|(.*)/
	def sequenceRule = /([CGAT]*)/
	
	
	def String ID
	def Integer cutoff
	def Integer memberNum  //Note: Rename to memberCount
	def String sampleSite
	
	def String sequence = ''
	

	def parser = {
		def headerFilter = (it =~ headerRule)
		def sequenceFilter = (it =~ sequenceRule)
		if (headerFilter.matches()) {
			ID = headerFilter[0][1]
			
			cutoff = headerFilter[0][2] as Integer
			
			memberNum = headerFilter[0][3] as Integer
			
			sampleSite = headerFilter[0][4]
			
		} else if (sequenceFilter.matches()) {
			sequence += sequenceFilter[0][1]
		}
	}
	
	def void parse() {
		userInput.eachLine(parser)
	}
	
	
}
