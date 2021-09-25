package com.robertx22.age_of_exile.mmorpg.registers.client;

import com.robertx22.age_of_exile.mmorpg.registers.common.SlashContainers;
import com.robertx22.age_of_exile.player_skills.crafting_inv.ProfCraftScreen;
import com.robertx22.age_of_exile.player_skills.items.backpacks.BackpackScreen;
import com.robertx22.age_of_exile.player_skills.items.backpacks.mat_pouch.MatBagScreen;
import com.robertx22.age_of_exile.vanilla_mc.blocks.runeword_station.RuneWordStationGui;
import com.robertx22.age_of_exile.vanilla_mc.blocks.salvage_station.GuiGearSalvage;
import net.minecraft.client.gui.ScreenManager;

public class ContainerGuiRegisters {

    public static void reg() {

        ScreenManager.register(SlashContainers.SALVAGE.get(), GuiGearSalvage::new);
        ScreenManager.register(SlashContainers.BACKPACK.get(), BackpackScreen::new);
        ScreenManager.register(SlashContainers.RUNEWORD.get(), RuneWordStationGui::new);
        ScreenManager.register(SlashContainers.MAT_POUCH.get(), MatBagScreen::new);
        ScreenManager.register(SlashContainers.PROF_CRAFTING.get(), ProfCraftScreen::new);

    }

}
