package example.service.component_generator.builder;

import example.model.Inductor;
import example.service.component_generator.BasicValueFiller;
import example.service.component_generator.ComponentBuilder;
import org.apache.commons.lang3.RandomUtils;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class InductorBuilder implements ComponentBuilder<Inductor> {

    private static final float MIN_VALUE = 1e-7f; // 0.1 uH
    private static final float MAX_VALUE = 1.0f; // 1 H

    private final BasicValueFiller basicValueFiller;

    @Inject
    public InductorBuilder(final BasicValueFiller basicValueFiller) {
        this.basicValueFiller = basicValueFiller;
    }

    @Override
    public Class<Inductor> getComponentClass() {
        return Inductor.class;
    }

    @Override
    public Inductor buildComponent() {
        Inductor inductor = new Inductor();
        basicValueFiller.fill(inductor);
        inductor.setTolerance(BuilderUtils.pickRandomTolerance());
        inductor.setValue(RandomUtils.nextFloat(MIN_VALUE, MAX_VALUE));
        return inductor;
    }
}
