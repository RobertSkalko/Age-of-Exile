package com.robertx22.age_of_exile.mmorpg.registers;

import com.robertx22.age_of_exile.mmorpg.Ref;
import net.minecraft.block.Block;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.item.Item;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

import java.util.function.Supplier;

public class BaseReg {

    Identifier id(String id) {
        return new Identifier(Ref.MODID, id);
    }

    public <T extends Item> T item(T c, String id) {
        Registry.register(Registry.ITEM, new Identifier(Ref.MODID, id), c);
        return c;
    }

    public <T extends Block> T block(String id, T c) {
        Registry.register(Registry.BLOCK, new Identifier(Ref.MODID, id), c);
        return c;
    }

    public <T extends BlockEntity> BlockEntityType<T> blockEntity(Block block, Supplier<T> en) {
        BlockEntityType<T> type = BlockEntityType.Builder.create(en, block)
            .build(null);
        return Registry.register(Registry.BLOCK_ENTITY_TYPE, Registry.BLOCK.getId(block)
            .toString(), type);

    }

    public SoundEvent sound(String id) {
        SoundEvent sound = new SoundEvent(new Identifier(Ref.MODID, id));
        Registry.register(Registry.SOUND_EVENT, new Identifier(Ref.MODID, id), sound);
        return sound;
    }
}
