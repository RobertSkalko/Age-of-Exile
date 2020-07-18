package com.robertx22.mine_and_slash.mmorpg.registers.common;

import com.robertx22.mine_and_slash.vanilla_mc.blocks.item_modify_station.TileGearModify;
import com.robertx22.mine_and_slash.vanilla_mc.blocks.repair_station.TileGearRepair;
import com.robertx22.mine_and_slash.vanilla_mc.blocks.salvage_station.TileGearSalvage;
import com.robertx22.mine_and_slash.database.data.spells.blocks.holy_flower.HolyFlowerTileEntity;
import com.robertx22.mine_and_slash.database.data.spells.blocks.magma_flower.MagmaFlowerTileEntity;
import com.robertx22.mine_and_slash.database.data.spells.blocks.thorn_bush.ThornBushTileEntity;
import com.robertx22.mine_and_slash.mmorpg.Ref;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

@Mod.EventBusSubscriber(modid = Ref.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModTileEntities {

    public static DeferredRegister<BlockEntityType<?>> REG = new DeferredRegister<>(ForgeRegistries.TILE_ENTITIES, Ref.MODID);

    public static RegistryObject<BlockEntityType<TileGearModify>> GEAR_MODIFY =
        REG.register(ModBlocks.GEAR_MODIFY.getId()
                .getPath(),
            () -> BlockEntityType.Builder.create(TileGearModify::new, ModBlocks.GEAR_MODIFY.get())
                .build(null));

    public static RegistryObject<BlockEntityType<TileGearRepair>> GEAR_REPAIR =
        REG.register(ModBlocks.GEAR_REPAIR.getId()
                .getPath(),
            () -> BlockEntityType.Builder.create(TileGearRepair::new, ModBlocks.GEAR_REPAIR.get())
                .build(null));

    public static RegistryObject<BlockEntityType<TileGearSalvage>> GEAR_SALVAGE =
        REG.register(ModBlocks.GEAR_SALVAGE.getId()
                .getPath(),
            () -> BlockEntityType.Builder.create(TileGearSalvage::new, ModBlocks.GEAR_SALVAGE.get())
                .build(null));

    public static RegistryObject<BlockEntityType<MagmaFlowerTileEntity>> MAGMA_FLOWER =
        REG.register(ModBlocks.MAGMA_FLOWER.getId()
                .getPath(),
            () -> BlockEntityType.Builder.create(MagmaFlowerTileEntity::new, ModBlocks.MAGMA_FLOWER.get())
                .build(null));

    public static RegistryObject<BlockEntityType<HolyFlowerTileEntity>> HOLY_FLOWER =
        REG.register(ModBlocks.HOLY_FLOWER.getId()
                .getPath(),
            () -> BlockEntityType.Builder.create(HolyFlowerTileEntity::new, ModBlocks.HOLY_FLOWER.get())
                .build(null));

    public static RegistryObject<BlockEntityType<ThornBushTileEntity>> THORN_BUSH =
        REG.register(ModBlocks.THORN_BUSH.getId()
                .getPath(),
            () -> BlockEntityType.Builder.create(ThornBushTileEntity::new, ModBlocks.THORN_BUSH.get())
                .build(null));

}
