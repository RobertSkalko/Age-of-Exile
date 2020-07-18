package info.loenwind.autosave.handlers.java;

import java.lang.reflect.Type;
import java.util.Set;



import net.minecraft.nbt.CompoundTag;
import info.loenwind.autosave.Registry;
import info.loenwind.autosave.handlers.IHandler;
import info.loenwind.autosave.util.NBTAction;
import info.loenwind.autosave.util.NonnullType;
import info.loenwind.autosave.util.TypeUtil;

public class HandlePrimitive<T> implements IHandler<T> {

  public interface WriterFunc<@NonnullType T> {

    void set(CompoundTag tag, String name, T object);

  }

  public interface ReaderFunc<@NonnullType T> {

    T get(CompoundTag tag, String name);

  }

  private final T defaultValue;

  private final Class<?> primitiveClass;
  private final Class<?> boxedClass;

  private final WriterFunc<T> writer;
  private final ReaderFunc<T> reader;

  public HandlePrimitive(T defVal, Class<T> boxedClass, Class<?> primitiveClass, WriterFunc<T> writer, ReaderFunc<T> reader) {
    this.defaultValue = defVal;
    this.primitiveClass = primitiveClass;
    this.boxedClass = boxedClass;
    this.writer = writer;
    this.reader = reader;
  }

  @Override
  public IHandler<T> getHandler(Registry registry, Type type) {
    Class<?> primitive = primitiveClass;
    return (primitive != null && TypeUtil.isAssignable(primitive, type)) || TypeUtil.isAssignable(boxedClass, type) ? this : null;
  }

  @Override
  public boolean store(Registry registry, Set<NBTAction> phase, CompoundTag nbt, Type type, String name,
      T object) throws IllegalArgumentException, IllegalAccessException {
    writer.set(nbt, name, object);
    return true;
  }

  @Override
  public T read(Registry registry, Set<NBTAction> phase, CompoundTag nbt, Type type, String name,
      T object) {
    return nbt.contains(name) ? reader.get(nbt, name) : object != null ? object : defaultValue;
  }

}
