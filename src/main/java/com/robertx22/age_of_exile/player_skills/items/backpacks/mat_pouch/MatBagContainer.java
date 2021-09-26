package com.robertx22.age_of_exile.player_skills.items.backpacks.mat_pouch;

import com.robertx22.age_of_exile.mmorpg.registers.common.SlashContainers;
import com.robertx22.age_of_exile.player_skills.items.backpacks.BackpackInventory;
import com.robertx22.age_of_exile.player_skills.items.backpacks.BackpackItem;
import com.robertx22.age_of_exile.uncommon.datasaving.StackSaving;
import com.robertx22.age_of_exile.vanilla_mc.blocks.BaseTileContainer;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.PacketBuffer;

import java.util.UUID;

public class MatBagContainer extends BaseTileContainer {

    ItemStack stack;
    BackpackInventory backpackInv;
    PlayerEntity player;

    public MatBagContainer(int syncid, PlayerInventory playerinv, PacketBuffer buf) {
        this(playerinv.player.getMainHandItem(), syncid, playerinv);
    }

    public MatBagContainer(ItemStack stack, int id, PlayerInventory invPlayer) {
        super(6 * 9, SlashContainers.MAT_POUCH.get(), id, invPlayer);

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

            this.backpackInv = new BackpackInventory(invPlayer.player, stack);

            int num = 0;
            for (int y = 0; y < 6; ++y) {
                for (int x = 0; x < 9; ++x) {

                    int xpos = 8 + x * 18;

                    int ypos = 18 + (y) * 18;
                    this.addSlot(new IngredientPouchSlot(backpackInv, num, xpos, ypos));

                    num++;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    protected int PLAYER_INVENTORY_YPOS() {
        return 139;
    }

    @Override
    protected int HOTBAR_YPOS() {
        return 197;
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

    private class IngredientPouchSlot extends Slot {

        public IngredientPouchSlot(BackpackInventory inventory, int index, int x, int y) {
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
            if (!StackSaving.INGREDIENTS.has(stack)) {
                return false;
            }
            return true;
        }
    }
}
