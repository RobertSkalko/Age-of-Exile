package com.robertx22.age_of_exile.mmorpg.registers.deferred_wrapper;

import com.robertx22.age_of_exile.mmorpg.registers.common.SlashDeferred;
import com.robertx22.library_of_exile.registry.IGUID;
import net.minecraft.block.Block;
import net.minecraft.entity.EntityType;
import net.minecraft.item.Item;
import net.minecraft.particles.ParticleType;
import net.minecraft.tileentity.TileEntityType;
import net.minecraftforge.fml.RegistryObject;

public class Def {

    public static <T extends Block> RegObj<T> block(String id, T block) {
        RegistryObject<T> reg = SlashDeferred.BLOCKS.register(id, () -> block);
        RegObj<T> obj = new RegObj<T>(reg);
        return obj;
    }

    public static <T extends Item & IGUID> RegObj<T> item(T object) {
        return item(object.GUID(), object);
    }

    public static <T extends Item> RegObj<T> item(T object, String id) {
        return item(id, object);
    }

    public static <T extends Item> RegObj<T> item(String id, T object) {
        RegistryObject<T> reg = SlashDeferred.ITEMS.register(id, () -> object);
        RegObj<T> wrapper = new RegObj<T>(reg);
        return wrapper;
    }

    public static <T extends ParticleType<?>> RegObj<T> particle(String id, T object) {
        RegistryObject<T> reg = SlashDeferred.PARTICLES.register(id, () -> object);
        RegObj<T> wrapper = new RegObj<T>(reg);
        return wrapper;
    }

    public static <T extends TileEntityType<?>> RegObj<T> blockEntity(String id, T object) {
        RegistryObject<T> reg = SlashDeferred.BLOCK_ENTITIES.register(id, () -> object);
        RegObj<T> wrapper = new RegObj<T>(reg);
        return wrapper;
    }

    public static <T extends EntityType<?>> RegObj<T> entity(String id, T object) {
        RegistryObject<T> reg = SlashDeferred.ENTITIES.register(id, () -> object);
        RegObj<T> wrapper = new RegObj<T>(reg);
        return wrapper;
    }
}
