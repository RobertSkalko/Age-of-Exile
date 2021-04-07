package com.robertx22.age_of_exile.vanilla_mc.blocks.bases;

import com.robertx22.age_of_exile.uncommon.utilityclasses.ClientOnly;
import com.robertx22.age_of_exile.vanilla_mc.blocks.IAutomatable;
import com.robertx22.library_of_exile.tile_bases.NonFullBlock;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.entity.ExperienceOrbEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.SidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.screen.NamedScreenHandlerFactory;
import net.minecraft.util.Tickable;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

public abstract class BaseModificationStation extends BlockEntity implements SidedInventory, Tickable, NamedScreenHandlerFactory {

    static UUID EMPTY_ID = UUID.fromString("3f715e84-4318-4025-9f23-875e3738c19b");

    public BaseModificationStation(BlockEntityType<?> tileEntityTypeIn, int slots) {
        super(tileEntityTypeIn);
        itemStacks = new ItemStack[slots];
        clear();
    }

    public abstract boolean modifyItem(int number, PlayerEntity player);

    public ItemStack[] itemStacks;

    public int ticks = 0;
    public int cook_ticks = 0;
    public int fuel = 0;

    public int expEarned = 0;
    OwnerData ownerData = new OwnerData();

    public UUID ownerID = EMPTY_ID;

    public PlayerEntity getOwner() {

        if (world.isClient) {
            return ClientOnly.getPlayerById(ownerID);
        }

        PlayerEntity en = null;
        try {
            en = world.getServer()
                .getPlayerManager()
                .getPlayer(ownerID);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return en;
    }

    // todo allow stacking items
    public boolean canPushTo(int index, ItemStack stack) {
        return itemStacks[index].isEmpty();
    }

    // todo allow stacking items
    public void pushTo(int index, ItemStack stack) {
        itemStacks[index] = stack;
    }

    @Override
    public void tick() {
        if (world != null && !world.isClient) {
            ticks++;
            if (this instanceof ISmeltingStation) {
                ISmeltingStation smelt = (ISmeltingStation) this;
                smelt.onSmeltTick();
                world.setBlockState(this.pos, this.world.getBlockState(this.pos)
                    .with(NonFullBlock.light, this.cook_ticks > 0), 3);
            }
            if (ticks % 2000 == 0) {
                updateOwnerData();
            }
        }
    }

    void updateOwnerData() {

        PlayerEntity p = getOwner();

        if (p != null) {
            this.ownerData = new OwnerData(p);
        }

    }

    @Override
    public int[] getAvailableSlots(Direction side) {
        int[] ar = new int[slots().size()];
        for (int i = 0; i < slots().size(); i++) {
            ar[i] = i;
        }
        return ar;

    }

    private List<Integer> slots() {

        List<Integer> slots = new ArrayList<>();

        for (int i = 0; i < itemStacks.length; i++) {
            slots.add(i);
        }

        return slots;
    }

    @Override
    public boolean canInsert(int index, ItemStack stack, Direction direction) {

        if (this instanceof IAutomatable) {
            IAutomatable auto = (IAutomatable) this;
            if (direction.getAxis()
                .isVertical() && auto.isInputSlot(index)) {
                // don't insert shit
                return auto.isValidInput(stack);
            }
            if (direction.getAxis()
                .isHorizontal()) {
                // don't insert shit
                if (auto.isFuelSlot(index)) {
                    return auto.isValidFuel(stack);
                }
            }
        }
        return false;
    }

    @Override
    public boolean canExtract(int index, ItemStack stack, Direction direction) {
        if (this instanceof IAutomatable) {
            IAutomatable auto = (IAutomatable) this;
            return auto.isOutputSlot(index) && auto.isValidOuput(stack);
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

    // this must be called manually when the container opens, or else it isn't called at all
    // vanilla doesnt call it by itself
    @Override
    public void onOpen(PlayerEntity player) {
        if (ownerID.equals(player.getUuid()) == false) {
            ownerID = player.getUuid();
        } else {
            if (expEarned > 0) {
                dropExperience(world, new Vec3d(pos.getX(), pos.getY(), pos.getZ()), expEarned);
                expEarned = 0;
            }
        }
    }

    private static void dropExperience(World world, Vec3d vec3d, float f) {
        int j = (int) f;
        float g = MathHelper.fractionalPart((float) f);
        if (g != 0.0F && Math.random() < (double) g) {
            ++j;
        }

        while (j > 0) {
            int k = ExperienceOrbEntity.roundToOrbSize(j);
            j -= k;
            world.spawnEntity(new ExperienceOrbEntity(world, vec3d.x, vec3d.y, vec3d.z, k));
        }

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

    @Override
    public CompoundTag toTag(CompoundTag nbt) {
        super.toTag(nbt); // The super call is required to save and load the tiles location

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
        nbt.put("Items", dataForAllSlots);
        nbt.putInt("ticks", ticks);
        nbt.putInt("cook_ticks", cook_ticks);
        nbt.putInt("fuel", fuel);
        nbt.putUuid("owner_id", ownerID);

        return nbt;
    }

    // This is where you load the dataInstance that you saved in write
    @Override
    public void fromTag(BlockState state, CompoundTag nbt) {
        try {
            super.fromTag(state, nbt); // The super call is required to save and load the tiles location
            final byte NBT_TYPE_COMPOUND = 10; // See NBTBase.createNewByType() for a listing
            ListTag dataForAllSlots = nbt.getList("Items", NBT_TYPE_COMPOUND);

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
            ticks = nbt.getInt("ticks");
            cook_ticks = nbt.getInt("cook_ticks");
            fuel = nbt.getInt("fuel");
            if (nbt.contains("owner_id")) {
                ownerID = nbt.getUuid("owner_id");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
