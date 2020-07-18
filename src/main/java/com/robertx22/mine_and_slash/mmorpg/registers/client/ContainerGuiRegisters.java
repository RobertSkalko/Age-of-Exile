package com.robertx22.mine_and_slash.mmorpg.registers.client;

import com.robertx22.mine_and_slash.vanilla_mc.blocks.item_modify_station.ContainerGearModify;
import com.robertx22.mine_and_slash.vanilla_mc.blocks.item_modify_station.GuiGearModify;
import com.robertx22.mine_and_slash.vanilla_mc.blocks.repair_station.ContainerGearRepair;
import com.robertx22.mine_and_slash.vanilla_mc.blocks.repair_station.GuiGearRepair;
import com.robertx22.mine_and_slash.vanilla_mc.blocks.salvage_station.ContainerGearSalvage;
import com.robertx22.mine_and_slash.vanilla_mc.blocks.salvage_station.GuiGearSalvage;
import com.robertx22.mine_and_slash.vanilla_mc.items.bags.currency_bag.ContainerCurrencyBag;
import com.robertx22.mine_and_slash.vanilla_mc.items.bags.currency_bag.GuiCurrencyBag;
import net.minecraft.client.gui.screen.Screens;
import com.robertx22.mine_and_slash.mmorpg.registers.common.ModContainers;

public class ContainerGuiRegisters {

    public static void reg() {

        // BLACKED "OBSOLTE" PARTS ARE NEEDED OR IT ERROS !!!!
        Screens.<ContainerGearModify, GuiGearModify>register(ModContainers.GEAR_MODIFY.get(), GuiGearModify::new);

        Screens.<ContainerGearRepair, GuiGearRepair>register(ModContainers.GEAR_REPAIR.get(), GuiGearRepair::new);
        Screens.<ContainerGearSalvage, GuiGearSalvage>register(ModContainers.GEAR_SALVAGE.get(), GuiGearSalvage::new);
        Screens.<ContainerCurrencyBag, GuiCurrencyBag>register(ModContainers.CURRENCY_BAG, GuiCurrencyBag::new);

    }

}
