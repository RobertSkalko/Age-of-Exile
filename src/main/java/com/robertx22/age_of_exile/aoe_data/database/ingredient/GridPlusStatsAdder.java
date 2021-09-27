package com.robertx22.age_of_exile.aoe_data.database.ingredient;

import com.robertx22.age_of_exile.aoe_data.database.stats.Stats;
import com.robertx22.age_of_exile.database.data.StatModifier;
import com.robertx22.age_of_exile.database.data.stats.types.crafting.craft_grid.IngTouchThis;
import com.robertx22.age_of_exile.mmorpg.registers.common.items.IngredientItems;
import com.robertx22.age_of_exile.saveclasses.player_skills.PlayerSkillEnum;
import com.robertx22.age_of_exile.uncommon.interfaces.data_items.IRarity;
import com.robertx22.library_of_exile.registry.ExileRegistryInit;

public class GridPlusStatsAdder implements ExileRegistryInit {

    @Override
    public void registerAll() {

        IngredientBuilder.of(IngredientItems.ENIGMA_STONE, IRarity.RARE_ID)
                .allowedIn(PlayerSkillEnum.JEWEL_CRAFTING)
                .allowedIn(PlayerSkillEnum.WEAPON_CRAFTING)
                .allowedIn(PlayerSkillEnum.ALCHEMY)
                .stats(new StatModifier(-4, 15, Stats.SPELL_CRIT_DAMAGE.get()))
                .stats(new StatModifier(-2, 6, Stats.SPELL_CRIT_CHANCE.get()))
                .stats(new StatModifier(-25, -25, IngTouchThis.getInstance()))
                .build();

    }
}
