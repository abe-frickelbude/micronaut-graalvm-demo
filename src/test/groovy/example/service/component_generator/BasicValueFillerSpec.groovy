package example.service.component_generator

import example.model.Resistor
import example.service.component_generator.BasicValueFiller
import spock.lang.Specification
/**
 * Unit test for {@linkplain BasicValueFiller}
 *
 * In this case, the BasicValueFiller component under test is very simple as it doesn't have any dependencies,
 * so the test is quite straightforward as well.
 *
 */
class BasicValueFillerSpec extends Specification {

    def "test fill basic values" () {

        given:
        final BasicValueFiller subject = new BasicValueFiller()
        final Resistor resistor = new Resistor()

        when:
        subject.fill(resistor)

        then:
        resistor.manufacturer != null
        resistor.description != null
    }
}