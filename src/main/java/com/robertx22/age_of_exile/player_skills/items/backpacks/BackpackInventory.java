package com.robertx22.age_of_exile.player_skills.items.backpacks;

import com.robertx22.age_of_exile.player_skills.items.foods.SkillItemTier;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.Inventories;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.collection.DefaultedList;

public class BackpackInventory extends SimpleInventory {

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
            stack.removeSubTag("Items");
        } else {
            writeNBT(stack.getOrCreateTag());
        }
    }

    public static int getMaxSize() {
        return getSizeBackpack(SkillItemTier.TIER4.tier);
    }

    private void readNBT(CompoundTag compound) {
        final DefaultedList<ItemStack> list = DefaultedList.ofSize(getMaxSize(), ItemStack.EMPTY);

        Inventories.fromTag(compound, list);
        for (int i = 0; i < list.size(); i++) {
            setStack(i, list.get(i));
        }
    }

    private void writeNBT(CompoundTag compound) {
        final DefaultedList<ItemStack> list = DefaultedList.ofSize(getMaxSize(), ItemStack.EMPTY);
        for (int i = 0; i < list.size(); i++) {
            list.set(i, getStack(i));
        }

        Inventories.toTag(compound, list, false);
    }

}