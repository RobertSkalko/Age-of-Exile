package com.robertx22.age_of_exile.player_skills.ingredient;

import com.robertx22.age_of_exile.mmorpg.registers.common.SlashRecipeSers;
import com.robertx22.age_of_exile.player_skills.ingredient.data.CraftingProcessData;
import com.robertx22.age_of_exile.player_skills.ingredient.data.IngredientData;
import com.robertx22.age_of_exile.player_skills.ingredient.items.CraftToolItem;
import com.robertx22.age_of_exile.saveclasses.player_skills.PlayerSkillEnum;
import com.robertx22.age_of_exile.uncommon.datasaving.StackSaving;
import net.minecraft.inventory.CraftingInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.SpecialRecipe;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.List;

public class ProfCraftingRecipe extends SpecialRecipe {

    public ProfCraftingRecipe(ResourceLocation id) {
        super(id);
    }

    @Override
    public boolean matches(CraftingInventory inv, World world) {
        List<IngredientData> list = new ArrayList<>();

        PlayerSkillEnum skill = null;

        for (int i = 0; i < inv.getContainerSize(); ++i) {
            ItemStack stack = inv.getItem(i);
            if (!stack.isEmpty()) {
                if (stack.getItem() instanceof CraftToolItem == false && !StackSaving.INGREDIENTS.has(stack)) {
                    return false;
                }
            }
        }

        for (int i = 0; i < inv.getContainerSize(); ++i) {
            ItemStack stack = inv.getItem(i);
            if (stack.getItem() instanceof CraftToolItem) {
                CraftToolItem tool = (CraftToolItem) stack.getItem();
                skill = tool.skill;
            }
        }

        if (skill == null) {
            return false;
        }

        for (int i = 0; i < inv.getContainerSize(); ++i) {
            ItemStack stack = inv.getItem(i);
            if (StackSaving.INGREDIENTS.has(stack)) {
                IngredientData data = StackSaving.INGREDIENTS.loadFrom(stack);
                if (data != null) {
                    if (!data.getIngredient()
                        .isAllowedInProfession(skill.id)) {
                        return false;
                    }

                    list.add(data);
                }
            }
        }

        if (list.isEmpty() || list.size() > 6) {
            return false;
        }

        return true;
    }

    @Override
    public ItemStack assemble(CraftingInventory inv) {
        List<IngredientData> list = new ArrayList<>();

        PlayerSkillEnum skill = null;

        for (int i = 0; i < inv.getContainerSize(); ++i) {
            ItemStack stack = inv.getItem(i);
            if (stack.getItem() instanceof CraftToolItem) {
                CraftToolItem tool = (CraftToolItem) stack.getItem();
                skill = tool.skill;
            }
        }

        if (skill == null) {
            return ItemStack.EMPTY;
        }

        for (int i = 0; i < inv.getContainerSize(); ++i) {
            ItemStack stack = inv.getItem(i);
            if (StackSaving.INGREDIENTS.has(stack)) {
                IngredientData data = StackSaving.INGREDIENTS.loadFrom(stack);
                if (data != null) {
                    list.add(data);
                }
            }
        }

        if (list.isEmpty()) {
            return ItemStack.EMPTY;
        }

        CraftingProcessData data = new CraftingProcessData();
        data.prof = skill.GUID();
        data.ingredients = list;

        ItemStack stack = new ItemStack(skill.getCraftResultItem());

        StackSaving.CRAFT_PROCESS.saveTo(stack, data);

        return stack;
    }

    @Override
    public boolean canCraftInDimensions(int x, int y) {
        return x * y >= 2 && x * y < 10;
    }

    @Override
    public IRecipeSerializer<?> getSerializer() {
        return SlashRecipeSers.PROF_CRAFTING.get();
    }
}