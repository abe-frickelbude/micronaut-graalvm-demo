package example.service.core

import example.model.Diode
import example.model.ElectronicComponent
import example.model.Manufacturer
import example.model.Resistor
import example.service.component_generator.RandomComponentFactory
import example.test_harness.ReflectionUtils
import io.micronaut.data.model.Page
import io.micronaut.data.model.Pageable
import org.slf4j.Logger
import spock.lang.Specification

/**
 * A typical Spock unit test. This class shows some of the typical structure of a Spock-based
 * test, along with some Spock features such as interaction-based testing, mocking, and
 * using with() in "then:" block for a more compact syntax
 *
 * Be aware that this is meant as an example, and the test is incomplete w.r.t. to code
 * coverage, i.e. some cases are not tested here!
 */
@SuppressWarnings('GroovyAccessibility')
class InMemoryRepositorySpec extends Specification {

    private InMemoryRepository subject

    private static int NUM_ITEMS = 100

    // setup() is a Spock fixture method that is most definitely used!
    @SuppressWarnings('unused')
    def setup() {

        def componentFactory = Mock(RandomComponentFactory)
        componentFactory.makeComponent() >> new Resistor()

        subject = new InMemoryRepository(componentFactory, NUM_ITEMS)

        def mockLogger = Mock(Logger)

        // Hack to set a final field - this is actually ill-advised and should be avoid in the future
        ReflectionUtils.setField(subject, "log", mockLogger)

        subject.init() // populate internal component list
    }

    def "check init" () {

        given:
        def factory = Mock(RandomComponentFactory)
        def repo = new InMemoryRepository(factory, NUM_ITEMS)

        when: "repository is initialized"
        repo.init()

        then: "internal list/map are populated"

        with(repo) {
            repo.numItems == NUM_ITEMS
            repo.componentList.size() == NUM_ITEMS
            repo.componentMap.size() == NUM_ITEMS
            repo.idSequence.get() == NUM_ITEMS
        }

        and: "the component factory has been called NUM_ITEMS times"
        NUM_ITEMS * factory.makeComponent() >> Mock(ElectronicComponent)
    }

    def "check find by id"() {

        expect:
        for(int id = 0; id < NUM_ITEMS; id++) {

            def item = subject.find(id)
            item != null
            item.id == id
        }
    }

    def "check find by pageable"() {

        given:
        Pageable pageable = Pageable.from(1,7)

        when:
        Page itemPage = subject.find(pageable)

        then:
        itemPage != null
        with(itemPage) {
            size == 7
            offset == 7
            content != null
            content.size() == 7
        }
    }

    def "check findAll"() {

        when:
        def items = subject.findAll()

        then:
        with(items) {
            size() == NUM_ITEMS
            items.each {
                it != null
                it.id != null
            }
        }
    }

    def "check save" () {

        given:
        ElectronicComponent component = new Diode()
        component.manufacturer = Manufacturer.IFR

        when:
        subject.save(component)

        then:
        subject.componentList.size() == NUM_ITEMS + 1
        subject.componentMap.size() == NUM_ITEMS + 1
        component.id != null
    }

    def "check update"() {

        given:
        ElectronicComponent component = subject.find(42)
        def description = "Updated description"
        component.setDescription(description)

        when:
        subject.save(component)

        then:
        def updComponent = subject.find(42)
        updComponent.description == description
    }

    def "check delete"() {

        when:
        subject.delete(42)

        then:
        subject.find(42) == null
        subject.componentList.size() == NUM_ITEMS - 1
        subject.componentMap.size() == NUM_ITEMS - 1
    }
}
