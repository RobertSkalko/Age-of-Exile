package info.loenwind.autosave.handlers.java;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import info.loenwind.autosave.Registry;
import info.loenwind.autosave.exceptions.NoHandlerFoundException;
import info.loenwind.autosave.handlers.IHandler;
import info.loenwind.autosave.handlers.java.util.HandleCollection;
import info.loenwind.autosave.util.TypeUtil;

@SuppressWarnings("rawtypes")
public class HandleArrayList extends HandleCollection<ArrayList> {
  
  public HandleArrayList() throws NoHandlerFoundException {
    this(Registry.GLOBAL_REGISTRY, new Type[0]);
  }

  protected HandleArrayList(Registry registry, Type... types) throws NoHandlerFoundException {
    super(ArrayList.class, registry, types);
  }

  @Override
  protected ArrayList makeCollection() {
    return new ArrayList();
  }
  
  @Override
  protected boolean canHandle(Type type) {
    return TypeUtil.toClass(type) == List.class || super.canHandle(type);
  }

  @Override
  protected IHandler<? extends ArrayList> create(Registry registry, Type... types) throws NoHandlerFoundException {
    return new HandleArrayList(registry, types);
  }

}
