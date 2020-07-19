package info.loenwind.autosave.engine;

import com.google.common.base.Preconditions;
import info.loenwind.autosave.Reader;
import info.loenwind.autosave.Registry;
import info.loenwind.autosave.Writer;
import info.loenwind.autosave.annotations.AfterRead;
import info.loenwind.autosave.annotations.Factory;
import info.loenwind.autosave.annotations.Storable;
import info.loenwind.autosave.annotations.Store;
import info.loenwind.autosave.exceptions.NoHandlerFoundException;
import info.loenwind.autosave.handlers.IHandler;
import info.loenwind.autosave.handlers.internal.HandleStorable;
import info.loenwind.autosave.handlers.internal.NullHandler;
import info.loenwind.autosave.util.Log;
import info.loenwind.autosave.util.NBTAction;
import info.loenwind.autosave.util.NullHelper;
import info.loenwind.autosave.util.TypeUtil;
import net.minecraft.nbt.CompoundTag;

import java.lang.reflect.*;
import java.util.*;

/**
 * The thread-safe engine that handles (re-)storing {@link Storable} objects by storing their fields. The fields to (re-)store must be annotated {@link Store}.
 * <p>
 * It will also process the annotated fields of superclasses, as long as there is an unbroken chain of {@link Storable} annotations (without special handlers).
 * Fields that have the same name as a field in a sub-/super-class will be processed independently.
 * <p>
 * If the final superclass has an {@link IHandler} registered in the {@link Registry}, it will also be processed. However, this will <i>not</i> work for
 * handlers that return a new object instead of changing the given one. A handler can check for this case by seeing if its "name" parameter is
 * {@link StorableEngine#SUPERCLASS_KEY}.
 * <p>
 * Note: If a {@link Storable} object is encountered in a {@link Store} field, it is handled by {@link HandleStorable}---which delegates here.
 * <p>
 * Note 2: There are public entrances to this class in {@link Writer} and {@link Reader}.
 */
@SuppressWarnings({
    "rawtypes",
    "unchecked"
})
public class StorableEngine {

    private static final ThreadLocal<StorableEngine> INSTANCE = new ThreadLocal<StorableEngine>() {
        @Override
        protected StorableEngine initialValue() {
            return new StorableEngine();
        }
    };

    @FunctionalInterface
    private interface ObjectFactory {

        Object get() throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException;

    }

    private static class AfterReadCallback {
        private final Method callback;
        private final boolean isStatic;

        AfterReadCallback(Method m) throws IllegalArgumentException {
            Preconditions.checkArgument(m.getReturnType() == void.class, "AfterRead methods cannot return a value", m);
            Preconditions.checkArgument(m.getParameterCount() == 0, "AfterRead methods cannot take parameters", m);

            m.setAccessible(true);
            this.callback = m;
            this.isStatic = Modifier.isStatic(m.getModifiers());
        }

        public void apply(
            Object inst) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
            callback.invoke(isStatic ? null : inst);
        }

