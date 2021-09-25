package com.robertx22.age_of_exile.aoe_data.database.ingredient;

import com.robertx22.age_of_exile.aoe_data.database.stats.Stats;
import com.robertx22.age_of_exile.database.data.StatModifier;
import com.robertx22.age_of_exile.database.data.stats.types.crafting.craft_grid.IngNotTouchThis;
import com.robertx22.age_of_exile.database.data.stats.types.crafting.craft_grid.IngTouchThis;
import com.robertx22.age_of_exile.mmorpg.registers.common.items.IngredientItems;
import com.robertx22.age_of_exile.saveclasses.player_skills.PlayerSkillEnum;
import com.robertx22.age_of_exile.uncommon.interfaces.data_items.IRarity;
import com.robertx22.library_of_exile.registry.ExileRegistryInit;

public class GridIngredientsAdder implements ExileRegistryInit {

    // this will require a rebalance if i make my own crafting container with 6 slots only

    @Override
    public void registerAll() {

        IngredientBuilder.of(IngredientItems.BLACKPEARL, IRarity.EPIC_ID)
            .allowedIn(PlayerSkillEnum.JEWEL_CRAFTING)
            .allowedIn(PlayerSkillEnum.ALCHEMY)
            .stats(new StatModifier(3, 6, Stats.CRIT_DAMAGE.get()))
            .stats(new StatModifier(-20, -20, IngTouchThis.getInstance()))
            .build();

        IngredientBuilder.of(IngredientItems.BONE_FIGURINE, IRarity.UNCOMMON)
            .allowedIn(PlayerSkillEnum.JEWEL_CRAFTING)
            .allowedIn(PlayerSkillEnum.ARMOR_CRAFTING)
            .allowedIn(PlayerSkillEnum.WEAPON_CRAFTING)
            .stats(new StatModifier(10, 10, IngNotTouchThis.getInstance()))
            .stats(new StatModifier(-25, -25, IngTouchThis.getInstance()))
            .build();
    }
}
