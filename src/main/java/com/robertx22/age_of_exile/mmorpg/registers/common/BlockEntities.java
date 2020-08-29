package com.robertx22.age_of_exile.mmorpg.registers.common;

import com.robertx22.age_of_exile.database.data.spells.blocks.holy_flower.HolyFlowerTileEntity;
import com.robertx22.age_of_exile.database.data.spells.blocks.magma_flower.MagmaFlowerTileEntity;
import com.robertx22.age_of_exile.database.data.spells.blocks.thorn_bush.ThornBushTileEntity;
import com.robertx22.age_of_exile.mmorpg.ModRegistry;
import com.robertx22.age_of_exile.vanilla_mc.blocks.item_modify_station.TileGearModify;
import com.robertx22.age_of_exile.vanilla_mc.blocks.repair_station.TileGearRepair;
import com.robertx22.age_of_exile.vanilla_mc.blocks.salvage_station.TileGearSalvage;
import com.robertx22.magic_mod.furnace.EssentiaFurnaceBlockEntity;
import net.minecraft.block.Block;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.util.registry.Registry;

import java.util.function.Supplier;

public class BlockEntities {

    public BlockEntityType<TileGearModify> GEAR_MODIFY = of(ModRegistry.BLOCKS.GEAR_MODIFY, TileGearModify::new);
    public BlockEntityType<TileGearRepair> GEAR_REPAIR = of(ModRegistry.BLOCKS.GEAR_REPAIR, TileGearRepair::new);
    public BlockEntityType<TileGearSalvage> GEAR_SALVAGE = of(ModRegistry.BLOCKS.GEAR_SALVAGE, TileGearSalvage::new);
    public BlockEntityType<MagmaFlowerTileEntity> MAGMA_FLOWER = of(ModRegistry.BLOCKS.MAGMA_FLOWER, MagmaFlowerTileEntity::new);
    public BlockEntityType<HolyFlowerTileEntity> HOLY_FLOWER = of(ModRegistry.BLOCKS.HOLY_FLOWER, HolyFlowerTileEntity::new);
    public BlockEntityType<ThornBushTileEntity> THORN_BUSH = of(ModRegistry.BLOCKS.THORN_BUSH, ThornBushTileEntity::new);
    public BlockEntityType<EssentiaFurnaceBlockEntity> ESSENTIA_FURNACE = of(ModRegistry.BLOCKS.ESSENTIA_FURNACE, EssentiaFurnaceBlockEntity::new);

    private <T extends BlockEntity> BlockEntityType<T> of(Block block, Supplier<T> en) {
        BlockEntityType<T> type = BlockEntityType.Builder.create(en, block)
            .build(null);
        return Registry.register(Registry.BLOCK_ENTITY_TYPE, Registry.BLOCK.getId(block)
            .toString(), type);

    }
}
