package example.model;

import example.model.ElectronicComponent;

/**
 * Inductors. Inductance value unit is [Henry], tolerance is specified in %.
 * 
 * @author Ibragim Kuliev
 *
 */
public class Inductor extends ElectronicComponent {

    public static final String TYPE = "inductor";
    private float value;
    private int tolerance;

    public Inductor() {
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