        @Override
        public String toString() {
            return NullHelper.notnullJ(callback.toString(), "Method#toString");
        }
    }

    public static final
    String NULL_POSTFIX = "-";
    public static final
    String EMPTY_POSTFIX = "+";
    public static final
    String SUPERCLASS_KEY = "__superclass";
    private final
    Map<Class<?>, List<Field>> fieldCache = new HashMap<>();
    private final
    Map<Field, Set<NBTAction>> phaseCache = new HashMap<>();
    private final
    Map<Field, List<IHandler>> fieldHandlerCache = new HashMap<>();
    private final
    Map<Class<?>, Class<?>> superclassCache = new HashMap<>();
    private final
    Map<Class<?>, List<IHandler>> superclassHandlerCache = new HashMap<>();
    private final
    Map<Class<?>, ObjectFactory> factoryCache = new HashMap<>();
    private final
    Map<Class<?>, List<AfterReadCallback>> callbackCache = new HashMap<>();

    private StorableEngine() {
    }

    public static <T> void read(Registry registry, Set<NBTAction> phase, CompoundTag tag,
                                T object) throws IllegalAccessException, InstantiationException, NoHandlerFoundException {
        INSTANCE.get()
            .read_impl(registry, phase, tag, object);
    }

    public static <T> void store(Registry registry, Set<NBTAction> phase, CompoundTag tag,
                                 T object) throws IllegalAccessException, InstantiationException, NoHandlerFoundException {
        INSTANCE.get()
            .store_impl(registry, phase, tag, object);
    }

    public <T> void read_impl(Registry registry, Set<NBTAction> phase, CompoundTag tag,
                              T object) throws IllegalAccessException, InstantiationException, NoHandlerFoundException {
        Class<? extends Object> clazz = object.getClass();
        if (!fieldCache.containsKey(clazz)) {
            cacheHandlers(registry, clazz);
        }

        Log.livetraceNBT("Reading NBT data for object ", object, " of class ", clazz, " for phase(s) ", phase, " from NBT ", tag);
        for (Field field : fieldCache.get(clazz)) {
            if (!Collections.disjoint(phaseCache.get(field), phase)) {
                Object fieldData = field.get(object);
                String fieldName = field.getName();
                if (!tag.contains(fieldName + NULL_POSTFIX) && fieldName != null) {
                    for (IHandler handler : fieldHandlerCache.get(field)) {
                        Log.livetraceNBT("Trying to read data for field ", fieldName, " with handler ", handler);
                        Object result = handler.read(registry, phase, tag, TypeUtil.getGenericType(field), fieldName, fieldData);
                        if (result != null) {
                            Log.livetraceNBT("Read data for field ", fieldName, " with handler ", handler, " yielded data: ", result);
                            field.set(object, result);
                            break;
                        }
                    }
                } else {
                    Log.livetraceNBT("Field ", fieldName, " is set to null. NULL_POSTFIX=", tag
                        .contains(fieldName + NULL_POSTFIX));
                    field.set(object, null);
                }
            } else {
                Log.livetraceNBT("Field ", field.getName(), " is not part of the current phase.");
            }
        }

        Class<?> superclazz = superclassCache.get(clazz);
        if (superclazz != null) {
            for (IHandler handler : superclassHandlerCache.get(superclazz)) {
                Log.livetraceNBT("Trying to read data for super class ", superclazz, " with handler ", handler);
                if (handler.read(registry, phase, tag, superclazz, SUPERCLASS_KEY, object) != null) {
                    Log.livetraceNBT("Read data for super class ", superclazz, " with handler ", handler);
                    break;
                }
            }
        }

        for (AfterReadCallback callback : callbackCache.get(clazz)) {
            try {
                callback.apply(object);
            } catch (IllegalArgumentException | InvocationTargetException e) {
                throw new RuntimeException("Failed to invoke AfterRead: " + callback, e);
            }
        }
        Log.livetraceNBT("Read NBT data for object ", object, " of class ", clazz);
    }

    public <T> void store_impl(Registry registry, Set<NBTAction> phase, CompoundTag tag,
                               T object) throws IllegalAccessException, InstantiationException, NoHandlerFoundException {
        Class<? extends Object> clazz = object.getClass();
        if (!fieldCache.containsKey(clazz)) {
            cacheHandlers(registry, clazz);
        }

        Log.livetraceNBT("Saving NBT data for object ", object, " of class ", clazz, " for phase(s) ", phase, " into NBT ", tag);
        for (Field field : fieldCache.get(clazz)) {
            if (!Collections.disjoint(phaseCache.get(field), phase)) {
                Object fieldData = field.get(object);
                String fieldName = field.getName();
                if (fieldData != null && fieldName != null) {
                    for (IHandler handler : fieldHandlerCache.get(field)) {
                        Log.livetraceNBT("Trying to save data for field ", fieldName, " with handler ", handler);
                        if (handler.store(registry, phase, tag, TypeUtil.getGenericType(field), fieldName, fieldData)) {
                            Log.livetraceNBT("Saved data for field ", fieldName, " with handler ", handler, ". NBT now is ", tag);
                            break;
                        }
                    }
                } else {
                    Log.livetraceNBT("Field ", fieldName, " is null. Setting NULL_POSTFIX.");
                    tag.putBoolean(fieldName + NULL_POSTFIX, true);
                }
            } else {
                Log.livetraceNBT("Field ", field.getName(), " is not part of the current phase.");
            }
        }

        Class<?> superclazz = superclassCache.get(clazz);
        if (superclazz != null) {
            for (IHandler handler : superclassHandlerCache.get(superclazz)) {
                Log.livetraceNBT("Trying to save data for super class ", superclazz, " with handler ", handler);
                if (handler.store(registry, phase, tag, superclazz, SUPERCLASS_KEY, object)) {
                    Log.livetraceNBT("Saved data for super class ", superclazz, " with handler ", handler);
                    break;
                }
            }
        }

        Log.livetraceNBT("Saved NBT data for object ", object, " of class ", clazz);
    }

    public static <T> T getSingleField(Registry registry, Set<NBTAction> phase, CompoundTag tag,
                                       String fieldName, Type type,
                                       T object) throws InstantiationException, IllegalAccessException, IllegalArgumentException, NoHandlerFoundException {
        if (!tag.contains(fieldName + NULL_POSTFIX)) {
            for (IHandler<T> handler : registry.findHandlers(type)) {
                T result = handler.read(registry, phase, tag, type, fieldName, object);
                if (result != null) {
                    return result;
                }
            }
        }
        return null;
    }

    public static <T> void setSingleField(Registry registry, Set<NBTAction> phase,
                                          CompoundTag tag, String fieldName,
                                          Type fieldType,
                                          T fieldData) throws InstantiationException, IllegalAccessException, IllegalArgumentException, NoHandlerFoundException {
        if (fieldData != null) {
            tag.remove(fieldName + NULL_POSTFIX);
            for (IHandler<T> handler : registry.findHandlers(fieldType)) {
                if (handler.store(registry, phase, tag, fieldType, fieldName, fieldData)) {
                    return;
                }
            }
            throw new NoHandlerFoundException(fieldType, fieldName);
        } else {
            tag.remove(fieldName);
            tag.putBoolean(fieldName + NULL_POSTFIX, true);
            return;
        }
    }

    private void cacheHandlers(Registry registry,
                               Class<?> clazz) throws IllegalAccessException, InstantiationException, NoHandlerFoundException {
        final ArrayList<Field> fieldList = new ArrayList<Field>();
        for (Field field : clazz.getDeclaredFields()) {
            Store annotation = field.getAnnotation(Store.class);
            if (annotation != null) {
                ArrayList<IHandler> handlerList = new ArrayList<IHandler>();
                String fieldName = field.getName();
                if (fieldName != null) {
                    Type fieldType = NullHelper.notnullJ(field.getGenericType(), "Field#getGenericType");
                    Class<? extends IHandler> handlerClass = annotation.handler();
                    if (handlerClass != NullHandler.class) {
                        IHandler handler = handlerClass.newInstance()
                            .getHandler(registry, fieldType);
                        if (handler != null) {
                            handlerList.add(handler);
                        } else {
                            throw new NoHandlerFoundException("Handler specified in annotation on " + field + " does not apply to " + fieldType + ".");
                        }
                    }
                    handlerList.addAll(registry.findHandlers(fieldType));
                    if (handlerList.isEmpty()) {
                        throw new NoHandlerFoundException(field, clazz);
                    }
                    EnumSet<NBTAction> enumSet = EnumSet.noneOf(NBTAction.class);
                    enumSet.addAll(Arrays.asList(annotation.value()));
                    phaseCache.put(field, enumSet);
                    field.setAccessible(true);
                    fieldList.add(field);
                    fieldHandlerCache.put(field, handlerList);
                }
            }
        }

        // Find factory method
        for (Method method : clazz.getDeclaredMethods()) {
            if (method.isAnnotationPresent(Factory.class)) {
                Preconditions.checkArgument(!factoryCache.containsKey(clazz), "Cannot have multiple factory methods on class", method);
                Preconditions.checkArgument(clazz.isAssignableFrom(method.getReturnType()), "Factory method return type must be assignable to the owner type", method);
                Preconditions.checkArgument(method.getParameterCount() == 0, "Factory method cannot take parameters", method);
                method.setAccessible(true);
                factoryCache.put(clazz, () -> method.invoke(null));
            }
        }

        // Find factory constructor
        try {
            Constructor<?> ctor = clazz.getDeclaredConstructor();
            boolean hasAnnotation = ctor.isAnnotationPresent(Factory.class);
            if (Modifier.isPublic(ctor.getModifiers()) || hasAnnotation) {
                if (!factoryCache.containsKey(clazz)) {
                    ctor.setAccessible(true);
                    factoryCache.put(clazz, ctor::newInstance);
                } else if (hasAnnotation) {
                    throw new IllegalArgumentException("Cannot have a Factory constructor and a Factory method in the same class (" + clazz + ")");
                }
            }
        } catch (NoSuchMethodException | SecurityException ignored) {
        }

        // Give helpful error if constructor is mis-annotated
        for (Constructor<?> ctor : clazz.getDeclaredConstructors()) {
            if (ctor.isAnnotationPresent(Factory.class)) {
                Preconditions.checkArgument(ctor.getParameterCount() == 0, "Factory constructor cannot take parameters", ctor);
            }
        }

        Class<?> superclazz = clazz.getSuperclass();
        if (superclazz != null) {
            Storable annotation = superclazz.getAnnotation(Storable.class);
            if (annotation != null) {
                if (annotation.handler() == HandleStorable.class) {
                    cacheHandlers(registry, superclazz);
                    fieldList.addAll(fieldCache.get(superclazz));
                } else {
                    superclassCache.put(clazz, superclazz);
                    if (!superclassCache.containsKey(superclazz)) {
                        superclassHandlerCache.put(superclazz, (List<IHandler>) Arrays.asList(annotation
                            .handler()
                            .newInstance()));
                    }
                }
            } else {
                List<IHandler> handlers = registry.findHandlers(superclazz);
                if (!handlers.isEmpty()) {
                    superclassCache.put(clazz, superclazz);
                    if (!superclassCache.containsKey(superclazz)) {
                        superclassHandlerCache.put(superclazz, handlers);
                    }
                }
            }
        }

        // Find callback methods
        List<AfterReadCallback> callbacks = new ArrayList<>();
        for (Method m : clazz.getDeclaredMethods()) {
            if (m.isAnnotationPresent(AfterRead.class)) {
                callbacks.add(new AfterReadCallback(m));
            }
        }
        callbackCache.put(clazz, callbacks);

        fieldCache.put(clazz, fieldList);
    }

    public Object instantiate_impl(Registry registry,
                                   Type type) throws IllegalArgumentException, IllegalAccessException, InstantiationException, NoHandlerFoundException {
        Class<?> clazz = TypeUtil.toClass(type);
        if (!fieldCache.containsKey(clazz)) {
            cacheHandlers(registry, clazz);
        }
        if (factoryCache.containsKey(clazz)) {
            ObjectFactory factory = factoryCache.get(clazz);
            try {
                Object result = factory.get();
                Preconditions.checkNotNull(result, "Factory methods cannot return null", clazz);
                return result;
            } catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
                throw new RuntimeException("Failed to invoke factory method or constructor on " + clazz, e);
            }
        }
        throw new IllegalArgumentException("No factory found for " + clazz);
    }

    public static <T> T instantiate(Registry registry,
                                    Type type) throws IllegalArgumentException, IllegalAccessException, InstantiationException, NoHandlerFoundException {
        return (T) INSTANCE.get()
            .instantiate_impl(registry, type);
    }

}
