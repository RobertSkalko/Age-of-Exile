package com.robertx22.age_of_exile.mmorpg.registers.common;

import com.robertx22.age_of_exile.capability.entity.EntityCap;
import com.robertx22.age_of_exile.capability.player.*;
import com.robertx22.age_of_exile.capability.player.data.PlayerDeathData;
import com.robertx22.age_of_exile.dimension.dungeon_data.WorldDungeonCap;
import com.robertx22.age_of_exile.dimension.player_data.PlayerMapsCap;
import com.robertx22.age_of_exile.mmorpg.Ref;
import nerdhub.cardinal.components.api.ComponentRegistry;
import nerdhub.cardinal.components.api.ComponentType;
import nerdhub.cardinal.components.api.event.EntityComponentCallback;
import nerdhub.cardinal.components.api.event.WorldComponentCallback;
import nerdhub.cardinal.components.api.util.EntityComponents;
import nerdhub.cardinal.components.api.util.RespawnCopyStrategy;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.Identifier;

@Deprecated
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
            .attach(EntityComponentCallback.event(PlayerEntity.class), x -> new PlayerSpellCap.DefaultImpl(x));

    public ComponentType<PlayerFavor> PLAYER_FAVOR =
        ComponentRegistry.INSTANCE.registerIfAbsent(
            PlayerFavor.RESOURCE,
            PlayerFavor.class)
            .attach(EntityComponentCallback.event(PlayerEntity.class), x -> new PlayerFavor(x));

    public ComponentType<PlayerMapsCap> PLAYER_MAPS =
        ComponentRegistry.INSTANCE.registerIfAbsent(
            PlayerMapsCap.RESOURCE,
            PlayerMapsCap.class)
            .attach(EntityComponentCallback.event(PlayerEntity.class), x -> new PlayerMapsCap(x));

    public ComponentType<PlayerStatPointsCap> STAT_POINTS =
        ComponentRegistry.INSTANCE.registerIfAbsent(
            PlayerStatPointsCap.RESOURCE,
            PlayerStatPointsCap.class)
            .attach(EntityComponentCallback.event(PlayerEntity.class), x -> new PlayerStatPointsCap(x));

    public ComponentType<TeamCap> TEAM =
        ComponentRegistry.INSTANCE.registerIfAbsent(
            TeamCap.RESOURCE,
            TeamCap.class)
            .attach(EntityComponentCallback.event(PlayerEntity.class), x -> new TeamCap(x));

    public ComponentType<PlayerCharCap> PLAYER_CHARACTERS =
        ComponentRegistry.INSTANCE.registerIfAbsent(
            PlayerCharCap.RESOURCE,
            PlayerCharCap.class)
            .attach(EntityComponentCallback.event(PlayerEntity.class), x -> new PlayerCharCap(x));

    public ComponentType<PlayerSkills> PLAYER_SKILLS =
        ComponentRegistry.INSTANCE.registerIfAbsent(
            PlayerSkills.RESOURCE,
            PlayerSkills.class)
            .attach(EntityComponentCallback.event(PlayerEntity.class), x -> new PlayerSkills(x));

    public ComponentType<EntityPerks> PERKS =
        ComponentRegistry.INSTANCE.registerIfAbsent(
            new Identifier(Ref.MODID, "perks"),
            EntityPerks.class)
            .attach(EntityComponentCallback.event(PlayerEntity.class), x -> new EntityPerks(x));

    public ComponentType<PlayerDeathData> PLAYER_DEATH_DATA =
        ComponentRegistry.INSTANCE.registerIfAbsent(
            new Identifier(Ref.MODID, "player_death_data"),
            PlayerDeathData.class)
            .attach(EntityComponentCallback.event(PlayerEntity.class), x -> new PlayerDeathData());

    public ComponentType<WorldDungeonCap> DUNGEON_DATA =
        ComponentRegistry.INSTANCE.registerIfAbsent(
            WorldDungeonCap.RESOURCE,
            WorldDungeonCap.class)
            .attach(WorldComponentCallback.EVENT, x -> new WorldDungeonCap(x));

    public ComponentRegisters() {

        EntityComponents.setRespawnCopyStrategy(UNIT_DATA, RespawnCopyStrategy.ALWAYS_COPY);
        EntityComponents.setRespawnCopyStrategy(PLAYER_MAPS, RespawnCopyStrategy.ALWAYS_COPY);
        EntityComponents.setRespawnCopyStrategy(PLAYER_SPELLS, RespawnCopyStrategy.ALWAYS_COPY);
        EntityComponents.setRespawnCopyStrategy(PERKS, RespawnCopyStrategy.ALWAYS_COPY);
        EntityComponents.setRespawnCopyStrategy(PLAYER_FAVOR, RespawnCopyStrategy.ALWAYS_COPY);
        EntityComponents.setRespawnCopyStrategy(PLAYER_CHARACTERS, RespawnCopyStrategy.ALWAYS_COPY);
        EntityComponents.setRespawnCopyStrategy(PLAYER_SKILLS, RespawnCopyStrategy.ALWAYS_COPY);
        EntityComponents.setRespawnCopyStrategy(PLAYER_DEATH_DATA, RespawnCopyStrategy.ALWAYS_COPY);
        EntityComponents.setRespawnCopyStrategy(STAT_POINTS, RespawnCopyStrategy.ALWAYS_COPY);
        EntityComponents.setRespawnCopyStrategy(TEAM, RespawnCopyStrategy.ALWAYS_COPY);

    }

}
