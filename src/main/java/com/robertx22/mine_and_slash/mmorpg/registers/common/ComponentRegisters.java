package com.robertx22.mine_and_slash.mmorpg.registers.common;

import com.robertx22.mine_and_slash.capability.entity.EntityCap;
import com.robertx22.mine_and_slash.capability.player.PlayerSpellCap;
import com.robertx22.mine_and_slash.capability.player.PlayerStatsCap;
import com.robertx22.mine_and_slash.capability.world.AntiMobFarmCap;
import com.robertx22.mine_and_slash.mmorpg.Ref;
import nerdhub.cardinal.components.api.ComponentRegistry;
import nerdhub.cardinal.components.api.ComponentType;
import nerdhub.cardinal.components.api.event.EntityComponentCallback;
import nerdhub.cardinal.components.api.event.WorldComponentCallback;
import nerdhub.cardinal.components.api.util.EntityComponents;
import nerdhub.cardinal.components.api.util.RespawnCopyStrategy;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.Identifier;

public class ComponentRegisters {

    public ComponentType<EntityCap.UnitData> UNIT_DATA =
        ComponentRegistry.INSTANCE.registerIfAbsent(
            new Identifier(Ref.MODID, "unit_data"),
            EntityCap.UnitData.class)
            .attach(EntityComponentCallback.event(LivingEntity.class), x -> new EntityCap.DefaultImpl(x));

    public ComponentType<PlayerSpellCap.ISpellsCap> PLAYER_SPELLS =
        ComponentRegistry.INSTANCE.registerIfAbsent(
            new Identifier(Ref.MODID, "spells"),
            PlayerSpellCap.ISpellsCap.class)
            .attach(EntityComponentCallback.event(PlayerEntity.class), x -> new PlayerSpellCap.DefaultImpl());

    public ComponentType<PlayerStatsCap.IPlayerStatPointsData> PLAYER_STAT_POINTS =
        ComponentRegistry.INSTANCE.registerIfAbsent(
            new Identifier(Ref.MODID, "stat_points"),
            PlayerStatsCap.IPlayerStatPointsData.class)
            .attach(EntityComponentCallback.event(PlayerEntity.class), x -> new PlayerStatsCap.DefaultImpl());

    public ComponentType<AntiMobFarmCap.IAntiMobFarmData> ANTI_MOB_FARM =
        ComponentRegistry.INSTANCE.registerIfAbsent(
            new Identifier(Ref.MODID, "anti_mob_farm"),
            AntiMobFarmCap.IAntiMobFarmData.class)
            .attach(WorldComponentCallback.EVENT, x -> new AntiMobFarmCap.DefaultImpl());

    public ComponentRegisters() {
        EntityComponents.setRespawnCopyStrategy(UNIT_DATA, RespawnCopyStrategy.ALWAYS_COPY);
        EntityComponents.setRespawnCopyStrategy(PLAYER_SPELLS, RespawnCopyStrategy.ALWAYS_COPY);
        EntityComponents.setRespawnCopyStrategy(PLAYER_STAT_POINTS, RespawnCopyStrategy.ALWAYS_COPY);
    }

}
