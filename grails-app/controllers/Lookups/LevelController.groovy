package Lookups

import static org.springframework.http.HttpStatus.*
import grails.transaction.Transactional

@Transactional(readOnly = true)
class LevelController {

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond Level.list(params), model:[levelCount: Level.count()]
    }

    def show(Level level) {
        respond level
    }

    def create() {
        respond new Level(params)
    }

    @Transactional
    def save(Level level) {
        if (level == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (level.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond level.errors, view:'create'
            return
        }

        level.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.created.message', args: [message(code: 'level.label', default: 'Level'), level.id])
                redirect level
            }
            '*' { respond level, [status: CREATED] }
        }
    }

    def edit(Level level) {
        respond level
    }

    @Transactional
    def update(Level level) {
        if (level == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (level.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond level.errors, view:'edit'
            return
        }

        level.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'level.label', default: 'Level'), level.id])
                redirect level
            }
            '*'{ respond level, [status: OK] }
        }
    }

    @Transactional
    def delete(Level level) {

        if (level == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        level.delete flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'level.label', default: 'Level'), level.id])
                redirect action:"index", method:"GET"
            }
            '*'{ render status: NO_CONTENT }
        }
    }

    protected void notFound() {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'level.label', default: 'Level'), params.id])
                redirect action: "index", method: "GET"
            }
            '*'{ render status: NOT_FOUND }
        }
    }
}
