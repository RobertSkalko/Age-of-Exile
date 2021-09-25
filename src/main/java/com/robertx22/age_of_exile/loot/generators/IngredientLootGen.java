package com.robertx22.age_of_exile.loot.generators;

import com.robertx22.age_of_exile.config.forge.ServerContainer;
import com.robertx22.age_of_exile.database.data.ingredient.SlashIngredient;
import com.robertx22.age_of_exile.database.registry.ExileDB;
import com.robertx22.age_of_exile.loot.LootInfo;
import com.robertx22.age_of_exile.loot.blueprints.GearBlueprint;
import com.robertx22.age_of_exile.player_skills.ingredient.data.IngredientData;
import com.robertx22.age_of_exile.uncommon.datasaving.StackSaving;
import com.robertx22.age_of_exile.uncommon.enumclasses.LootType;
import com.robertx22.library_of_exile.utils.RandomUtils;
import net.minecraft.item.ItemStack;

public class IngredientLootGen extends BaseLootGen<GearBlueprint> {

    public IngredientLootGen(LootInfo info) {
        super(info);
    }

    @Override
    public float baseDropChance() {
        return (float) (ServerContainer.get().INGREDIENT_DROPRATE.get()
            .floatValue());
    }

    @Override
    public LootType lootType() {
        return LootType.Ingredient;
    }

    @Override
    public boolean condition() {
        if (info.favorRank != null) {
            if (!info.favorRank.can_salvage_loot) {
                return false;
            }
        }
        return true;
    }

    @Override
    public ItemStack generateOne() {

        SlashIngredient ing = ExileDB.Ingredients()
            .random();

        IngredientData data = IngredientData.of(ing, info.tier);

        ItemStack stack = new ItemStack(ing.getItem());

        int count = RandomUtils.RandomRange(1, 3);

        stack.setCount(count);

        StackSaving.INGREDIENTS.saveTo(stack, data);

        return stack;

    }

}
