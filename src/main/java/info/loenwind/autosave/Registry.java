package info.loenwind.autosave;

import info.loenwind.autosave.annotations.Storable;
import info.loenwind.autosave.exceptions.NoHandlerFoundException;
import info.loenwind.autosave.handlers.IHandler;
import info.loenwind.autosave.handlers.internal.HandleStorable;
import info.loenwind.autosave.handlers.java.*;
import info.loenwind.autosave.handlers.java.util.HandleSimpleCollection;
import info.loenwind.autosave.handlers.minecraft.HandleBlockPos;
import info.loenwind.autosave.handlers.minecraft.HandleBlockState;
import info.loenwind.autosave.handlers.minecraft.HandleItemStack;
import info.loenwind.autosave.handlers.util.DelegatingHandler;
import info.loenwind.autosave.util.BitUtil;
import info.loenwind.autosave.util.NullableType;
import info.loenwind.autosave.util.TypeUtil;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.Identifier;
import org.apache.commons.lang3.ArrayUtils;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

/**
 * A registry for {@link IHandler}s.
 *
 * <p>
 * Registries use Java-like inheritance. That means any registry, except the base registry {@link Registry#GLOBAL_REGISTRY}, has exactly one super-registry.
 * When looking for handlers, all handlers from this registry and all its super-registries will be returned in order.
 */
@SuppressWarnings({"rawtypes"})
public class Registry {

    /**
     * This is the super-registry of all registries. It contains handlers for Java primitives, Java classes, Minecraft classes and Forge classes.
     * <p>
     * You can registerForgeConfigs new handlers here if you want other mods to be able to store your objects. Otherwise please use your own registry.
     */

    public static final
    Registry GLOBAL_REGISTRY = new Registry(true);

