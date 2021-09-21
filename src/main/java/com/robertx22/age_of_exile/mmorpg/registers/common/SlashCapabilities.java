package com.robertx22.age_of_exile.mmorpg.registers.common;

import com.robertx22.age_of_exile.capability.ChunkPopulatedCap;
import com.robertx22.age_of_exile.capability.entity.EntityData;
import com.robertx22.age_of_exile.capability.player.EntitySpellCap;
import com.robertx22.age_of_exile.capability.player.RPGPlayerData;
import com.robertx22.age_of_exile.dimension.dungeon_data.WorldDungeonCap;
import com.robertx22.library_of_exile.components.PlayerCapabilities;
import net.minecraftforge.common.capabilities.CapabilityManager;

public class SlashCapabilities {

    public static void register() {

        CapabilityManager.INSTANCE.register(
            EntitySpellCap.ISpellsCap.class,
            new EntitySpellCap.Storage(),
            () -> {
                return new EntitySpellCap.SpellCap(null);
            });

        CapabilityManager.INSTANCE.register(
            RPGPlayerData.class,
            new RPGPlayerData.Storage(),
            () -> {
                return new RPGPlayerData(null);
            });

        CapabilityManager.INSTANCE.register(
            ChunkPopulatedCap.class,
            new ChunkPopulatedCap.Storage(),
            () -> {
                return new ChunkPopulatedCap();
            });

        CapabilityManager.INSTANCE.register(
            WorldDungeonCap.class,
            new WorldDungeonCap.Storage(),
            () -> {
                return new WorldDungeonCap(null);
            });

        CapabilityManager.INSTANCE.register(
            EntityData.class,
            new EntityData.Storage(),
            () -> {
                return new EntityData(null);
            });

        PlayerCapabilities.register(EntityData.Data, new EntityData(null)); // todo will forge's async screw with this?
        PlayerCapabilities.register(EntitySpellCap.Data, new EntitySpellCap.SpellCap(null)); // todo will forge's async screw with this?
        PlayerCapabilities.register(RPGPlayerData.Data, new RPGPlayerData(null)); // todo will forge's async screw with this?

    }
}
