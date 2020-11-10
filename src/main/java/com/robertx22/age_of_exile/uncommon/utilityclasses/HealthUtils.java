package com.robertx22.age_of_exile.uncommon.utilityclasses;

import com.robertx22.age_of_exile.capability.entity.EntityCap;
import com.robertx22.age_of_exile.uncommon.datasaving.Load;
import net.minecraft.entity.LivingEntity;

public class HealthUtils {

    public static float getCombinedHealthMulti(LivingEntity en) {
        return getCombinedCurrentHealth(en) / getCombinedMaxHealth(en);
    }

    public static float getCombinedMaxHealth(LivingEntity en) {
        EntityCap.UnitData data = Load.Unit(en);
        return data.getUnit()
            .magicShieldData()
            .getAverageValue() + en.getMaxHealth();

    }

    public static float getCombinedCurrentHealth(LivingEntity en) {
        EntityCap.UnitData data = Load.Unit(en);
        return data.getResources()
            .getMagicShield() + en.getHealth();
    }
}
