package example.service.component_generator.builder;

import example.model.Diode;
import example.service.component_generator.BasicValueFiller;
import example.service.component_generator.ComponentBuilder;
import org.apache.commons.lang3.RandomUtils;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class DiodeBuilder implements ComponentBuilder<Diode> {

    private static final float MIN_VF = 0.15f; // can be this low for Schottky diodes
    private static final float MAX_VF = 0.7f;
    private static final int MIN_VREV_MAX = 30;
    private static final int MAX_VREV_MAX = 80;
    private static final float MIN_IF_MAX = 0.01f; // 10 mA, applicable e.g. to small-signal diodes
    private static final float MAX_IF_MAX = 10.0f; // 10 A

    private final BasicValueFiller basicValueFiller;

    @Inject
    public DiodeBuilder(BasicValueFiller basicValueFiller) {
        this.basicValueFiller = basicValueFiller;
    }

    @Override
    public Class<Diode> getComponentClass() {
        return Diode.class;
    }

    @Override
    public Diode buildComponent() {
        Diode diode = new Diode();
        basicValueFiller.fill(diode);
        diode.setVf(RandomUtils.nextFloat(MIN_VF, MAX_VF));
        diode.setVrevMax(RandomUtils.nextInt(MIN_VREV_MAX, MAX_VREV_MAX + 1));
        diode.setIfMax(RandomUtils.nextFloat(MIN_IF_MAX, MAX_IF_MAX));
        return diode;
    }
}
