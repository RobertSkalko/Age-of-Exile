package info.loenwind.autosave.util;

import info.loenwind.autosave.util.VersionProxy.GameVersion;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodHandles.Lookup;



public class VersionProxy<T> {

  enum GameVersion {
    BEFORE_1_13,
    V1_13_x,
    ;

    static GameVersion discover() {
      try {
        Class.forName("net.minecraftforge.fml.common.FMLCommonHandler");
        return GameVersion.BEFORE_1_13;
      } catch (ClassNotFoundException e) {
        return GameVersion.V1_13_x;
      }
    }
  }

  private static final GameVersion VERSION = GameVersion.discover();

  private final T func;

  VersionProxy(T func) {
    this.func = func;
  }

  public T get() {
    return func;
  }

  private static final Lookup LOOKUP = MethodHandles.publicLookup();
}
