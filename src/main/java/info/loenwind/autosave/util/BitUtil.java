package info.loenwind.autosave.util;

public class BitUtil {
  
  public static int getLongMSB(long val) {
    return (int) (val >>> 32);
  }
  
  public static int getLongLSB(long val) {
    return (int) (val & 0xFFFFFFFFL);
  }
  
  public static long longFromInts(int msb, int lsb) {
    return (Integer.toUnsignedLong(msb) << 32) | Integer.toUnsignedLong(lsb);
  }

}