    static {
        // Java primitives
        GLOBAL_REGISTRY.register(new HandlePrimitive<Boolean>(false, Boolean.class, boolean.class, CompoundTag::putBoolean, CompoundTag::getBoolean));
        GLOBAL_REGISTRY.register(new HandlePrimitive<Character>((char) 0, Character.class, char.class, (nbt, name, c) -> nbt
            .putInt(name, (int) c), (nbt, name) -> (char) nbt.getInt(name)));
        GLOBAL_REGISTRY.register(new HandlePrimitive<Byte>((byte) 0, Byte.class, byte.class, CompoundTag::putByte, CompoundTag::getByte));
        GLOBAL_REGISTRY.register(new HandlePrimitive<Short>((short) 0, Short.class, short.class, CompoundTag::putShort, CompoundTag::getShort));
        GLOBAL_REGISTRY.register(new HandlePrimitive<Integer>(0, Integer.class, int.class, CompoundTag::putInt, CompoundTag::getInt));
        GLOBAL_REGISTRY.register(new HandlePrimitive<Long>(0L, Long.class, long.class, CompoundTag::putLong, CompoundTag::getLong));
        GLOBAL_REGISTRY.register(new HandlePrimitive<Float>(0F, Float.class, float.class, CompoundTag::putFloat, CompoundTag::getFloat));
        GLOBAL_REGISTRY.register(new HandlePrimitive<Double>(0D, Double.class, double.class, CompoundTag::putDouble, CompoundTag::getDouble));
        GLOBAL_REGISTRY.register(new HandleEnum());
        GLOBAL_REGISTRY.register(new HandleString());

        // Primitive array handlers

        // byte/Byte
        IHandler<byte[]> byteArrayHandler = new HandlePrimitive<byte @NullableType []>(new byte[0], byte[].class, null, CompoundTag::putByteArray, CompoundTag::getByteArray);
        GLOBAL_REGISTRY.register(byteArrayHandler);
        GLOBAL_REGISTRY.register(new DelegatingHandler<>(Byte[].class, byteArrayHandler, ArrayUtils::toPrimitive, ArrayUtils::toObject));

        // int/Integer
        IHandler<int[]> intArrayHandler = new HandlePrimitive<int @NullableType []>(new int[0], int[].class, null, CompoundTag::putIntArray, CompoundTag::getIntArray);
        GLOBAL_REGISTRY.register(intArrayHandler);
        GLOBAL_REGISTRY.register(new DelegatingHandler<>(Integer[].class, intArrayHandler, ArrayUtils::toPrimitive, ArrayUtils::toObject));

        // The rest are packed into int[]

        // short/Short
        IHandler<short[]> shortArrayHandler = new HandlePrimitive<short @NullableType []>(new short[0], short[].class, null, (nbt, name, arr) -> {
            int[] ret = new int[arr.length];
            for (int i = 0; i < ret.length; i++) {
                ret[i] = (int) arr[i];
            }
            nbt.putIntArray(name, ret);
        }, (nbt, name) -> {
            int[] read = nbt.getIntArray(name);
            short[] ret = new short[read.length];
            for (int i = 0; i < ret.length; i++) {
                ret[i] = (short) read[i];
            }
            return ret;
        });
        GLOBAL_REGISTRY.register(shortArrayHandler);
        GLOBAL_REGISTRY.register(new DelegatingHandler<>(Short[].class, shortArrayHandler, ArrayUtils::toPrimitive, ArrayUtils::toObject));

        // char/Character
        IHandler<char[]> charArrayHandler = new HandlePrimitive<char @NullableType []>(new char[0], char[].class, null, (nbt, name, arr) -> {
            int[] ret = new int[arr.length];
            for (int i = 0; i < ret.length; i++) {
                ret[i] = (int) arr[i];
            }
            nbt.putIntArray(name, ret);
        }, (nbt, name) -> {
            int[] read = nbt.getIntArray(name);
            char[] ret = new char[read.length];
            for (int i = 0; i < ret.length; i++) {
                ret[i] = (char) read[i];
            }
            return ret;
        });
        GLOBAL_REGISTRY.register(charArrayHandler);
        GLOBAL_REGISTRY.register(new DelegatingHandler<>(Character[].class, charArrayHandler, ArrayUtils::toPrimitive, ArrayUtils::toObject));

        // float/Float
        IHandler<float[]> floatArrayHandler = new HandlePrimitive<float @NullableType []>(new float[0], float[].class, null, (nbt, name, arr) -> {
            int[] ret = new int[arr.length];
            for (int i = 0; i < ret.length; i++) {
                ret[i] = Float.floatToIntBits(arr[i]);
            }
            nbt.putIntArray(name, ret);
        }, (nbt, name) -> {
            int[] read = nbt.getIntArray(name);
            float[] ret = new float[read.length];
            for (int i = 0; i < ret.length; i++) {
                ret[i] = Float.intBitsToFloat(read[i]);
            }
            return ret;
        });
        GLOBAL_REGISTRY.register(floatArrayHandler);
        GLOBAL_REGISTRY.register(new DelegatingHandler<>(Float[].class, floatArrayHandler, ArrayUtils::toPrimitive, ArrayUtils::toObject));

        // long/Long
        IHandler<long[]> longArrayHandler = new HandlePrimitive<long @NullableType []>(new long[0], long[].class, null, (nbt, name, arr) -> {
            int[] ret = new int[arr.length * 2];
            for (int i = 0; i < arr.length; i++) {
                ret[i * 2] = BitUtil.getLongMSB(arr[i]);
                ret[i * 2 + 1] = BitUtil.getLongLSB(arr[i]);
            }
            nbt.putIntArray(name, ret);
        }, (nbt, name) -> {
            int[] read = nbt.getIntArray(name);
            long[] ret = new long[read.length / 2];
            for (int i = 0; i < ret.length; i++) {
                ret[i] = BitUtil.longFromInts(read[i * 2], read[i * 2 + 1]);
            }
            return ret;
        });
        GLOBAL_REGISTRY.register(longArrayHandler);
        GLOBAL_REGISTRY.register(new DelegatingHandler<>(Long[].class, longArrayHandler, ArrayUtils::toPrimitive, ArrayUtils::toObject));

        // double/Double
        // Reuse the long[] handler since we can just stream convert
        IHandler<double[]> doubleArrayHandler = new DelegatingHandler<>(double[].class, longArrayHandler, (doubleArr) -> Arrays
            .stream(doubleArr)
            .mapToLong(d -> Double.doubleToLongBits(d))
            .toArray(), (longArr) -> Arrays.stream(longArr)
            .mapToDouble(l -> Double.longBitsToDouble(l))
            .toArray());
        GLOBAL_REGISTRY.register(doubleArrayHandler);
        GLOBAL_REGISTRY.register(new DelegatingHandler<>(Double[].class, doubleArrayHandler, ArrayUtils::toPrimitive, ArrayUtils::toObject));

        // Fallback array handler
        GLOBAL_REGISTRY.register(new HandleArrays());

        // Collections
        try {
            // List/ArrayList
            GLOBAL_REGISTRY.register(new HandleArrayList());
            // LinkedList
            GLOBAL_REGISTRY.register(new HandleSimpleCollection<>(LinkedList.class));

            // Set/HashSet
            GLOBAL_REGISTRY.register(new HandleHashSet());
            GLOBAL_REGISTRY.register(new HandleEnumSet());

            GLOBAL_REGISTRY.register(new HandleHashMap());
            GLOBAL_REGISTRY.register(new HandleEnum2EnumMap<>()); // This MUST be before HandleEnumMap as it is a special case
            GLOBAL_REGISTRY.register(new HandleEnumMap<>());
        } catch (NoHandlerFoundException e) {
        }

        // Minecraft basic types
        GLOBAL_REGISTRY.register(new HandleItemStack());
        GLOBAL_REGISTRY.register(new HandleBlockPos());
        GLOBAL_REGISTRY.register(new HandleBlockState());
        GLOBAL_REGISTRY.register(new DelegatingHandler<>(Identifier.class, new HandleString(), Identifier::toString, Identifier::new));

        // Annotated objects
        GLOBAL_REGISTRY.register(new HandleStorable<Object>());
    }

