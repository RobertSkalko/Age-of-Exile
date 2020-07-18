package com.robertx22.mine_and_slash.vanilla_mc.items.bags;

import net.minecraft.container.Container;
import net.minecraft.container.ContainerType;
import net.minecraft.container.Slot;
import net.minecraft.container.SlotActionType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.BasicInventory;
import net.minecraft.item.ItemStack;




public abstract class BaseContainer extends Container {

    public BaseInventory inventory;

    public abstract BaseSlot slot(BasicInventory inv, int index, int x, int y);

    public static int size = 9 * 2;
    public static int numRows = 2;

    public int bagHash;

    public BaseContainer( ContainerType<?> type, int id,
                         PlayerInventory playerInv, BaseInventory basebag) {
        super(type, id);

        this.inventory = basebag;
        this.bagHash = basebag.getStack()
            .hashCode();

        int i = (this.numRows - 4) * 18;

        for (int j = 0; j < this.numRows; ++j) {
            for (int k = 0; k < 9; ++k) {
                this.addSlot(this.slot(inventory, k + j * 9, 8 + k * 18, 18 + j * 18));
            }
        }

        for (int l = 0; l < 3; ++l) {
            for (int j1 = 0; j1 < 9; ++j1) {
                this.addSlot(new Slot(playerInv, j1 + l * 9 + 9, 8 + j1 * 18, 103 + l * 18 + i));
            }
        }

        for (int i1 = 0; i1 < 9; ++i1) {
            this.addSlot(new Slot(playerInv, i1, 8 + i1 * 18, 161 + i));
        }

    }

    @Override
    public ItemStack onSlotClick(int slotId, int dragType, SlotActionType clickTypeIn,
                               PlayerEntity player) {
        try {
            if (this.getSlot(slotId)
                .getStack()
                .getItem() instanceof BaseBagItem) {
                return ItemStack.EMPTY;
            }
        } catch (Exception e) {
        }

        return super.onSlotClick(slotId, dragType, clickTypeIn, player);
    }

    @Override
    public void sendContentUpdates() {
        super.sendContentUpdates();

        inventory.writeItemStack();

    }

    @Override
    public boolean canUse( PlayerEntity player) {
        ItemStack held = player.getMainHandStack();

        return held == this.inventory.getStack() && this.inventory.getStack()
            .isEmpty() == false && held.hashCode() == this.bagHash && held.getItem() instanceof BaseBagItem;
    }


    @Override
    public ItemStack transferSlot(PlayerEntity playerIn, int index) {
        ItemStack itemstack = ItemStack.EMPTY;
        Slot slot = this.slots.get(index);

        if (slot != null && slot.hasStack()) {
            ItemStack itemstack1 = slot.getStack();
            itemstack = itemstack1.copy();

            if (index < this.numRows * 9) {
                if (!this.insertItem(itemstack1, this.numRows * 9, this.slots
                    .size(), true)) {
                    return ItemStack.EMPTY;
                }
            } else if (!this.insertItem(itemstack1, 0, this.numRows * 9, false)) {
                return ItemStack.EMPTY;
            }

            if (itemstack1.isEmpty()) {
                slot.setStack(ItemStack.EMPTY);
            } else {
                slot.markDirty();
            }
        }

        return itemstack;
    }
}