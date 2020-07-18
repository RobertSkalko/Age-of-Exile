package com.robertx22.mine_and_slash.mmorpg.registers.common;

import com.robertx22.mine_and_slash.vanilla_mc.blocks.item_modify_station.BlockGearModify;
import com.robertx22.mine_and_slash.vanilla_mc.blocks.repair_station.BlockGearRepair;
import com.robertx22.mine_and_slash.vanilla_mc.blocks.salvage_station.BlockGearSalvage;
import com.robertx22.mine_and_slash.database.data.spells.blocks.holy_flower.HolyFlowerBlock;
import com.robertx22.mine_and_slash.database.data.spells.blocks.magma_flower.MagmaFlowerBlock;
import com.robertx22.mine_and_slash.database.data.spells.blocks.thorn_bush.ThornBushBlock;
import com.robertx22.mine_and_slash.mmorpg.Ref;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.IForgeRegistry;

@Mod.EventBusSubscriber(modid = Ref.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModBlocks {

    public static DeferredRegister<Block> REG = new DeferredRegister<>(ForgeRegistries.BLOCKS, Ref.MODID);

    public static RegistryObject<BlockGearModify> GEAR_MODIFY = REG.register("modify_station", BlockGearModify::new);
    public static RegistryObject<BlockGearSalvage> GEAR_SALVAGE = REG.register("salvage_station", BlockGearSalvage::new);
    public static RegistryObject<BlockGearRepair> GEAR_REPAIR = REG.register("repair_station", BlockGearRepair::new);

    public static RegistryObject<MagmaFlowerBlock> MAGMA_FLOWER = REG.register("magma_flower", MagmaFlowerBlock::new);
    public static RegistryObject<HolyFlowerBlock> HOLY_FLOWER = REG.register("holy_flower", HolyFlowerBlock::new);
    public static RegistryObject<ThornBushBlock> THORN_BUSH = REG.register("thorn_bush", ThornBushBlock::new);

    @SubscribeEvent
    public static void registerBlocks(RegistryEvent.Register<Block> event) {
        IForgeRegistry<Block> reg = event.getRegistry();

    }

    @SubscribeEvent
    public static void registerItems(RegistryEvent.Register<Item> event) {
        IForgeRegistry<Item> reg = event.getRegistry();

    }
}
