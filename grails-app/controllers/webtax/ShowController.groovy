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
	def analyseService

	def index = { }

	/*-------analyseForm--------
	 * Takes user's criteria for the summary view, analyse. The criteria are
	 * list of sample sites, MOTU clustering cutoff, clumping threshold (put poorly represented MOTU's in one category),
	 * a filtering phrase that applies to the MOTU's hits, minimum bitscore, minimum difference between first and next bitscore,
	 * taxonomic type to show, and chart type to display.
	 */
	def analyseForm = {
		return [dataset: params.dataset, params: params]
	}

	/*------analyse--------
	 * Gets criteria from analyseForm, constructs and executes queries (these have many steps)
	 * and draws the results in tables and charts.
	 */
	def analyse = {
		//user input validity checks
		if(!params.dataset) {
			flash.message = "No dataset supplied!"
			redirect(action:'analyseForm', params: params)
			return
		}

		if(!params.sites) {
			flash.message = "Please enter site(s)."
			redirect(action:'analyseForm', params: params)
			return
		}
		if(!params.threshold.isNumber()) {
			flash.message = "Threshold must be a number."
			redirect(action:'analyseForm', params: params)
			return

		}
		if(!params.cutoff.isNumber()) {
			flash.message = "Cutoff must be a number."
			redirect(action:'analyseForm', params: params)
			return
		}
		if(!params.minBitScore.isNumber()) {
			flash.message = "Minimum bitscore must be a number."
			redirect(action:'analyseForm', params: params)
			return
		}
		if(!params.minBitScoreStep.isNumber()) {
			flash.message = "Minimum bitscore step must be a number."
			redirect(action:'analyseForm', params: params)
			return
		}
		//end of validity checks

		//take user's input
		def type = params.type
		def cutoff = params.cutoff
		def minBitScore = params.minBitScore as Integer
		def minBitScoreStep = params.minBitScoreStep as Integer
		String taxonomyURL = grailsApplication.config.taxonomyURL



		//make the sites string into a list and trim off excess whitespace
		def sites = params.sites.split(",")
		for (i in 0..<sites.size()) {
			sites[i] = sites[i].trim()
		}

		//call analyseService to process the input and then retrieve output and pass to the view
		analyseService.processCriteria(params.dataset, sites as List, params.threshold, params.keyPhrase, cutoff, minBitScore, minBitScoreStep, type)
		def reps = analyseService.getReps()
		def data = analyseService.getData()
		def tableData = analyseService.getTableData(reps)


		//format type for a neat table header
		if (type =~ /tax.*/) {
			type = type[3..-1]
		}
		type = type.capitalize()

		return [reps: reps, tableData: tableData, type: type, data: data, sites:sites, chart: params.chart, params: params, dataset:params.dataset, taxonomyURL:taxonomyURL]
	}


	/*--------search-------
	 * Takes a user's query and passes to results action.
	 */
	def search = {
		return [dataset:params.dataset]
	}

	def descend = {
		params.keyPhrase = params.id
		def properties = ['phylum', 'taxClass', 'taxOrder', 'family', 'genus', 'species']
		def depth = 0
		properties.eachWithIndex { prop, i -> if(prop == params.type) depth = i }
		if (depth != 5) depth++
		params.type = properties[depth]
		redirect(action:'analyse', params:params)
	}


	/*--------results----------
	 * Creates a paginated list that only displays data with the specified sample site and cutoff.
	 */
	def results = {
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

		return [motuInstanceList: motus, motuInstanceTotal: total, params:params, dataset:params.dataset]
	}


	/*-------showTable--------
	 * Displays a table of blast hits for a particular MOTU.
	 * Each row is a hit, columns hold information about accession number,  
	 * bitscore, taxid, and taxonomic data.
	 */
	def showTable = {
		String taxidURL = grailsApplication.config.taxidURL
		String taxonomyURL = grailsApplication.config.taxonomyURL
		grailsApplication.config.databasePath
		def motuInstance = Motu.get(params.id)

		if (!motuInstance) {
			flash.listMessage = "MOTU not found."
			redirect(action: "list", params: [dataset: params.dataset])
		}
		else {
			//if (!params.max) params.max = 10
			//if (!params.offset) params.offset = 0
			if (!params.sort) params.sort = "bitScore"
			if (!params.order) params.order = "desc"

			//display an empty list for a motu with no hits
			if (!motuInstance.hits*.id.size()) {
				return [motuInstance: motuInstance, hits: [], dataset:params.dataset]
			}

			//for some reason a custon withCriteria query needs to be supplied for sorting to work
			def hitS = BlastHit.withCriteria {
				//maxResults(params.max?.toInteger())
				//firstResult(params.offset?.toInteger())
				'in'("id", motuInstance.hits*.id)
				order(params.sort, params.order)
			}
			return [motuInstance: motuInstance, hits: hitS, dataset:params.dataset, taxidURL:taxidURL, taxonomyURL:taxonomyURL]
		}
	}


	/*--------list----------
	 * Creates a pagianted list of all MOTUs within one dataset.
	 */
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


	/*------switchDataset--------
	 * Stores current view and redirects user to a page where the dataset can be switched.
	 */
	def switchDataset = {
		return [prevAction: params.prevAction, prevController: params.prevController, dataset: params.dataset]
	}


	/*--------executeSwitch---------
	 * Redirects user to the previously used view but with their specified dataset.
	 */
	def executeSwitch = {
		redirect(action: params.prevAction, controller: params.prevController, params: [dataset: params.newDataset])
	}


}
