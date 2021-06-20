package com.robertx22.age_of_exile.aoe_data.database.salvage_recipes;

import com.robertx22.age_of_exile.aoe_data.datapacks.generators.LootTableGenerator;
import com.robertx22.age_of_exile.database.data.salvage_recipes.ItemIngredient;
import com.robertx22.age_of_exile.database.data.salvage_recipes.ItemRequirement;
import com.robertx22.age_of_exile.database.data.salvage_recipes.SalvageRecipe;
import com.robertx22.age_of_exile.database.registry.ExileRegistryInit;
import com.robertx22.age_of_exile.uncommon.interfaces.data_items.IRarity;

public class SalvageRecipes implements ExileRegistryInit {

    @Override
    public void registerAll() {

        SalvageRecipe.of("gem_recipe", "Gem Recipe", LootTableGenerator.GEM_SALVAGE_RECIPE,
            ItemIngredient.of(ItemRequirement.rarity(IRarity.MYTHIC_ID)),
            ItemIngredient.of(ItemRequirement.rarity(IRarity.MYTHIC_ID)),
            ItemIngredient.of(ItemRequirement.rarity(IRarity.MYTHIC_ID))
        )
            .addToSerializables();

        SalvageRecipe.of("currency_recipe", "Currency Recipe", LootTableGenerator.CURRENCIES_SALVAGE_RECIPE,
            ItemIngredient.of(ItemRequirement.rarity(IRarity.UNIQUE_ID)),
            ItemIngredient.of(ItemRequirement.rarity(IRarity.UNIQUE_ID)),
            ItemIngredient.of(ItemRequirement.rarity(IRarity.UNIQUE_ID))
        )
            .rolls(2)
            .addToSerializables();

    }
}
