package example.service.component_generator.builder;

import example.model.BipolarTransistor;
import example.service.component_generator.BasicValueFiller;
import example.service.component_generator.ComponentBuilder;
import example.util.RandomValueUtils;
import org.apache.commons.lang3.RandomUtils;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class BipolarTransistorBuilder implements ComponentBuilder<BipolarTransistor> {

    private static final float MIN_IC_MAX = 0.01f; // 10 mA
    private static final float MAX_IC_MAX = 5.0f; // 5 A (perhaps we have a 2N3055 here....? :D)

    private static final int MIN_VCEMAX = 10; // 10 V
    private static final int MAX_VCEMAX = 600; // 10 V

    private static final int MIN_HFE = 20;
    private static final int MAX_HFE = 500;

    private final BasicValueFiller basicValueFiller;

    @Inject
    public BipolarTransistorBuilder(final BasicValueFiller basicValueFiller) {
        this.basicValueFiller = basicValueFiller;
    }

    @Override
    public Class<BipolarTransistor> getComponentClass() {
        return BipolarTransistor.class;
    }

    @Override
    public BipolarTransistor buildComponent() {

        var bjt = new BipolarTransistor();
        basicValueFiller.fill(bjt);
        bjt.setPolarity(RandomValueUtils.pickRandomValue(BipolarTransistor.Polarity.values()));
        bjt.sethFE(RandomUtils.nextInt(MIN_HFE, MAX_HFE + 1));
        bjt.setVceMax(RandomUtils.nextInt(MIN_VCEMAX, MAX_VCEMAX + 1));
        bjt.setIcMax(RandomUtils.nextFloat(MIN_IC_MAX, MAX_IC_MAX));
        return bjt;
    }
}
