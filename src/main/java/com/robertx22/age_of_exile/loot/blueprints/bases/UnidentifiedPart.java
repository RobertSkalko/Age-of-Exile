package com.robertx22.age_of_exile.loot.blueprints.bases;

import com.robertx22.age_of_exile.database.data.rarities.IGearRarity;
import com.robertx22.age_of_exile.loot.blueprints.GearBlueprint;
import com.robertx22.library_of_exile.utils.RandomUtils;

public class UnidentifiedPart extends BlueprintPart<Boolean, GearBlueprint> {

    public UnidentifiedPart(GearBlueprint blueprint) {
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
