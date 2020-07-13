package example.service_tests

import io.micronaut.runtime.EmbeddedApplication
import io.micronaut.test.annotation.MicronautTest
import spock.lang.Specification
import javax.inject.Inject

@MicronautTest
class Micronaut_gvmSpec extends Specification {

    @Inject
    EmbeddedApplication application

    void 'test app init ok'() {
        expect:
        application.running
    }

}