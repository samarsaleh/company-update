package Lookups

import static org.springframework.http.HttpStatus.*
import grails.transaction.Transactional

@Transactional(readOnly = true)
class UniversityController {

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond University.list(params), model:[universityCount: University.count()]
    }

    def show(University university) {
        respond university
    }

    def create() {
        respond new University(params)
    }

    @Transactional
    def save(University university) {
        if (university == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (university.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond university.errors, view:'create'
            return
        }

        university.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.created.message', args: [message(code: 'university.label', default: 'University'), university.id])
                redirect university
            }
            '*' { respond university, [status: CREATED] }
        }
    }

    def edit(University university) {
        respond university
    }

    @Transactional
    def update(University university) {
        if (university == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (university.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond university.errors, view:'edit'
            return
        }

        university.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'university.label', default: 'University'), university.id])
                redirect university
            }
            '*'{ respond university, [status: OK] }
        }
    }

    @Transactional
    def delete(University university) {

        if (university == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        university.delete flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'university.label', default: 'University'), university.id])
                redirect action:"index", method:"GET"
            }
            '*'{ render status: NO_CONTENT }
        }
    }

    protected void notFound() {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'university.label', default: 'University'), params.id])
                redirect action: "index", method: "GET"
            }
            '*'{ render status: NOT_FOUND }
        }
    }
}
