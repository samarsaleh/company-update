package untitled5

import static org.springframework.http.HttpStatus.*
import grails.transaction.Transactional

@Transactional(readOnly = true)
class EmpPositionController {

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond EmpPosition.list(params), model:[empPositionCount: EmpPosition.count()]
    }

    def show(EmpPosition empPosition) {
        respond empPosition
    }

    def create() {
        respond new EmpPosition(params)
    }

    @Transactional
    def save(EmpPosition empPosition) {
        if (empPosition == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (empPosition.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond empPosition.errors, view:'create'
            return
        }

        empPosition.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.created.message', args: [message(code: 'empPosition.label', default: 'EmpPosition'), empPosition.id])
                redirect empPosition
            }
            '*' { respond empPosition, [status: CREATED] }
        }
    }

    def edit(EmpPosition empPosition) {
        respond empPosition
    }

    @Transactional
    def update(EmpPosition empPosition) {
        if (empPosition == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (empPosition.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond empPosition.errors, view:'edit'
            return
        }

        empPosition.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'empPosition.label', default: 'EmpPosition'), empPosition.id])
                redirect empPosition
            }
            '*'{ respond empPosition, [status: OK] }
        }
    }

    @Transactional
    def delete(EmpPosition empPosition) {

        if (empPosition == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        empPosition.delete flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'empPosition.label', default: 'EmpPosition'), empPosition.id])
                redirect action:"index", method:"GET"
            }
            '*'{ render status: NO_CONTENT }
        }
    }

    protected void notFound() {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'empPosition.label', default: 'EmpPosition'), params.id])
                redirect action: "index", method: "GET"
            }
            '*'{ render status: NOT_FOUND }
        }
    }
}
