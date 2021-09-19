package com.robertx22.age_of_exile.player_skills.items.backpacks;

import net.minecraft.core.NonNullList;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.world.ContainerHelper;

public class BackpackInventory extends Inventory {

    protected final ItemStack stack;

    int size;
    PlayerEntity player;

    public BackpackInventory(PlayerEntity player, ItemStack stack) {
        super(getMaxSize());
        this.stack = stack;
        this.size = getMaxSize();
        this.player = player;

        setup();
    }

    public static int getSizeBackpack(ItemStack stack) {
        BackpackInfo info = BackpackContainer.getInfo(null, stack);
        return getSizeBackpack(info.extraRows);
    }

    public static int getSizeBackpack(int extraRows) {
        return 9 + extraRows * 9;
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
            stack.removeTagKey("Items");
        } else {
            writeNBT(stack.getOrCreateTag());
        }
    }

    public static int getMaxSize() {
        return getSizeBackpack(5);
    }

    private void readNBT(CompoundNBT compound) {
        final NonNullList<ItemStack> list = NonNullList.withSize(getMaxSize(), ItemStack.EMPTY);

        ContainerHelper.loadAllItems(compound, list);
        for (int i = 0; i < list.size(); i++) {
            setItem(i, list.get(i));
        }
    }

    private void writeNBT(CompoundNBT compound) {
        final NonNullList<ItemStack> list = NonNullList.withSize(getMaxSize(), ItemStack.EMPTY);
        for (int i = 0; i < list.size(); i++) {
            list.set(i, getItem(i));
        }

        ContainerHelper.saveAllItems(compound, list, false);
    }

}