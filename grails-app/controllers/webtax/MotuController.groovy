package webtax


import org.springframework.context.ApplicationContext
import org.springframework.web.context.support.WebApplicationContextUtils

class MotuController {

	def inputParserService
	def csvService
	def printableService

	static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

	def index = {

		redirect(action: "list", params: params)
	}




	def upload = {
		def datasetName = params.datasetName
		def dataset = new Dataset(name: datasetName).save(flush: true)



		withForm {


			def f = request.getFile('myFile')
			if(!f.empty) {

				f.transferTo( new File('./userUpload/input.fasta') )

				def job = new Job(progress: 0).save(flush:true)

				runAsync {
					inputParserService.parseAndAdd(job.id, datasetName)
				}

				redirect(action:'status', params:[jobId:job.id, dataset: datasetName])


			} else {
				flash.message = 'file cannot be empty'

				redirect(action:'list')
			}
		}


	}

	def repForm = {
		return [dataset: params.dataset]
	}

	def printable = {
		//println params.datum
		//println params.datum.getClass()
		def data = printableService.reFormat(params.datum as String)
		return [datum: data, site: params.site, chartType: params.chartType]
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
			
			
			def hits = motus.collect {it.hits.max {it.bitScore}}

			
			
			
			
			

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
			//println totalHits[counter]
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



	def status = {
		return [jobId:params.jobId, dataset: params.dataset]
	}

	def search = {
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
	
	def downloadTableView = {		
		def file = csvService.makeTableViewCSV(params.hits)
		response.setContentType( "application-xdownload")
		response.setHeader("Content-Disposition", "attachment; filename=${params.motuInstance}.csv")
	    //response.getOutputStream() << new ByteArrayInputStream( out )
		response.outputStream << file.newInputStream()
	}
	
	def downloadListView = {
		println params.dataset
		def file = csvService.makeListViewCSV(params.dataset)
		response.setContentType( "application-xdownload")
		response.setHeader("Content-Disposition", "attachment; filename=${params.dataset}.csv")
		response.outputStream << file.newInputStream()
		
	}
	
	def downloadRepresentView = {
		def file = csvService.makeRepresentViewCSV(params.data, params.sites, params.type)
		response.setContentType( "application-xdownload")
		response.setHeader("Content-Disposition", "attachment; filename=temporaryTitle.csv")
		response.outputStream << file.newInputStream()
	}





	def list = {
		params.max = Math.min(params.max ? params.int('max') : 10, 100)
		return [motuInstanceList: Motu.list(params), motuInstanceTotal: Motu.count(), dataset: params.dataset]

	}

	def create = {
	}

	def save = {
		def motuInstance = new Motu(params)
		if (motuInstance.save(flush: true)) {
			flash.message = "${message(code: 'default.created.message', args: [message(code: 'motu.label', default: 'Motu'), motuInstance.id])}"

			redirect(action: "show", id: motuInstance.id)
		}
		else {
			render(view: "create", model: [motuInstance: motuInstance])
		}
	}

	def show = {
		def motuInstance = Motu.get(params.id)
		if (!motuInstance) {
			flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'motu.label', default: 'Motu'), params.id])}"

			redirect(action: "list")
		}
		else {
			[motuInstance: motuInstance]
		}
	}

	def edit = {
		def motuInstance = Motu.get(params.id)
		if (!motuInstance) {
			flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'motu.label', default: 'Motu'), params.id])}"

			redirect(action: "list")
		}
		else {
			return [motuInstance: motuInstance]
		}
	}

	def update = {
		def motuInstance = Motu.get(params.id)
		if (motuInstance) {
			if (params.version) {
				def version = params.version.toLong()
				if (motuInstance.version > version) {

					motuInstance.errors.rejectValue("version", "default.optimistic.locking.failure", [message(code: 'motu.label', default: 'Motu')] as Object[], "Another user has updated this Motu while you were editing")
					render(view: "edit", model: [motuInstance: motuInstance])
					return
				}
			}
			motuInstance.properties = params
			if (!motuInstance.hasErrors() && motuInstance.save(flush: true)) {
				flash.message = "${message(code: 'default.updated.message', args: [message(code: 'motu.label', default: 'Motu'), motuInstance.id])}"
				redirect(action: "show", id: motuInstance.id)
			}
			else {
				render(view: "edit", model: [motuInstance: motuInstance])
			}
		}
		else {
			flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'motu.label', default: 'Motu'), params.id])}"
			redirect(action: "list")
		}
	}

	def delete = {
		def motuInstance = Motu.get(params.id)
		if (motuInstance) {
			try {
				motuInstance.delete(flush: true)
				flash.message = "${message(code: 'default.deleted.message', args: [message(code: 'motu.label', default: 'Motu'), params.id])}"
				redirect(action: "list")
			}
			catch (org.springframework.dao.DataIntegrityViolationException e) {
				flash.message = "${message(code: 'default.not.deleted.message', args: [message(code: 'motu.label', default: 'Motu'), params.id])}"
				redirect(action: "show", id: params.id)
			}
		}
		else {
			flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'motu.label', default: 'Motu'), params.id])}"
			redirect(action: "list")
		}
	}
}
