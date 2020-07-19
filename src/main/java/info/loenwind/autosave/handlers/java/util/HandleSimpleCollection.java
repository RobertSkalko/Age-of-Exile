package info.loenwind.autosave.handlers.java.util;

import info.loenwind.autosave.Registry;
import info.loenwind.autosave.exceptions.NoHandlerFoundException;
import info.loenwind.autosave.handlers.IHandler;
import info.loenwind.autosave.util.NonnullType;
import info.loenwind.autosave.util.TypeUtil;

import java.lang.reflect.Type;
import java.util.Collection;
import java.util.function.Supplier;

@SuppressWarnings("rawtypes")
public class HandleSimpleCollection<T extends Collection> extends HandleCollection<T> {

    private final Supplier<@NonnullType ? extends T> factory;

    public HandleSimpleCollection(Class<? extends T> clazz) throws NoHandlerFoundException {
        this(clazz, TypeUtil.defaultConstructorFactory(clazz));
    }

    public HandleSimpleCollection(Class<? extends T> clazz, Supplier<? extends T> factory) throws NoHandlerFoundException {
        super(clazz);
        this.factory = factory;
    }

    public HandleSimpleCollection(Class<? extends T> clazz, Supplier<? extends T> factory, Registry registry, Type... types) throws NoHandlerFoundException {
        super(clazz, registry, types);
        this.factory = factory;
    }

    @Override
    protected T makeCollection() {
        return factory.get();
    }

    @Override

    protected IHandler<? extends T> create(Registry registry, Type... types) throws NoHandlerFoundException {
        return new HandleSimpleCollection<>(clazz, factory, registry, types);
    }

}
