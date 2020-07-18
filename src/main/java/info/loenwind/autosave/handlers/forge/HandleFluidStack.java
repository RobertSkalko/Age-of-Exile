package info.loenwind.autosave.handlers.forge;

import java.lang.reflect.Type;
import java.util.Set;



import info.loenwind.autosave.Registry;
import info.loenwind.autosave.exceptions.NoHandlerFoundException;
import info.loenwind.autosave.handlers.IHandler;
import info.loenwind.autosave.util.NBTAction;
import net.minecraft.nbt.CompoundTag;
import net.minecraftforge.fluids.FluidStack;

public class HandleFluidStack implements IHandler<FluidStack> {

  public HandleFluidStack() {
  }

  @Override
  public Class<?> getRootType() {
    return FluidStack.class;
  }

  @Override
  public boolean store(Registry registry, Set<NBTAction> phase, CompoundTag nbt, Type type, String name, FluidStack object)
      throws IllegalArgumentException, IllegalAccessException, InstantiationException, NoHandlerFoundException {
    CompoundTag tag = new CompoundTag();
    object.writeToNBT(tag);
    nbt.put(name, tag);
    return false;
  }

  @Override
  public FluidStack read(Registry registry, Set<NBTAction> phase, CompoundTag nbt, Type type, String name,
      FluidStack object) throws IllegalArgumentException, IllegalAccessException, InstantiationException, NoHandlerFoundException {
    if (nbt.contains(name)) {
      return FluidStack.loadFluidStackFromNBT(nbt.getCompound(name));
    }
    return null;
  }

}
