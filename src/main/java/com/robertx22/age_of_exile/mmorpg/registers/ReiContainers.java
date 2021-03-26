package com.robertx22.age_of_exile.mmorpg.registers;

import com.robertx22.age_of_exile.mmorpg.registers.client.ReiPlugin;
import com.robertx22.age_of_exile.vanilla_mc.blocks.cooking_station.CookingContainer;
import com.robertx22.age_of_exile.vanilla_mc.blocks.cooking_station.CookingTile;
import me.shedaniel.rei.server.ContainerInfo;
import me.shedaniel.rei.server.ContainerInfoHandler;
import net.minecraft.screen.ScreenHandler;

public class ReiContainers implements Runnable {

    @Override
    public void run() {

        ContainerInfoHandler.registerContainerInfo(ReiPlugin.FOOD, new ContainerInfo<CookingContainer>() {
            @Override
            public Class<? extends ScreenHandler> getContainerClass() {
                return CookingContainer.class;
            }

            @Override
            public int getCraftingResultSlotIndex(CookingContainer screenHandler) {
                return CookingTile.OUTPUT_SLOTS.get(0);
            }

            @Override
            public int getCraftingWidth(CookingContainer screenHandler) {
                return 3;
            }

            @Override
            public int getCraftingHeight(CookingContainer screenHandler) {
                return 1;
            }
        });
    }
}
