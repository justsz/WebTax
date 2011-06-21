package webtax

class Blaster {
	
	def blastDatabase = "/home/justs/workspace/WebTax/databases/silva/ssu_silva.fasta"
	//def megablastPath = "/home/justs/Desktop/blast/megablast"
	def megablastPath = "/usr/bin/megablast"
	
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
		def command = "$megablastPath -d $blastDatabase -i blastInput.fsa -a $processors -m 8 -v 10 -b 10 -H 1"
		Process proc = command.execute()
		proc.waitFor()

		//Map results = [:]

		//process blast output
		proc.in.eachLine{ line ->
			def rows = line.split(/\t/)
			def motu = rows[0]
			def hit = rows[1]
			Integer score = rows[11].toFloat() //Changed from float to int. Can the score even be a float?
			
			def aHit = new BlastHit(accNum: hit, bitScore: score)
			//println hit
			//println score
			Motu.get(inputMotu.id).addToHits(aHit)
			//results.put(hit, score)
		}

		//seq2results.put(it.seq, results)
	}

	//seq2results.get(it.seq).each{ entry ->
		//sql.execute("""insert into blast_hit (motu_id, acc, bitscore) values ($it.motu_id, $entry.key, $entry.value)""")
	}




