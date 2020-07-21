package info.loenwind.autosave.handlers.util;

import info.loenwind.autosave.Registry;
import info.loenwind.autosave.exceptions.NoHandlerFoundException;
import info.loenwind.autosave.handlers.IHandler;
import info.loenwind.autosave.util.NBTAction;
import info.loenwind.autosave.util.NonnullType;
import info.loenwind.autosave.util.TypeUtil;
import net.minecraft.nbt.CompoundTag;

import java.lang.reflect.Type;
import java.util.Set;
import java.util.function.Function;

public class DelegatingHandler<T, R> implements IHandler<T> {

    private final Type type;
    private final IHandler<R> delegate;
    private final Function<@NonnullType T, R> storeConverter;
    private final Function<@NonnullType R, T> readConverter;

    /**
     * This exists to make javac happy in cases where T might not be strongly
     * bound by the function parameters.
     */
    public DelegatingHandler(Class<T> type, IHandler<R> delegate, Function<@NonnullType T, R> storeConverter, Function<@NonnullType R, T> readConverter) {
        this((Type) type, delegate, storeConverter, readConverter);
    }

    public DelegatingHandler(Type type, IHandler<R> delegate, Function<@NonnullType T, R> storeConverter, Function<@NonnullType R, T> readConverter) {
        this.type = type;
        this.delegate = delegate;
        this.storeConverter = storeConverter;
        this.readConverter = readConverter;
    }

    @Override
    public Class<?> getRootType() {
        return TypeUtil.toClass(type);
    }

    @Override
    public boolean store(Registry registry, Set<NBTAction> phase, CompoundTag nbt, Type type, String name, T object)
        throws IllegalArgumentException, IllegalAccessException, InstantiationException, NoHandlerFoundException {
        R obj = storeConverter.apply(object);
        if (obj == null) {
            throw new IllegalArgumentException("Store converter returned null unexpectedly.");
        }
        return delegate.store(registry, phase, nbt, type, name, obj);
    }

    @Override
    public T read(Registry registry, Set<NBTAction> phase, CompoundTag nbt, Type type, String name, T object)
        throws IllegalArgumentException, IllegalAccessException, InstantiationException, NoHandlerFoundException {
        R intermediate = delegate.read(registry, phase, nbt, type, name, object == null ? null : storeConverter.apply(object));
        return intermediate == null ? null : readConverter.apply(intermediate);
    }
}
