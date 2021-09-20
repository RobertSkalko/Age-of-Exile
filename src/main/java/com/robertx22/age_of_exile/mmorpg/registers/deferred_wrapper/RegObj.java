package com.robertx22.age_of_exile.mmorpg.registers.deferred_wrapper;

import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.IForgeRegistryEntry;

public class RegObj<T extends IForgeRegistryEntry<? super T>> {

    public RegObj(RegistryObject<T> obj) {
        this.obj = obj;
    }

    private final RegistryObject<T> obj;

    public T get() {
        return obj.get();
    }

    public RegistryObject<T> getRegistryObject() {
        return obj;
    }


}
