package com.robertx22.age_of_exile.mmorpg.registers.client;

import com.robertx22.age_of_exile.mmorpg.ModRegistry;
import com.robertx22.age_of_exile.player_skills.items.backpacks.BackpackScreen;
import com.robertx22.age_of_exile.vanilla_mc.SkillGemsScreen;
import com.robertx22.age_of_exile.vanilla_mc.blocks.item_modify_station.ContainerGearModify;
import com.robertx22.age_of_exile.vanilla_mc.blocks.item_modify_station.GuiGearModify;
import com.robertx22.age_of_exile.vanilla_mc.blocks.repair_station.ContainerGearRepair;
import com.robertx22.age_of_exile.vanilla_mc.blocks.repair_station.GuiGearRepair;
import com.robertx22.age_of_exile.vanilla_mc.blocks.salvage_station.ContainerGearSalvage;
import com.robertx22.age_of_exile.vanilla_mc.blocks.salvage_station.GuiGearSalvage;
import com.robertx22.age_of_exile.vanilla_mc.blocks.socket_station.SocketStationContainer;
import com.robertx22.age_of_exile.vanilla_mc.blocks.socket_station.SocketStationGui;
import net.fabricmc.fabric.api.client.screen.ScreenProviderRegistry;
import net.fabricmc.fabric.api.client.screenhandler.v1.ScreenRegistry;
import net.minecraft.client.MinecraftClient;
import net.minecraft.text.LiteralText;

public class ContainerGuiRegisters {

    @SuppressWarnings("deprecation")
    public static void reg() {

        ScreenProviderRegistry.INSTANCE.<ContainerGearModify>registerFactory(ModRegistry.CONTAINERS.GEAR_MODIFY, x -> new GuiGearModify(x, MinecraftClient.getInstance().player.inventory, new LiteralText("")));
        ScreenProviderRegistry.INSTANCE.<ContainerGearRepair>registerFactory(ModRegistry.CONTAINERS.GEAR_REPAIR, x -> new GuiGearRepair(x, MinecraftClient.getInstance().player.inventory, new LiteralText("")));
        ScreenProviderRegistry.INSTANCE.<ContainerGearSalvage>registerFactory(ModRegistry.CONTAINERS.GEAR_SALVAGE, x -> new GuiGearSalvage(x, MinecraftClient.getInstance().player.inventory, new LiteralText("")));
        ScreenProviderRegistry.INSTANCE.<SocketStationContainer>registerFactory(ModRegistry.CONTAINERS.GEAR_SOCKET, x -> new SocketStationGui(x, MinecraftClient.getInstance().player.inventory, new LiteralText("")));

        ScreenRegistry.register(ModRegistry.CONTAINERS.BACKPACK_TYPE, BackpackScreen::new);
        ScreenRegistry.register(ModRegistry.CONTAINERS.SKILL_GEMS_TYPE, SkillGemsScreen::new);

    }

}
