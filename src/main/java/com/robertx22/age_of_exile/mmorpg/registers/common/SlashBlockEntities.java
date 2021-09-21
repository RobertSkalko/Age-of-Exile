package com.robertx22.age_of_exile.mmorpg.registers.common;

import com.robertx22.age_of_exile.dimension.spawner.ModSpawnerBlockEntity;
import com.robertx22.age_of_exile.dimension.teleporter.TeleportedBlockEntity;
import com.robertx22.age_of_exile.dimension.teleporter.portal_block.PortalBlockEntity;
import com.robertx22.age_of_exile.mmorpg.registers.deferred_wrapper.Def;
import com.robertx22.age_of_exile.mmorpg.registers.deferred_wrapper.RegObj;
import com.robertx22.age_of_exile.vanilla_mc.blocks.alchemy.AlchemyTile;
import com.robertx22.age_of_exile.vanilla_mc.blocks.cooking_station.CookingTile;
import com.robertx22.age_of_exile.vanilla_mc.blocks.runeword_station.RuneWordStationTile;
import com.robertx22.age_of_exile.vanilla_mc.blocks.salvage_station.TileGearSalvage;
import com.robertx22.age_of_exile.vanilla_mc.blocks.smithing.SmithingTile;
import com.robertx22.age_of_exile.vanilla_mc.blocks.tablet.TabletStationTile;
import net.minecraft.tileentity.TileEntityType;

public class SlashBlockEntities {

    public static void init() {

    }

    public static RegObj<TileEntityType<RuneWordStationTile>> RUNEWORD = Def.blockEntity("runeword", () -> TileEntityType.Builder.of(RuneWordStationTile::new, SlashBlocks.RUNEWORD.get())
        .build(null));
    public static RegObj<TileEntityType<CookingTile>> COOKING = Def.blockEntity("cooking", () -> TileEntityType.Builder.of(CookingTile::new, SlashBlocks.COOKING_STATION.get())
        .build(null));
    public static RegObj<TileEntityType<TileGearSalvage>> SALVAGE = Def.blockEntity("salvage", () -> TileEntityType.Builder.of(TileGearSalvage::new, SlashBlocks.GEAR_SALVAGE.get())
        .build(null));
    public static RegObj<TileEntityType<TabletStationTile>> TABLET = Def.blockEntity("tablet", () -> TileEntityType.Builder.of(TabletStationTile::new, SlashBlocks.TABLET_STATION.get())
        .build(null));
    public static RegObj<TileEntityType<AlchemyTile>> ALCHEMY_STATION = Def.blockEntity("alchemy", () -> TileEntityType.Builder.of(AlchemyTile::new, SlashBlocks.ALCHEMY_STATION.get())
        .build(null));
    public static RegObj<TileEntityType<SmithingTile>> SMITHING_STATION = Def.blockEntity("smithing", () -> TileEntityType.Builder.of(SmithingTile::new, SlashBlocks.SMITHING_STATION.get())
        .build(null));
    public static RegObj<TileEntityType<TeleportedBlockEntity>> TELEPORTER = Def.blockEntity("teleporter", () -> TileEntityType.Builder.of(TeleportedBlockEntity::new, SlashBlocks.TELEPORTER.get())
        .build(null));
    public static RegObj<TileEntityType<PortalBlockEntity>> PORTAL = Def.blockEntity("portal", () -> TileEntityType.Builder.of(PortalBlockEntity::new, SlashBlocks.PORTAL.get())
        .build(null));
    public static RegObj<TileEntityType<ModSpawnerBlockEntity>> SPAWNER = Def.blockEntity("spawner", () -> TileEntityType.Builder.of(ModSpawnerBlockEntity::new, SlashBlocks.SPAWNER.get())
        .build(null));

}
