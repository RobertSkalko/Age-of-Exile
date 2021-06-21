package com.robertx22.age_of_exile.uncommon.utilityclasses;

import com.robertx22.age_of_exile.database.data.exile_effects.EffectTags;
import com.robertx22.age_of_exile.database.data.exile_effects.ExileEffect;
import com.robertx22.age_of_exile.database.registry.Database;
import com.robertx22.age_of_exile.uncommon.datasaving.Load;
import net.minecraft.entity.LivingEntity;

public class ExileEffectUtils {

    public static int countEffectsWithTag(LivingEntity en, EffectTags tag) {

        int amount = 0;

        for (String k : Load.Unit(en)
            .getStatusEffectsData().exileMap.keySet()) {
            ExileEffect eff = Database.ExileEffects()
                .get(k);
            if (eff.hasTag(tag)) {
                amount++;
            }
        }

        return amount;

    }
}
