package com.robertx22.age_of_exile.player_skills.items.backpacks;

import net.minecraft.inventory.Inventories;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.collection.DefaultedList;

public class BackpackInventory extends SimpleInventory {

    protected final ItemStack stack;

    int size;

    public BackpackInventory(ItemStack stack) {
        super(getSizeBackpack(stack));
        this.stack = stack;
        this.size = getSizeBackpack(stack);

        setup();
    }

    public static int getSizeBackpack(ItemStack stack) {
        BackpackItem backpack = (BackpackItem) stack.getItem();
        return 18 + backpack.tier.tier * 9;
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
        final DefaultedList<ItemStack> list = DefaultedList.ofSize(size(), ItemStack.EMPTY);
        Inventories.fromTag(compound, list);
        for (int i = 0; i < list.size(); i++) {
            setStack(i, list.get(i));
        }
    }

    private void writeNBT(CompoundTag compound) {
        final DefaultedList<ItemStack> list = DefaultedList.ofSize(size(), ItemStack.EMPTY);
        for (int i = 0; i < list.size(); i++) {
            list.set(i, getStack(i));
        }
        Inventories.toTag(compound, list, false);
    }

}