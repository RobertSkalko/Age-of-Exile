package info.loenwind.autosave;

import info.loenwind.autosave.engine.StorableEngine;
import info.loenwind.autosave.exceptions.NoHandlerFoundException;
import info.loenwind.autosave.util.NBTAction;
import info.loenwind.autosave.util.NullHelper;
import net.minecraft.nbt.CompoundTag;

import java.lang.reflect.Type;
import java.util.EnumSet;
import java.util.Set;

/**
 * Store an object's fields to NBT data.
 */
public class Writer {

    public static <T> void write(Registry registry, Set<NBTAction> phase, CompoundTag tag, T object) {
        try {
            StorableEngine.store(registry, phase, tag, object);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        } catch (InstantiationException e) {
            throw new RuntimeException(e);
        } catch (NoHandlerFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public static <T> void write(Set<NBTAction> phase, CompoundTag tag, T object) {
        write(Registry.GLOBAL_REGISTRY, NullHelper.notnull(phase, "Missing phase"), NullHelper.notnull(tag, "Missing NBT"), object);
    }

    public static <T> void write(Registry registry, NBTAction phase, CompoundTag tag, T object) {
        write(registry, NullHelper.notnullJ(EnumSet.of(phase), "EnumSet.of()"), NullHelper.notnull(tag, "Missing NBT"), object);
    }

    public static <T> void write(NBTAction phase, CompoundTag tag, T object) {
        write(Registry.GLOBAL_REGISTRY, NullHelper.notnullJ(EnumSet.of(phase), "EnumSet.of()"), NullHelper.notnull(tag, "Missing NBT"), object);
    }

    public static <T> void write(Registry registry, CompoundTag tag, T object) {
        write(registry, NullHelper.notnullJ(EnumSet.allOf(NBTAction.class), "EnumSet.allOf()"), NullHelper.notnull(tag, "Missing NBT"), object);
    }

    public static <T> void write(CompoundTag tag, T object) {
        write(Registry.GLOBAL_REGISTRY, NullHelper.notnullJ(EnumSet.allOf(NBTAction.class), "EnumSet.allOf()"), NullHelper.notnull(tag, "Missing NBT"), object);
    }

    public static <T> void writeField(CompoundTag tag, Type fieldType, String fieldName, T object) {
        try {
            StorableEngine.setSingleField(Registry.GLOBAL_REGISTRY, NullHelper.notnullJ(EnumSet.allOf(NBTAction.class), "EnumSet.allOf()"),
                NullHelper.notnull(tag, "Missing NBT"), NullHelper.notnull(fieldName, "Missing field name"), NullHelper.notnull(fieldType, "Missing field class"),
                object);
        } catch (InstantiationException e) {
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        } catch (IllegalArgumentException e) {
            throw new RuntimeException(e);
        } catch (NoHandlerFoundException e) {
            throw new RuntimeException(e);
        }
    }

}
