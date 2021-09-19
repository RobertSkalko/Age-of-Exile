package com.robertx22.age_of_exile.vanilla_mc.blocks.tablet;

import com.robertx22.age_of_exile.vanilla_mc.blocks.BaseTileContainer;
import com.robertx22.age_of_exile.vanilla_mc.blocks.bases.VanillaFuelSlot;
import com.robertx22.age_of_exile.vanilla_mc.blocks.slots.OutputSlot;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.container.Slot;
import net.minecraft.util.math.BlockPos;

public class TabletStationContainer extends BaseTileContainer {

    IInventory tile;

    public TabletStationContainer(int i, PlayerInventory invPlayer, IInventory inventory,
                                  BlockPos pos) {

        super(TabletStationTile.totalSlots(), null, i, invPlayer);
        this.tile = inventory;
        this.pos = pos;
        int count = 0;

        tile.startOpen(invPlayer.player);

        addSlot(new Slot(inventory, count++, 44, 17));
        addSlot(new Slot(inventory, count++, 44, 38));
        addSlot(new Slot(inventory, count++, 44, 59));

        addSlot(new VanillaFuelSlot(inventory, count++, 44, 98)); // fuel
        addSlot(new OutputSlot(inventory, count++, 121, 38)); // output

    }

    @Override
    public boolean stillValid(PlayerEntity player) {
        return tile.stillValid(player);
    }

}