    private final List<IHandler> handlers = new ArrayList<IHandler>();

    private final Registry parent;

    /**
     * Creates the {@link Registry#GLOBAL_REGISTRY}.
     *
     * @param root A placeholder
     */
    private Registry(boolean root) {
        parent = root ? null : null;
    }

    /**
     * Crates a new registry which extends {@link Registry#GLOBAL_REGISTRY}.
     */
    public Registry() {
        this(GLOBAL_REGISTRY);
    }

    /**
     * Creates a new registry which extends the given parent.
     *
     * @param parent The parent to extend
     */
    public Registry(Registry parent) {
        this.parent = parent;
    }

    /**
     * Registers a new {@link IHandler}.
     *
     * @param handler The {@link IHandler} to registerForgeConfigs
     */
    public void register(IHandler handler) {
        handlers.add(handler);
    }

    /**
     * Registers a new {@link IHandler} that has higher priority than all existing handlers.
     *
     * @param handler The {@link IHandler} to registerForgeConfigs
     */
    public void registerPriority(IHandler handler) {
        handlers.add(0, handler);
    }

    /**
     * Finds all {@link IHandler}s from this registry and all its parents that can handle the given class.
     *
     * <p>
     * Handlers will be returned in this order:
     * <ol>
     * <li>The annotated special handler of the given class
     * <li>The annotated special handler(s) of its superclass(es)
     * <li>The registered handlers from this registry
     * <li>The registered handlers from this registry's super-registries
     * <li>{@link HandleStorable} if the class is annotated {@link Storable} without a special handler
     * </ol>
     * <p>
     * Note: If a class is annotated {@link Storable}, then all subclasses must be annotated {@link Storable}, too.
     * <p>
     * Note 2: If a class is annotated {@link Storable} without a special handler, all subclasses must either also be annotated {@link Storable} without a special
     * handler or their handlers must be able to handle the inheritance because {@link HandleStorable} will <i>not</i> be added to this list in this case.
     * <p>
     * Note 3: If a handler can handle a class but not its subclasses, it will not be added to this list for the subclasses.
     *
     * @param type The class that should be handled
     * @return A list of all {@link IHandler}s that can handle the class. If none are found, an empty list is returned.
     * @throws InstantiationException
     * @throws IllegalAccessException
     */

    public List<IHandler> findHandlers(
        Type type) throws InstantiationException, IllegalAccessException {
        List<IHandler> result = new ArrayList<IHandler>();

        Class<?> clazz = TypeUtil.toClass(type);
        Storable annotation = clazz.getAnnotation(Storable.class);
        while (annotation != null) {
            if (annotation.handler() != HandleStorable.class) {
                result.add(annotation.handler()
                    .newInstance());
            }
            Class<?> superclass = clazz.getSuperclass();
            if (superclass != null) {
                annotation = superclass.getAnnotation(Storable.class);
                clazz = superclass;
            } else {
                break; // Theoretically impossible as the hierarchy should always reach Object before null, but handle the case anyways
            }
        }

        findRegisteredHandlers(this, type, result);

        return result;
    }

    private void findRegisteredHandlers(Registry caller, Type type,
                                        List<IHandler> result) {
        for (IHandler handler : handlers) {
            handler = handler.getHandler(caller, type);
            if (handler != null) {
                result.add(handler);
            }
        }
        final Registry thisParent = parent;
        if (thisParent != null) {
            thisParent.findRegisteredHandlers(caller, type, result);
        }
    }

}
