package info.loenwind.autosave.handlers.java;

import info.loenwind.autosave.Registry;
import info.loenwind.autosave.exceptions.NoHandlerFoundException;
import info.loenwind.autosave.handlers.IHandler;
import info.loenwind.autosave.util.NBTAction;
import info.loenwind.autosave.util.NullHelper;
import info.loenwind.autosave.util.TypeUtil;
import net.minecraft.nbt.CompoundTag;

import java.lang.reflect.Array;
import java.lang.reflect.Type;
import java.util.Collections;
import java.util.List;
import java.util.Set;

@SuppressWarnings({
    "rawtypes",
    "unchecked"
})
public class HandleArrays implements IHandler<Object> {

    private final Type componentType;
    private final List<IHandler> componentHandlers;

    public HandleArrays() {
        this(Registry.GLOBAL_REGISTRY, null);
    }

    protected HandleArrays(Registry registry, Type componentType) {
        this.componentType = componentType;
        List<IHandler> handlers = NullHelper.notnullJ(Collections.emptyList(), "Collections.emptyList()");
        if (componentType != null) {
            try {
                handlers = registry.findHandlers(componentType);
            } catch (InstantiationException | IllegalAccessException e) {
            }
        }
        this.componentHandlers = handlers;
    }

    @Override

    public IHandler<?> getHandler(Registry registry, Type type) {
        Class<?> clazz = TypeUtil.toClass(type);
        if (clazz.isArray()) {
            return new HandleArrays(registry, clazz.getComponentType());
        }
        return null;
    }

    @Override
    public boolean store(Registry registry, Set<NBTAction> phase, CompoundTag nbt, Type type, String name, Object object)
        throws IllegalArgumentException, IllegalAccessException, InstantiationException, NoHandlerFoundException {
        CompoundTag tag = new CompoundTag();
        int size = Array.getLength(object);
        tag.putInt("size", size);
        for (int i = 0; i < size; i++) {
            Object elem = Array.get(object, i);
            if (elem != null) {
                for (IHandler handler : componentHandlers) {
                    if (handler.store(registry, phase, tag, type, i + "", elem)) {
                        break;
                    }
                }
            }
        }
        nbt.put(name, tag);
        return true;
    }

    @Override
    public Object read(Registry registry, Set<NBTAction> phase, CompoundTag nbt, Type type, String name, Object object)
        throws IllegalArgumentException, IllegalAccessException, InstantiationException, NoHandlerFoundException {
        Type compType = componentType;
        if (compType == null) {
            return null;
        }
        if (nbt.contains(name)) {
            CompoundTag tag = nbt.getCompound(name);
            int size = tag.getInt("size");

            if (object == null) {
                object = Array.newInstance(TypeUtil.toClass(compType), size);
            }

            for (int i = 0; i < size; i++) {
                if (tag.contains(i + "")) {
                    for (IHandler handler : componentHandlers) {
                        Object result = handler.read(registry, phase, tag, compType, i + "", null);
                        if (result != null) {
                            Array.set(object, i, result);
                            break;
                        }
                    }
                } else {
//          object.add(makeEmptyValueObject()); // TODO
                }
            }
        }
        return object;
    }

}
