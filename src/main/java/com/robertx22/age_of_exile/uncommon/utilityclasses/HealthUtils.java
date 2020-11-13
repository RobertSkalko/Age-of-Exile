package com.robertx22.age_of_exile.uncommon.utilityclasses;

import com.robertx22.age_of_exile.capability.entity.EntityCap;
import com.robertx22.age_of_exile.database.data.stats.types.resources.health.Health;
import com.robertx22.age_of_exile.uncommon.datasaving.Load;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.MathHelper;

public class HealthUtils {

    public static void heal(LivingEntity en, float heal) {
        if (en instanceof PlayerEntity) {
            en.heal(heal);
        } else {
            en.heal(heal);
        }
    }

    public static float vanillaToReal(LivingEntity en, float dmg) {
        return Health.getInstance()
            .scale(dmg, Load.Unit(en)
                .getLevel());
    }

    public static float realToVanilla(LivingEntity en, float dmg) {
        float antiScaling = 1F / Health.getInstance()
            .scale(1F, Load.Unit(en)
                .getLevel());
        return antiScaling * dmg;
    }

    public static float getCombinedHealthMulti(LivingEntity en) {
        return getCombinedCurrentHealth(en) / getCombinedMaxHealth(en);
    }

    public static float getMaxHealth(LivingEntity en) {
        EntityCap.UnitData data = Load.Unit(en);

        float maxhp = MathHelper.clamp(en.getMaxHealth(), 0, 40);
        // all increases after this would just reduce enviro damage

        float scaledhp = Health.getInstance()
            .scale(maxhp, data.getLevel());

        return data.getUnit()
            .healthData()
            .getAverageValue() + scaledhp;

    }

    public static int getCurrentHealth(LivingEntity entity) {

        float multi = entity.getHealth() / entity.getMaxHealth();

        float max = getMaxHealth(entity);

        return (int) (max * multi);

    }

    public static float getCombinedMaxHealth(LivingEntity en) {
        EntityCap.UnitData data = Load.Unit(en);
        return data.getUnit()
            .magicShieldData()
            .getAverageValue() + getMaxHealth(en);

    }

    public static float getCombinedCurrentHealth(LivingEntity en) {
        EntityCap.UnitData data = Load.Unit(en);
        return data.getResources()
            .getMagicShield() + getCurrentHealth(en);
    }
}
