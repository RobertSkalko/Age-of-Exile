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
import net.minecraftforge.common.capabilities.ICapabilityProvider;



public class Load {

    public static boolean hasUnit(Entity provider) { // tterag said this is correct
        if (provider != null) {
            return provider.getCapability(EntityCap.Data)
                .isPresent();
        }
        return false;
    }


    public static PlayerSpellCap.ISpellsCap spells(LivingEntity provider) {

        if (provider instanceof PlayerEntity) {
            return provider.getCapability(PlayerSpellCap.Data)
                .orElse(new PlayerSpellCap.DefaultImpl());
        } else {
            return new PlayerSpellCap.DefaultImpl();

        }
    }

    public static UnitData Unit(Entity entity) {

        if (entity != null) {

            return entity.getCapability(EntityCap.Data)
                .orElse(new EntityCap.DefaultImpl());

        }
        return null;
    }

    public static PlayerStatsCap.IPlayerStatPointsData statPoints(PlayerEntity provider) {

        if (provider != null) {

            return provider.getCapability(PlayerStatsCap.Data)
                .orElse(new PlayerStatsCap.DefaultImpl());

        }
        return null;
    }

    public static AntiMobFarmCap.IAntiMobFarmData antiMobFarm(World provider) {

        if (provider != null) {
            return provider.getCapability(AntiMobFarmCap.Data)
                .orElse(new AntiMobFarmCap.DefaultImpl());
        }
        return null;
    }

}
