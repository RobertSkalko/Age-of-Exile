package info.loenwind.autosave.exceptions;

import java.lang.reflect.Field;
import java.lang.reflect.Type;



public class NoHandlerFoundException extends Exception {

  private static final long serialVersionUID = -6324172401194016237L;

  public NoHandlerFoundException(Field field, Object o) {
    super("No storage handler found for field " + field.getName() + " of type " + field.getGenericType() + " of " + o);
  }

  public NoHandlerFoundException(Type type, String name) {
    this(type, name, null);
  }

  public NoHandlerFoundException(Type type, String name, Exception e) {
    super("No storage handler found for field " + name + " of type " + type, e);
  }

  public NoHandlerFoundException(String msg) {
    super(msg);
  }
}
