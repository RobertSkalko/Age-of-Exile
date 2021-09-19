package com.robertx22.age_of_exile.uncommon.datasaving;

import com.robertx22.age_of_exile.capability.ChunkPopulatedCap;
import com.robertx22.age_of_exile.capability.entity.EntityData;
import com.robertx22.age_of_exile.capability.player.EntitySpellCap;
import com.robertx22.age_of_exile.capability.player.RPGPlayerData;
import com.robertx22.age_of_exile.dimension.dungeon_data.WorldDungeonCap;
import com.robertx22.age_of_exile.mmorpg.ModRegistry;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.world.World;
import net.minecraft.world.chunk.Chunk;

public class Load {

    public static EntitySpellCap.ISpellsCap spells(LivingEntity provider) {
        return ModRegistry.COMPONENTS.SPELLS.get(provider);
    }

    public static ChunkPopulatedCap chunkPopulated(Chunk chunk) {
        return ModRegistry.COMPONENTS.CHUNK_POPULATED.get(chunk);
    }

    public static EntityData Unit(Entity entity) {

        EntityData data = null;
        try {
            data = ModRegistry.COMPONENTS.UNIT_DATA.get(entity);
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (data == null) {
            System.out.println("Unit data is null? " + entity.getEntityName());
        }

        return data;
    }

    public static RPGPlayerData playerRPGData(PlayerEntity provider) {
        return ModRegistry.COMPONENTS.PLAYER_RPG_DATA.get(provider);
    }

    public static WorldDungeonCap dungeonData(World world) {
        return ModRegistry.COMPONENTS.DUNGEON_DATA.get(world);
    }

}
