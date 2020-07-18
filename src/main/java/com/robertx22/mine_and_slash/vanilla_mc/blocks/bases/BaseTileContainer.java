package com.robertx22.mine_and_slash.vanilla_mc.blocks.bases;

import net.minecraft.container.Container;
import net.minecraft.container.ContainerType;
import net.minecraft.container.Slot;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;



public abstract class BaseTileContainer extends Container {

    public int size = 0;

    public BlockPos pos;

    protected BaseTileContainer(int size, ContainerType<?> type, int id) {
        super(type, id);
        this.size = size;
    }

    public boolean isPlayerInventory(int index) {
        return index < this.slots.size() - size;
    }

    public int getPlayerInvStart() {
        return 0;
    }

    public int getPlayerInvEnd() {
        return this.slots.size() - size;
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
