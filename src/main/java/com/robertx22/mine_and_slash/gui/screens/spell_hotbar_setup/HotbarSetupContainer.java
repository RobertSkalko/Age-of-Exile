package com.robertx22.mine_and_slash.gui.screens.spell_hotbar_setup;

import com.robertx22.mine_and_slash.uncommon.datasaving.Load;
import com.robertx22.mine_and_slash.uncommon.datasaving.SkillGem;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.slot.Slot;

public class HotbarSetupContainer extends ScreenHandler {

    private final Inventory inventory;
    private final int rows;

    public HotbarSetupContainer(int syncId, PlayerEntity player) {
        super(null, syncId);

        this.inventory = Load.spells(player)
            .getInventory();
        this.rows = 1;
        inventory.onOpen(player);

        int n;
        int m;
        for (n = 0; n < this.rows; ++n) {
            for (m = 0; m < 9; ++m) {
                this.addSlot(new SpellSlot(inventory, m + n * 9, 8 + m * 18, 54));
            }
        }

        for (n = 0; n < 3; ++n) {
            for (m = 0; m < 9; ++m) {
                this.addSlot(new Slot(player.inventory, m + n * 9 + 9, 8 + m * 18, 84 + n * 18));
            }
        }

        for (n = 0; n < 9; ++n) {
            this.addSlot(new Slot(player.inventory, n, 8 + n * 18, 142));
        }

    }

    static class SpellSlot extends Slot {

        public SpellSlot(Inventory inventory, int index, int x, int y) {
            super(inventory, index, x, y);
        }

        @Override
        public boolean canInsert(ItemStack stack) {
            return SkillGem.Load(stack) != null;
        }

    }

    public boolean canUse(PlayerEntity player) {
        return true;
    }

    public ItemStack transferSlot(PlayerEntity player, int index) {
        ItemStack itemStack = ItemStack.EMPTY;
        Slot slot = (Slot) this.slots.get(index);
        if (slot != null && slot.hasStack()) {
            ItemStack itemStack2 = slot.getStack();
            itemStack = itemStack2.copy();
            if (index < this.rows * 9) {
                if (!this.insertItem(itemStack2, this.rows * 9, this.slots.size(), true)) {
                    return ItemStack.EMPTY;
                }
            } else if (!this.insertItem(itemStack2, 0, this.rows * 9, false)) {
                return ItemStack.EMPTY;
            }

            if (itemStack2.isEmpty()) {
                slot.setStack(ItemStack.EMPTY);
            } else {
                slot.markDirty();
            }
        }

        return itemStack;
    }

    public void close(PlayerEntity player) {
        super.close(player);
        this.inventory.onClose(player);
    }

    public Inventory getInventory() {
        return this.inventory;
    }

    @Environment(EnvType.CLIENT)
    public int getRows() {
        return this.rows;
    }
}
