package Lookups

import static org.springframework.http.HttpStatus.*
import grails.transaction.Transactional

@Transactional(readOnly = true)
class DegreeController {

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond Degree.list(params), model:[degreeCount: Degree.count()]
    }

    def show(Degree degree) {
        respond degree
    }

    def create() {
        respond new Degree(params)
    }

    @Transactional
    def save(Degree degree) {
        if (degree == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (degree.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond degree.errors, view:'create'
            return
        }

        degree.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.created.message', args: [message(code: 'degree.label', default: 'Degree'), degree.id])
                redirect degree
            }
            '*' { respond degree, [status: CREATED] }
        }
    }

    def edit(Degree degree) {
        respond degree
    }

    @Transactional
    def update(Degree degree) {
        if (degree == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (degree.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond degree.errors, view:'edit'
            return
        }

        degree.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'degree.label', default: 'Degree'), degree.id])
                redirect degree
            }
            '*'{ respond degree, [status: OK] }
        }
    }

    @Transactional
    def delete(Degree degree) {

        if (degree == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        degree.delete flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'degree.label', default: 'Degree'), degree.id])
                redirect action:"index", method:"GET"
            }
            '*'{ render status: NO_CONTENT }
        }
    }

    protected void notFound() {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'degree.label', default: 'Degree'), params.id])
                redirect action: "index", method: "GET"
            }
            '*'{ render status: NOT_FOUND }
        }
    }
}
