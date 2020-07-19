package com.robertx22.mine_and_slash.vanilla_mc.items.bags;

import net.minecraft.inventory.Inventories;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.collection.DefaultedList;

public class BaseInventory extends SimpleInventory {

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
        if (isEmpty()) {
            stack.removeSubTag("Items");
        } else {
            writeNBT(stack.getOrCreateTag());
        }
    }

    private void readNBT(CompoundTag compound) {
        final DefaultedList<ItemStack> list = DefaultedList.<ItemStack>ofSize(size(), ItemStack.EMPTY);
        Inventories.fromTag(compound, list);
        for (int i = 0; i < list.size(); i++) {
            setStack(i, list.get(i));
        }
    }

    private void writeNBT(CompoundTag compound) {
        final DefaultedList<ItemStack> list = DefaultedList.<ItemStack>ofSize(size(), ItemStack.EMPTY);
        for (int i = 0; i < list.size(); i++) {
            list.set(i, getStack(i));
        }
        Inventories.toTag(compound, list, false);
    }
}