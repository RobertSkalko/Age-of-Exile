package com.robertx22.age_of_exile.player_skills.ingredient;

import com.robertx22.age_of_exile.player_skills.crafting_inv.ProfCraftResult;
import com.robertx22.age_of_exile.player_skills.ingredient.data.CraftSlotData;
import com.robertx22.age_of_exile.player_skills.ingredient.data.CraftingProcessData;
import com.robertx22.age_of_exile.player_skills.ingredient.data.IngredientData;
import com.robertx22.age_of_exile.saveclasses.player_skills.PlayerSkillEnum;
import com.robertx22.age_of_exile.uncommon.datasaving.StackSaving;
import com.robertx22.age_of_exile.uncommon.localization.Words;
import com.robertx22.age_of_exile.uncommon.utilityclasses.OnScreenMessageUtils;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.CraftingInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.network.play.server.STitlePacket;
import net.minecraft.util.text.StringTextComponent;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ProfCraftingRecipe {

    public static ProfCraftResult canCraft(PlayerEntity player, CraftingInventory inv, Inventory resultinv, PlayerSkillEnum skill) {
        List<IngredientData> list = new ArrayList<>();

        if (skill.isGearCraftingProf()) {
            boolean hasgear = false;

            boolean hasgearwithdata = false;

            for (int i = 0; i < inv.getContainerSize(); ++i) {
                ItemStack stack = inv.getItem(i);
                if (!stack.isEmpty()) {

                    if (StackSaving.GEARS.has(stack)) {
                        hasgearwithdata = true;
                        break;
                    }

                    if (skill.gearMatchesProfession(stack.getItem())) {

                        OnScreenMessageUtils.sendMessage((ServerPlayerEntity) player, new StringTextComponent("No gear"), STitlePacket.Type.SUBTITLE);

                        hasgear = true;
                    }

                }
            }
            if (hasgearwithdata) {
                return ProfCraftResult.fail(Words.GearHasData);
            }
            if (!hasgear) {
                return ProfCraftResult.fail(Words.NoGear);
            }
        }

        for (int i = 0; i < inv.getContainerSize(); ++i) {
            ItemStack stack = inv.getItem(i);
            if (!stack.isEmpty()) {

                if (skill.isGearCraftingProf()) {
                    if (skill.gearMatchesProfession(stack.getItem())) {
                        continue;
                    }
                }

                if (!StackSaving.INGREDIENTS.has(stack)) {
                    return ProfCraftResult.fail(Words.WrongIngredient);
                }
            }
        }

        HashMap<String, Integer> countmap = new HashMap<>();

        for (int i = 0; i < inv.getContainerSize(); ++i) {
            ItemStack stack = inv.getItem(i);
            if (StackSaving.INGREDIENTS.has(stack)) {
                IngredientData data = StackSaving.INGREDIENTS.loadFrom(stack);
                if (data != null) {
                    if (!data.getIngredient()
                        .isAllowedInProfession(skill.id)) {
                        return ProfCraftResult.fail(Words.WrongRecipeIngredient);
                    }
                    if (data.getIngredient()
                        .isOneOfAKind()) {
                        countmap.put(data.getIngredient()
                            .getOneOfAKindId(), countmap.getOrDefault(data.getIngredient()
                            .getOneOfAKindId(), 0) + 1);
                    }
                    if (countmap.getOrDefault(data.getIngredient()
                        .getOneOfAKindId(), 0) > 1) {
                        return ProfCraftResult.fail(Words.CantUseMoreOf);
                    }
                    list.add(data);
                }
            }
        }

        if (list.isEmpty()) {
            return ProfCraftResult.fail(Words.NoIngredients);
        }

        if (list.size() > 6) {
            return ProfCraftResult.fail(Words.NoIngredients);
        }

        return ProfCraftResult.success();

    }

    public static ItemStack craftResult(CraftingInventory inv, Inventory resultinv, PlayerSkillEnum skill) {
        List<CraftSlotData> list = new ArrayList<>();

        int ingredientCount = 0;

        for (int i = 0; i < inv.getContainerSize(); ++i) {
            ItemStack stack = inv.getItem(i);

            CraftSlotData slot = new CraftSlotData();
            slot.place = i;

            if (StackSaving.INGREDIENTS.has(stack)) {
                IngredientData data = StackSaving.INGREDIENTS.loadFrom(stack);

                slot.ing = data;

                if (data != null) {
                    ingredientCount++;
                }

            }

            list.add(slot);
        }

        if (ingredientCount < 1) {
            return ItemStack.EMPTY;
        }

        CraftingProcessData data = new CraftingProcessData();
        data.prof = skill.GUID();
        data.ingredients = list;

        ItemStack stack = ItemStack.EMPTY;

        if (skill.isGearCraftingProf()) {
            for (int i = 0; i < inv.getContainerSize(); ++i) {
                ItemStack gear = inv.getItem(i);
                if (!gear.isEmpty()) {
                    if (skill.gearMatchesProfession(gear.getItem())) {
                        stack = gear.copy();
                    }
                }
            }

        } else {
            stack = new ItemStack(skill.getCraftResultItem());
        }

        StackSaving.CRAFT_PROCESS.saveTo(stack, data);
        return stack;
    }

}