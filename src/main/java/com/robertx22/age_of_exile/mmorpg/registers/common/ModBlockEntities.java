package com.robertx22.age_of_exile.mmorpg.registers.common;

import com.robertx22.age_of_exile.mmorpg.ModRegistry;
import com.robertx22.age_of_exile.vanilla_mc.blocks.buff_station.ScribeBuffTile;
import com.robertx22.age_of_exile.vanilla_mc.blocks.cooking_station.CookingTile;
import com.robertx22.age_of_exile.vanilla_mc.blocks.item_modify_station.TileGearModify;
import com.robertx22.age_of_exile.vanilla_mc.blocks.repair_station.TileGearRepair;
import com.robertx22.age_of_exile.vanilla_mc.blocks.salvage_station.TileGearSalvage;
import com.robertx22.age_of_exile.vanilla_mc.blocks.socket_station.SocketStationBlockEntity;
import net.minecraft.block.Block;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.util.registry.Registry;

import java.util.function.Supplier;

public class ModBlockEntities {

    public BlockEntityType<TileGearModify> GEAR_MODIFY = of(ModRegistry.BLOCKS.GEAR_MODIFY, TileGearModify::new);
    public BlockEntityType<SocketStationBlockEntity> SOCKET_STATION = of(ModRegistry.BLOCKS.SOCKET_STATION, SocketStationBlockEntity::new);
    public BlockEntityType<TileGearRepair> GEAR_REPAIR = of(ModRegistry.BLOCKS.GEAR_REPAIR, TileGearRepair::new);
    public BlockEntityType<TileGearSalvage> GEAR_SALVAGE = of(ModRegistry.BLOCKS.GEAR_SALVAGE, TileGearSalvage::new);
    public BlockEntityType<ScribeBuffTile> SCRIBE_BUFF = of(ModRegistry.BLOCKS.SCRIBE_BUFF, ScribeBuffTile::new);
    public BlockEntityType<CookingTile> COOKING = of(ModRegistry.BLOCKS.COOKING_STATION, CookingTile::new);

    private <T extends BlockEntity> BlockEntityType<T> of(Block block, Supplier<T> en) {
        BlockEntityType<T> type = BlockEntityType.Builder.create(en, block)
            .build(null);
        return Registry.register(Registry.BLOCK_ENTITY_TYPE, Registry.BLOCK.getId(block)
            .toString(), type);

    }
}
