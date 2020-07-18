package com.robertx22.mine_and_slash.loot.blueprints.bases;

import com.robertx22.mine_and_slash.loot.blueprints.ItemBlueprint;
import com.robertx22.mine_and_slash.uncommon.interfaces.data_items.ITiered;
import com.robertx22.mine_and_slash.uncommon.utilityclasses.RandomUtils;
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
            int finalTier = RandomUtils.RandomRange(number - variance, number + variance);

            return MathHelper.clamp(finalTier, minTier, maxTier);

        } else {
            return number;
        }

    }
}


