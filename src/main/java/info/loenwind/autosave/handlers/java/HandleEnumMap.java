package info.loenwind.autosave.handlers.java;

import java.lang.reflect.Type;
import java.util.EnumMap;
import java.util.Set;


import net.minecraft.nbt.CompoundTag;
import info.loenwind.autosave.Registry;
import info.loenwind.autosave.engine.StorableEngine;
import info.loenwind.autosave.exceptions.NoHandlerFoundException;
import info.loenwind.autosave.handlers.IHandler;
import info.loenwind.autosave.handlers.java.util.HandleMap;
import info.loenwind.autosave.util.NBTAction;
import info.loenwind.autosave.util.NonnullType;
import info.loenwind.autosave.util.NullHelper;
import info.loenwind.autosave.util.TypeUtil;

@SuppressWarnings({"unchecked", "rawtypes"})
public class HandleEnumMap<K extends Enum<K>> extends HandleMap<EnumMap<K, ?>>{

  private final Class<K> enumClass;
  private final K[] enumValues;

  public HandleEnumMap() throws NoHandlerFoundException {
    super((Class<EnumMap<K, ?>>) (Class) EnumMap.class);
    this.enumClass = (Class<K>) (Class) Enum.class;
    this.enumValues = (K[]) new Enum[0];
  }

  protected HandleEnumMap(Registry registry, Class<K> enumClass, Type valueClass) throws NoHandlerFoundException {
    super((Class<EnumMap<K, ?>>) (Class) EnumMap.class, registry, enumClass, valueClass);
    this.enumClass = enumClass;
    this.enumValues = NullHelper.notnullJ(enumClass.getEnumConstants(), "Class#getEnumConstants");
  }

  @Override
  protected IHandler<? extends EnumMap<K, ?>> create(Registry registry, @NonnullType Type... types) throws NoHandlerFoundException {
    return new HandleEnumMap<K>(registry, (Class<K>) TypeUtil.toClass(types[0]), types[1]);
  }

  @Override
  public boolean store(Registry registry, Set<NBTAction> phase, CompoundTag nbt, Type type, String name,
      EnumMap<K, ?> object) throws IllegalArgumentException, IllegalAccessException, InstantiationException, NoHandlerFoundException {
    CompoundTag tag = new CompoundTag();
    for (K key : enumValues) {
      Object val = object.get(key);
      String keystr = NullHelper.notnullJ(Integer.toString(key.ordinal()), "Integer.toString is null");
      if (val != null) {
        storeRecursive(1, registry, phase, tag, keystr, val);
      } else {
        tag.putBoolean(keystr + StorableEngine.NULL_POSTFIX, true);
      }
    }
    nbt.put(name, tag);
    return true;
  }

  @Override
  public EnumMap<K, ?> read(Registry registry, Set<NBTAction> phase, CompoundTag nbt, Type type,
      String name, EnumMap<K, ?> object)
      throws IllegalArgumentException, IllegalAccessException, InstantiationException, NoHandlerFoundException {
    if (nbt.contains(name)) {
      if (object == null) {
        object = createMap();
      }
      CompoundTag tag = nbt.getCompound(name);
      for (K key : enumValues) {
        String keystr = NullHelper.notnullJ(Integer.toString(key.ordinal()), "Integer.toString is null");
        if (!tag.getBoolean(keystr + StorableEngine.NULL_POSTFIX)) {
          object.put(key, readRecursive(1, registry, phase, tag, keystr, null));
        }
      }
    }
    return object;
  }

  @Override
  protected EnumMap<K, ?> createMap() {
    return new EnumMap<>(enumClass);
  }

}
