package com.robertx22.mine_and_slash.uncommon.datasaving;

import com.robertx22.mine_and_slash.capability.entity.EntityCap;
import com.robertx22.mine_and_slash.capability.entity.EntityCap.UnitData;
import com.robertx22.mine_and_slash.capability.player.PlayerSpellCap;
import com.robertx22.mine_and_slash.capability.player.PlayerStatsCap;
import com.robertx22.mine_and_slash.capability.world.AntiMobFarmCap;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.world.World;

public class Load {

    // TODO REIMPLEMENT TODO

    public static boolean hasUnit(Entity provider) {
        return true;
    }

    public static PlayerSpellCap.ISpellsCap spells(LivingEntity provider) {

        return new PlayerSpellCap.DefaultImpl();

    }

    public static UnitData Unit(Entity entity) {

        if (entity != null) {

            return new EntityCap.DefaultImpl();

        }
        return null;
    }

    public static PlayerStatsCap.IPlayerStatPointsData statPoints(PlayerEntity provider) {

        if (provider != null) {

            return new PlayerStatsCap.DefaultImpl();

        }
        return null;
    }

    public static AntiMobFarmCap.IAntiMobFarmData antiMobFarm(World provider) {

        if (provider != null) {
            return new AntiMobFarmCap.DefaultImpl();
        }
        return null;
    }

}
