package com.robertx22.age_of_exile.vanilla_mc.blocks.alchemy;

import com.robertx22.age_of_exile.vanilla_mc.blocks.BaseTileContainer;
import com.robertx22.age_of_exile.vanilla_mc.blocks.slots.OutputSlot;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.screen.slot.Slot;
import net.minecraft.util.math.BlockPos;

public class AlchemyContainer extends BaseTileContainer {

    Inventory tile;

    public AlchemyContainer(int i, PlayerInventory invPlayer, Inventory inventory,
                            BlockPos pos) {

        super(AlchemyTile.totalSlots(), null, i, invPlayer);
        this.tile = inventory;
        this.pos = pos;
        int count = 0;

        tile.onOpen(invPlayer.player);

        addSlot(new Slot(inventory, count++, 57, 38));
        addSlot(new Slot(inventory, count++, 80, 31));
        addSlot(new Slot(inventory, count++, 103, 38));

        addSlot(new OutputSlot(inventory, count++, 80, 72)); // output

    }

    @Override
    public boolean canUse(PlayerEntity player) {
        return tile.canPlayerUse(player);
    }

}
