package info.loenwind.autosave;

import java.lang.reflect.Type;
import java.util.EnumSet;
import java.util.Set;


import net.minecraft.nbt.CompoundTag;
import info.loenwind.autosave.annotations.Storable;
import info.loenwind.autosave.annotations.Store;
import info.loenwind.autosave.engine.StorableEngine;
import info.loenwind.autosave.exceptions.NoHandlerFoundException;
import info.loenwind.autosave.handlers.IHandler;
import info.loenwind.autosave.util.NBTAction;
import info.loenwind.autosave.util.NullHelper;

/**
 * Store an object's fields to NBT data.
 *
 */
public class Writer {

  /**
   * Store an object's fields to NBT data as if its class was annotated
   * {@link Storable} without a special handler.
   *
   * <p>
   * See also: {@link Store} for the field annotation.
   *
   * @param registry
   *          The {@link Registry} to look up {@link IHandler}s for the fields
   *          of the given object
   * @param phase
   *          A set of {@link NBTAction}s to indicate which fields to process.
   *          Only fields that are annotated with a matching {@link NBTAction}
   *          are stored.
   * @param tag
   *          A {@link CompoundNBT} to write to. This CompoundNBT
   *          represents the whole object, with its fields in the tags.
   * @param object
   *          The object that should be stored
   */
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

  /**
   * Store an object's fields to NBT data as if its class was annotated
   * {@link Storable} without a special handler using the {@link Registry}
   * {@link Registry#GLOBAL_REGISTRY GLOBAL_REGISTRY}.
   *
   * <p>
   * See also: {@link Store} for the field annotation.
   *
   * @param phase
   *          A set of {@link NBTAction}s to indicate which fields to process.
   *          Only fields that are annotated with a matching {@link NBTAction}
   *          are stored.
   * @param tag
   *          A {@link CompoundNBT} to write to. This CompoundNBT
   *          represents the whole object, with its fields in the tags.
   * @param object
   *          The object that should be stored
   */
  public static <T> void write(@Nullable Set<NBTAction> phase, CompoundTag tag, T object) {
    write(Registry.GLOBAL_REGISTRY, NullHelper.notnull(phase, "Missing phase"), NullHelper.notnull(tag, "Missing NBT"), object);
  }

  /**
   * Store an object's fields to NBT data as if its class was annotated
   * {@link Storable} without a special handler.
   *
   * <p>
   * See also: {@link Store} for the field annotation.
   *
   * @param registry
   *          The {@link Registry} to look up {@link IHandler}s for the fields
   *          of the given object
   * @param phase
   *          A s{@link NBTAction} to indicate which fields to process. Only
   *          fields that are annotated with a matching {@link NBTAction} are
   *          stored.
   * @param tag
   *          A {@link CompoundNBT} to write to. This CompoundNBT
   *          represents the whole object, with its fields in the tags.
   * @param object
   *          The object that should be stored
   */
  public static <T> void write(Registry registry, NBTAction phase, CompoundTag tag, T object) {
    write(registry, NullHelper.notnullJ(EnumSet.of(phase), "EnumSet.of()"), NullHelper.notnull(tag, "Missing NBT"), object);
  }

  /**
   * Store an object's fields to NBT data as if its class was annotated
   * {@link Storable} without a special handler using the {@link Registry}
   * {@link Registry#GLOBAL_REGISTRY GLOBAL_REGISTRY}.
   *
   * <p>
   * See also: {@link Store} for the field annotation.
   *
   * @param phase
   *          A s{@link NBTAction} to indicate which fields to process. Only
   *          fields that are annotated with a matching {@link NBTAction} are
   *          stored.
   * @param tag
   *          A {@link CompoundNBT} to write to. This CompoundNBT
   *          represents the whole object, with its fields in the tags.
   * @param object
   *          The object that should be stored
   */
  public static <T> void write(NBTAction phase, CompoundTag tag, T object) {
    write(Registry.GLOBAL_REGISTRY, NullHelper.notnullJ(EnumSet.of(phase), "EnumSet.of()"), NullHelper.notnull(tag, "Missing NBT"), object);
  }

  /**
   * Store an object's fields to NBT data as if its class was annotated
   * {@link Storable} without a special handler, ignoring {@link NBTAction}
   * restrictions.
   *
   * <p>
   * See also: {@link Store} for the field annotation.
   *
   * @param registry
   *          The {@link Registry} to look up {@link IHandler}s for the fields
   *          of the given object
   * @param tag
   *          A {@link CompoundNBT} to write to. This CompoundNBT
   *          represents the whole object, with its fields in the tags.
   * @param object
   *          The object that should be stored
   */
  public static <T> void write(Registry registry, CompoundTag tag, T object) {
    write(registry, NullHelper.notnullJ(EnumSet.allOf(NBTAction.class), "EnumSet.allOf()"), NullHelper.notnull(tag, "Missing NBT"), object);
  }

  /**
   * Store an object's fields to NBT data as if its class was annotated
   * {@link Storable} without a special handler using the {@link Registry}
   * {@link Registry#GLOBAL_REGISTRY GLOBAL_REGISTRY}, ignoring {@link NBTAction}
   * restrictions.
   *
   * <p>
   * See also: {@link Store} for the field annotation.
   *
   * @param tag
   *          A {@link CompoundNBT} to write to CompoundNBT represents the
   *          whole object, with its fields in the tags.
   * @param object
   *          The object that should be stored
   */
  public static <T> void write(@Nullable CompoundTag tag, T object) {
    write(Registry.GLOBAL_REGISTRY, NullHelper.notnullJ(EnumSet.allOf(NBTAction.class), "EnumSet.allOf()"), NullHelper.notnull(tag, "Missing NBT"), object);
  }

  /**
   * Store a single field to NBT data.
   *
   * @param tag
   *          A {@link CompoundNBT} to write to CompoundNBT represents the whole object, with its fields in the tags.
   * @param fieldType
   *          The class of the field (not the whole object)
   * @param fieldName
   *          The name of the field
   * @param object
   *          The object that should be stored. May be null, in which case an old value will be removed.
   */
  public static <T> void writeField(@Nullable CompoundTag tag, Type fieldType, String fieldName, T object) {
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
