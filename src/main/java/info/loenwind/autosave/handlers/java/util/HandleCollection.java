package info.loenwind.autosave.handlers.java.util;

import java.lang.reflect.Type;
import java.util.Collection;
import java.util.Set;


import net.minecraft.nbt.CompoundTag;
import info.loenwind.autosave.Registry;
import info.loenwind.autosave.exceptions.NoHandlerFoundException;
import info.loenwind.autosave.handlers.IHandler;
import info.loenwind.autosave.handlers.util.HandleGenericType;
import info.loenwind.autosave.util.NBTAction;

@SuppressWarnings({"rawtypes", "unchecked"})
public abstract class HandleCollection<T extends Collection> extends HandleGenericType<T> {

  public HandleCollection(Class<? extends T> clazz) throws NoHandlerFoundException {
    this(clazz, Registry.GLOBAL_REGISTRY, new Type[0]);
  }

  protected HandleCollection(Class<? extends T> clazz, Registry registry, Type... types) throws NoHandlerFoundException {
    super(clazz, registry, types);
  }

  @Override
  public boolean store(Registry registry, Set<NBTAction> phase, CompoundTag nbt, Type type, String name, T object)
      throws IllegalArgumentException, IllegalAccessException, InstantiationException, NoHandlerFoundException {
    CompoundTag tag = new CompoundTag();
    tag.putInt("size", object.size());
    int i = 0;
    for (Object elem : object) {
      if (elem != null) {
        for (IHandler handler : subHandlers[0]) {
          handler.store(registry, phase, tag, type, i + "", elem);
        }
      }
      i++;
    }
    nbt.put(name, tag);
    return true;
  }

  @Override
  public T read(Registry registry, Set<NBTAction> phase, CompoundTag nbt, Type type, String name,
      T object) throws IllegalArgumentException, IllegalAccessException, InstantiationException, NoHandlerFoundException {
    if (nbt.contains(name)) {
      if (object == null) {
        object = makeCollection();
      } else {
        object.clear();
      }

      CompoundTag tag = nbt.getCompound(name);
      int size = tag.getInt("size");
      for (int i = 0; i < size; i++) {
        object.add(readRecursive(0, registry, phase, tag, i + "", null));
      }
    }
    return object;
  }

  abstract protected T makeCollection();
}
