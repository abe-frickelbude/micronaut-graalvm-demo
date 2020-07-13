package example.service_tests

import spock.lang.Specification

class GroovyInitSpec extends Specification {

    def "init ok"() {
        expect:
        true == true
    }
}
