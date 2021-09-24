package com.robertx22.age_of_exile.aoe_data.database.ingredient;

import com.robertx22.age_of_exile.database.data.StatModifier;
import com.robertx22.age_of_exile.database.data.stats.types.crafting.CraftingSuccessChance;
import com.robertx22.age_of_exile.database.data.stats.types.crafting.IncreaseMinRarityStat;
import com.robertx22.age_of_exile.database.data.stats.types.crafting.PlusConsumableUses;
import com.robertx22.age_of_exile.mmorpg.registers.common.items.IngredientItems;
import com.robertx22.age_of_exile.saveclasses.player_skills.PlayerSkillEnum;
import com.robertx22.age_of_exile.uncommon.interfaces.data_items.IRarity;
import com.robertx22.library_of_exile.registry.ExileRegistryInit;

public class SpecialIngredientsAdder implements ExileRegistryInit {

    @Override
    public void registerAll() {

        // always use same stat ranges here!!!

        IngredientBuilder.of(IngredientItems.WISHING_BOWL, IRarity.RARE_ID)
            .allowedIn(PlayerSkillEnum.JEWEL_CRAFTING)
            .allowedIn(PlayerSkillEnum.ARMOR_CRAFTING)
            .stats(new StatModifier(1, 1, IncreaseMinRarityStat.getInstance()))
            .setIsArtifact()
            .build();

        IngredientBuilder.of(IngredientItems.POT_OF_GREED, IRarity.RARE_ID)
            .allowedIn(PlayerSkillEnum.ALCHEMY)
            .allowedIn(PlayerSkillEnum.COOKING)
            .allowedIn(PlayerSkillEnum.INSCRIBING)
            .stats(new StatModifier(2, 2, PlusConsumableUses.getInstance()))
            .setIsArtifact()
            .build();

        IngredientBuilder.of(IngredientItems.SOUL_MATRIX, IRarity.EPIC_ID)
            .allowedIn(PlayerSkillEnum.ALCHEMY)
            .allowedIn(PlayerSkillEnum.COOKING)
            .allowedIn(PlayerSkillEnum.INSCRIBING)
            .allowedIn(PlayerSkillEnum.JEWEL_CRAFTING)
            .allowedIn(PlayerSkillEnum.WEAPON_CRAFTING)
            .allowedIn(PlayerSkillEnum.ARMOR_CRAFTING)
            .stats(new StatModifier(20, 20, CraftingSuccessChance.getInstance()))
            .setIsArtifact()
            .build();

    }
}
