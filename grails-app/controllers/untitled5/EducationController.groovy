package untitled5

import static org.springframework.http.HttpStatus.*
import grails.transaction.Transactional

@Transactional(readOnly = true)
class EducationController {

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond Education.list(params), model:[educationCount: Education.count()]
    }

    def show(Education education) {
        respond education

    }

    def create() {
        respond new Education(params)
        ///employee.id: 3


    }

    @Transactional
    def save(Education education) {
        if (education == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (education.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond education.errors, view:'create'
            return
        }

        education.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.created.message', args: [message(code: 'education.label', default: 'Education'), education.id])
                redirect education
            }
            '*' { respond education, [status: CREATED] }
        }
    }

    def edit(Education education) {
        respond education
    }

    @Transactional
    def update(Education education) {
        if (education == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (education.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond education.errors, view:'edit'
            return
        }

        education.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'education.label', default: 'Education'), education.id])
                redirect education
            }
            '*'{ respond education, [status: OK] }
        }
    }

    @Transactional
    def delete(Education education) {

        if (education == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        education.delete flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'education.label', default: 'Education'), education.id])
                redirect action:"index", method:"GET"
            }
            '*'{ render status: NO_CONTENT }
        }
    }

    protected void notFound() {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'education.label', default: 'Education'), params.id])
                redirect action: "index", method: "GET"
            }
            '*'{ render status: NOT_FOUND }
        }
    }
}
