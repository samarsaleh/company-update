package Lookups

import static org.springframework.http.HttpStatus.*
import grails.transaction.Transactional

@Transactional(readOnly = true)
class TownController {

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond Town.list(params), model:[townCount: Town.count()]
    }

    def show(Town town) {
        respond town
    }

    def create() {
        respond new Town(params)
    }

    @Transactional
    def save(Town town) {
        if (town == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (town.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond town.errors, view:'create'
            return
        }

        town.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.created.message', args: [message(code: 'town.label', default: 'Town'), town.id])
                redirect town
            }
            '*' { respond town, [status: CREATED] }
        }
    }

    def edit(Town town) {
        respond town
    }

    @Transactional
    def update(Town town) {
        if (town == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (town.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond town.errors, view:'edit'
            return
        }

        town.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'town.label', default: 'Town'), town.id])
                redirect town
            }
            '*'{ respond town, [status: OK] }
        }
    }

    @Transactional
    def delete(Town town) {

        if (town == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        town.delete flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'town.label', default: 'Town'), town.id])
                redirect action:"index", method:"GET"
            }
            '*'{ render status: NO_CONTENT }
        }
    }

    protected void notFound() {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'town.label', default: 'Town'), params.id])
                redirect action: "index", method: "GET"
            }
            '*'{ render status: NOT_FOUND }
        }
    }
}
