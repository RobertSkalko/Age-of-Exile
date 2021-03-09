package com.robertx22.age_of_exile.gui.screens.skill_gems;

import com.robertx22.age_of_exile.database.data.skill_gem.SkillGemData;
import com.robertx22.age_of_exile.database.data.skill_gem.SkillGemType;
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
            if (place.slotType == SkillGemType.SKILL_GEM) {
                addSlot(new SkillGemSlot(invPlayer.player, inventory, count++, place.x + 1, place.y + 1));
            } else {
                addSlot(new SupportGemSlot(invPlayer.player, inventory, count++, place.x + 1, place.y + 1));
            }
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

    public static class SkillGemSlot extends Slot {
        PlayerEntity player;

        public SkillGemSlot(PlayerEntity player, Inventory inventory, int index, int x, int y) {
            super(inventory, index, x, y);
            this.player = player;
        }

        @Override
        public boolean canInsert(ItemStack stack) {
            SkillGemData gem = SkillGemData.fromStack(stack);
            return gem != null && gem.getSkillGem().type == SkillGemType.SKILL_GEM && gem.canPlayerUse(player);
        }
    }

    public static class SupportGemSlot extends Slot {

        PlayerEntity player;

        public SupportGemSlot(PlayerEntity player, Inventory inventory, int index, int x, int y) {
            super(inventory, index, x, y);
            this.player = player;
        }

        @Override
        public boolean canInsert(ItemStack stack) {
            SkillGemData gem = SkillGemData.fromStack(stack);

            return gem != null && gem.getSkillGem().type == SkillGemType.SUPPORT_GEM && gem.canPlayerUse(player);
        }
    }

}