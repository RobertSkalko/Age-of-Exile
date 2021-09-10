package com.robertx22.age_of_exile.saveclasses.stat_soul;

import com.robertx22.age_of_exile.mmorpg.ModRegistry;
import com.robertx22.age_of_exile.uncommon.datasaving.StackSaving;
import net.minecraft.inventory.CraftingInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.recipe.SpecialCraftingRecipe;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;

public class StatSoulRecipe extends SpecialCraftingRecipe {
    public StatSoulRecipe(Identifier id) {
        super(id);
    }

    @Override
    public boolean matches(CraftingInventory inv, World world) {
        boolean hassoul = false;
        boolean hasmatchinggear = false;

        StatSoulItem soul = null;
        StatSoulData data = null;
        for (int i = 0; i < inv.size(); ++i) {
            ItemStack stack = inv.getStack(i);
            if (stack.getItem() instanceof StatSoulItem) {
                soul = (StatSoulItem) stack.getItem();
                data = StackSaving.STAT_SOULS.loadFrom(stack);
                if (hassoul) {
                    return false; // dont allow 2! that would make 1 disappear
                }
                hassoul = true;
            }
        }

        if (soul == null || data == null) {
            return false;
        }

        for (int i = 0; i < inv.size(); ++i) {
            ItemStack stack = inv.getStack(i);
            if (data.canInsertIntoStack(stack)) {
                if (hasmatchinggear) {
                    return false; // dont allow 2! that would make 1 disappear
                }
                hasmatchinggear = true;
            }

        }

        return hassoul && hasmatchinggear;
    }

    @Override
    public ItemStack craft(CraftingInventory inv) {

        ItemStack soul = ItemStack.EMPTY;
        ItemStack gear = ItemStack.EMPTY;
        StatSoulData data = null;

        for (int i = 0; i < inv.size(); ++i) {
            ItemStack stack = inv.getStack(i);
            if (stack.getItem() instanceof StatSoulItem) {
                soul = stack.copy();
                data = StackSaving.STAT_SOULS.loadFrom(soul);
                break;
            }
        }

        if (data == null) {
            return ItemStack.EMPTY;
        }

        for (int i = 0; i < inv.size(); ++i) {
            ItemStack stack = inv.getStack(i);
            if (data.canInsertIntoStack(stack)) {
                gear = stack.copy();
                break;
            }
        }

        data.insertAsUnidentifiedOn(gear);

        return gear;
    }

    @Override
    public boolean fits(int width, int height) {
        return width > 1 || height > 1;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return ModRegistry.RECIPE_SER.STAT_SOUL_RECIPE;
    }

}
