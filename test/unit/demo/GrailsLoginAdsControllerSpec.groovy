package demo



import grails.test.mixin.*
import spock.lang.*

@TestFor(GrailsLoginAdsController)
@Mock(GrailsLoginAds)
class GrailsLoginAdsControllerSpec extends Specification {

    def populateValidParams(params) {
        assert params != null
        params.name = 'Some Ad'
    }

    void "Test the save action correctly persists an instance"() {
        when:"The save action is executed with an invalid instance"
            request.contentType = FORM_CONTENT_TYPE
            request.method = 'POST'
            def grailsLoginAds = new GrailsLoginAds()
            grailsLoginAds.validate()
            controller.save(grailsLoginAds)

        then:"The create view is rendered again with the correct model"
            model.grailsLoginAdsInstance!= null
            view == 'create'

        when:"The save action is executed with a valid instance"
            response.reset()
            populateValidParams(params)
            grailsLoginAds = new GrailsLoginAds(params)

            controller.save(grailsLoginAds)

        then:"A redirect is issued to the show action"
            response.redirectedUrl == '/grailsLoginAds/show/1'
            controller.flash.message != null
            GrailsLoginAds.count() == 1

    }
}
