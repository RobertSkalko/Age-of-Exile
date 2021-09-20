package com.robertx22.age_of_exile.mmorpg.registers.common;

import com.robertx22.age_of_exile.dimension.spawner.ModSpawnerBlockEntity;
import com.robertx22.age_of_exile.dimension.teleporter.TeleportedBlockEntity;
import com.robertx22.age_of_exile.dimension.teleporter.portal_block.PortalBlockEntity;
import com.robertx22.age_of_exile.mmorpg.ModRegistry;
import com.robertx22.age_of_exile.mmorpg.registers.deferred_wrapper.Def;
import com.robertx22.age_of_exile.mmorpg.registers.deferred_wrapper.RegObj;
import com.robertx22.age_of_exile.vanilla_mc.blocks.alchemy.AlchemyTile;
import com.robertx22.age_of_exile.vanilla_mc.blocks.cooking_station.CookingTile;
import com.robertx22.age_of_exile.vanilla_mc.blocks.runeword_station.RuneWordStationTile;
import com.robertx22.age_of_exile.vanilla_mc.blocks.salvage_station.TileGearSalvage;
import com.robertx22.age_of_exile.vanilla_mc.blocks.smithing.SmithingTile;
import com.robertx22.age_of_exile.vanilla_mc.blocks.tablet.TabletStationTile;
import net.minecraft.block.Block;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.registry.Registry;

import java.util.function.Supplier;

public class SlashBlockEntities {

    public static void init() {

    }

    public static RegObj<TileEntityType<RuneWordStationTile>> RUNEWORD = of(ModRegistry.BLOCKS.RUNEWORD.get(), RuneWordStationTile::new);
    public static RegObj<TileEntityType<TileGearSalvage>> GEAR_SALVAGE = of(ModRegistry.BLOCKS.GEAR_SALVAGE.get(), TileGearSalvage::new);
    public static RegObj<TileEntityType<CookingTile>> COOKING = of(ModRegistry.BLOCKS.COOKING_STATION.get(), CookingTile::new);
    public static RegObj<TileEntityType<TabletStationTile>> TABLET = of(ModRegistry.BLOCKS.TABLET_STATION.get(), TabletStationTile::new);
    public static RegObj<TileEntityType<AlchemyTile>> ALCHEMY_STATION = of(ModRegistry.BLOCKS.ALCHEMY_STATION.get(), AlchemyTile::new);
    public static RegObj<TileEntityType<SmithingTile>> SMITHING_STATION = of(ModRegistry.BLOCKS.SMITHING_STATION.get(), SmithingTile::new);
    public static RegObj<TileEntityType<TeleportedBlockEntity>> TELEPORTER = of(ModRegistry.BLOCKS.TELEPORTER.get(), TeleportedBlockEntity::new);
    public static RegObj<TileEntityType<PortalBlockEntity>> PORTAL = of(ModRegistry.BLOCKS.PORTAL.get(), PortalBlockEntity::new);
    public static RegObj<TileEntityType<ModSpawnerBlockEntity>> SPAWNER = of(ModRegistry.BLOCKS.SPAWNER.get(), ModSpawnerBlockEntity::new);

    private static <T extends TileEntity> RegObj<TileEntityType<T>> of(Block block, Supplier<T> en) {
        TileEntityType<T> type = TileEntityType.Builder.of(en, block)
            .build(null);

        return Def.blockEntity(Registry.BLOCK.getKey(block)
            .getPath(), type);

    }
}
