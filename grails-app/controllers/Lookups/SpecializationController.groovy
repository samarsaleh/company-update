package Lookups

import static org.springframework.http.HttpStatus.*
import grails.transaction.Transactional

@Transactional(readOnly = true)
class SpecializationController {

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond Specialization.list(params), model:[specializationCount: Specialization.count()]
    }

    def show(Specialization specialization) {
        respond specialization
    }

    def create() {
        respond new Specialization(params)
    }

    @Transactional
    def save(Specialization specialization) {
        if (specialization == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (specialization.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond specialization.errors, view:'create'
            return
        }

        specialization.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.created.message', args: [message(code: 'specialization.label', default: 'Specialization'), specialization.id])
                redirect specialization
            }
            '*' { respond specialization, [status: CREATED] }
        }
    }

    def edit(Specialization specialization) {
        respond specialization
    }

    @Transactional
    def update(Specialization specialization) {
        if (specialization == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (specialization.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond specialization.errors, view:'edit'
            return
        }

        specialization.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'specialization.label', default: 'Specialization'), specialization.id])
                redirect specialization
            }
            '*'{ respond specialization, [status: OK] }
        }
    }

    @Transactional
    def delete(Specialization specialization) {

        if (specialization == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        specialization.delete flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'specialization.label', default: 'Specialization'), specialization.id])
                redirect action:"index", method:"GET"
            }
            '*'{ render status: NO_CONTENT }
        }
    }

    protected void notFound() {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'specialization.label', default: 'Specialization'), params.id])
                redirect action: "index", method: "GET"
            }
            '*'{ render status: NOT_FOUND }
        }
    }
}
