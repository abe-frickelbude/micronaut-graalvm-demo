package example.model;

import example.model.ElectronicComponent;

/**
 * Resistors. Resistance value unit is [Ohm], tolerance is specified in %.
 * 
 * @author Ibragim Kuliev
 *
 */
public class Resistor extends ElectronicComponent {

    public static final String TYPE = "resistor";

    private float value;
    private int tolerance;

    public Resistor() {
        super(TYPE);
    }

    public float getValue() {
        return value;
    }

    public void setValue(final float value) {
        this.value = value;
    }

    public int getTolerance() {
        return tolerance;
    }

    public void setTolerance(final int tolerance) {
        this.tolerance = tolerance;
    }
}
