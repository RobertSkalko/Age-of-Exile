package com.robertx22.age_of_exile.player_skills.recipe_types;

import com.google.common.collect.Lists;
import com.robertx22.age_of_exile.mmorpg.registers.common.SlashRecipeSers;
import net.minecraft.inventory.CraftingInventory;
import net.minecraft.item.DyeItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.SpecialRecipe;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

import java.util.List;

public class ProfCraftingTabletRecipe extends SpecialRecipe {

    public ProfCraftingTabletRecipe(ResourceLocation id) {
        super(id);
    }

    @Override
    public boolean matches(CraftingInventory inv, World world) {
        ItemStack itemstack = ItemStack.EMPTY;
        List<ItemStack> list = Lists.newArrayList();

        // TODO

        for (int i = 0; i < inv.getContainerSize(); ++i) {
            ItemStack itemstack1 = inv.getItem(i);

        }
        return false;
    }

    @Override
    public ItemStack assemble(CraftingInventory inv) {
        List<DyeItem> list = Lists.newArrayList();
        ItemStack itemstack = ItemStack.EMPTY;

        // TODO

        for (int i = 0; i < inv.getContainerSize(); ++i) {
            ItemStack itemstack1 = inv.getItem(i);

        }

        return ItemStack.EMPTY;
    }

    @Override
    public boolean canCraftInDimensions(int x, int y) {
        return x * y >= 2;
    }

    @Override
    public IRecipeSerializer<?> getSerializer() {
        return SlashRecipeSers.PROF_CRAFTING.get();
    }
}