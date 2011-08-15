package webtax

class VisualizeService {
	static transactional = false
	//the filter by phrase keywords will be looked for in these properties of each hit
	def properties = ['species', 'genus', 'taxOrder', 'family', 'taxClass', 'phylum']

	//a list that will contain a map for each sample site. The map will hold the MOTU summary data
	def reps = []

	//google's chart API takes a list of maps, so data will hold the same information
	//as reps but formatted differently
	def data = []
	def totalHits = []


	//sites, params.threshold, params.keyPhrase, cutoff, minBitScore, minBitScoreStep, type
	def void processCriteria(String dataset, List sites, String threshold, String keyPhrase, String cutoff, Integer minBitScore, Integer minBitScoreStep, String type) {

		reps = []
		data = []
		totalHits = []

		//counter that keeps track of which sample site is being processed to
		//separate out the data between samples
		def counter = 0

		for (site in sites) {
			reps[counter] = [:]
			data[counter] = []

			//list all motus that belong to the current dataset, and have the site and cutoff specified
			def motus = Motu.withCriteria {
				'in'("id", Dataset.findByName(dataset).motus*.id)
				eq("site", site)
				eq("cutoff", cutoff)
			}

			//make a list of frequencies and corresponding hits
			//must not sort before the frequencies of each hit is taken into account!
			def freqs = motus.collect {it.freq}
			def hits = motus.collect { it.hits }	//looks like [[h1, h2...], [h11, h12...],...]



			//sort each list of hits within the big list
			hits = hits*.sort { -it.bitScore }




			//drop all the hits that have below the minimum bitscore
			hits = hits.collect {subHitList -> subHitList = subHitList.split{hit -> hit.bitScore >= minBitScore  }[0] }


			if (minBitScoreStep != 0) {
				hits = hits.collect {
					if (it[0] != null && it[1] != null) {
						if ((it[0].bitScore - it[1].bitScore) >= minBitScoreStep) {it = it[0]}
						else it = null
					} else it = null
				}
				//hits = hits.split{it}[0]	//trim out nulls
			} else {
				hits = hits.collect { it = it[0] }
			}

			def hitsWithFreqs = [:]
			for (i in 0..<hits.size()) {
				hitsWithFreqs.put (hits[i], 0)
			}

			for (i in 0..<hits.size()) {
				hitsWithFreqs[hits[i]] += freqs[i].toInteger()
			}




			for (h in hitsWithFreqs) {

				if (h.key) {
					for (prop in properties) {
						if (h.key[prop] =~ ".*${keyPhrase}.*") {
							if (reps[counter].containsKey(h.key[type])) {
								reps[counter][h.key[type]] += (h.value.toInteger())
							} else {
								reps[counter].put(h.key[type], h.value.toInteger())
							}
							break
						}
					}
				}
			}
			def dataForChart = reps[counter].sort {a, b -> b.value <=> a.value}
			reps[counter] = reps[counter].sort {a, b -> -(b.key <=> a.key)}



			totalHits[counter] = 0
			reps[counter].each {key, value -> totalHits[counter] += value}
			def others = ['others', 0]

			dataForChart.each {key, value ->
				if ((value / totalHits[counter]) > ((threshold.toDouble()) / 100)) {	//Clump together under "others" chart sections for motus that represent less than threshold% of the total motu count
					def entry = [key, value]
					data[counter].add(entry)
				} else {
					others[1] += value
				}
			}
			if((threshold.toDouble()) != 0) {
				data[counter].add(others)
				reps[counter].put(others)
			}

			counter++
		}

	}
	
	def List getReps() {
		return reps
	}
	
	def List getData() {
		return data
	}
	
	def List getTableData(List repses) {
		def repses2 = repses.clone()
		
		def allNames = []
		
		repses.each { map -> 
			map.each { entry ->
				  if (!allNames.contains(entry.key)) allNames.add(entry.key)
			}	
		}
		
		allNames.sort {it}
		
		for (i in 0..<repses.size()) {
			allNames.each { name ->
				 if(!repses2[i].containsKey(name)) repses2[i].putAt(name, 0) 
			}			 
		}
		
		def tableData = []
		
		for (i in 0..allNames.size()) {
			tableData[i] = []
			tableData[i][0] = allNames[i]
			
			for (j in 1..repses2.size()) {
				tableData[i][j] = repses2[j-1][allNames[i]]
			}
		}
		
		return tableData
	}

}
