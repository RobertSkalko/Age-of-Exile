package com.robertx22.mine_and_slash.mmorpg.registers.common;

import com.robertx22.mine_and_slash.capability.entity.EntityCap;
import com.robertx22.mine_and_slash.capability.player.PlayerSpellCap;
import com.robertx22.mine_and_slash.capability.player.PlayerStatsCap;
import com.robertx22.mine_and_slash.capability.world.AntiMobFarmCap;
import net.minecraftforge.common.capabilities.CapabilityManager;

public class CapabilityRegister {
    public static void register() {

        CapabilityManager.INSTANCE.register(
            EntityCap.UnitData.class, new EntityCap.Storage(), EntityCap.DefaultImpl::new);

        CapabilityManager.INSTANCE.register(
            PlayerSpellCap.ISpellsCap.class, new PlayerSpellCap.Storage(), PlayerSpellCap.DefaultImpl::new);

        CapabilityManager.INSTANCE.register(
            AntiMobFarmCap.IAntiMobFarmData.class, new AntiMobFarmCap.Storage(), AntiMobFarmCap.DefaultImpl::new);

        CapabilityManager.INSTANCE.register(
            PlayerStatsCap.IPlayerStatPointsData.class, new PlayerStatsCap.Storage(), PlayerStatsCap.DefaultImpl::new);

    }

}
