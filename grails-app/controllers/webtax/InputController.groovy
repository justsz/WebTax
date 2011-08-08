package webtax

import java.util.UUID


class InputController {
	def inputParserService

	//allowedMethods ?

	def index = {
		redirect(action: "add", params: params)
	}

	def add = {
		return [dataset: params.dataset]	//pretty sure that the if is not needed
	}

	def uploadFiles = {
		def dataset = params.dataset
		def ant = new AntBuilder()
		def destination = new File("${grailsApplication.config.userInputPath}${dataset}")

		withForm {
			def up = request.getFile("myFile")
			UUID uuid = UUID.randomUUID()
			def file = new File("${grailsApplication.config.userInputPath}temp${uuid}")
			up.transferTo(file)
			//def file = up.getFileItem().getStoreLocation()
		
			ant.unzip(src: file, dest: destination, overwrite:"true")
			file.delete()
			new File(destination, '__MACOSX').delete()	//deletes the thingy mac throws in its zip files
		}

		redirect(action:"review", params: [destination: destination, dataset: dataset])

	}

	//Refactoring oppurtunity: pass just the dataset and rebuild destination from that.

	def review = {
		def dir = new File(params.destination)
		def files = []
		dir.eachFile{ files.add(it.getName()) }

		def databaseFile = new File("${grailsApplication.config.databasePath}databases.txt")
		def dbs = []
		databaseFile.eachLine { dbs.add(it) }

		return [files: files, destination: params.destination, dataset: params.dataset, dbs:dbs]
	}

	def deleteFiles = {
		params.each { if(it.value == 'on') new File(params.destination, it.key).delete() }
		redirect (action:'review', params: [destination: params.destination, dataset: params.dataset])
	}

	def blast = {
		def dataset = params.dataset
		def database = params.database
		def destination = params.destination
		new Dataset(name: dataset).save(flush: true)	//test if dataset name is unique

		def dir = new File(params.destination)
		def files = []
		dir.eachFile{ files.add(it) }

		def jobIds = []

		files.each {
			def fileName = it.getName()
			def job = new Job(progress: 0, name: fileName).save(flush:true)
			jobIds.add(job.id)
			runAsync {
				inputParserService.parseAndAdd(job.id, dataset, database, destination, fileName)	//Refactioring opportunity: just pass a file.
			}
		}

		redirect(action:'statuses', params:[jobIds: jobIds, dataset: dataset])
	}

	def statuses = {
		return [jobIds: params.jobIds, dataset: params.dataset]
	}
}
