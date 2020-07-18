package info.loenwind.autosave.handlers.minecraft;

import java.lang.reflect.Type;
import java.util.Set;



import info.loenwind.autosave.Registry;
import info.loenwind.autosave.exceptions.NoHandlerFoundException;
import info.loenwind.autosave.handlers.IHandler;
import info.loenwind.autosave.util.NBTAction;
import net.minecraft.block.BlockState;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtHelper;

public class HandleBlockState implements IHandler<BlockState> {

  public HandleBlockState() {
  }

  @Override
  public Class<?> getRootType() {
    return BlockState.class;
  }

  @Override
  public boolean store(Registry registry, Set<NBTAction> phase, CompoundTag nbt, Type type, String name, BlockState object)
      throws IllegalArgumentException, IllegalAccessException, InstantiationException, NoHandlerFoundException {
    CompoundTag tag = NbtHelper.fromBlockState(object);
    nbt.put(name, tag);
    return true;
  }

  @Override
  public BlockState read(Registry registry, Set<NBTAction> phase, CompoundTag nbt, Type type, String name,
      BlockState object) throws IllegalArgumentException, IllegalAccessException, InstantiationException, NoHandlerFoundException {
    return NbtHelper.toBlockState(nbt.getCompound(name));
  }

}
