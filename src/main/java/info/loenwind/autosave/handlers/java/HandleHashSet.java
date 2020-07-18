package info.loenwind.autosave.handlers.java;

import java.lang.reflect.Type;
import java.util.HashSet;
import java.util.Set;

import info.loenwind.autosave.Registry;
import info.loenwind.autosave.exceptions.NoHandlerFoundException;
import info.loenwind.autosave.handlers.IHandler;
import info.loenwind.autosave.handlers.java.util.HandleCollection;
import info.loenwind.autosave.util.TypeUtil;

@SuppressWarnings("rawtypes")
public class HandleHashSet extends HandleCollection<HashSet> {
  
  public HandleHashSet() throws NoHandlerFoundException {
    super(HashSet.class);
  }

  protected HandleHashSet(Registry registry, Type... types) throws NoHandlerFoundException {
    super(HashSet.class, registry, types);
  }
  
  @Override
  protected HashSet makeCollection() {
    return new HashSet();
  }
  
  @Override
  protected boolean canHandle(Type type) {
    return TypeUtil.toClass(type) == Set.class || super.canHandle(type);
  }

  @Override
  protected IHandler<? extends HashSet> create(Registry registry, Type... types) throws NoHandlerFoundException {
    return new HandleHashSet(registry, types);
  }

}
