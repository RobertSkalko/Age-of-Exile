package com.robertx22.age_of_exile.player_skills.items.backpacks;

import com.robertx22.age_of_exile.player_skills.items.foods.SkillItemTier;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.Inventories;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.collection.DefaultedList;

/*
I'm not fully satisfied with how hacky it is but basically:
Inventory is filled as if it's maximum possible size
Methods are overrided so auto pickup doesn't put items in the first 9 slots (reserved for upgrades),
and the last slots if the bag isn't big enough
This is done to eliminate the possibility of items being lost due to bag becoming smaller..
 */
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

    // NEEDED SO IT DOESNT AUTO PIKCUP INTO UPGRADE SLOTS
    public ItemStack addStack(ItemStack stack) {
        ItemStack itemStack = stack.copy();
        this.addToExistingSlot(itemStack);
        if (itemStack.isEmpty()) {
            return ItemStack.EMPTY;
        } else {
            this.addToNewSlot(itemStack);
            return itemStack.isEmpty() ? ItemStack.EMPTY : itemStack;
        }
    }

    private void addToNewSlot(ItemStack stack) {
        for (int i = 9; i < getSizeBackpack(stack); ++i) {
            ItemStack itemStack = this.getStack(i);
            if (itemStack.isEmpty()) {
                this.setStack(i, stack.copy());
                stack.setCount(0);
                return;
            }
        }

    }

    private void addToExistingSlot(ItemStack stack) {
        for (int i = 9; i < getSizeBackpack(stack); ++i) {
            ItemStack itemStack = this.getStack(i);
            if (this.canCombine(itemStack, stack)) {
                this.transfer(stack, itemStack);
                if (stack.isEmpty()) {
                    return;
                }
            }
        }

    }

    private boolean canCombine(ItemStack one, ItemStack two) {
        return one.getItem() == two.getItem() && ItemStack.areTagsEqual(one, two);
    }

    private void transfer(ItemStack source, ItemStack target) {
        int i = Math.min(this.getMaxCountPerStack(), target.getMaxCount());
        int j = Math.min(source.getCount(), i - target.getCount());
        if (j > 0) {
            target.increment(j);
            source.decrement(j);
            this.markDirty();
        }

    }
    // NEEDED SO IT DOESNT AUTO PIKCUP INTO UPGRADE SLOTS

    public static int getSizeBackpack(ItemStack stack) {
        BackpackInfo info = BackpackContainer.getInfo(null, stack);
        return getSizeBackpack(info.extraRows);
    }

    public static int getSizeBackpack(int extraRows) {
        return 27 + extraRows * 9;
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