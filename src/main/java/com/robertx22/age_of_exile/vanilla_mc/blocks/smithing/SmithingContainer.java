package com.robertx22.age_of_exile.vanilla_mc.blocks.smithing;

import com.robertx22.age_of_exile.vanilla_mc.blocks.BaseTileContainer;
import com.robertx22.age_of_exile.vanilla_mc.blocks.bases.VanillaFuelSlot;
import com.robertx22.age_of_exile.vanilla_mc.blocks.cooking_station.CookingTile;
import com.robertx22.age_of_exile.vanilla_mc.blocks.slots.OutputSlot;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.screen.slot.Slot;
import net.minecraft.util.math.BlockPos;

public class SmithingContainer extends BaseTileContainer {

    Inventory tile;

    public SmithingContainer(int i, PlayerInventory invPlayer, Inventory inventory,
                             BlockPos pos) {

        super(CookingTile.totalSlots(), null, i, invPlayer);
        this.tile = inventory;
        this.pos = pos;
        int count = 0;

        tile.onOpen(invPlayer.player);

        addSlot(new Slot(inventory, count++, 37, 26));
        addSlot(new Slot(inventory, count++, 55, 26));
        addSlot(new Slot(inventory, count++, 73, 26));

        addSlot(new VanillaFuelSlot(inventory, count++, 55, 62)); // fuel
        addSlot(new OutputSlot(inventory, count++, 114, 43)); // output

    }

    @Override
    public boolean canUse(PlayerEntity player) {
        return tile.canPlayerUse(player);
    }

}
