package com.robertx22.age_of_exile.repair_kits;

import com.robertx22.age_of_exile.mmorpg.ModRegistry;
import com.robertx22.age_of_exile.uncommon.datasaving.Gear;
import net.minecraft.inventory.CraftingInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.recipe.SpecialCraftingRecipe;
import net.minecraft.util.Identifier;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;

public class RepairWithKitRecipe extends SpecialCraftingRecipe {

    public RepairWithKitRecipe(Identifier id) {
        super(id);
    }

    @Override
    public boolean matches(CraftingInventory inv, World world) {
        int count = 0;
        boolean haskit = false;
        boolean hasgear = false;

        for (int i = 0; i < inv.size(); ++i) {
            ItemStack stack = inv.getStack(i);
            if (stack.getItem() instanceof RepairKitItem) {

                if (stack.getDamage() >= stack.getMaxDamage()) {
                    return false; // no durability left
                }

                haskit = true;
                count++;
                continue;
            } else if (Gear.has(stack)) {
                if (!stack.isDamaged()) {
                    return false; // already full dura
                }
                hasgear = true;
                count++;
                continue;
            } else {
                if (!stack.isEmpty()) {
                    return false;
                }
            }
        }

        return count == 2 && haskit && hasgear;
    }

    @Override
    public ItemStack craft(CraftingInventory inv) {

        ItemStack gear = ItemStack.EMPTY;

        for (int i = 0; i < inv.size(); ++i) {
            ItemStack stack = inv.getStack(i);

            if (Gear.has(stack)) {
                gear = stack.copy();
                continue;
            }
        }
        int val = getRepairValue(inv);

        gear.setDamage(gear.getDamage() - val);

        return gear;
    }

    public int getRepairValue(CraftingInventory inv) {

        ItemStack kit = ItemStack.EMPTY;
        ItemStack gear = ItemStack.EMPTY;

        for (int i = 0; i < inv.size(); ++i) {
            ItemStack stack = inv.getStack(i);
            if (stack.getItem() instanceof RepairKitItem) {
                kit = stack;
                continue;
            }
            if (Gear.has(stack)) {
                gear = stack;
                continue;
            }
        }
        int val = kit.getMaxDamage() - kit.getDamage();
        val = MathHelper.clamp(val, 0, gear.getDamage());

        return val;
    }

    @Override
    public DefaultedList<ItemStack> getRemainingStacks(CraftingInventory inventory) {
        DefaultedList<ItemStack> defaultedList = DefaultedList.ofSize(inventory.size(), ItemStack.EMPTY);

        for (int i = 0; i < defaultedList.size(); ++i) {
            ItemStack stack = inventory.getStack(i)
                .copy();
            Item item = stack
                .getItem();
            if (item instanceof RepairKitItem) {
                stack.setDamage(stack.getDamage() + getRepairValue(inventory));
                defaultedList.set(i, stack);
            }
        }

        return defaultedList;
    }

    @Override
    public boolean fits(int width, int height) {
        return width > 1 || height > 1;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return ModRegistry.RECIPE_SER.REPAIR_WITH_KIT;
    }

}
