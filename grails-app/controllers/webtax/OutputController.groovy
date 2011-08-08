package webtax

class OutputController {
	def exportDataService
	def printableService

    def index = { }
	
	def downloadTableView = {
		def file = exportDataService.makeTableView(params.hits, params.separator)
		response.setContentType( "application-xdownload")
		response.setHeader("Content-Disposition", "attachment; filename=${params.motuInstance}.${params.separator}")
		//response.getOutputStream() << new ByteArrayInputStream( out )
		response.outputStream << file.newInputStream()
	}

	def downloadListView = {
		def file = exportDataService.makeListView(params.dataset, params.separator)
		response.setContentType( "application-xdownload")
		response.setHeader("Content-Disposition", "attachment; filename=${params.dataset}.${params.separator}")
		response.outputStream << file.newInputStream()

	}

	def downloadRepresentView = {
		def file = exportDataService.makeRepresentView(params.data, params.sites, params.type, params.separator)
		response.setContentType( "application-xdownload")
		response.setHeader("Content-Disposition", "attachment; filename=${params.sites}${params.type}.${params.separator}")
		response.outputStream << file.newInputStream()
	}
	
	def downloadSearchView = {
		println params.motus
		def file = exportDataService.makeSearchView(params.motus, params.separator)
		response.setContentType( "application-xdownload")
		response.setHeader("Content-Disposition", "attachment; filename=${params.dataset}.${params.separator}")
		response.outputStream << file.newInputStream()
	}
	
	def printable = {
		//println params.datum
		//println params.datum.getClass()
		def data = printableService.reFormat(params.datum as String)
		return [datum: data, site: params.site, chartType: params.chartType, dataset: params.dataset]
	}
}
