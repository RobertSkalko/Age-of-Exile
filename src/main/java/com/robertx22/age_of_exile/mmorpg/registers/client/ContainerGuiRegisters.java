package com.robertx22.age_of_exile.mmorpg.registers.client;

import com.robertx22.age_of_exile.gui.screens.spell_hotbar_setup.HotbarSetupContainer;
import com.robertx22.age_of_exile.gui.screens.spell_hotbar_setup.SpellHotbatSetupScreen;
import com.robertx22.age_of_exile.mmorpg.ModRegistry;
import com.robertx22.age_of_exile.vanilla_mc.blocks.item_modify_station.ContainerGearModify;
import com.robertx22.age_of_exile.vanilla_mc.blocks.item_modify_station.GuiGearModify;
import com.robertx22.age_of_exile.vanilla_mc.blocks.repair_station.ContainerGearRepair;
import com.robertx22.age_of_exile.vanilla_mc.blocks.repair_station.GuiGearRepair;
import com.robertx22.age_of_exile.vanilla_mc.blocks.salvage_station.ContainerGearSalvage;
import com.robertx22.age_of_exile.vanilla_mc.blocks.salvage_station.GuiGearSalvage;
import com.robertx22.forgotten_magic.teleport.furnace.EssentiaFurnaceContainer;
import com.robertx22.forgotten_magic.teleport.furnace.EssentiaFurnaceGui;
import net.fabricmc.fabric.api.client.screen.ScreenProviderRegistry;
import net.minecraft.client.MinecraftClient;
import net.minecraft.text.LiteralText;

public class ContainerGuiRegisters {

    @SuppressWarnings("deprecation")
    public static void reg() {

        ScreenProviderRegistry.INSTANCE.<ContainerGearModify>registerFactory(ModRegistry.CONTAINERS.GEAR_MODIFY, x -> new GuiGearModify(x, MinecraftClient.getInstance().player.inventory, new LiteralText("")));
        ScreenProviderRegistry.INSTANCE.<ContainerGearRepair>registerFactory(ModRegistry.CONTAINERS.GEAR_REPAIR, x -> new GuiGearRepair(x, MinecraftClient.getInstance().player.inventory, new LiteralText("")));
        ScreenProviderRegistry.INSTANCE.<ContainerGearSalvage>registerFactory(ModRegistry.CONTAINERS.GEAR_SALVAGE, x -> new GuiGearSalvage(x, MinecraftClient.getInstance().player.inventory, new LiteralText("")));
        ScreenProviderRegistry.INSTANCE.<HotbarSetupContainer>registerFactory(ModRegistry.CONTAINERS.HOTBAR_SETUP, x -> new SpellHotbatSetupScreen(x, MinecraftClient.getInstance().player));
        ScreenProviderRegistry.INSTANCE.<EssentiaFurnaceContainer>registerFactory(ModRegistry.CONTAINERS.ESSENTIA_FURNACE, x -> new EssentiaFurnaceGui(x, MinecraftClient.getInstance().player.inventory, new LiteralText("")))
        ;

    }

}
