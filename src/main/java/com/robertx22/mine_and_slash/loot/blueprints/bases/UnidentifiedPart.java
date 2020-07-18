package com.robertx22.mine_and_slash.loot.blueprints.bases;

import com.robertx22.mine_and_slash.database.data.rarities.GearRarity;
import com.robertx22.mine_and_slash.loot.blueprints.ItemBlueprint;
import com.robertx22.mine_and_slash.uncommon.utilityclasses.RandomUtils;

public class UnidentifiedPart extends BlueprintPart<Boolean> {

    public UnidentifiedPart(ItemBlueprint blueprint) {
        super(blueprint);
    }

    public float chance = 0;

    @Override
    protected Boolean generateIfNull() {
        if (chance == 0) {
            chance = ((GearRarity) blueprint.rarity.get())
                .unidentifiedChance();
        }

        return RandomUtils.roll(chance);
    }
}
