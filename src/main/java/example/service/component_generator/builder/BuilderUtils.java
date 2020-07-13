package example.service.component_generator.builder;

import example.util.RandomValueUtils;

public final class BuilderUtils {

    private static final Integer[] TOLERANCES = {
            1, 2, 5, 10
    };

    public static Integer pickRandomTolerance() {
        return RandomValueUtils.pickRandomValue(TOLERANCES);
    }
}
