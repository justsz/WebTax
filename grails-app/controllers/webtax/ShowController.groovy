package webtax

class ShowController {
	def index = { }
	
	def repForm = {
		return [dataset: params.dataset]
	}
	
	def represent = {
		def properties = ['species', 'genus', 'taxOrder', 'family', 'taxClass', 'phylum']
		def reps = []
		def data = []
		def totalHits = []
		def type = params.type
		def cutoff = params.cutoff
		def sites = params.sites.split(",")

		for (i in 0..<sites.size()) {
			sites[i] = sites[i].trim()
		}

		def counter = 0

		for (site in sites) {
			reps[counter] = [:]
			data[counter] = []

			def motus = Motu.withCriteria {
				'in'("id", Dataset.findByName(params.dataset).motus*.id)
				eq("site", site)
				eq("cutoff", cutoff)
			}


			//			def hits = motus.collect {it.hits.max {it.bitScore}}

			def minBitScore = params.minBitScore as Integer
			def minBitScoreStep = params.minBitScoreStep as Integer
			def hits = motus.collect { it.hits }

			hits = hits*.sort { -it.bitScore }

			hits = hits.collect { it  = it.split{ it.bitScore >= minBitScore  }[0] }		//change to  x -> x.... format for readability

			hits = hits.split { it.size() > 1 }[0]	//trim out singletons
			if (minBitScoreStep != 0) {
				hits = hits.collect { if ((it[0].bitScore - it[1].bitScore) >= minBitScoreStep) it = it[0]
					else it = null }
				hits = hits.split{it}[0]	//trim out nulls
			} else {
				hits = hits.collect { it = it[0] }
			}



			for (h in hits) {
				if (h) {
					for (prop in properties) {
						if (h[prop] =~ ".*${params.keyPhrase}.*") {
							if (reps[counter].containsKey(h[type])) {
								reps[counter][h[type]]++
							} else {
								reps[counter].put(h[type], 1)
							}
							break
						}
					}
				}
			}
			reps[counter] = reps[counter].sort {a, b -> b.value <=> a.value}
			totalHits[counter] = 0
			reps[counter].each {key, value -> totalHits[counter] += value}
			def others = ['others', 0]

			reps[counter].each {key, value ->
				if ((value / totalHits[counter]) > ((params.threshold as Integer) / 100)) {	//Clump together under "others" chart sections for motus that represent less than threshold% of the total motu count
					def entry = [key, value]
					data[counter].add(entry)
				} else {
					others[1] += value
				}
			}
			data[counter].add(others)
			reps[counter].put(others)
			counter++
		}

		return [reps: reps, type: type, data: data, sites:sites, chart: params.chart, params: params]
	}

	def search = {
		return [dataset:params.dataset]
	}

	def results = {
		//Add some default values in case user doesn't want to give a value.
		//def motus = Motu.findAllBySiteAndCutoff(params.site, params.cutoff)
		params.max = Math.min(params.max ? params.int('max') : 10, 100)
		def query = {
			eq('site', params.site)
			eq('cutoff', params.cutoff)
		}

		def motus = Motu.createCriteria().list(params, query)
		def total = Motu.createCriteria().count(query)

		//		request.motuInstanceList = motus
		//		request.motuInstanceTotal = total

		return [motuInstanceList: motus, motuInstanceTotal: total, params:params]
	}

	def showTable = {
		def motuInstance = Motu.get(params.id)
		if (!motuInstance) {
			flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'motu.label', default: 'Motu'), params.id])}"

			redirect(action: "list")
		}
		else {


			if (!params.max) params.max = 10
			if (!params.offset) params.offset = 0
			if (!params.sort) params.sort = "bitScore"
			if (!params.order) params.order = "desc"

			def hitS = BlastHit.withCriteria {
				maxResults(params.max?.toInteger())
				firstResult(params.offset?.toInteger())
				'in'("id", motuInstance.hits*.id)

				order(params.sort, params.order)
			}
			[motuInstance: motuInstance, hits: hitS]
		}
	}

	def list = {
		
				params.max = Math.min(params.max ? params.int('max') : 10, 100)
				if (!params.sort) params.sort = "id"
				if (!params.order) params.order = "asc"
		
				def query = {
					'in'("id", Dataset.findByName(params.dataset).motus*.id)
					//order(params.sort, params.order)
				}
		
				def motus = Motu.createCriteria().list(params, query)
				def total = Motu.createCriteria().count(query)
		
				return [motuInstanceList: motus, motuInstanceTotal: total, dataset: params.dataset]
		
			}
	
	
}
