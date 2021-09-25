package com.robertx22.age_of_exile.player_skills.crafting_inv;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.CraftingInventory;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.ItemStack;

public class ProfCraftResultSlot extends Slot {
    private final CraftingInventory craftSlots;
    private final PlayerEntity player;

    public ProfCraftResultSlot(PlayerEntity p, CraftingInventory craftInv, IInventory inv, int num, int x, int y) {
        super(inv, num, x, y);
        this.player = p;
        this.craftSlots = craftInv;
    }

    @Override
    public boolean mayPlace(ItemStack stack) {
        return false;
    }

    void useIngredients() {
        // apply crafting cost
        for (int i = 0; i < craftSlots.getContainerSize(); i++) {
            ItemStack ing = craftSlots.getItem(i);
            ing.shrink(1);
        }
    }

    /*
    @Override
    protected void onQuickCraft(ItemStack stack, int num) {    // apply crafting cost
        useIngredients();
    }

    @Override
    protected void onSwapCraft(int num) {    // apply crafting cost
        useIngredients();
    }
     */

    @Override
    public ItemStack onTake(PlayerEntity p, ItemStack stack) {
        useIngredients();

        return super.onTake(p, stack);
    }
}
