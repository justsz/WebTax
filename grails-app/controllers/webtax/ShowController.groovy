package webtax

class ShowController {
	def index = { }

	def repForm = {
		return [dataset: params.dataset, params: params]
	}

	def represent = {
		if(!params.dataset) {
			flash.message = "No dataset supplied!"
			redirect(action:'repForm', params: params)
			return
		}
		
		if(!params.sites) {
			flash.message = "Please enter site(s)."
			redirect(action:'repForm', params: params)
			return
		}
		if(!params.threshold.isNumber()) {
			flash.message = "Threshold must be a number."
			redirect(action:'repForm', params: params)
			return

		}
		if(!params.cutoff) {
			flash.message = "Please enter a cutoff."
			redirect(action:'repForm', params: params)
			return
		}
		if(!params.minBitScore.isNumber()) {
			flash.message = "Minimum bitscore must be a number."
			redirect(action:'repForm', params: params)
			return
		}
		if(!params.minBitScoreStep.isNumber()) {
			flash.message = "Minimum bitscore step must be a number."
			redirect(action:'repForm', params: params)
			return
		}

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
			def freqs = motus.collect {it.freq}
			def hits = motus.collect { it.hits }	//[[h1, h2...], [h11, h12...],...]


			hits = hits*.sort { -it.bitScore }

			hits = hits.collect { it  = it.split{ it.bitScore >= minBitScore  }[0] }		//change to  x -> x.... format for readability

			//hits = hits.split { it.size() > 1 }[0]	//trim out singletons
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
						if (h.key[prop] =~ ".*${params.keyPhrase}.*") {
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
			reps[counter] = reps[counter].sort {a, b -> b.value <=> a.value}


			totalHits[counter] = 0
			reps[counter].each {key, value -> totalHits[counter] += value}
			def others = ['others', 0]

			reps[counter].each {key, value ->
				if ((value / totalHits[counter]) > ((params.threshold.toDouble()) / 100)) {	//Clump together under "others" chart sections for motus that represent less than threshold% of the total motu count
					def entry = [key, value]
					data[counter].add(entry)
				} else {
					others[1] += value
				}
			}
			if((params.threshold.toDouble()) != 0) {
				data[counter].add(others)
				reps[counter].put(others)
			}

			counter++
		}


		return [reps: reps, type: type, data: data, sites:sites, chart: params.chart, params: params, dataset:params.dataset]
	}

	def search = {
		return [dataset:params.dataset]
	}

	def results = {
		//Add some default values in case user doesn't want to give a value.
		//def motus = Motu.findAllBySiteAndCutoff(params.site, params.cutoff)
		params.max = Math.min(params.max ? params.int('max') : 10, 100)

		if (!params.dataset || !Dataset.findByName(params.dataset)) {
			flash.message = "Dataset not found."
			redirect (action: 'search', params: params)
			return
		}

		if (!Dataset.findByName(params.dataset).motus.size()) {
			flash.message = "Dataset is empty."
			redirect (action: 'search', params: params)
			return
		}

		def query = {
			'in'("id", Dataset.findByName(params.dataset).motus*.id)
			eq('site', params.site)
			eq('cutoff', params.cutoff)
		}

		def motus = Motu.createCriteria().list(params, query)
		def total = Motu.createCriteria().count(query)

		//		request.motuInstanceList = motus
		//		request.motuInstanceTotal = total

		return [motuInstanceList: motus, motuInstanceTotal: total, params:params, dataset:params.dataset]
	}

	def showTable = {
		def motuInstance = Motu.get(params.id)
		
		if (!motuInstance) {
			flash.listMessage = "MOTU not found."
			redirect(action: "list", params: [dataset: params.dataset])
		}
		else {


			if (!params.max) params.max = 10
			if (!params.offset) params.offset = 0
			if (!params.sort) params.sort = "bitScore"
			if (!params.order) params.order = "desc"
			
			if (!motuInstance.hits*.id.size()) {
				return [motuInstance: motuInstance, hits: [], dataset:params.dataset]
			}

			def hitS = BlastHit.withCriteria {
				maxResults(params.max?.toInteger())
				firstResult(params.offset?.toInteger())
				'in'("id", motuInstance.hits*.id)

				order(params.sort, params.order)
			}
			return [motuInstance: motuInstance, hits: hitS, dataset:params.dataset]
		}
	}


	def list = {



		if (!params.dataset || !Dataset.findByName(params.dataset)) {
			flash.listMessage = "Dataset not found."
			return [motuInstanceList: [], motuInstanceTotal: 0, dataset: params.dataset]
		}

		if (!Dataset.findByName(params.dataset).motus.size()) {
			flash.listMessage = "Dataset is empty."
			return [motuInstanceList: [], motuInstanceTotal: 0, dataset: params.dataset]
		}

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

	def switchDataset = {
		return [prevAction: params.prevAction, prevController: params.prevController, dataset: params.dataset]
	}

	def executeSwitch = {
		redirect(action: params.prevAction, controller: params.prevController, params: [dataset: params.newDataset])
	}


}
