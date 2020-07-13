package example;

import io.micronaut.runtime.Micronaut;

public class Application {

    public static void main(String[] args) {

        Micronaut.build(args)
                .eagerInitSingletons(true)
                .eagerInitConfiguration(true)
                .mainClass(Application.class)
                .start();
    }
}
