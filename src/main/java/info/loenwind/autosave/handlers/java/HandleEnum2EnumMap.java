package info.loenwind.autosave.handlers.java;

import info.loenwind.autosave.Registry;
import info.loenwind.autosave.exceptions.NoHandlerFoundException;
import info.loenwind.autosave.handlers.IHandler;
import info.loenwind.autosave.handlers.java.util.HandleMap;
import info.loenwind.autosave.util.*;
import net.minecraft.nbt.CompoundTag;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.EnumMap;
import java.util.Set;

/**
 * This is a specialized version of {@link HandleEnumMap}, for maps with enum values as well.
 * <p>
 * It will compress the stored data into a single long, throwing an error on construction if there are too many
 * values to do so.
 *
 * @author tterrag
 */
@SuppressWarnings({
    "rawtypes",
    "unchecked"
})
public class HandleEnum2EnumMap<T extends Enum<T>> extends HandleMap<EnumMap<T, Enum>> {

    private final Class<T> keyClass;
    private final T[] keys;

    private final Enum[] vals;
    private final int valspace;

    public HandleEnum2EnumMap() throws NoHandlerFoundException {
        super((Class<EnumMap<T, Enum>>) (Class) EnumMap.class);
        this.keyClass = (Class<T>) (Class) Enum.class;
        this.keys = (T[]) new Enum[0];
        this.vals = new Enum[0];
        this.valspace = 0;
    }

    protected HandleEnum2EnumMap(Registry registry, Class<? extends Enum> keyClass,
                                 Class<? extends Enum> valClass) throws NoHandlerFoundException {
        super((Class<EnumMap<T, Enum>>) (Class) EnumMap.class, registry, keyClass, valClass);
        this.keyClass = (Class<T>) keyClass;
        this.keys = (T[]) getEnumConstants(keyClass);
        this.vals = getEnumConstants(valClass);
        // Add one to vals.length for null
        this.valspace = getValspace(vals.length);

        if (keys.length * valspace > 64) {
            throw new IllegalArgumentException(
                "Enums " + keyClass + " and " + valClass + " cannot be used, as they have too many combinations.");
        }
    }

    private <E> E[] getEnumConstants(Class<E> clazz) {
        return NullHelper.notnullJ(clazz.getEnumConstants(), "Class#getEnumConstants");
    }

    private int getValspace(int valCount) {
        return Integer.numberOfTrailingZeros(Integer.highestOneBit(valCount + 1)) + 1;
    }

    @Override
    protected EnumMap<T, Enum> createMap() {
        return new EnumMap<>(keyClass);
    }

    @Override
    public boolean canHandle(Type type) {
        if (super.canHandle(type) && type instanceof ParameterizedType) {
            Type[] types = ((ParameterizedType) type).getActualTypeArguments();
            @NonnullType Class[] paramClasses = new Class[types.length];
            for (int i = 0; i < types.length; i++) {
                paramClasses[i] = TypeUtil.toClass(
                    NullHelper.notnullJ(types[i], "ParameterizedType#getActualTypeArguments[i]"));
            }
            if (paramClasses.length == 2 && paramClasses[0].isEnum() && paramClasses[1].isEnum()) {
                // Make sure we can store this, otherwise it will fall back to EnumMap handler
                int keys = getEnumConstants(paramClasses[0]).length;
                int vals = getEnumConstants(paramClasses[1]).length;
                int valspace = getValspace(vals);
                return keys * valspace <= 64;
            }
        }
        return false;
    }

    @Override
    protected IHandler<? extends EnumMap<T, Enum>> create(Registry registry,
                                                          @NonnullType Type... types) throws NoHandlerFoundException {
        return new HandleEnum2EnumMap(registry, TypeUtil.toClass(types[0]), TypeUtil.toClass(types[1]));
    }

    @Override
    public boolean store(Registry registry, Set<NBTAction> phase, CompoundTag nbt, Type type, String name,
                         EnumMap<T, Enum> object) throws IllegalArgumentException, IllegalAccessException,
        InstantiationException, NoHandlerFoundException {
        long value = 0;
        for (T key : keys) {
            // 0 is null, all ordinal values are shifted up by 1
            long subvalue = 0;
            if (object.containsKey(key)) {
                subvalue = object.get(key)
                    .ordinal() + 1;
            }
            value = value | (subvalue << (key.ordinal() * valspace));
        }
        nbt.putIntArray(name, new int[]{
            valspace,
            BitUtil.getLongMSB(value),
            BitUtil.getLongLSB(value)
        });
        return true;
    }

    @Override
    public EnumMap read(Registry registry, Set<NBTAction> phase, CompoundTag nbt, Type type, String name,
                        EnumMap<T, Enum> object) throws IllegalArgumentException, IllegalAccessException,
        InstantiationException, NoHandlerFoundException {
        if (nbt.contains(name)) {
            if (object == null) {
                object = createMap();
            }
            int[] raw = nbt.getIntArray(name);
            if (raw.length == 0) {
                // Convert old data
                long value = nbt.getLong(name);
                for (T key : keys) {
                    long subvalue = (value >>> (key.ordinal() * 8)) & 0xFF;
                    if (subvalue > 0 && subvalue < vals.length) {
                        object.put(key, vals[(int) subvalue]);
                    } else {
                        object.remove(key);
                    }
                }
                return object;
            }
            int space = raw[0];
            int mask = (1 << space) - 1;
            long value = BitUtil.longFromInts(raw[1], raw[2]);
            for (T key : keys) {
                long subvalue = (value >>> (key.ordinal() * space)) & mask;
                if (subvalue > 0 && subvalue <= vals.length) {
                    object.put(key, vals[(int) subvalue - 1]);
                } else if (subvalue == 0) {
                    object.remove(key);
                } else {
                    Log.error("Found invalid map value when parsing enum2enum map! Data: %s   type: %s", nbt, type);
                    Thread.dumpStack();
                }
            }
        }
        return object;
    }

}
