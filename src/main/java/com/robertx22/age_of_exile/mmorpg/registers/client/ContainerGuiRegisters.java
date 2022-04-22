package com.robertx22.age_of_exile.mmorpg.registers.client;

import com.robertx22.age_of_exile.mmorpg.registers.common.SlashContainers;
import com.robertx22.age_of_exile.player_skills.items.backpacks.BackpackScreen;
import com.robertx22.age_of_exile.player_skills.items.backpacks.mat_pouch.MatBagScreen;
import net.minecraft.client.gui.ScreenManager;

public class ContainerGuiRegisters {

    public static void reg() {

        ScreenManager.register(SlashContainers.BACKPACK.get(), BackpackScreen::new);
        ScreenManager.register(SlashContainers.MAT_POUCH.get(), MatBagScreen::new);

    }

}
