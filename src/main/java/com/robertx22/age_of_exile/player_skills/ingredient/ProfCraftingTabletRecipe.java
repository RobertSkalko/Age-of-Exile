package com.robertx22.age_of_exile.player_skills.ingredient;

import com.robertx22.age_of_exile.database.data.StatModifier;
import com.robertx22.age_of_exile.mmorpg.registers.common.CraftedConsumableItems;
import com.robertx22.age_of_exile.mmorpg.registers.common.SlashRecipeSers;
import com.robertx22.age_of_exile.saveclasses.ExactStatData;
import com.robertx22.age_of_exile.saveclasses.player_skills.PlayerSkillEnum;
import com.robertx22.age_of_exile.uncommon.datasaving.StackSaving;
import com.robertx22.age_of_exile.uncommon.utilityclasses.LevelUtils;
import com.robertx22.library_of_exile.utils.RandomUtils;
import net.minecraft.inventory.CraftingInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.SpecialRecipe;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.List;

public class ProfCraftingTabletRecipe extends SpecialRecipe {

    public ProfCraftingTabletRecipe(ResourceLocation id) {
        super(id);
    }

    @Override
    public boolean matches(CraftingInventory inv, World world) {
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
            return false;
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

        CraftedConsumableData data = new CraftedConsumableData();
        data.prof = skill.GUID();

        for (IngredientData x : list) {
            int lvl = LevelUtils.tierToLevel(x.tier);
            int perc = RandomUtils.RandomRange(0, 100);
            for (StatModifier s : x.getIngredient().stats) {
                ExactStatData stat = s.ToExactStat(perc, lvl);
                stat.multiplyBy(skill.craftedStatMulti);
                data.stats.add(stat);
            }
        }

        Item item = Items.AIR;

        if (skill == PlayerSkillEnum.COOKING) {
            data.uses = 3;
            item = CraftedConsumableItems.FOOD.get();
        }

        ItemStack stack = new ItemStack(item);

        StackSaving.CRAFTED_CONSUMABLE.saveTo(stack, data);

        return stack;
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