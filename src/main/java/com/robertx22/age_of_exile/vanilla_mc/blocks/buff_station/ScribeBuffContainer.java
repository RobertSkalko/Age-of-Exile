package com.robertx22.age_of_exile.vanilla_mc.blocks.buff_station;

import com.robertx22.age_of_exile.vanilla_mc.blocks.BaseTileContainer;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;

public class ScribeBuffContainer extends BaseTileContainer {

    public static final int SCRIBE_BUFF_SLOT_COUNT = 2;

    IInventory tile;

    public ScribeBuffContainer(int i, PlayerInventory invPlayer, IInventory inventory,
                               BlockPos pos) {

        super(SCRIBE_BUFF_SLOT_COUNT, null, i, invPlayer);
        this.tile = inventory;
        this.pos = pos;
        int count = 0;

        tile.startOpen(invPlayer.player);

        addSlot(new Slot(inventory, count++, 13, 56) {
            @Override
            public boolean mayPlace(ItemStack stack) {
                return ScribeBuffTile.isValidPaper(stack);
            }
        });
        addSlot(new Slot(inventory, count++, 33, 56) {
            @Override
            public boolean mayPlace(ItemStack stack) {
                return ScribeBuffTile.isValidInk(stack);
            }
        });

    }

    @Override
    public boolean stillValid(PlayerEntity player) {
        return tile.stillValid(player);
    }

}