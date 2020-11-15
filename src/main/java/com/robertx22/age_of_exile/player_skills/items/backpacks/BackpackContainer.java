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

    PlayerEntity player;

    public BackpackContainer(int syncid, PlayerInventory playerinv, PacketByteBuf buf) {
        this(playerinv.player.getMainHandStack(), syncid, playerinv);
    }

    public BackpackContainer(ItemStack stack, int id, PlayerInventory invPlayer) {
        super(getSizeBackpack(stack), ModRegistry.CONTAINERS.BACKPACK_TYPE, id, invPlayer);
        this.player = invPlayer.player;

        if (!stack.hasTag()) {
            stack.setTag(new CompoundTag());
        }
        stack.getTag()
            .putString("id", UUID.randomUUID()
                .toString()); // so 2 backpacks can never be the same
        this.stack = stack.copy(); // copy so i can later check if its same stack

        int rows = 6;

        for (int y = 0; y < rows; ++y) {
            for (int x = 0; x < 9; ++x) {
                int slotNumber = 9 + y * 9 + x;
                int xpos = 8 + x * 18;
                int ypos = 11 + y * 18;
                this.addSlot(new BackpackSlot(invPlayer, slotNumber, xpos, ypos));
            }
        }

    }

    static int getSizeBackpack(ItemStack stack) {

        return 50; // todo

    }

    @Override
    public boolean canUse(PlayerEntity player) {
        return ItemStack.areEqual(player.getMainHandStack(), stack);
    }

    private class BackpackSlot extends Slot {

        public BackpackSlot(Inventory inventory, int index, int x, int y) {
            super(inventory, index, x, y);
        }

        @Override
        public boolean canTakeItems(PlayerEntity player) {
            return !(getStack().getItem() instanceof BackpackItem);
        }

        @Override
        public boolean canInsert(ItemStack stack) {
            return !(getStack().getItem() instanceof BackpackItem);
        }
    }
}
