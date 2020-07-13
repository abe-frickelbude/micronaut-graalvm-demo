package example.test_harness

import groovy.util.logging.Slf4j

import java.lang.reflect.Field
import java.lang.reflect.Modifier


@Slf4j
class ReflectionUtils {

    private ReflectionUtils() {

    }

    /**
     * An improved version of Spring's ReflectionTestUtils.setField() method that adds the
     * ability to set final fields.
     *
     * Primarily used to inject mock loggers into test subjects when performing interaction
     * tests.
     *
     * @param target
     * @param name
     * @param value
     */
    static void setField(final Object target, final String name, final Object value) {

        try {

            Field field = target.class.getDeclaredField(name)
            if (!field.isAccessible()) {
                field.setAccessible(true)
            }

            // remove "final" modifier if necessary
            Field modifiers = Field.class.getDeclaredField("modifiers")
            if (field.getModifiers() & Modifier.FINAL) {
                modifiers.setAccessible(true)
                modifiers.setInt(field, field.getModifiers() & ~Modifier.FINAL)
            }

            field.set(target, value)

        } catch (ReflectiveOperationException ex) {
            log.warn("Cannot set specified field", ex)
        }
    }
}
