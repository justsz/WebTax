class BootStrap {
	
	def executorService
	
    def init = { servletContext ->
		executorService.executor.setMaximumPoolSize(1)
		executorService.executor.setCorePoolSize(1)
		
		//def taxdumpPath = '/home/justs/workspace/WebTax/databases/NCBIdump'
		//def start1 = System.currentTimeMillis()	//benchmark
		//def parser = new webtax.FastaParser()
		//MotuController.taxer = new webtax.TaxIdProcessor(taxdumpPath)
		//println System.currentTimeMillis() - start1
    }
    def destroy = {
    }
}
