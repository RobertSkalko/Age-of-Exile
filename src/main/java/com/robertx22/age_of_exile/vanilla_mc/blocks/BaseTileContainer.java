package com.robertx22.age_of_exile.vanilla_mc.blocks;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.screen.slot.Slot;
import net.minecraft.util.math.BlockPos;

public abstract class BaseTileContainer extends ScreenHandler {

    public int size = 0;

    public BlockPos pos;

    private static final int HOTBAR_SLOT_COUNT = 9;
    private static final int PLAYER_INVENTORY_ROW_COUNT = 3;
    private static final int PLAYER_INVENTORY_COLUMN_COUNT = 9;

    protected BaseTileContainer(int size, ScreenHandlerType<?> type, int id, PlayerInventory invPlayer) {
        super(type, id);
        this.size = size;

        final int PLAYER_INVENTORY_XPOS = 8;
        final int PLAYER_INVENTORY_YPOS = 125;

        final int SLOT_X_SPACING = 18;
        final int SLOT_Y_SPACING = 18;
        final int HOTBAR_YPOS = 183;
        // Add the players hotbar to the gui - the [xpos, ypos] location of each item
        for (int x = 0; x < HOTBAR_SLOT_COUNT; x++) {
            int slotNumber = x;
            addSlot(new Slot(invPlayer, slotNumber, PLAYER_INVENTORY_XPOS + SLOT_X_SPACING * x, HOTBAR_YPOS));
        }
        // Add the rest of the players inventory to the gui
        for (int y = 0; y < PLAYER_INVENTORY_ROW_COUNT; y++) {
            for (int x = 0; x < PLAYER_INVENTORY_COLUMN_COUNT; x++) {
                int slotNumber = HOTBAR_SLOT_COUNT + y * PLAYER_INVENTORY_COLUMN_COUNT + x;
                int xpos = PLAYER_INVENTORY_XPOS + x * SLOT_X_SPACING;
                int ypos = PLAYER_INVENTORY_YPOS + y * SLOT_Y_SPACING;
                addSlot(new Slot(invPlayer, slotNumber, xpos, ypos));
            }
        }

    }

    public boolean isPlayerInventory(int index) {
        return index >= getPlayerInvStart() && index <= getPlayerInvEnd();
    }

    public int getPlayerInvStart() {
        return 0;
    }

    public int getPlayerInvEnd() {
        return this.slots.size() - size - 1;
    }

    public int getContainerStart() {
        return this.slots.size() - size;
    }

    public int getContainerEnd() {
        return this.slots.size();
    }

    @Override
    public ItemStack transferSlot(PlayerEntity playerIn, int index) {

        ItemStack itemstack = ItemStack.EMPTY;

        try {
            Slot slot = this.slots.get(index);
            if (slot != null && slot.hasStack()) {
                ItemStack itemstack1 = slot.getStack();
                itemstack = itemstack1.copy();

                if (isPlayerInventory(index)) {
                    if (!this.insertItem(itemstack1, getContainerStart(), getContainerEnd(), true)) {
                        return ItemStack.EMPTY;
                    }
                } else if (!this.insertItem(itemstack1, getPlayerInvStart(), getPlayerInvEnd(), false)) {
                    return ItemStack.EMPTY;
                }

                if (itemstack1.isEmpty()) {
                    slot.setStack(ItemStack.EMPTY);
                } else {
                    slot.markDirty();
                }
            }
        } catch (Exception e) {
            //e.printStackTrace();
            return itemstack;
        }

        return itemstack;
    }

    // detectAndSendChanges is called every tick and can be used to get listeners and update, BUT it would take a huge rewrite and it's not worth it.

}