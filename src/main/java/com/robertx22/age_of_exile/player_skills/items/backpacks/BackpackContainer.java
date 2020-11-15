package com.robertx22.age_of_exile.player_skills.items.backpacks;

import com.robertx22.age_of_exile.mmorpg.ModRegistry;
import com.robertx22.library_of_exile.tile_bases.BaseTileContainer;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.screen.slot.Slot;

import java.util.UUID;

public class BackpackContainer extends BaseTileContainer {

    ItemStack stack;
    BackpackInventory backpackInv;
    PlayerEntity player;

    public BackpackContainer(int syncid, PlayerInventory playerinv, PacketByteBuf buf) {
        this(playerinv.player.getMainHandStack(), syncid, playerinv);
    }

    public BackpackContainer(ItemStack stack, int id, PlayerInventory invPlayer) {
        super(BackpackInventory.getSizeBackpack(stack), ModRegistry.CONTAINERS.BACKPACK_TYPE, id, invPlayer);
        try {
            this.player = invPlayer.player;

            if (!stack.hasTag()) {
                stack.setTag(new CompoundTag());
            }
            if (!stack.getTag()
                .contains("id")) {
                stack.getTag()
                    .putString("id", UUID.randomUUID()
                        .toString()); // so 2 backpacks can never be the same
            }
            this.stack = stack.copy(); // copy so i can later check if its same stack

            BackpackItem backpack = (BackpackItem) stack.getItem();

            this.backpackInv = new BackpackInventory(stack);

            int rows = 2 + backpack.tier.tier;

            int num = 0;
            for (int y = 0; y < rows; ++y) {
                for (int x = 0; x < 9; ++x) {

                    int xpos = 8 + x * 18;
                    int ypos = 11 + y * 18;
                    this.addSlot(new BackpackSlot(backpack.type, backpackInv, num, xpos, ypos));
                    num++;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public void sendContentUpdates() {
        super.sendContentUpdates();
        backpackInv.writeItemStack();
    }

    @Override
    public boolean canUse(PlayerEntity player) {

        try {
            if (!player.getMainHandStack()
                .getTag()
                .getString("id")
                .equals(stack.getTag()
                    .getString("id"))) {
                return false;
            }
        } catch (Exception e) {
        }

        if (player.getMainHandStack()
            .getItem() != stack.getItem()) {
            return false;
        }
        return true;
    }

    private class BackpackSlot extends Slot {
        BackpackType type;

        public BackpackSlot(BackpackType type, Inventory inventory, int index, int x, int y) {
            super(inventory, index, x, y);
            this.type = type;
        }

        @Override
        public boolean canTakeItems(PlayerEntity player) {

            if (getStack().getItem() instanceof BackpackItem) {
                return false;
            }

            if (!type.canAcceptStack(getStack())) {
                return false;
            }

            return !(getStack().getItem() instanceof BackpackItem) && type.canAcceptStack(getStack());
        }

        @Override
        public boolean canInsert(ItemStack stack) {
            if (stack.getItem() instanceof BackpackItem) {
                return false;
            }

            if (!type.canAcceptStack(stack)) {
                return false;
            }

            return true;
        }
    }
}
