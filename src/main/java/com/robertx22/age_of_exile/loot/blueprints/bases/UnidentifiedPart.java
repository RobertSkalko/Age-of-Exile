package com.robertx22.age_of_exile.loot.blueprints.bases;

import com.robertx22.age_of_exile.database.data.rarities.IGearRarity;
import com.robertx22.age_of_exile.loot.blueprints.ItemBlueprint;
import com.robertx22.age_of_exile.uncommon.utilityclasses.RandomUtils;

public class UnidentifiedPart extends BlueprintPart<Boolean> {

    public UnidentifiedPart(ItemBlueprint blueprint) {
        super(blueprint);
    }

    public float chance = 0;

    @Override
    protected Boolean generateIfNull() {
        if (chance == 0) {
            chance = ((IGearRarity) blueprint.rarity.get())
                .unidentifiedChance();
        }

        return RandomUtils.roll(chance);
    }
}
