//import grails.plugin.executor.PersistenceContextExecutorWrapper
import java.util.concurrent.Executors



 // Place your Spring DSL code here
 beans = {

//    executorService(PersistenceContextExecutorWrapper) { bean ->
//        bean.destroyMethod = 'destroy'
//        sessionFactory = ref("sessionFactory")
//        executor = Executors.newFixedThreadPool(1)
//    }
	 
	 executorService(  grails.plugin.executor.PersistenceContextExecutorWrapper ) { bean->
		 bean.destroyMethod = 'destroy' //keep this destroy method so it can try and clean up nicely
		 persistenceInterceptor = ref("persistenceInterceptor")
		 //this can be whatever from Executors (don't write your own and pre-optimize)
		 executor = Executors.newFixedThreadPool(1)
	 }

 }