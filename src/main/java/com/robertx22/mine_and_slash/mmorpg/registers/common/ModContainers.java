package com.robertx22.mine_and_slash.mmorpg.registers.common;

import com.robertx22.mine_and_slash.vanilla_mc.blocks.item_modify_station.ContainerGearModify;
import com.robertx22.mine_and_slash.vanilla_mc.blocks.repair_station.ContainerGearRepair;
import com.robertx22.mine_and_slash.vanilla_mc.blocks.salvage_station.ContainerGearSalvage;
import com.robertx22.mine_and_slash.database.base.CreativeTabs;
import com.robertx22.mine_and_slash.vanilla_mc.items.bags.currency_bag.ContainerCurrencyBag;
import com.robertx22.mine_and_slash.mmorpg.Ref;
import net.minecraft.block.Block;
import net.minecraft.container.ContainerType;
import net.minecraft.item.Item;
import net.minecraftforge.common.extensions.IForgeContainerType;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.ObjectHolder;

@Mod.EventBusSubscriber(modid = Ref.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModContainers {

    public static DeferredRegister<ContainerType<?>> REG = new DeferredRegister<>(ForgeRegistries.CONTAINERS, Ref.MODID);

    public static RegistryObject<ContainerType<ContainerGearModify>> GEAR_MODIFY = REG.register(of(ModBlocks.GEAR_MODIFY), () -> IForgeContainerType.create(ContainerGearModify::new));
    public static RegistryObject<ContainerType<ContainerGearRepair>> GEAR_REPAIR = REG.register(of(ModBlocks.GEAR_REPAIR), () -> IForgeContainerType.create(ContainerGearRepair::new));
    public static RegistryObject<ContainerType<ContainerGearSalvage>> GEAR_SALVAGE = REG.register(of(ModBlocks.GEAR_SALVAGE), () -> IForgeContainerType.create(ContainerGearSalvage::new));

    static Item.Settings stationProp = new Item.Settings().group(CreativeTabs.MyModTab);

    static <T extends Block> String of(RegistryObject<T> block) {
        return block.getId()
            .getPath();
    }

    static final String CURRENCY_BAG_ID = Ref.MODID + ":" + "currency_bag";

    static final String PROFESSION_RECIPE_CONTAINER_ID = Ref.MODID + ":" + "profession_recipe_container";

    @ObjectHolder(CURRENCY_BAG_ID)
    public static final ContainerType<ContainerCurrencyBag> CURRENCY_BAG = null;

    @SubscribeEvent
    public static void registerContainers(
        final RegistryEvent.Register<ContainerType<?>> event) {

        IForgeRegistry<ContainerType<?>> r = event.getRegistry();

        r.register(new ContainerType<>(ContainerCurrencyBag::new).setRegistryName(CURRENCY_BAG_ID));

    }

}
