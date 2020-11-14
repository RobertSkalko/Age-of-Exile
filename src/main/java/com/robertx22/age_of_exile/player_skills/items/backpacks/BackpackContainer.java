package com.robertx22.age_of_exile.player_skills.items.backpacks;

import com.robertx22.age_of_exile.mmorpg.ModRegistry;
import com.robertx22.library_of_exile.tile_bases.BaseTileContainer;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.PacketByteBuf;

import java.util.UUID;

public class BackpackContainer extends BaseTileContainer {

    ItemStack stack;

    public BackpackContainer(int syncid, PlayerInventory playerinv, PacketByteBuf buf) {
        this(playerinv.player.getMainHandStack(), syncid, playerinv);
    }

    public BackpackContainer(ItemStack stack, int id, PlayerInventory invPlayer) {
        super(getSizeBackpack(stack), ModRegistry.CONTAINERS.BACKPACK_TYPE, id, invPlayer);
        this.stack = stack;

        if (!stack.hasTag()) {
            stack.setTag(new CompoundTag());
        }
        stack.getTag()
            .putString("id", UUID.randomUUID()
                .toString()); // so 2 backpacks can never be the same
    }

    static int getSizeBackpack(ItemStack stack) {

        return 50; // todo

    }

    @Override
    public boolean canUse(PlayerEntity player) {
        return ItemStack.areEqual(player.getMainHandStack(), stack);
    }
}
