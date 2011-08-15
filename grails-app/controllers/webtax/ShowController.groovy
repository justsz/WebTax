/*
*---------------------------------ShowController-------------------------------------
* This controller handles all the display aspects of the user's input
* after blasting and annotating. This includes listing all MOTUs, showing an
* individual MOTU in a table with its 10 best matches from megablast, searching
* through motus based on sample name and cutoff value, and a summary view that
* has a selection of criteria and chart types to produce an overview of what kinds of
* creatures are in the user's sample. 
* Charts are drawn using Google's visualization API plugin.
* Data can be downloaded via OutputController.
*------------------------------------------------------------------------------------
* Common things: the dataset String is always passed around so that it would be unique
* to the browser window and links could be easily shared.
*/

package webtax

class ShowController {
	def visualizeService
	
	def index = { }

	/*-------repForm--------
	* Takes user's criteria for the summary view, represent. The criteria are
	* list of sample sites, MOTU clustering cutoff, clumping threshold (put poorly represented MOTU's in one category),
	* a filtering phrase that applies to the MOTU's hits, minimum bitscore, minimum difference between first and next bitscore,
	* taxonomic type to show, and chart type to display.
	*/
	def repForm = {
		return [dataset: params.dataset, params: params]
	}

	/*------represent--------
	* Gets criteria from repForm, constructs and executes queries (these have many steps)
	* and draws the results in tables and charts.
	*/
	def represent = {
		//user input validity checks
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
		if(!params.cutoff.isNumber()) {
			flash.message = "Cutoff must be a number."
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
		//end of validity checks

		//take user's input
		def type = params.type
		def cutoff = params.cutoff
		def minBitScore = params.minBitScore as Integer
		def minBitScoreStep = params.minBitScoreStep as Integer

		//make the sites string into a list and trim off excess whitespace
		def sites = params.sites.split(",")
		for (i in 0..<sites.size()) {
			sites[i] = sites[i].trim()
		}
		//println sites.getClass()
		
		visualizeService.processCriteria(params.dataset, sites as List, params.threshold, params.keyPhrase, cutoff, minBitScore, minBitScoreStep, type)
		def reps = visualizeService.getReps()	
		def data = visualizeService.getData()
		def tableData = visualizeService.getTableData(reps)	
		

		return [reps: reps, tableData: tableData, type: type, data: data, sites:sites, chart: params.chart, params: params, dataset:params.dataset]
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
