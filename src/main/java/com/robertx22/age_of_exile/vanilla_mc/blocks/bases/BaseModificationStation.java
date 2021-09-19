package com.robertx22.age_of_exile.vanilla_mc.blocks.bases;

import com.robertx22.age_of_exile.uncommon.utilityclasses.ClientOnly;
import com.robertx22.age_of_exile.vanilla_mc.blocks.IAutomatable;
import com.robertx22.library_of_exile.tile_bases.NonFullBlock;
import net.minecraft.block.BlockState;
import net.minecraft.entity.item.ExperienceOrbEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.Direction;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

public abstract class BaseModificationStation extends TileEntity implements ISidedInventory, ITickableTileEntity, INamedContainerProvider {

    static UUID EMPTY_ID = UUID.fromString("3f715e84-4318-4025-9f23-875e3738c19b");

    public BaseModificationStation(TileEntityType<?> tileEntityTypeIn, int slots) {
        super(tileEntityTypeIn);
        itemStacks = new ItemStack[slots];
        clearContent();
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

        if (level.isClientSide) {
            return ClientOnly.getPlayerById(ownerID);
        }

        PlayerEntity en = null;
        try {
            en = level.getServer()
                .getPlayerList()
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
        if (level != null && !level.isClientSide) {
            ticks++;
            if (this instanceof ISmeltingStation) {
                ISmeltingStation smelt = (ISmeltingStation) this;
                smelt.onSmeltTick();
                level.setBlock(this.worldPosition, this.level.getBlockState(this.worldPosition)
                    .setValue(NonFullBlock.light, this.cook_ticks > 0), 3);
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
    public int[] getSlotsForFace(Direction side) {
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
    public boolean canPlaceItemThroughFace(int index, ItemStack stack, Direction direction) {

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
    public boolean canTakeItemThroughFace(int index, ItemStack stack, Direction direction) {
        if (this instanceof IAutomatable) {
            IAutomatable auto = (IAutomatable) this;
            return auto.isOutputSlot(index) && auto.isValidOuput(stack);
        }
        return false;
    }

    // Gets the stack in the given slot
    @Override
    public ItemStack getItem(int i) {
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
    public ItemStack removeItem(int slotIndex, int count) {
        ItemStack itemStackInSlot = getItem(slotIndex);
        if (itemStackInSlot.isEmpty())
            return ItemStack.EMPTY; // isEmpty(), EMPTY_ITEM

        ItemStack itemStackRemoved;
        if (itemStackInSlot.getCount() <= count) { // getStackSize
            itemStackRemoved = itemStackInSlot;
            setItem(slotIndex, ItemStack.EMPTY); // EMPTY_ITEM
        } else {
            itemStackRemoved = itemStackInSlot.split(count);
            if (itemStackInSlot.getCount() == 0) { // getStackSize
                setItem(slotIndex, ItemStack.EMPTY); // EMPTY_ITEM
            }
        }
        setChanged();
        return itemStackRemoved;
    }

    // Gets the number of slots in the inventory
    @Override
    public int getContainerSize() {
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
    public void setItem(int slotIndex, ItemStack itemstack) {
        itemStacks[slotIndex] = itemstack;
        if (!itemstack.isEmpty() && itemstack.getCount() > getMaxStackSize()) { // isEmpty(); getStackSize()
            itemstack.setCount(getMaxStackSize()); // setStackSize()
        }
        setChanged();
    }

    // set all slots to empty
    @Override
    public void clearContent() {
        Arrays.fill(itemStacks, ItemStack.EMPTY); // EMPTY_ITEM
    }

    // this must be called manually when the container opens, or else it isn't called at all
    // vanilla doesnt call it by itself
    @Override
    public void startOpen(PlayerEntity player) {
        if (ownerID.equals(player.getUUID()) == false) {
            ownerID = player.getUUID();
        } else {
            if (expEarned > 0) {
                dropExperience(level, new Vector3d(worldPosition.getX(), worldPosition.getY(), worldPosition.getZ()), expEarned);
                expEarned = 0;
            }
        }
    }

    private static void dropExperience(World world, Vector3d vec3d, float f) {
        int j = (int) f;
        float g = MathHelper.frac((float) f);
        if (g != 0.0F && Math.random() < (double) g) {
            ++j;
        }

        while (j > 0) {
            int k = ExperienceOrbEntity.getExperienceValue(j);
            j -= k;
            world.addFreshEntity(new ExperienceOrbEntity(world, vec3d.x, vec3d.y, vec3d.z, k));
        }

    }

    @Override
    public void stopOpen(PlayerEntity player) {
    }

// -----------------------------------------------------------------------------------------------------------
// The following methods are not needed for this example but are part of
// IInventory so they must be implemented

    // Unused unless your container specifically uses it.
// Return true if the given stack is allowed to go in the given slot
    @Override
    public boolean canPlaceItem(int slotIndex, ItemStack itemstack) {
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
    public ItemStack removeItemNoUpdate(int slotIndex) {

        ItemStack itemStack = getItem(slotIndex);
        if (!itemStack.isEmpty())
            setItem(slotIndex, ItemStack.EMPTY); // isEmpty(); EMPTY_ITEM
        return itemStack;
    }

    @Override
    public int getMaxStackSize() {
        return 64;
    }

    @Override
    public boolean stillValid(PlayerEntity player) {

        if (this.level.getBlockEntity(this.worldPosition) != this)
            return false;
        final double X_CENTRE_OFFSET = 0.5;
        final double Y_CENTRE_OFFSET = 0.5;
        final double Z_CENTRE_OFFSET = 0.5;
        final double MAXIMUM_DISTANCE_SQ = 8.0 * 8.0;
        return player.distanceToSqr(worldPosition.getX() + X_CENTRE_OFFSET, worldPosition.getY() + Y_CENTRE_OFFSET, worldPosition
            .getZ() + Z_CENTRE_OFFSET) < MAXIMUM_DISTANCE_SQ;
    }

    @Override
    public CompoundNBT save(CompoundNBT nbt) {
        super.save(nbt); // The super call is required to save and load the tiles location

        ListNBT dataForAllSlots = new ListNBT();
        for (int i = 0; i < this.itemStacks.length; ++i) {
            if (!this.itemStacks[i].isEmpty()) { // isEmpty()
                CompoundNBT dataForThisSlot = new CompoundNBT();
                dataForThisSlot.putByte("Slot", (byte) i);
                this.itemStacks[i].save(dataForThisSlot);
                dataForAllSlots.add(dataForThisSlot);
            }
        }
        // the array of hashmaps is then inserted into the instance hashmap for the
        // container
        nbt.put("Items", dataForAllSlots);
        nbt.putInt("ticks", ticks);
        nbt.putInt("cook_ticks", cook_ticks);
        nbt.putInt("fuel", fuel);
        nbt.putUUID("owner_id", ownerID);

        return nbt;
    }

    // This is where you load the dataInstance that you saved in write
    @Override
    public void load(BlockState state, CompoundNBT nbt) {
        try {
            super.load(state, nbt); // The super call is required to save and load the tiles location
            final byte NBT_TYPE_COMPOUND = 10; // See NBTBase.createNewByType() for a listing
            ListNBT dataForAllSlots = nbt.getList("Items", NBT_TYPE_COMPOUND);

            Arrays.fill(itemStacks, ItemStack.EMPTY); // set all slots to empty EMPTY_ITEM
            for (int i = 0; i < dataForAllSlots.size(); ++i) {
                CompoundNBT dataForOneSlot = dataForAllSlots.getCompound(i);
                byte slotNumber = dataForOneSlot.getByte("Slot");
                if (slotNumber >= 0 && slotNumber < this.itemStacks.length) {
                    this.itemStacks[slotNumber] = ItemStack.of(dataForOneSlot);
                }
            }

            // Load everything else. Trim the arrays (or pad with 0) to make sure they have
            // the correct number of elements
            ticks = nbt.getInt("ticks");
            cook_ticks = nbt.getInt("cook_ticks");
            fuel = nbt.getInt("fuel");
            if (nbt.contains("owner_id")) {
                ownerID = nbt.getUUID("owner_id");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
