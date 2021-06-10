package com.robertx22.age_of_exile.uncommon.datasaving;

import com.robertx22.age_of_exile.capability.ChunkPopulatedCap;
import com.robertx22.age_of_exile.capability.entity.EntityCap.UnitData;
import com.robertx22.age_of_exile.capability.player.*;
import com.robertx22.age_of_exile.dimension.dungeon_data.WorldDungeonCap;
import com.robertx22.age_of_exile.dimension.player_data.PlayerMapsCap;
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

    public static UnitData Unit(Entity entity) {

        UnitData data = null;
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

    public static PlayerFavor favor(PlayerEntity provider) {
        return ModRegistry.COMPONENTS.PLAYER_FAVOR.get(provider);
    }

    public static PlayerMapsCap playerMaps(PlayerEntity provider) {
        return ModRegistry.COMPONENTS.PLAYER_MAPS.get(provider);
    }

    public static TeamCap team(PlayerEntity provider) {
        return ModRegistry.COMPONENTS.TEAM.get(provider);
    }

    public static PlayerStatPointsCap statPoints(PlayerEntity provider) {
        return ModRegistry.COMPONENTS.STAT_POINTS.get(provider);
    }

    public static WorldDungeonCap dungeonData(World world) {
        return ModRegistry.COMPONENTS.DUNGEON_DATA.get(world);
    }

    public static PlayerLoadoutsCap loadouts(PlayerEntity provider) {
        return ModRegistry.COMPONENTS.PLAYER_LOADOUTS.get(provider);
    }

    public static PlayerSkills playerSkills(PlayerEntity provider) {
        return ModRegistry.COMPONENTS.PLAYER_SKILLS.get(provider);
    }

    public static EntityPerks perks(Entity provider) {
        return ModRegistry.COMPONENTS.PERKS.get(provider);
    }
}
