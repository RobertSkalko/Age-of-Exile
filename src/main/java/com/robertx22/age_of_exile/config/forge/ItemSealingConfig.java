package com.robertx22.age_of_exile.config.forge;

import com.robertx22.age_of_exile.database.data.currency.base.ICurrencyItemEffect;
import com.robertx22.age_of_exile.saveclasses.item_classes.GearItemData;
import com.robertx22.age_of_exile.uncommon.utilityclasses.LevelUtils;

public class ItemSealingConfig {

    private boolean ENABLE_SEALING_FEATURE = true;

    private int START_AT_LEVEL = 20;

    private float BASE_INSTABILITY_MULTI = 0.1F;

    public float ALWAYS_SEAL_AT_X_INSTABILITY = 1000;

    private float EXTRA_INSTABILITY_MULTI_AT_MAX_LEVEL = 1F;

    public float getInstability(GearItemData gear, ICurrencyItemEffect effect) {
        if (!ENABLE_SEALING_FEATURE) {
            return 0;
        }
        if (gear.level < START_AT_LEVEL) {
            return 0;
        }

        float num = effect.getInstability();

        float multi = BASE_INSTABILITY_MULTI + (LevelUtils.getMaxLevelMultiplier(gear.level) * EXTRA_INSTABILITY_MULTI_AT_MAX_LEVEL);

        num *= multi;

        return num;
    }

    public float getChanceToSeal(GearItemData gear) {
        if (!ENABLE_SEALING_FEATURE) {
            return 0;
        }
        if (gear.level < START_AT_LEVEL) {
            return 0;
        }

        float chance = gear.getInstability() / ALWAYS_SEAL_AT_X_INSTABILITY * 100F;

        return chance;

    }
}
