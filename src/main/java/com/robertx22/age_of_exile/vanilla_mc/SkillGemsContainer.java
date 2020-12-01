package com.robertx22.age_of_exile.vanilla_mc;

import com.robertx22.age_of_exile.mmorpg.ModRegistry;
import com.robertx22.age_of_exile.saveclasses.spells.skill_gems.SkillGemsData;
import com.robertx22.age_of_exile.uncommon.datasaving.Load;
import com.robertx22.library_of_exile.tile_bases.BaseTileContainer;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.screen.slot.Slot;

public class SkillGemsContainer extends BaseTileContainer {

    Inventory tile;

    public SkillGemsContainer(int syncid, PlayerInventory playerinv, PacketByteBuf buf) {
        this(syncid, playerinv, Load.spells(playerinv.player)
            .getSkillGemData());
    }

    public SkillGemsContainer(int i, PlayerInventory invPlayer, Inventory inventory) {

        super(SkillGemsData.SIZE, ModRegistry.CONTAINERS.SKILL_GEMS_TYPE, i, invPlayer);
        this.tile = inventory;

        int count = 0;

        for (SkillGemsData.Places place : SkillGemsData.Places.values()) {
            addSlot(new Slot(inventory, count++, place.x + 1, place.y + 1));
        }

    }

    @Override
    public ItemStack transferSlot(PlayerEntity playerIn, int index) {
        return ItemStack.EMPTY; // no shift click needed here
    }

    @Override
    public boolean canUse(PlayerEntity player) {
        return tile.canPlayerUse(player);
    }
}