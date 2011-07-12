//Most of this file is a port of Martin Jones' Taxonerator.
//

package webtax

class Blaster {

	Blaster() {
		myTreeData = new TaxIdProcessor()
	}

	def blastDatabase = "./databases/silvaSmall/ssu_small.fasta"
	def megablastPath = "./blast/bin/megablast" //Note: works with megablast v 2.2.21. Doesn't work with v 2.4.21 (Probably fault of my installation.)
	def taxdumpPath = "./databases/NCBIdump"

	def processors = 1 //Give user option to choose number of cores later on.

	def motuID
	def seq
	BlastHit foundHit
	

	def acc2taxid = [:]


	def myTreeData

	def void doBlast(Motu inputMotu) {
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
		
		def command = "$megablastPath -d $blastDatabase -i blastInput.fsa -a $processors -m 8 -v 10 -b 10 -H 1"
		Process proc = command.execute()
		proc.waitFor()

		//process blast output
		def acc = -1
		def score = -1
		//def hits = []

		proc.in.eachLine{ line ->
			def rows = line.split(/\t/)
			acc = rows[1]
			score = rows[11] as Double

			def taxid


			//Find taxid
			if (acc2taxid.containsKey(acc)) {
				taxid = acc2taxid[acc]
				
//				if (!(inputMotu.addToHits(BlastHit.list().find { it.accNum == acc }))) {
//					def hit = new BlastHit(accNum: acc, bitScore: score, taxID: taxid).save()
//					addLineage(taxid, hit)
//					inputMotu.addToHits(hit)
//				}
			} else {
				taxid = getTaxidForAcc(acc)
				acc2taxid.put(acc, taxid)
				
//				def hit = new BlastHit(accNum: acc, bitScore: score, taxID: taxid).save()
//				addLineage(taxid, hit)
//				inputMotu.addToHits(hit)
			}
			
			//Add appropriate hit
				//returns null if hit is not in database
			//println foundHit
			foundHit = BlastHit.findByAccNum(acc)
			if (!foundHit) {
				//println "blaa"
				def hit = new BlastHit(accNum: acc, bitScore: score, taxID: taxid).save()
				addLineage(taxid, hit)
				inputMotu.addToHits(hit)
			} else {
				inputMotu.addToHits(foundHit)
			}


			
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


		}
	}


}
