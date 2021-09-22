package com.robertx22.age_of_exile.aoe_data.database.ingredient;

import com.robertx22.age_of_exile.aoe_data.database.stats.Stats;
import com.robertx22.age_of_exile.database.data.StatModifier;
import com.robertx22.age_of_exile.database.data.stats.types.defense.Armor;
import com.robertx22.age_of_exile.mmorpg.registers.common.items.IngredientItems;
import com.robertx22.age_of_exile.saveclasses.player_skills.PlayerSkillEnum;
import com.robertx22.age_of_exile.uncommon.enumclasses.Elements;
import com.robertx22.age_of_exile.uncommon.enumclasses.ModType;
import com.robertx22.age_of_exile.uncommon.interfaces.data_items.IRarity;
import com.robertx22.library_of_exile.registry.ExileRegistryInit;

public class EpicIngredientsAdder implements ExileRegistryInit {

    @Override
    public void registerAll() {

        IngredientBuilder.of(IngredientItems.FRIGID_RUNESTONE, IRarity.EPIC_ID)
            .allowedIn(PlayerSkillEnum.INSCRIBING)
            .allowedIn(PlayerSkillEnum.WEAPON_CRAFTING)
            .stats(new StatModifier(2, 8, Stats.ELEMENTAL_SPELL_DAMAGE.get(Elements.Water)))
            .build();

        IngredientBuilder.of(IngredientItems.BLACKPEARL, IRarity.EPIC_ID)
            .allowedIn(PlayerSkillEnum.JEWEL_CRAFTING)
            .allowedIn(PlayerSkillEnum.ALCHEMY)
            .stats(new StatModifier(2, 6, Stats.CRIT_DAMAGE.get()))
            .stats(new StatModifier(-3, -5, Armor.getInstance(), ModType.PERCENT))
            .build();

        IngredientBuilder.of(IngredientItems.EARTH_CRYSTAL, IRarity.EPIC_ID)
            .allowedIn(PlayerSkillEnum.ARMOR_CRAFTING)
            .allowedIn(PlayerSkillEnum.ALCHEMY)
            .stats(new StatModifier(2, 8, Stats.ELEMENTAL_SPELL_DAMAGE.get(Elements.Earth)))
            .build();

        IngredientBuilder.of(IngredientItems.DARK_STONE, IRarity.EPIC_ID)
            .allowedIn(PlayerSkillEnum.ARMOR_CRAFTING)
            .allowedIn(PlayerSkillEnum.WEAPON_CRAFTING)
            .stats(new StatModifier(1, 4, Stats.ELEMENTAL_SPELL_DAMAGE.get(Elements.Fire)))
            .stats(new StatModifier(1, 3, Stats.SPELL_CRIT_CHANCE.get()))
            .build();

    }
}
