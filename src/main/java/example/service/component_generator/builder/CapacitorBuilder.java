package example.service.component_generator.builder;

import example.model.Capacitor;
import example.service.component_generator.BasicValueFiller;
import example.service.component_generator.ComponentBuilder;
import org.apache.commons.lang3.RandomUtils;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class CapacitorBuilder implements ComponentBuilder<Capacitor> {

    private static final float MIN_VALUE = 1e-12f; // 1pF
    private static final float MAX_VALUE = 1e-3f; // 1000 uF

    private final BasicValueFiller basicValueFiller;

    @Inject
    public CapacitorBuilder(final BasicValueFiller basicValueFiller) {
        this.basicValueFiller = basicValueFiller;
    }

    @Override
    public Class<Capacitor> getComponentClass() {
        return Capacitor.class;
    }

    @Override
    public Capacitor buildComponent() {
        var capacitor = new Capacitor();
        basicValueFiller.fill(capacitor);
        capacitor.setTolerance(BuilderUtils.pickRandomTolerance());
        capacitor.setValue(RandomUtils.nextFloat(MIN_VALUE, MAX_VALUE));
        return capacitor;
    }
}
