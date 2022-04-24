package com.robertx22.age_of_exile.uncommon.datasaving;

import com.robertx22.age_of_exile.capability.ChunkPopulatedCap;
import com.robertx22.age_of_exile.capability.entity.EntityData;
import com.robertx22.age_of_exile.capability.player.EntitySpellCap;
import com.robertx22.age_of_exile.capability.player.RPGPlayerData;
import com.robertx22.age_of_exile.dimension.dungeon_data.WorldDungeonCap;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.world.World;
import net.minecraft.world.chunk.Chunk;

public class Load {

    public static EntitySpellCap.SpellCap spells(LivingEntity provider) {
        return provider.getCapability(EntitySpellCap.Data)
            .orElse(null);
    }

    public static ChunkPopulatedCap chunkPopulated(Chunk chunk) {
        return chunk.getCapability(ChunkPopulatedCap.Data)
            .orElse(null);
    }

    public static EntityData Unit(Entity entity) {
        return entity.getCapability(EntityData.Data)
            .orElse(new EntityData((LivingEntity) entity));
    }

    public static RPGPlayerData playerRPGData(PlayerEntity player) {
        return player.getCapability(RPGPlayerData.Data)
            .orElse(null);
    }

    public static WorldDungeonCap dungeonData(World world) {
        return world.getCapability(WorldDungeonCap.Data)
            .orElse(null);
    }

}
