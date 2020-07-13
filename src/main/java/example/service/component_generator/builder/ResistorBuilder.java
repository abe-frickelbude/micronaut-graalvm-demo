package example.service.component_generator.builder;

import example.model.Resistor;
import example.service.component_generator.BasicValueFiller;
import example.service.component_generator.ComponentBuilder;
import org.apache.commons.lang3.RandomUtils;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class ResistorBuilder implements ComponentBuilder<Resistor> {

    private static final float MIN_VALUE = 0.05f; // 5 mOhm
    private static final float MAX_VALUE = 1.0e7f; // 10 MOhm

    private final BasicValueFiller basicValueFiller;

    @Inject
    public ResistorBuilder(final BasicValueFiller basicValueFiller) {
        this.basicValueFiller = basicValueFiller;
    }

    @Override
    public Class<Resistor> getComponentClass() {
        return Resistor.class;
    }

    @Override
    public Resistor buildComponent() {
        Resistor resistor = new Resistor();
        basicValueFiller.fill(resistor);
        resistor.setTolerance(BuilderUtils.pickRandomTolerance());
        resistor.setValue(RandomUtils.nextFloat(MIN_VALUE, MAX_VALUE));
        return resistor;
    }
}
