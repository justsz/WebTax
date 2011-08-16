/*
*---------------------------VisualizeService---------------------------------
* This service is used in ShowController to take a user's query and turn
* into data that can be passed to the view and then easily made into a
* table and graphs.
* First processCriteria is called, which fills in the instance variables that
* can then be accessed by ShowController to retrieve the formatted data.
*----------------------------------------------------------------------------
*/


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


	/*--------processCriteria-----------
	* Fills in the instance variables with data retrieved based on the user's query.
	* Note: the data retrieved from the database is not entirely consistent between runs, the motus represented by one or two hits
	* tend to jump around. I was not able to fix this at the time. The majority of data is consistent, though.
	*/
	def void processCriteria(String dataset, List sites, String threshold, String keyPhrase, String cutoff, Integer minBitScore, Integer minBitScoreStep, String type) {

		reps = []
		data = []
		totalHits = []

		//counter that keeps track of which sample site is being processed to
		//separate out the data between samples
		def counter = 0

		for (site in sites) {
			reps[counter] = [:]	//each site gets a map entry in reps
			data[counter] = []	//each site gets a list entry in data

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

			//drop all hits that have below the minimum bitscore
			hits = hits.collect {subHitList -> subHitList = subHitList.split{hit -> hit.bitScore >= minBitScore  }[0] }


			//bitscore step stuff is excluded for now, just the highest bitscore hit is taken
			//if (minBitScoreStep != 0) {
			//	hits = hits.collect {
			//		if (it[0] != null && it[1] != null) {
			//			if ((it[0].bitScore - it[1].bitScore) >= minBitScoreStep) {it = it[0]}
			//			else it = null
			//		} else it = null
			//	}
			//} else {
				hits = hits.collect { it = it[0] }
			//}

			def hitsWithFreqs = [:]
			//for (i in 0..<hits.size()) {
				hits.each { hitsWithFreqs.put (it, 0) }
			//}
			

			//now, because hits wasn't sorted at any time, it has the same order as freqs. This means that going through hits
			//and assigning its entry in hitsWithFreqs the value of freqs at the same index will yield hitsWithFreqs that holds all
			//unique found hits with the correct frequency sums
			for (i in 0..<hits.size()) {
				hitsWithFreqs[hits[i]] += freqs[i].toInteger()
			}




			for (h in hitsWithFreqs) {
				if (h.key) {
					for (prop in properties) {	//cycle through properties of the hit and only put it into reps if one of the properties matches keyPhrase
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
			
			//sort in order of high to low bitscore
			reps[counter] = reps[counter].sort {a, b -> b.value <=> a.value}



			totalHits[counter] = 0
			reps[counter].each {key, value -> totalHits[counter] += value}
			def others = ['others', 0]

			//fill in the data for google graphs
			reps[counter].each {key, value ->
				//Clump together under "others" chart sections for motus that represent less than threshold% of the total motu count
				if ((value / totalHits[counter]) > ((threshold.toDouble()) / 100)) {	
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
	

	/*---------getReps---------
	* Returns a list of maps where each map contains data about a sample's makeup.
	* Used when there is only one sample and by getTableData.
	*/
	def List getReps() {
		return reps
	}
	

	/*--------getData---------
	* Returns a list of lists of list of lists(!) that is used by google charts.
	*/
	def List getData() {
		return data
	}
	

	/*----------getTableData----------
	* Takes the makeup data of many sites, creates a master list that holds
	* every present creature's taxonomic name and also how many of the creature is
	* identified in each site. Formatted for easy parsing and displaying by the visualization
	* view.
	*/
	def List getTableData(List repses) {
		def repses2 = repses.clone()
		def allNames = []
		
		//go through all hits and find every unique name 
		repses.each { map -> 
			map.each { entry ->
				  if (!allNames.contains(entry.key)) allNames.add(entry.key)
			}	
		}
		
		//sort names alphabetically
		allNames.sort {it}
		
		//go through the hits and fill in with missing names so that all maps have all names
		for (i in 0..<repses2.size()) {
			allNames.each { name ->
				 if(!repses2[i].containsKey(name)) repses2[i].putAt(name, 0) 
			}			 
		}
		
		def tableData = []
		
		//go through all the names and for each put a list in tableData that has the creature's name at index 0
		//and then the number of reads matching to that name in each sample site. The order of the read numbers
		//matches the List sites that ShowController action visualize already has.
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
