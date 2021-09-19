package com.robertx22.age_of_exile.vanilla_mc.blocks.smithing;

import com.robertx22.age_of_exile.vanilla_mc.blocks.BaseTileContainer;
import com.robertx22.age_of_exile.vanilla_mc.blocks.bases.VanillaFuelSlot;
import com.robertx22.age_of_exile.vanilla_mc.blocks.cooking_station.CookingTile;
import com.robertx22.age_of_exile.vanilla_mc.blocks.slots.OutputSlot;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.container.Slot;
import net.minecraft.util.math.BlockPos;

public class SmithingContainer extends BaseTileContainer {

    IInventory tile;

    public SmithingContainer(int i, PlayerInventory invPlayer, IInventory inventory,
                             BlockPos pos) {

        super(CookingTile.totalSlots(), null, i, invPlayer);
        this.tile = inventory;
        this.pos = pos;
        int count = 0;

        tile.startOpen(invPlayer.player);

        addSlot(new Slot(inventory, count++, 37, 26));
        addSlot(new Slot(inventory, count++, 55, 26));
        addSlot(new Slot(inventory, count++, 73, 26));

        addSlot(new VanillaFuelSlot(inventory, count++, 55, 62)); // fuel
        addSlot(new OutputSlot(inventory, count++, 114, 43)); // output

    }

    @Override
    public boolean stillValid(PlayerEntity player) {
        return tile.stillValid(player);
    }

}
