package info.loenwind.autosave.util;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.function.Supplier;

public class TypeUtil {

    /**
     * Convert a {@link Type} to a {@link Class}, discarding all generic
     * information.
     *
     * @param type The type to convert.
     * @return The raw Class object.
     */
    public static Class<?> toClass(Type type) {
        return (Class<?>) (type instanceof ParameterizedType ? NullHelper.notnullJ(
                ((ParameterizedType) type).getRawType(), "ParameterizedType#getRawType") : type);
    }

    /**
     * Analogous to {@link Class#isAssignableFrom(Class)}, but converts the passed
     * type to a class via {{@link #toClass(Type)} first.
     *
     * @param clazz The class to be the left-hand comparison
     * @param type  The type to be the right-hand comparison
     * @return {@link Class#isAssignableFrom(Class)}
     */
    public static boolean isAssignable(Class<?> clazz, Type type) {
        return clazz.isAssignableFrom(toClass(type));
    }

    public static <T> Supplier<T> defaultConstructorFactory(Class<T> clazz) {
        MethodHandle handle;
        try {
            handle = MethodHandles.lookup().unreflectConstructor(clazz.getConstructor());
        } catch (IllegalAccessException | NoSuchMethodException | SecurityException e) {
            throw new RuntimeException(e);
        }
        return () -> {
            try {
                return (T) handle.invoke();
            } catch (Throwable e) {
                throw new RuntimeException(e);
            }
        };
    }

    public static Type getGenericType(Field field) {
        return NullHelper.notnullJ(
                field.getGenericType(),
                "Field#getGenericType"
        ); // TODO Would caching this be worthwhile? Seems to be cached in Field already.
    }

}
