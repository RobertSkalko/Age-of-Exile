package info.loenwind.autosave.handlers.internal;

import java.lang.reflect.Type;
import java.util.Set;


import net.minecraft.nbt.CompoundTag;
import info.loenwind.autosave.Registry;
import info.loenwind.autosave.exceptions.NoHandlerFoundException;
import info.loenwind.autosave.handlers.IHandler;
import info.loenwind.autosave.util.NBTAction;

/**
 * A dummy {@link IHandler} that is used as default value for fields. It will be
 * ignored and the fields' handlers will be looked up in the {@link Registry}
 * instead.
 * <p>
 * This is needed because Java annotations do not allow "null" as a default
 * value for class parameters.
 * <p>
 * Do not add this handler to an annotation.
 */
public class NullHandler implements IHandler<NullHandler> {

  private NullHandler() {
  }

  @Override
  public boolean store(Registry registry, Set<NBTAction> phase, CompoundTag nbt, Type type, String name,
      NullHandler object) throws IllegalArgumentException, IllegalAccessException, InstantiationException, NoHandlerFoundException {
    return false;
  }

  @Override
  public NullHandler read(Registry registry, Set<NBTAction> phase, CompoundTag nbt, Type type,
      String name, NullHandler object)
      throws IllegalArgumentException, IllegalAccessException, InstantiationException, NoHandlerFoundException {
    return null;
  }

}
