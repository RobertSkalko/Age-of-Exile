package info.loenwind.autosave.handlers.util;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Set;



import net.minecraft.nbt.CompoundTag;
import info.loenwind.autosave.Registry;
import info.loenwind.autosave.exceptions.NoHandlerFoundException;
import info.loenwind.autosave.handlers.IHandler;
import info.loenwind.autosave.util.Log;
import info.loenwind.autosave.util.NBTAction;
import info.loenwind.autosave.util.NonnullType;
import info.loenwind.autosave.util.NullHelper;
import info.loenwind.autosave.util.TypeUtil;

@SuppressWarnings("rawtypes")
public abstract class HandleGenericType<T> implements IHandler<T> {

  protected final Class<? extends T> clazz;

  protected final @NonnullType Type[] types;
  protected final List<IHandler>[] subHandlers;

  @SuppressWarnings("unchecked")
  protected HandleGenericType(Class<? extends T> clazz, Registry registry, Type... parameterTypes) throws NoHandlerFoundException {
    this.clazz = clazz;
    this.types = new Type[getRequiredParameters()];
    this.subHandlers = (List<IHandler>[]) new List[getRequiredParameters()];

    if (parameterTypes.length == 0) {
      return;
    }
    if (parameterTypes.length != getRequiredParameters()) {
      throw new IllegalArgumentException("Mismatch of parameter count. Required: " + getRequiredParameters() + "  Found: " + parameterTypes.length);
    }

    for (int i = 0; i < getRequiredParameters(); i++) {
      Type type = parameterTypes[i];
      types[i] = type; // Shallow copy
      if (type == null) {
        throw new IllegalArgumentException("Null type passed to HandleGenericType()");
      }
      try {
        List<IHandler> handlers = registry.findHandlers(type);
        if (handlers.isEmpty()) {
          throw new NoHandlerFoundException(type, "Unknown");
        }
        subHandlers[i] = handlers;
      } catch (IllegalAccessException | InstantiationException e) {
        throw new NoHandlerFoundException(type, "Unknown", e);
      }
    }
  }

  @Override
  public final Class<?> getRootType() {
    return clazz;
  }

  protected abstract IHandler<? extends T> create(Registry registry, Type... types) throws NoHandlerFoundException;

  protected int getRequiredParameters() {
    return getRootType().getTypeParameters().length;
  }

  protected boolean canHandle(Type type) {
    return TypeUtil.isAssignable(getRootType(), type);
  }

  @Override
  public IHandler<? extends T> getHandler(Registry registry, Type type) {
    if (!canHandle(type)) {
      return null;
    }

    if (type instanceof ParameterizedType) {
      try {
        return create(registry, NullHelper.notnullJ(((ParameterizedType) type).getActualTypeArguments(), "ParameterizedType#getActualTypeArguments"));
      } catch (NoHandlerFoundException e) {
        Log.LOGGER.error(e);
      } // Fallthrough to return null
    }

    return null;
  }

  @SuppressWarnings("unchecked")
  protected final void storeRecursive(int param, Registry registry, Set<NBTAction> phase, CompoundTag nbt, String name, Object object)
      throws IllegalArgumentException, IllegalAccessException, InstantiationException, NoHandlerFoundException {
    for (IHandler handler : subHandlers[param]) {
      if (handler.store(registry, phase, nbt, types[param], name, object)) {
        return;
      }
    }
  }

  @SuppressWarnings({ "unchecked"})
  protected final <V> V readRecursive(int param, Registry registry, Set<NBTAction> phase, CompoundTag nbt, String name, V object)
      throws IllegalArgumentException, IllegalAccessException, InstantiationException, NoHandlerFoundException {
    for (IHandler handler : subHandlers[param]) {
      V result = (V) handler.read(registry, phase, nbt, types[param], name, object);
      if (result != null) {
        return result;
      }
    }
    return null;
  }
}
