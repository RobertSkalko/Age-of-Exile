package com.robertx22.age_of_exile.aoe_data.database.ingredient;

import com.robertx22.age_of_exile.database.data.StatModifier;
import com.robertx22.age_of_exile.database.data.stats.types.defense.Armor;
import com.robertx22.age_of_exile.database.data.stats.types.generated.ElementalResist;
import com.robertx22.age_of_exile.mmorpg.registers.common.items.IngredientItems;
import com.robertx22.age_of_exile.saveclasses.player_skills.PlayerSkillEnum;
import com.robertx22.age_of_exile.uncommon.enumclasses.Elements;
import com.robertx22.age_of_exile.uncommon.enumclasses.ModType;
import com.robertx22.age_of_exile.uncommon.interfaces.data_items.IRarity;
import com.robertx22.library_of_exile.registry.ExileRegistryInit;

public class SlashIngredientsAdder implements ExileRegistryInit {

    @Override
    public void registerAll() {

        SlashIngredientBuilder.of("guardian_scales", "Guardian Scales", IRarity.COMMON_ID, IngredientItems.GUARDIAN_SCALES)
            .allowedIn(PlayerSkillEnum.ALCHEMY)
            .allowedIn(PlayerSkillEnum.ARMOR_CRAFTING)
            .stats(new StatModifier(2, 6, new ElementalResist(Elements.Water)))
            .stats(new StatModifier(2, 4, Armor.getInstance(), ModType.PERCENT))
            .build()

        ;
    }
}
