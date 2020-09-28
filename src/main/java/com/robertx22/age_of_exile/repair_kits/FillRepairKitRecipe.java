package com.robertx22.age_of_exile.repair_kits;

import com.robertx22.age_of_exile.mmorpg.ModRegistry;
import com.robertx22.age_of_exile.vanilla_mc.blocks.slots.FuelSlot;
import net.minecraft.inventory.CraftingInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.recipe.SpecialCraftingRecipe;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.List;

public class FillRepairKitRecipe extends SpecialCraftingRecipe {
    public FillRepairKitRecipe(Identifier id) {
        super(id);
    }

    @Override
    public boolean matches(CraftingInventory inv, World world) {
        boolean haskit = false;
        boolean hasrepair = false;

        for (int i = 0; i < inv.size(); ++i) {
            ItemStack stack = inv.getStack(i);
            if (stack.getItem() instanceof RepairKitItem) {
                if (haskit) {
                    return false; // dont allow 2! that would make 1 disappear
                }
                haskit = true;
                continue;
            } else if (FuelSlot.FUEL_VALUES.getOrDefault(stack.getItem(), 0) > 0) {
                hasrepair = true;
                continue;
            } else {
                if (!stack.isEmpty()) {
                    return false;
                }
            }
        }

        return haskit && hasrepair;
    }

    @Override
    public ItemStack craft(CraftingInventory inv) {

        ItemStack kit = ItemStack.EMPTY;
        List<ItemStack> repair = new ArrayList<>();

        for (int i = 0; i < inv.size(); ++i) {
            ItemStack stack = inv.getStack(i);
            if (stack.getItem() instanceof RepairKitItem) {
                kit = stack.copy();
                continue;
            }
            if (FuelSlot.FUEL_VALUES.getOrDefault(stack.getItem(), 0) > 0) {
                repair.add(stack);
            }
        }

        int val = 0;

        for (ItemStack x : repair) {
            val += FuelSlot.FUEL_VALUES.getOrDefault(x.getItem(), 0);
        }

        kit.setDamage(kit.getDamage() - val);

        return kit;
    }

    @Override
    public boolean fits(int width, int height) {
        return width > 1 || height > 1;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return ModRegistry.RECIPE_SER.FILL_REPAIR_KIT;
    }

}
