package com.robertx22.age_of_exile.mmorpg.registers.common;

import com.robertx22.age_of_exile.dimension.spawner.ModSpawnerBlockEntity;
import com.robertx22.age_of_exile.dimension.teleporter.TeleportedBlockEntity;
import com.robertx22.age_of_exile.dimension.teleporter.portal_block.PortalBlockEntity;
import com.robertx22.age_of_exile.mmorpg.ModRegistry;
import com.robertx22.age_of_exile.vanilla_mc.blocks.alchemy.AlchemyTile;
import com.robertx22.age_of_exile.vanilla_mc.blocks.buff_station.ScribeBuffTile;
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

public class ModBlockEntities {

    public TileEntityType<RuneWordStationTile> SOCKET_STATION = of(ModRegistry.BLOCKS.RUNEWORD_STATION, RuneWordStationTile::new);
    public TileEntityType<TileGearSalvage> GEAR_SALVAGE = of(ModRegistry.BLOCKS.GEAR_SALVAGE, TileGearSalvage::new);
    public TileEntityType<ScribeBuffTile> SCRIBE_BUFF = of(ModRegistry.BLOCKS.SCRIBE_BUFF, ScribeBuffTile::new);
    public TileEntityType<CookingTile> COOKING = of(ModRegistry.BLOCKS.COOKING_STATION, CookingTile::new);
    public TileEntityType<TabletStationTile> TABLET = of(ModRegistry.BLOCKS.TABLET_STATION, TabletStationTile::new);
    public TileEntityType<AlchemyTile> ALCHEMY_STATION = of(ModRegistry.BLOCKS.ALCHEMY_STATION, AlchemyTile::new);
    public TileEntityType<SmithingTile> SMITHING_STATION = of(ModRegistry.BLOCKS.SMITHING_STATION, SmithingTile::new);
    public TileEntityType<TeleportedBlockEntity> TELEPORTER = of(ModRegistry.BLOCKS.TELEPORTER, TeleportedBlockEntity::new);
    public TileEntityType<PortalBlockEntity> PORTAL = of(ModRegistry.BLOCKS.PORTAL, PortalBlockEntity::new);
    public TileEntityType<ModSpawnerBlockEntity> SPAWNER = of(ModRegistry.BLOCKS.SPAWNER, ModSpawnerBlockEntity::new);

    private <T extends TileEntity> TileEntityType<T> of(Block block, Supplier<T> en) {
        TileEntityType<T> type = TileEntityType.Builder.of(en, block)
            .build(null);
        return Registry.register(Registry.BLOCK_ENTITY_TYPE, Registry.BLOCK.getKey(block)
            .toString(), type);

    }
}
