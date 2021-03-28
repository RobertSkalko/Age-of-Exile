package com.robertx22.age_of_exile.vanilla_mc.blocks.tablet;

import com.robertx22.age_of_exile.vanilla_mc.blocks.BaseTileContainer;
import com.robertx22.age_of_exile.vanilla_mc.blocks.bases.VanillaFuelSlot;
import com.robertx22.age_of_exile.vanilla_mc.blocks.cooking_station.CookingTile;
import com.robertx22.age_of_exile.vanilla_mc.blocks.slots.OutputSlot;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.screen.slot.Slot;
import net.minecraft.util.math.BlockPos;

public class TabletStationContainer extends BaseTileContainer {

    Inventory tile;

    public TabletStationContainer(int i, PlayerInventory invPlayer, Inventory inventory,
                                  BlockPos pos) {

        super(CookingTile.totalSlots(), null, i, invPlayer);
        this.tile = inventory;
        this.pos = pos;
        int count = 0;

        tile.onOpen(invPlayer.player);

        addSlot(new Slot(inventory, count++, 44, 17));
        addSlot(new Slot(inventory, count++, 44, 38));
        addSlot(new Slot(inventory, count++, 44, 59));

        addSlot(new VanillaFuelSlot(inventory, count++, 44, 98)); // fuel
        addSlot(new OutputSlot(inventory, count++, 121, 38)); // output

    }

    @Override
    public boolean canUse(PlayerEntity player) {
        return tile.canPlayerUse(player);
    }

}
