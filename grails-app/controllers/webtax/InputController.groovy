/*
 *-------------------------------InputController---------------------------------
 * This controller handles user uploaded .zips with text files of MOTUs in them.
 * The zip is extracted into a directory, the user can review what files there are
 * and delete or add more. After they are satisfied, a blast database can be selected
 * from a list and files sent to megablast. An auto-refreshing status screen keeps the
 * user informed on progress.
 *---------------------------------------------------------------------------------
 * Common things: the dataset String is always passed around so that it would be unique
 * to the browser window and links could be easily shared. Also, the dataset's save path
 * is passed around, though it could be recreated form just by knowing the dataset..
 */

package webtax

import java.util.UUID


class InputController {
	def inputParserService	//inject the service that does blasting an annotation
	def ant = new AntBuilder()	//AntBuilder for unzipping and folder deletion

	def index = {
		redirect(action: "add", params: params)
	}

	/* ------add------
	 * Add takes a .zip file (.jar and .war are also accepted by ant) and dataset.
	 * The zip is sent to uploadFiles and the dataset set to the current dataset.
	 */
	def add = {
		return [dataset: params.dataset]
	}


	/*-------uploadFiles--------
	 * Takes a zip file from add, saves it on disk and then uses the one on 
	 * disk to unzip. This is done because Grais uses CommonsMultipartFile
	 * and the safest and easiest way to convert that to a plain File is by 
	 * using transferTo to get it on disk. Ant cannot work with the Grails 
	 * interpretation of File.
	 *
	 *
	 */	
	def uploadFiles = {
		def dataset = params.dataset

		//save path created by appending the dataset name to the save location defined in the config file
		def destination = new File("${grailsApplication.config.userInputPath}${dataset}")

		//tokenized input so that clicking on submit twice doesn't crash the app
		withForm {
			if (!dataset) {
				flash.message = "Please enter a dataset to work within."
				redirect (action:'add')
				return
			}

			//download file that the user has submitted
			def up = request.getFile("myFile")
			if(up.empty) {
				flash.message = "Uploaded file was empty."
				redirect (action:'add', params:[dataset:dataset])
				return
			}

			//create unique temporary file
			UUID uuid = UUID.randomUUID()
			def file = new File("${grailsApplication.config.userInputPath}temp${uuid}")

			//and save user's upload there
			up.transferTo(file)

			//use AntBuilder to unzip the file and then delete the temporary file created above
			try {
				ant.unzip(src: file, dest: destination, overwrite:"true")
			} catch(Exception e) {
				flash.message = "Unaccepted file format. Please upload a .zip file."
				file.delete()
				redirect (action:'add', params:[dataset:dataset])
				return
			}

			file.delete()
			//new File(destination, '__MACOSX').delete()	//deletes the thingy mac throws in its zip files
			redirect(action:"review", params: [destination: destination, dataset: dataset])
		}
	}

	/*-------review-------
	 * Displays a list of files that the user uploaded in the zip.
	 * Files can be deleted or more added. Deletion is done by the blast action,
	 * addition is just a redirect to the add action. The user can then select the database to 
	 * blast against.
	 */
	def review = {
		//create a list of all files in the dataset directory
		def dir = new File(params.destination)
		def files = []
		dir.eachFile{ files.add(it.getName()) }

		//create a list of available databases from the config-like databases.txt
		def databaseFile = new File("${grailsApplication.config.databasePath}databases.txt")
		def dbs = []
		def dbDescriptions =[]
		
		databaseFile.eachLine {
			def line = it.split(', ')
			dbs.add(line[0])
			dbDescriptions.add(line[1])
		}

		return [files: files, destination: params.destination, dataset: params.dataset, dbs:dbs, dbDescriptions:dbDescriptions]
	}


	/*-------blast--------
	 * Takes the user's uploaded files, creates a Job object for each and 
	 * sends the files off to inputParserService to be megablasted and annotated.
	 * Jobs are done asyncronously (but still with only 1 job at a time) so that the
	 * progresses could be displayed. Grails won't let you go to another view while the
	 * current action is still running, so runAsync (from executor plugin) spins off a new
	 * thread for each job.
	 */
	def blast = {
		//for some reason inputParserService doesn't take values from params so I have
		//created a variable for the needed param variables
		
		
		def dataset = params.dataset
		def database = params.database
		def destination = params.destination

		//test if dataset name is unique
		new Dataset(name: dataset).save(flush: true)

		//create list of files to blast from upload location
		def files = []
		params.each { if(it.value == 'on') files.add(it.key) }
		
		//do cleanup of unselected files and folders
		def dir = new File(params.destination)
		def filesToDelete = []
		dir.eachFile{ if(!files.contains(it.getName())) filesToDelete.add(it) }
		filesToDelete.each { file ->
			if (file.isFile()) file.delete()
			else ant.delete(dir: "${params.destination}/${file.getName()}")
		}

		if (files.size() == 0) {
			flash.message = "No files to annotate!"
			redirect (action:'review', params: params)
			return
		}

		//a list of all the jobs begun in this batch, these will be used by statuses to display the correct progresses
		def jobIds = []

		files.each {
			def fileName = it

			//job is saved and persisted immediately so that the statuses page can access the progress value
			def job = new Job(progress: 0, name: fileName).save(flush:true)
			jobIds.add(job.id)

			//spin off a thread for each file
			runAsync {
				inputParserService.parseAndAdd(job.id, dataset, database, destination, fileName)
			}
		}

		redirect(action:'statuses', params:[jobIds: jobIds, dataset: dataset])
	}


	/*-------statuses---------
	 * Displays a list of running jobs and their progresses.
	 * Page reloads every 10 seconds to show changes.
	 */
	def statuses = {
		return [jobIds: params.jobIds, dataset: params.dataset]
	}
}
