package example.service.component_generator.builder;

import example.model.MosfetTransistor;
import example.service.component_generator.BasicValueFiller;
import example.service.component_generator.ComponentBuilder;
import example.util.RandomValueUtils;
import org.apache.commons.lang3.RandomUtils;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class MosfetTransistorBuilder implements ComponentBuilder<MosfetTransistor> {

    private static final float MIN_IDS_MAX = 0.1f; // 100 mA
    private static final float MAX_IDS_MAX = 10.0f; // 10 A
    private static final float MIN_RDS_ON = 0.001f; // 1 mOhm, not unrealistic for modern low-voltage MOSFETs
    private static final float MAX_RDS_ON = 25.0f; // 25 Ohm

    private final BasicValueFiller basicValueFiller;

    @Inject
    public MosfetTransistorBuilder(final BasicValueFiller basicValueFiller) {
        this.basicValueFiller = basicValueFiller;
    }

    @Override
    public Class<MosfetTransistor> getComponentClass() {
        return MosfetTransistor.class;
    }

    @Override
    public MosfetTransistor buildComponent() {
        MosfetTransistor transistor = new MosfetTransistor();
        basicValueFiller.fill(transistor);
        transistor.setPolarity(RandomValueUtils.pickRandomValue(MosfetTransistor.Polarity.values()));
        transistor.setIdsMax(RandomUtils.nextFloat(MIN_IDS_MAX, MAX_IDS_MAX));
        transistor.setRdsON(RandomUtils.nextFloat(MIN_RDS_ON, MAX_RDS_ON));
        return transistor;
    }
}
