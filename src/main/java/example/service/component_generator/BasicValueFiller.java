package example.service.component_generator;

import example.model.ElectronicComponent;
import example.model.Manufacturer;
import example.util.RandomValueUtils;
import org.apache.commons.lang3.RandomStringUtils;

import javax.inject.Singleton;

/**
 * 
 * @author Ibragim Kuliev
 *
 */
@Singleton
public class BasicValueFiller {

    public ElectronicComponent fill(final ElectronicComponent component) {
        component.setDescription(makeRandomDescription(component.getClass()));
        component.setManufacturer(pickRandomManufacturer());
        return component;
    }

    private Manufacturer pickRandomManufacturer() {
        return RandomValueUtils.pickRandomValue(Manufacturer.values());
    }

    private <T> String makeRandomDescription(final Class<T> componentClass) {
        return componentClass.getSimpleName() + "-" + RandomStringUtils.randomAlphanumeric(8);
    }
}
