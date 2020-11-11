package untitled5

import grails.converters.JSON
import groovy.sql.Sql
import org.hibernate.Criteria
import org.hibernate.Session
import org.hibernate.cfg.Configuration
import org.hibernate.criterion.Restrictions



import static org.springframework.http.HttpStatus.*
import grails.transaction.Transactional

@Transactional(readOnly = true)
class EmployeeController {

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max) {

        params.max = Math.min(max ?: 10, 100)
        def c = Employee.createCriteria()
        def results = c.list {

            if( params["txtSearch"])
            {
                or{
                    like("firstName",  params["txtSearch"])
                    like("lastName",  params["txtSearch"])
                    like("workID",  params["txtSearch"])
                    like("idNumber",  params["txtSearch"])

                }
            }
        }

        respond  results , model:[employeeCount: Employee.count()]

    }
    /*   params.max = Math.min(max ?: 10, 100)

        // criteria
       *//* def c = Employee.createCriteria()
        def results = c.list {

            like("firstName", "sanaa")
            and {
                eq("gender", "Female")
            }
            //  maxResults(10)
         //   order("idNumber","desc")
    //        println "samaar"
        }
        println "${results[0]}"*//*
        // i need names
        //showing new table with new order
        if(params.textSearch){
            def textSearch =params.txtSearch
            println("inserted text -> "+textSearch)

            def newList = Employee.createCriteria().list (params) {
                println("if")
                ilike("lastName", textSearch)
            }
            println("text -> "+newList)
        }
        respond Employee.list(params), model: [employeeCount: Employee.count()]*/
// def q = Employee.executeQuery("select firstName from Employee where gender ='Female' ")
//    def q = Employee.executeQuery("SELECT COUNT(*) FROM Employee")
// def q = Employee.executeQuery("from Employee e INNER JOIN e.skillname sn where sn.main.note ='low'")
//    println(q)


    def show(Employee employee) {

        respond employee
        println("show"+params)
        println(employee.firstName)


    }

    def create() {
        respond new Employee(params)
            }

    @Transactional
    def save(Employee employee) {

        println(params)

        if (employee == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (employee.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond employee.errors, view:'create'
            return
        }

        employee.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.created.message', args: [message(code: 'employee.label', default: 'Employee'), employee.id])
                redirect employee
            }
            '*' { respond employee, [status: CREATED] }
        }
    }

    def edit(Employee employee) {
        respond employee
    }

    @Transactional
    def update(Employee employee) {
        if (employee == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (employee.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond employee.errors, view:'edit'
            return
        }

        employee.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'employee.label', default: 'Employee'), employee.id])
                redirect employee
            }
            '*'{ respond employee, [status: OK] }
        }
    }

    @Transactional
    def delete(Employee employee) {

        if (employee == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        employee.delete flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'employee.label', default: 'Employee'), employee.id])
                redirect action:"index", method:"GET"
            }
            '*'{ render status: NO_CONTENT }
        }
    }

    protected void notFound() {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'employee.label', default: 'Employee'), params.id])
                redirect action: "index", method: "GET"
            }
            '*'{ render status: NOT_FOUND }
        }
    }
}
