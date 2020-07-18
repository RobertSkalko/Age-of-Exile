package info.loenwind.autosave.util;

import java.util.HashSet;
import java.util.Set;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

// Copied from EnderCore and adjusted
public final class Log {

  public static final boolean inDev = System.getProperty("INDEV") != null;

  public static final Logger LOGGER = NullHelper.notnull(LogManager.getLogger("AutoSave"), "LogManager.getLogger");

  public static void warn(Object... msg) {
    LOGGER.warn(join("", msg));
  }

  public static void error(Object... msg) {
    LOGGER.error(join("", msg));
  }

  public static void info(Object... msg) {
    LOGGER.info(join("", msg));
  }

  public static void debug(Object... msg) {
    if (inDev) {
      LOGGER.info("INDEV: " + join("", msg));
    } else if (LOGGER.isDebugEnabled()) {
      LOGGER.debug(join("", msg));
    }
  }
  
  private static final Set<String> debugTraceRequesters = new HashSet<>();
  
  public static void enableExtremelyDetailedNBTActivity(String mod, boolean enable) {
    if (enable) {
      debugTraceRequesters.add(mod);
    } else {
      debugTraceRequesters.remove(mod);
    }
  }

  public static void livetraceNBT(Object... msg) {
    if (!debugTraceRequesters.isEmpty()) {
      LOGGER.info(join("", msg));
    }
  }

  public static String join(CharSequence delimiter, Object... elements) {
    StringBuilder joiner = new StringBuilder();
    for (Object cs : elements) {
      if (joiner.length() != 0) {
        joiner.append(delimiter);
      }
      joiner.append(cs);
    }
    return NullHelper.notnullJ(joiner.toString(), "StringBuilder#toString");
  }

  private Log() {
  }

}
