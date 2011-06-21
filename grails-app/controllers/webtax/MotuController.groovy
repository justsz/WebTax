package webtax

class MotuController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index = {
        redirect(action: "list", params: params)
    }
	
	
	//Handles a file upload
	//Necessary html is stuck into create.gsp in an ugly fashion 
		//!!! not sure what happens if user uploads a random file
	def upload = {
		def f = request.getFile('myFile')
		if(!f.empty) {
		  f.transferTo( new File('./userUpload/input.fasta') )
		  response.sendError(200,'Done');		 
		  
		  FastaParser parser = new FastaParser()
		  parser.parseAndAdd()
		}
		else {
		   flash.message = 'file cannot be empty'
		   redirect(action:'uploadForm')
		}
		//redirect(action: "list") //redirect is still broken
	}
	
	

    def list = {
        params.max = Math.min(params.max ? params.int('max') : 10, 100)
        [motuInstanceList: Motu.list(params), motuInstanceTotal: Motu.count()]
    }

    def create = {
        def motuInstance = new Motu()
        motuInstance.properties = params
        return [motuInstance: motuInstance]
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
