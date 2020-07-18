package com.robertx22.mine_and_slash.vanilla_mc.items.bags;

import net.minecraft.inventory.BasicInventory;
import net.minecraft.inventory.Inventories;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.DefaultedList;

public class BaseInventory extends BasicInventory {

    protected final ItemStack stack;

    public BaseInventory(ItemStack stack) {
        super(9 * 2);
        this.stack = stack;
        setup();
    }

    public void setup() {
        readItemStack();
    }

    public ItemStack getStack() {
        return stack;
    }

    public void readItemStack() {
        if (stack.getTag() != null) {
            readNBT(stack.getTag());
        }
    }

    public void writeItemStack() {
        if (isInvEmpty()) {
            stack.removeSubTag("Items");
        } else {
            writeNBT(stack.getOrCreateTag());
        }
    }

    private void readNBT(CompoundTag compound) {
        final DefaultedList<ItemStack> list = DefaultedList.<ItemStack>ofSize(getInvSize(), ItemStack.EMPTY);
        Inventories.fromTag(compound, list);
        for (int i = 0; i < list.size(); i++) {
            setInvStack(i, list.get(i));
        }
    }

    private void writeNBT(CompoundTag compound) {
        final DefaultedList<ItemStack> list = DefaultedList.<ItemStack>ofSize(getInvSize(), ItemStack.EMPTY);
        for (int i = 0; i < list.size(); i++) {
            list.set(i, getInvStack(i));
        }
        Inventories.toTag(compound, list, false);
    }
}