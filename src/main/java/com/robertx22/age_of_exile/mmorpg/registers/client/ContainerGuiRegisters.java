package com.robertx22.age_of_exile.mmorpg.registers.client;

import com.robertx22.age_of_exile.mmorpg.registers.common.ModContainers;
import com.robertx22.age_of_exile.player_skills.items.backpacks.BackpackScreen;
import com.robertx22.age_of_exile.vanilla_mc.blocks.alchemy.AlchemyScreen;
import com.robertx22.age_of_exile.vanilla_mc.blocks.cooking_station.CookingScreen;
import com.robertx22.age_of_exile.vanilla_mc.blocks.runeword_station.RuneWordStationGui;
import com.robertx22.age_of_exile.vanilla_mc.blocks.salvage_station.GuiGearSalvage;
import com.robertx22.age_of_exile.vanilla_mc.blocks.smithing.SmithingScreen;
import com.robertx22.age_of_exile.vanilla_mc.blocks.tablet.TabletStationScreen;
import net.minecraft.client.gui.ScreenManager;

public class ContainerGuiRegisters {

    public static void reg() {

        ScreenManager.register(ModContainers.SALVAGE.get(), GuiGearSalvage::new);
        ScreenManager.register(ModContainers.BACKPACK.get(), BackpackScreen::new);
        ScreenManager.register(ModContainers.SMITHING_STATION.get(), SmithingScreen::new);
        ScreenManager.register(ModContainers.COOKING_STATION.get(), CookingScreen::new);
        ScreenManager.register(ModContainers.ALCHEMY_STATION.get(), AlchemyScreen::new);
        ScreenManager.register(ModContainers.TABLET_STATION.get(), TabletStationScreen::new);
        ScreenManager.register(ModContainers.RUNEWORD.get(), RuneWordStationGui::new);

    }

}
