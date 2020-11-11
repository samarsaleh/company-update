package untitled5

import static org.springframework.http.HttpStatus.*
import grails.transaction.Transactional

@Transactional(readOnly = true)
class EmployeeSkillController {

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond EmployeeSkill.list(params), model:[employeeSkillCount: EmployeeSkill.count()]
    }

    def show(EmployeeSkill employeeSkill) {
        respond employeeSkill
    }

    def create() {
        respond new EmployeeSkill(params)
    }

    @Transactional
    def save(EmployeeSkill employeeSkill) {
        if (employeeSkill == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (employeeSkill.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond employeeSkill.errors, view:'create'
            return
        }

        employeeSkill.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.created.message', args: [message(code: 'employeeSkill.label', default: 'EmployeeSkill'), employeeSkill.id])
                redirect employeeSkill
            }
            '*' { respond employeeSkill, [status: CREATED] }
        }
    }

    def edit(EmployeeSkill employeeSkill) {
        respond employeeSkill
    }

    @Transactional
    def update(EmployeeSkill employeeSkill) {
        if (employeeSkill == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (employeeSkill.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond employeeSkill.errors, view:'edit'
            return
        }

        employeeSkill.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'employeeSkill.label', default: 'EmployeeSkill'), employeeSkill.id])
                redirect employeeSkill
            }
            '*'{ respond employeeSkill, [status: OK] }
        }
    }

    @Transactional
    def delete(EmployeeSkill employeeSkill) {

        if (employeeSkill == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        employeeSkill.delete flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'employeeSkill.label', default: 'EmployeeSkill'), employeeSkill.id])
                redirect action:"index", method:"GET"
            }
            '*'{ render status: NO_CONTENT }
        }
    }

    protected void notFound() {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'employeeSkill.label', default: 'EmployeeSkill'), params.id])
                redirect action: "index", method: "GET"
            }
            '*'{ render status: NOT_FOUND }
        }
    }
}
