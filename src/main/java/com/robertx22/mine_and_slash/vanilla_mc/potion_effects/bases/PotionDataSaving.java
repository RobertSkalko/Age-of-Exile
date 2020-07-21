package com.robertx22.mine_and_slash.vanilla_mc.potion_effects.bases;

import com.robertx22.mine_and_slash.capability.entity.EntityCap;
import com.robertx22.mine_and_slash.mmorpg.Ref;
import com.robertx22.mine_and_slash.uncommon.datasaving.Load;
import com.robertx22.mine_and_slash.vanilla_mc.potion_effects.bases.data.ExtraPotionData;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;

public class PotionDataSaving {

    private static String LOC = Ref.MODID + ":pot_data";

    public static ExtraPotionData getData(LivingEntity entity, StatusEffectInstance instance) {

        EntityCap.UnitData data = Load.Unit(entity);

        if (data.getStatusEffectsData()
            .has(instance.getEffectType())) {
            return data.getStatusEffectsData()
                .get(instance.getEffectType());
        }

        return new ExtraPotionData();
    }

    public static void saveData(LivingEntity entity, StatusEffectInstance instance, ExtraPotionData data) {
        EntityCap.UnitData unitdata = Load.Unit(entity);
        unitdata.getStatusEffectsData()
            .set(instance.getEffectType(), data);
    }

}
