package Lookups

import static org.springframework.http.HttpStatus.*
import grails.transaction.Transactional

@Transactional(readOnly = true)
class HighSchoolController {

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond HighSchool.list(params), model:[highSchoolCount: HighSchool.count()]
    }

    def show(HighSchool highSchool) {
        respond highSchool
    }

    def create() {
        respond new HighSchool(params)
    }

    @Transactional
    def save(HighSchool highSchool) {
        if (highSchool == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (highSchool.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond highSchool.errors, view:'create'
            return
        }

        highSchool.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.created.message', args: [message(code: 'highSchool.label', default: 'HighSchool'), highSchool.id])
                redirect highSchool
            }
            '*' { respond highSchool, [status: CREATED] }
        }
    }

    def edit(HighSchool highSchool) {
        respond highSchool
    }

    @Transactional
    def update(HighSchool highSchool) {
        if (highSchool == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (highSchool.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond highSchool.errors, view:'edit'
            return
        }

        highSchool.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'highSchool.label', default: 'HighSchool'), highSchool.id])
                redirect highSchool
            }
            '*'{ respond highSchool, [status: OK] }
        }
    }

    @Transactional
    def delete(HighSchool highSchool) {

        if (highSchool == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        highSchool.delete flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'highSchool.label', default: 'HighSchool'), highSchool.id])
                redirect action:"index", method:"GET"
            }
            '*'{ render status: NO_CONTENT }
        }
    }

    protected void notFound() {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'highSchool.label', default: 'HighSchool'), params.id])
                redirect action: "index", method: "GET"
            }
            '*'{ render status: NOT_FOUND }
        }
    }
}
