package demo

class GrailsLoginAds {
    String name

    static constraints = {
        name matches: /[A-Z].*/
    }
}
