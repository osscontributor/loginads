package demo



import static org.springframework.http.HttpStatus.*
import grails.transaction.Transactional

@Transactional(readOnly = true)
class GrailsLoginAdsController {

    @Transactional
    def save(GrailsLoginAds grailsLoginAdsInstance) {
        if (grailsLoginAdsInstance == null) {
            notFound()
            return
        }

        if (grailsLoginAdsInstance.hasErrors()) {
            respond grailsLoginAdsInstance.errors, view:'create'
            return
        }
        if (params.start > params.end ) {
            flash.message=message(code: 'default.not.valid.date.message')
            respond grailsLoginAdsInstance.errors, view:'create'
            def user=springSecurityService.principal
            def username=user.username
            [user:username]
            redirect(action:'create')
            return
        }

        grailsLoginAdsInstance.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.created.message', args: [message(code: 'grailsLoginAds.label', default: 'GrailsLoginAds'), grailsLoginAdsInstance.id])
                redirect grailsLoginAdsInstance
            }
            '*' { respond grailsLoginAdsInstance, [status: CREATED] }
        }
    }
}
