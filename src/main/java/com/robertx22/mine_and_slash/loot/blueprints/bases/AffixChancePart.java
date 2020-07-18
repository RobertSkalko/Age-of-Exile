package com.robertx22.mine_and_slash.loot.blueprints.bases;

import com.robertx22.mine_and_slash.database.data.rarities.GearRarity;
import com.robertx22.mine_and_slash.loot.blueprints.ItemBlueprint;
import com.robertx22.mine_and_slash.uncommon.utilityclasses.RandomUtils;

public class AffixChancePart extends BlueprintPart<Boolean> {

    public AffixChancePart(ItemBlueprint blueprint) {
        super(blueprint);
    }

    public float chance = 0;

    @Override
    protected Boolean generateIfNull() {
        if (chance == 0) {
            chance = ((GearRarity) blueprint.rarity.get())
                .AffixChance();
        }

        return RandomUtils.roll(chance);
    }
}
