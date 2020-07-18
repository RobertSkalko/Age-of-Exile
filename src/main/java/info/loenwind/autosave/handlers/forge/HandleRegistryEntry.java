package info.loenwind.autosave.handlers.forge;

import java.lang.reflect.Type;
import java.util.Set;



import info.loenwind.autosave.Registry;
import info.loenwind.autosave.exceptions.NoHandlerFoundException;
import info.loenwind.autosave.handlers.IHandler;
import info.loenwind.autosave.util.NBTAction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.Identifier;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.IForgeRegistryEntry;
import net.minecraftforge.registries.RegistryManager;

@SuppressWarnings("rawtypes")
public class HandleRegistryEntry implements IHandler<IForgeRegistryEntry> {

  // Usually handler must save into the name, but we cheat and use a second nbt key here
  private static final String REGISTRY = "*R";

  @Override
  public Class<?> getRootType() {
    return IForgeRegistryEntry.class;
  }

  @Override
  public boolean store(Registry registry, Set<NBTAction> phase, CompoundTag nbt, Type type, String name, IForgeRegistryEntry object)
      throws IllegalArgumentException, IllegalAccessException, InstantiationException, NoHandlerFoundException {
    Identifier loc = object.getRegistryName();
    if (loc == null) {
      throw new IllegalArgumentException("Registry entry must be registered to be stored: " + object);
    }
    nbt.putString(name, loc.toString());
    @SuppressWarnings("unchecked")
    final IForgeRegistry<?> forgeRegistry = GameRegistry.findRegistry(object.getRegistryType());
    if (forgeRegistry == null) {
      throw new IllegalArgumentException("Registry entry's registry must be registered to be stored: " + object);
    }
    nbt.putString(name + REGISTRY, RegistryManager.ACTIVE.getName(forgeRegistry).toString());
    return true;
  }

  @Override
  @Nullable
  public IForgeRegistryEntry read(Registry registry, Set<NBTAction> phase, CompoundTag nbt, Type type, String name, IForgeRegistryEntry object)
      throws IllegalArgumentException, IllegalAccessException, InstantiationException, NoHandlerFoundException {
    if (nbt.contains(name) && nbt.contains(name + REGISTRY)) {
      final Identifier registryName = new Identifier(nbt.getString(name + REGISTRY));
      IForgeRegistry forgeRegistry = RegistryManager.ACTIVE.getRegistry(registryName);
      if (forgeRegistry == null) {
        throw new IllegalArgumentException("Registry entry's registry must be registered to be read: " + registryName);
      }
      Identifier rl = new Identifier(nbt.getString(name));
      IForgeRegistryEntry ret = forgeRegistry.getValue(rl);
      return ret;
    } else {
      return object;
    }
  }

}
