package com.robertx22.mine_and_slash.uncommon.datasaving;

import com.robertx22.mine_and_slash.capability.entity.EntityCap.UnitData;
import com.robertx22.mine_and_slash.capability.player.PlayerSpellCap;
import com.robertx22.mine_and_slash.capability.player.PlayerStatsCap;
import com.robertx22.mine_and_slash.capability.world.AntiMobFarmCap;
import com.robertx22.mine_and_slash.mmorpg.ModRegistry;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.world.World;

public class Load {

    public static boolean hasUnit(Entity provider) {
        return true;
    }

    public static PlayerSpellCap.ISpellsCap spells(LivingEntity provider) {
        return ModRegistry.COMPONENTS.PLAYER_SPELLS.get(provider);
    }

    public static UnitData Unit(Entity entity) {
        return ModRegistry.COMPONENTS.UNIT_DATA.get(entity);
    }

    public static PlayerStatsCap.IPlayerStatPointsData statPoints(PlayerEntity provider) {
        return ModRegistry.COMPONENTS.PLAYER_STAT_POINTS.get(provider);
    }

    public static AntiMobFarmCap.IAntiMobFarmData antiMobFarm(World provider) {
        return ModRegistry.COMPONENTS.ANTI_MOB_FARM.get(provider);
    }

}
