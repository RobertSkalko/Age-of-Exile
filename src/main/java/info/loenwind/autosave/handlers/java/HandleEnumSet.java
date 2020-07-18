package info.loenwind.autosave.handlers.java;

import java.lang.reflect.Type;
import java.util.EnumSet;

import info.loenwind.autosave.Registry;
import info.loenwind.autosave.exceptions.NoHandlerFoundException;
import info.loenwind.autosave.handlers.IHandler;
import info.loenwind.autosave.handlers.java.util.HandleCollection;
import info.loenwind.autosave.util.NullHelper;
import info.loenwind.autosave.util.TypeUtil;

@SuppressWarnings("rawtypes")
public class HandleEnumSet extends HandleCollection<EnumSet> {
  
  public HandleEnumSet() throws NoHandlerFoundException {
    super(EnumSet.class);
  }

  protected HandleEnumSet(Registry registry, Type... types) throws NoHandlerFoundException {
    super(EnumSet.class, registry, types);
  }

  @SuppressWarnings("unchecked")
  @Override
  protected EnumSet makeCollection() {
    return NullHelper.notnullJ(EnumSet.noneOf((Class<Enum>) TypeUtil.toClass(types[0])), "EnumSet.noneOf");
  }

  @Override
  protected IHandler<? extends EnumSet> create(Registry registry, Type... types) throws NoHandlerFoundException {
    return new HandleEnumSet(registry, types);
  }
}
