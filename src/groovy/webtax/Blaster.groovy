//Most of this file is a port of Martin Jones' Taxonerator.

package webtax

class Blaster {

	def blastDatabase = "/home/justs/workspace/WebTax/databases/silva/ssu_silva.fasta"
	//def megablastPath = "/home/justs/Desktop/blast/megablast"
	def megablastPath = "/usr/bin/megablast" //Note: works with megablast v 2.2.21. Doesn't work with v 2.4.21
	def taxdumpPath = '/home/justs/workspace/WebTax/databases/NCBIdump'

	def processors = 1

	def motuID
	def seq



	// write a single sequence to the blast input file
	def void doBlast(Motu inputMotu) {
		motuID = inputMotu.seqID
		seq = inputMotu.sequence

		File blastInput = new File('blastInput.fsa')
		blastInput.delete()
		blastInput.append(">$motuID\n$seq\n")

		// check that there's a blast database
		File blastDb = new File(blastDatabase + ".nin")
		if (!blastDb.exists()){
			println "Can't find BLAST file " + blastDb.getAbsolutePath()
			System.exit(1)
		}

		//call blast
		def command = "$megablastPath -d $blastDatabase -i blastInput.fsa -a $processors -m 8 -v 1 -b 1 -H 1"
		Process proc = command.execute()
		proc.waitFor()



		//process blast output
		proc.in.eachLine{ line ->
			def rows = line.split(/\t/)
			def motu = rows[0]
			def hit = rows[1]
			Integer score = rows[11].toFloat() //Changed from float to int. Can the score even be a float?

			def taxon = getTaxidForHit(hit)
			

			def aHit = new BlastHit(accNum: hit, bitScore: score, taxID: taxon)
			addLineage(taxon, aHit)



			//println hit
			//println score
			Motu.get(inputMotu.id).addToHits(aHit)

		}

	}


	//Not checking if the taxID has been already accessed before.
	Integer getTaxidForHit(String acc){
		String result
		// use try catch because ncbi eutils are unreliable
		try{
			URL efetch = "http://eutils.ncbi.nlm.nih.gov/entrez/eutils/efetch.fcgi?db=nucleotide&id=$acc&rettype=gb".toURL()
			String gb = efetch.text

			gb.eachLine({
				if (it.startsWith('                     /db_xref="taxon:')){
					def taxidMatcher = (it =~ /taxon:(\d+)/)
					result = taxidMatcher[0][1]
				}
			})
		} catch(e){
			result = -1
		}

		if (result == null){
			return -1
		}
		else{
			return result.toInteger()
		}


	}
	
	void addLineage(Integer taxid, BlastHit hit) {		//might be problems if the databese is not accessed and only the BlastHit modified
		TaxIdProcessor myTreeData = new TaxIdProcessor(taxdumpPath)
		
				println 'making node'
				def node = myTreeData.getNodeForTaxid(taxid)
				println 'node made'
		
				for (TreeNode ancestor in myTreeData.getAncestorsForNode(node)){
					if (ancestor.rank == 'species') {
						hit.species = ancestor.name
					} else if (ancestor.rank == 'genus') {
						hit.genus = ancestor.name
					} else if (ancestor.rank == 'order') {
						hit.taxOrder = ancestor.name
					} else if (ancestor.rank == 'family') {
						hit.family = ancestor.name
					} else if (ancestor.rank == 'class') {
						hit.taxClass = ancestor.name
					} else if (ancestor.rank == 'phylum') {
						hit.phylum = ancestor.name
					} 
					
					
					
					// only add this tree node if we haven't already done so
		
					//println "adding info for $ancestor.taxid to database"
					//println "$ancestor.name, $ancestor.rank, $ancestor.taxid"
					
					//taxid needed?
					
					
					// keep a record of the taxid so we don't add it next time
					//taxAdded.add(ancestor.taxid)
		
		
				}
	}



}
