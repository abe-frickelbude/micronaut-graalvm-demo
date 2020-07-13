package example.service_tests

import example.model.ElectronicComponent
import example.service.component_generator.RandomComponentFactory
import io.micronaut.test.annotation.MicronautTest
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import spock.lang.Specification

import javax.inject.Inject
/**
 * Integration test for {@linkplain RandomComponentFactory}.
 *
 * This is an example of a "component" (alternative name: integration) test that uses an actual application
 * context in contrast to a unit test. In cases like this, the overhead of mocking/stubbing dependencies of a
 * component under test becomes unreasonable, and so it may be more feasible to actually initialize a [partial] app
 * context and let the container perform DI and other initialization.
 *
 * Assuming the components' dependencies have themselves been properly unit-tested in isolation and can be considered
 * "proven working", this kind of component test is now a reasonable compromise, even though it is not "pure"
 * w.r.t to outside influence by the application framework(s).
 *
 */
@MicronautTest
class RandomComponentFactorySpec extends Specification {

    private static final Logger log = LoggerFactory.getLogger(RandomComponentFactorySpec)

    @Inject
    private RandomComponentFactory subject

    void "test component construction"() {

        expect:
        for (int i = 0; i < 10; i++) {

            ElectronicComponent component = subject.makeComponent()

            component != null
            component.description != null
            component.createdAt != null
            component.manufacturer != null

            and:
            log.info(component.toString())
        }
    }
}
