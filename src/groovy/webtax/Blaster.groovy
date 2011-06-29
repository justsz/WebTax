//Most of this file is a port of Martin Jones' Taxonerator.
//

package webtax

class Blaster {
	
	Blaster() {
		myTreeData = new TaxIdProcessor()
	}

	def blastDatabase = "/home/justs/workspace/WebTax/databases/silva/ssu_silva.fasta"
	def megablastPath = "/usr/bin/megablast" //Note: works with megablast v 2.2.21. Doesn't work with v 2.4.21
	def taxdumpPath = '/home/justs/workspace/WebTax/databases/NCBIdump'

	def processors = 1 //Give user option to choose number of cores later on.

	def motuID
	def seq

	def acc2taxid = [:]
	//def taxAdded = []
	//def accAdded = []


	def myTreeData

	def void doBlast(Motu inputMotu) {
		//def start = System.currentTimeMillis()
		// write a single sequence to the blast input file
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
		//def blastStart = System.currentTimeMillis()
		def command = "$megablastPath -d $blastDatabase -i blastInput.fsa -a $processors -m 8 -v 10 -b 10 -H 1"
		Process proc = command.execute()
		proc.waitFor()
		//println "Blast time: ${System.currentTimeMillis() - blastStart}"
		
		//proc.in.eachLine {println it}

		//process blast output
		def acc = -1
		def score = -1
		//def hits = []
		
		proc.in.eachLine{ line ->
			def rows = line.split(/\t/)
			//def motu = rows[0]
			acc = rows[1]
			score = rows[11] as Integer //Changed from float to int. Can the score even be a float?

			def taxid
			
			//Old blast hits are not reused. If you want them to be used, sort out actions on delete.
			if (acc2taxid.containsKey(acc)) {
				//println "already have $acc"
				taxid = acc2taxid[acc]
				inputMotu.addToHits(BlastHit.list().find {it.accNum == acc })
			} else {
				taxid = getTaxidForAcc(acc)
				//println "taxid = $taxid"
				acc2taxid.put(acc, taxid)
			
				def hit = new BlastHit(accNum: acc, bitScore: score, taxID: taxid).save()
				addLineage(taxid, hit)
				
				//hits.add(hit)
				
				inputMotu.addToHits(hit)
			}
			//Motu.get(inputMotu.id).addToHits(hit)
		}
		
		
		
		//println (System.currentTimeMillis() - start)
	}







	Integer getTaxidForAcc(String acc){
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
		def node = myTreeData.getNodeForTaxid(taxid)
		if (node == null){
			println "WARNING: couldn't fine node with taxid $taxid in the NCBI taxdump"
		} else {

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

			}
			// keep a record of the taxid so we don't add it next time
			//taxAdded.add(ancestor.taxid)


		}
	}


}
