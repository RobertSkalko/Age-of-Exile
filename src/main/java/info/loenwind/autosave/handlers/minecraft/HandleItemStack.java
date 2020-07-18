package info.loenwind.autosave.handlers.minecraft;

import java.lang.reflect.Type;
import java.util.Set;



import info.loenwind.autosave.Registry;
import info.loenwind.autosave.engine.StorableEngine;
import info.loenwind.autosave.exceptions.NoHandlerFoundException;
import info.loenwind.autosave.handlers.IHandler;
import info.loenwind.autosave.util.NBTAction;
import info.loenwind.autosave.util.VersionProxy;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundTag;

public class HandleItemStack implements IHandler<ItemStack> {

  public HandleItemStack() {
  }

  @Override
  public Class<?> getRootType() {
    return ItemStack.class;
  }

  @Override
  public boolean store(Registry registry, Set<NBTAction> phase, CompoundTag nbt, Type type, String name, ItemStack object)
      throws IllegalArgumentException, IllegalAccessException, InstantiationException, NoHandlerFoundException {
    if (object.isEmpty()) {
      nbt.putBoolean(name + StorableEngine.EMPTY_POSTFIX, true);
    } else {
      CompoundTag tag = new CompoundTag();
      object.toTag(tag);
      nbt.put(name, tag);
    }
    return true;
  }

  @Override
  public ItemStack read(Registry registry, Set<NBTAction> phase, CompoundTag nbt, Type type, String name,
      ItemStack object) throws IllegalArgumentException, IllegalAccessException, InstantiationException, NoHandlerFoundException {
    if (nbt.contains(name)) {
      CompoundTag tag = nbt.getCompound(name);
      return ItemStack.fromTag(tag);
    } else if (nbt.contains(name + StorableEngine.EMPTY_POSTFIX)) {
      return ItemStack.EMPTY;
    }
    return object;
  }
}
