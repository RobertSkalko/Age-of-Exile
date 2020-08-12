package com.robertx22.age_of_exile.loot.blueprints.bases;

import com.robertx22.age_of_exile.loot.blueprints.ItemBlueprint;
import com.robertx22.age_of_exile.uncommon.interfaces.data_items.ITiered;
import com.robertx22.age_of_exile.uncommon.utilityclasses.RandomUtils;
import net.minecraft.util.math.MathHelper;

public class TierPart extends BlueprintPart<Integer> {

    public int number;
    public boolean isRandom = true;
    public int variance = 2;

    public int minTier = 1;
    public int maxTier = ITiered.getMaxTier();

    public TierPart(ItemBlueprint blueprint) {
        super(blueprint);
    }

    @Override
    protected Integer generateIfNull() {
        if (isRandom) {

            int finalTier = RandomUtils.RandomRange(
                MathHelper.clamp(number - variance, minTier, maxTier),
                MathHelper.clamp(number + variance, minTier, maxTier)
            );

            return MathHelper.clamp(finalTier, minTier, maxTier);

        } else {
            return number;
        }

    }
}


