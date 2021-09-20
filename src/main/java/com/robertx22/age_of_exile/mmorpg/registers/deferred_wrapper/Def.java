package com.robertx22.age_of_exile.mmorpg.registers.deferred_wrapper;

import com.robertx22.age_of_exile.mmorpg.SlashRef;
import com.robertx22.library_of_exile.registry.IGUID;
import net.minecraft.block.Block;
import net.minecraft.entity.EntityType;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.item.Item;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.particles.ParticleType;
import net.minecraft.potion.Effect;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.fml.RegistryObject;

import java.util.function.Supplier;

public class Def {

    public static <T extends Block> RegObj<T> block(String id, Supplier<T> block) {
        RegistryObject<T> reg = SlashDeferred.BLOCKS.register(id, block);
        RegObj<T> obj = new RegObj<T>(reg);
        return obj;
    }

    public static <T extends Item & IGUID> RegObj<T> item(Supplier<T> object) {
        return item(object.get()
            .GUID(), object);
    }
    // todo not lazy

    public static <T extends Item> RegObj<T> item(Supplier<T> object, String id) {
        return item(id, object);
    }

    public static <T extends Item> RegObj<T> item(String id, Supplier<T> object) {
        RegistryObject<T> reg = SlashDeferred.ITEMS.register(id, object);
        RegObj<T> wrapper = new RegObj<T>(reg);
        return wrapper;
    }

    public static <T extends ParticleType<?>> RegObj<T> particle(String id, Supplier<T> object) {
        RegistryObject<T> reg = SlashDeferred.PARTICLES.register(id, object);
        RegObj<T> wrapper = new RegObj<T>(reg);
        return wrapper;
    }

    public static <T extends Effect> RegObj<T> potion(String id, Supplier<T> object) {
        RegistryObject<T> reg = SlashDeferred.POTIONS.register(id, object);
        RegObj<T> wrapper = new RegObj<T>(reg);
        return wrapper;
    }

    public static <T extends TileEntityType<?>> RegObj<T> blockEntity(String id, Supplier<T> object) {
        RegistryObject<T> reg = SlashDeferred.BLOCK_ENTITIES.register(id, object);
        RegObj<T> wrapper = new RegObj<T>(reg);
        return wrapper;
    }

    public static <T extends EntityType<?>> RegObj<T> entity(String id, Supplier<T> object) {
        RegistryObject<T> reg = SlashDeferred.ENTITIES.register(id, object);
        RegObj<T> wrapper = new RegObj<T>(reg);
        return wrapper;
    }

    public static <T extends IRecipeSerializer<?>> RegObj<T> recipeSer(String id, Supplier<T> object) {
        RegistryObject<T> reg = SlashDeferred.RECIPE_SERIALIZERS.register(id, object);
        RegObj<T> wrapper = new RegObj<T>(reg);
        return wrapper;
    }

    public static <T extends ContainerType<?>> RegObj<T> container(String id, Supplier<T> object) {
        RegistryObject<T> reg = SlashDeferred.CONTAINERS.register(id, object);
        RegObj<T> wrapper = new RegObj<T>(reg);
        return wrapper;
    }

    public static RegObj<SoundEvent> sound(String id) {
        Supplier<SoundEvent> sup = () -> new SoundEvent(new ResourceLocation(SlashRef.MODID, id));
        RegistryObject<SoundEvent> reg = SlashDeferred.SOUNDS.register(id, sup);
        RegObj<SoundEvent> wrapper = new RegObj<SoundEvent>(reg);
        return wrapper;
    }
}
