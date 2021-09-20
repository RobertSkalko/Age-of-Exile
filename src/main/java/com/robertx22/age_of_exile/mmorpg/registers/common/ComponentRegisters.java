package com.robertx22.age_of_exile.mmorpg.registers.common;

import com.robertx22.age_of_exile.capability.ChunkPopulatedCap;
import com.robertx22.age_of_exile.capability.entity.EntityData;
import com.robertx22.age_of_exile.capability.player.EntitySpellCap;
import com.robertx22.age_of_exile.capability.player.RPGPlayerData;
import com.robertx22.age_of_exile.dimension.dungeon_data.WorldDungeonCap;
import com.robertx22.age_of_exile.mmorpg.SlashRef;
import nerdhub.cardinal.components.api.ComponentRegistry;
import nerdhub.cardinal.components.api.ComponentType;
import nerdhub.cardinal.components.api.event.ChunkComponentCallback;
import nerdhub.cardinal.components.api.event.EntityComponentCallback;
import nerdhub.cardinal.components.api.event.WorldComponentCallback;
import nerdhub.cardinal.components.api.util.EntityComponents;
import nerdhub.cardinal.components.api.util.RespawnCopyStrategy;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ResourceLocation;

public class ComponentRegisters {

    public ComponentType<EntityData> UNIT_DATA =
        ComponentRegistry.INSTANCE.registerIfAbsent(
                new ResourceLocation(SlashRef.MODID, "unit_data"),
                EntityData.class)
            .attach(EntityComponentCallback.event(LivingEntity.class), x -> new EntityData(x));

    public ComponentType<EntitySpellCap.ISpellsCap> SPELLS =
        ComponentRegistry.INSTANCE.registerIfAbsent(
                new ResourceLocation(SlashRef.MODID, "spells"),
                EntitySpellCap.ISpellsCap.class)
            .attach(EntityComponentCallback.event(LivingEntity.class), x -> new EntitySpellCap.SpellCap(x));

    public ComponentType<ChunkPopulatedCap> CHUNK_POPULATED =
        ComponentRegistry.INSTANCE.registerIfAbsent(
                ChunkPopulatedCap.RESOURCE,
                ChunkPopulatedCap.class)
            .attach(ChunkComponentCallback.EVENT, x -> new ChunkPopulatedCap());

    public ComponentType<RPGPlayerData> PLAYER_RPG_DATA =
        ComponentRegistry.INSTANCE.registerIfAbsent(
                RPGPlayerData.RESOURCE,
                RPGPlayerData.class)
            .attach(EntityComponentCallback.event(PlayerEntity.class), x -> new RPGPlayerData(x));

    public ComponentType<WorldDungeonCap> DUNGEON_DATA =
        ComponentRegistry.INSTANCE.registerIfAbsent(
                WorldDungeonCap.RESOURCE,
                WorldDungeonCap.class)
            .attach(WorldComponentCallback.EVENT, x -> new WorldDungeonCap(x));

    public ComponentRegisters() {

        EntityComponents.setRespawnCopyStrategy(UNIT_DATA, RespawnCopyStrategy.ALWAYS_COPY);

        EntityComponents.setRespawnCopyStrategy(PLAYER_RPG_DATA, RespawnCopyStrategy.ALWAYS_COPY);

        EntityComponents.setRespawnCopyStrategy(SPELLS, RespawnCopyStrategy.ALWAYS_COPY);

    }

}
