package com.robertx22.age_of_exile.vanilla_mc.blocks.buff_station;

import com.robertx22.age_of_exile.vanilla_mc.blocks.BaseTileContainer;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.screen.slot.Slot;
import net.minecraft.util.math.BlockPos;

public class ScribeBuffContainer extends BaseTileContainer {

    public static final int SCRIBE_BUFF_SLOT_COUNT = 2;

    Inventory tile;

    public ScribeBuffContainer(int i, PlayerInventory invPlayer, Inventory inventory,
                               BlockPos pos) {

        super(SCRIBE_BUFF_SLOT_COUNT, null, i, invPlayer);
        this.tile = inventory;
        this.pos = pos;
        int count = 0;

        addSlot(new Slot(inventory, count++, 13, 56));
        addSlot(new Slot(inventory, count++, 33, 56));

    }

    @Override
    public boolean canUse(PlayerEntity player) {
        return tile.canPlayerUse(player);
    }

}