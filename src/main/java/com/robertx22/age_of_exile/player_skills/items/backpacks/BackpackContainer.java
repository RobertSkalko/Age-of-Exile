package com.robertx22.age_of_exile.player_skills.items.backpacks;

import com.robertx22.age_of_exile.mmorpg.registers.common.ModContainers;
import com.robertx22.age_of_exile.vanilla_mc.blocks.BaseTileContainer;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.PacketBuffer;

import java.util.UUID;

public class BackpackContainer extends BaseTileContainer {

    ItemStack stack;
    BackpackInventory backpackInv;
    PlayerEntity player;
    BackpackInfo info;

    public BackpackContainer(int syncid, PlayerInventory playerinv, PacketBuffer buf) {
        this(playerinv.player.getMainHandItem(), syncid, playerinv);
    }

    public static BackpackInfo getInfo(PlayerEntity player, ItemStack stack) {
        return new BackpackInfo(player, stack);
    }

    public BackpackContainer(ItemStack stack, int id, PlayerInventory invPlayer) {
        super(BackpackInventory.getSizeBackpack(stack), ModContainers.BACKPACK.get(), id, invPlayer);

        try {
            this.player = invPlayer.player;

            if (!stack.hasTag()) {
                stack.setTag(new CompoundNBT());
            }
            if (!stack.getTag()
                .contains("id")) {
                stack.getTag()
                    .putString("id", UUID.randomUUID()
                        .toString()); // so 2 backpacks can never be the same
            }
            this.stack = stack.copy(); // copy so i can later check if its same stack

            this.info = getInfo(player, stack);

            this.backpackInv = new BackpackInventory(invPlayer.player, stack);

            int rows = 1 + info.extraRows;

            int num = 0;
            for (int y = 0; y < rows; ++y) {
                for (int x = 0; x < 9; ++x) {

                    int xpos = 8 + x * 18;

                    int ypos = 37 + (y) * 18;
                    this.addSlot(new BackpackSlot(backpackInv, num, xpos, ypos));

                    num++;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    protected int PLAYER_INVENTORY_YPOS() {
        return 150;
    }

    @Override
    protected int HOTBAR_YPOS() {
        return 208;
    }

    @Override
    public void broadcastChanges() {
        super.broadcastChanges();
        backpackInv.writeItemStack();
    }

    @Override
    public boolean stillValid(PlayerEntity player) {

        try {
            if (!player.getMainHandItem()
                .getTag()
                .getString("id")
                .equals(stack.getTag()
                    .getString("id"))) {
                return false;
            }
        } catch (Exception e) {
        }

        if (player.getMainHandItem()
            .getItem() != stack.getItem()) {
            return false;
        }
        return true;
    }

    private class BackpackSlot extends Slot {

        public BackpackSlot(BackpackInventory inventory, int index, int x, int y) {
            super(inventory, index, x, y);
        }

        @Override
        public boolean mayPickup(PlayerEntity player) {
            if (getItem().getItem() instanceof BackpackItem) {
                return false;
            }
            return true;
        }

        @Override
        public boolean mayPlace(ItemStack stack) {
            if (stack.getItem() instanceof BackpackItem) {
                return false;
            }

            return true;
        }
    }
}
