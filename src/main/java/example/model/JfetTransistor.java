package example.model;

import example.model.ElectronicComponent;

public class JfetTransistor extends ElectronicComponent {

    public static final String TYPE = "jfet";

    public JfetTransistor() {
        super(TYPE);
    }
}
