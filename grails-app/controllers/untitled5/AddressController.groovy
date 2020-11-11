package untitled5

import org.hibernate.Criteria
import org.hibernate.Query
import org.hibernate.Session
import org.hibernate.criterion.Restrictions

import static org.springframework.http.HttpStatus.*
import grails.transaction.Transactional

@Transactional(readOnly = true)
class AddressController {

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)

        def c = Address.createCriteria()

        def result = c.list {
            println params["btn1"]
            println params["btn2"]

            if (params["btn1"])
               eq("type", params["btn1"])
            else if(params["btn2"])
                eq("type", params["btn2"])
// //println "${result[0]}"
////            respond Address.list(params), model: [addressCount: Address.count()]
//
        }
            respond result, model: [addressCount: Address.count()]



    }

    def show(Address address) {
        respond address
    }

    def create() {
        respond new Address(params)
    }

    @Transactional
    def save(Address address) {
        if (address == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (address.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond address.errors, view:'create'
            return
        }

        address.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.created.message', args: [message(code: 'address.label', default: 'Address'), address.id])
                redirect address
            }
            '*' { respond address, [status: CREATED] }
        }
    }

    def edit(Address address) {
        respond address
    }

    @Transactional
    def update(Address address) {
        if (address == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (address.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond address.errors, view:'edit'
            return
        }

        address.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'address.label', default: 'Address'), address.id])
                redirect address
            }
            '*'{ respond address, [status: OK] }
        }
    }

    @Transactional
    def delete(Address address) {

        if (address == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        address.delete flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'address.label', default: 'Address'), address.id])
                redirect action:"index", method:"GET"
            }
            '*'{ render status: NO_CONTENT }
        }
    }

    protected void notFound() {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'address.label', default: 'Address'), params.id])
                redirect action: "index", method: "GET"
            }
            '*'{ render status: NOT_FOUND }
        }
    }
}
