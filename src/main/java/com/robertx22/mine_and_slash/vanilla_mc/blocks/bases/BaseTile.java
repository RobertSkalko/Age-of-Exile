package com.robertx22.mine_and_slash.vanilla_mc.blocks.bases;

import com.robertx22.mine_and_slash.vanilla_mc.items.misc.ItemCapacitor;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.SidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.screen.NamedScreenHandlerFactory;
import net.minecraft.util.Tickable;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.MathHelper;

import java.util.Arrays;

public abstract class BaseTile extends BlockEntity implements IOBlock, SidedInventory, Tickable, NamedScreenHandlerFactory {

    public BaseTile(BlockEntityType<?> tileEntityTypeIn) {
        super(tileEntityTypeIn);
    }

    protected ItemStack[] itemStacks;

    public int ticks = 0;
    public short cookTime = 0;
    public int fuel = 0;

    public abstract int ticksRequired();

    public abstract void finishCooking();

    public abstract boolean isCooking();

    public abstract int tickRate();

    public abstract void doActionEveryTime();

    public abstract int getCookTime();

    @Override
    public void tick() {
        try {
            if (!this.world.isClient) {

                ticks++;
                if (ticks > tickRate()) {
                    ticks = 0;

                    doActionEveryTime();

                    if (isCooking()) {

                        cookTime += tickRate();

                        if (cookTime >= ticksRequired()) {
                            finishCooking();
                            cookTime = 0;
                        }

                    } else {
                        cookTime = 0;
                    }

                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private static final short COOK_TIME_FOR_COMPLETION = 200; // vanilla value is 200 = 10 seconds

    public double fractionOfCookTimeComplete() {
        double fraction = cookTime / (double) COOK_TIME_FOR_COMPLETION;
        return MathHelper.clamp(fraction, 0.0, 1.0);
    }

    // OVERRIDE IF AUTOMATABLE
    @Override
    public int[] inputSlots() {
        return new int[0];
    }

    @Override
    public int[] getAvailableSlots(Direction side) {
        return slots();
    }

    @Override
    public boolean isItemValidOutput(ItemStack stack) {
        return true;
    }

    private int[] slots() {

        int[] ints = new int[this.itemStacks.length];

        for (int i = 0; i < itemStacks.length; i++) {
            ints[i] = i;
        }

        return ints;
    }

    public boolean containsSlot(int index, int[] slots) {

        for (int i : this.inputSlots()) {
            if (i == index) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean canInsert(int index, ItemStack itemStackIn, Direction direction) {

        if (this.isAutomatable() && containsSlot(index, this.inputSlots())) {
            // don't insert shit
            return this.isItemValidInput(itemStackIn);
        }
        return false;
    }

    @Override
    public boolean canExtract(int index, ItemStack stack, Direction direction) {

        if (this.isAutomatable()) {
            // don't extract stuff that's being processed

            if (stack.getItem() instanceof ItemCapacitor) {
                return false; // temp fix
            }

            return isOutputSlot(index);
        }
        return false;
    }

    // Gets the stack in the given slot
    @Override
    public ItemStack getStack(int i) {

        return itemStacks[i];
    }

    /**
     * Removes some of the units from itemstack in the given slot, and returns as a
     * separate itemstack
     *
     * @param slotIndex the slot number to remove the items from
     * @param count     the number of units to remove
     * @return a new itemstack containing the units removed from the slot
     */
    @Override
    public ItemStack removeStack(int slotIndex, int count) {
        ItemStack itemStackInSlot = getStack(slotIndex);
        if (itemStackInSlot.isEmpty())
            return ItemStack.EMPTY; // isEmpty(), EMPTY_ITEM

        ItemStack itemStackRemoved;
        if (itemStackInSlot.getCount() <= count) { // getStackSize
            itemStackRemoved = itemStackInSlot;
            setStack(slotIndex, ItemStack.EMPTY); // EMPTY_ITEM
        } else {
            itemStackRemoved = itemStackInSlot.split(count);
            if (itemStackInSlot.getCount() == 0) { // getStackSize
                setStack(slotIndex, ItemStack.EMPTY); // EMPTY_ITEM
            }
        }
        markDirty();
        return itemStackRemoved;
    }

    // Gets the number of slots in the inventory
    @Override
    public int size() {
        return itemStacks.length;
    }

    // returns true if all of the slots in the inventory are empty
    @Override
    public boolean isEmpty() {
        for (ItemStack itemstack : itemStacks) {
            if (!itemstack.isEmpty()) { // isEmpty()
                return false;
            }
        }

        return true;
    }

    // overwrites the stack in the given slotIndex with the given stack
    @Override
    public void setStack(int slotIndex, ItemStack itemstack) {
        itemStacks[slotIndex] = itemstack;
        if (!itemstack.isEmpty() && itemstack.getCount() > getMaxCountPerStack()) { // isEmpty(); getStackSize()
            itemstack.setCount(getMaxCountPerStack()); // setStackSize()
        }
        markDirty();
    }

    // set all slots to empty
    @Override
    public void clear() {
        Arrays.fill(itemStacks, ItemStack.EMPTY); // EMPTY_ITEM
    }

    @Override
    public void onOpen(PlayerEntity player) {
    }

    @Override
    public void onClose(PlayerEntity player) {
    }

    // -----------------------------------------------------------------------------------------------------------
    // The following methods are not needed for this example but are part of
    // IInventory so they must be implemented

    // Unused unless your container specifically uses it.
    // Return true if the given stack is allowed to go in the given slot
    @Override
    public boolean isValid(int slotIndex, ItemStack itemstack) {
        return true;
    }

    /**
     * This method removes the entire contents of the given slot and returns it.
     * Used by containers such as crafting tables which return any items in their
     * slots when you close the GUI
     *
     * @param slotIndex
     * @return
     */
    @Override
    public ItemStack removeStack(int slotIndex) {

        ItemStack itemStack = getStack(slotIndex);
        if (!itemStack.isEmpty())
            setStack(slotIndex, ItemStack.EMPTY); // isEmpty(); EMPTY_ITEM
        return itemStack;
    }

    @Override
    public int getMaxCountPerStack() {
        return 64;
    }

    @Override
    public boolean canPlayerUse(PlayerEntity player) {
        if (this.world.getBlockEntity(this.pos) != this)
            return false;
        final double X_CENTRE_OFFSET = 0.5;
        final double Y_CENTRE_OFFSET = 0.5;
        final double Z_CENTRE_OFFSET = 0.5;
        final double MAXIMUM_DISTANCE_SQ = 8.0 * 8.0;
        return player.squaredDistanceTo(pos.getX() + X_CENTRE_OFFSET, pos.getY() + Y_CENTRE_OFFSET, pos
            .getZ() + Z_CENTRE_OFFSET) < MAXIMUM_DISTANCE_SQ;
    }

    static public boolean isItemValidForFuelSlot(ItemStack itemStack) {
        return true;
    }

    static public boolean isItemValidForInputSlot(ItemStack itemStack) {

        return true;
    }

    static public boolean isItemValidForOutputSlot(ItemStack itemStack) {
        return false;
    }

    @Override
    public CompoundTag toTag(CompoundTag parentNBTTagCompound) {
        super.toTag(parentNBTTagCompound); // The super call is required to save and load the tiles location

        ListTag dataForAllSlots = new ListTag();
        for (int i = 0; i < this.itemStacks.length; ++i) {
            if (!this.itemStacks[i].isEmpty()) { // isEmpty()
                CompoundTag dataForThisSlot = new CompoundTag();
                dataForThisSlot.putByte("Slot", (byte) i);
                this.itemStacks[i].toTag(dataForThisSlot);
                dataForAllSlots.add(dataForThisSlot);
            }
        }
        // the array of hashmaps is then inserted into the instance hashmap for the
        // container
        parentNBTTagCompound.put("Items", dataForAllSlots);
        parentNBTTagCompound.putInt("ticks", ticks);

        // Save everything else
        parentNBTTagCompound.putShort("CookTime", cookTime);

        parentNBTTagCompound.putInt("fuel", this.fuel);
        return parentNBTTagCompound;
    }

    // This is where you load the dataInstance that you saved in write
    @Override
    public void fromTag(BlockState state, CompoundTag nbtTagCompound) {
        super.fromTag(state, nbtTagCompound); // The super call is required to save and load the tiles location
        final byte NBT_TYPE_COMPOUND = 10; // See NBTBase.createNewByType() for a listing
        ListTag dataForAllSlots = nbtTagCompound.getList("Items", NBT_TYPE_COMPOUND);

        Arrays.fill(itemStacks, ItemStack.EMPTY); // set all slots to empty EMPTY_ITEM
        for (int i = 0; i < dataForAllSlots.size(); ++i) {
            CompoundTag dataForOneSlot = dataForAllSlots.getCompound(i);
            byte slotNumber = dataForOneSlot.getByte("Slot");
            if (slotNumber >= 0 && slotNumber < this.itemStacks.length) {
                this.itemStacks[slotNumber] = ItemStack.fromTag(dataForOneSlot);
            }
        }

        // Load everything else. Trim the arrays (or pad with 0) to make sure they have
        // the correct number of elements
        cookTime = nbtTagCompound.getShort("CookTime");
        ticks = nbtTagCompound.getInt("ticks");
        this.fuel = nbtTagCompound.getInt("fuel");
    }

}
