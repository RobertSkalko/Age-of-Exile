package com.robertx22.age_of_exile.uncommon.utilityclasses;

import com.robertx22.age_of_exile.capability.entity.EntityCap;
import com.robertx22.age_of_exile.database.data.stats.types.resources.health.Health;
import com.robertx22.age_of_exile.uncommon.datasaving.Load;
import net.minecraft.entity.LivingEntity;

public class HealthUtils {

    public static void heal(LivingEntity en, float heal) {
        en.heal(heal);
    }

    public static float vanillaToReal(LivingEntity en, float dmg) {

        return Health.getInstance()
            .scale(dmg, Load.Unit(en)
                .getLevel());
    }

    public static float realToVanilla(LivingEntity en, float dmg) {
        float multi = dmg / getCombinedMaxHealth(en);
        float total = multi * en.getMaxHealth();
        return total;
    }

    public static float getCombinedHealthMulti(LivingEntity en) {
        return getCombinedCurrentHealth(en) / getCombinedMaxHealth(en);
    }

    public static float getMaxHealth(LivingEntity en) {
        EntityCap.UnitData data = Load.Unit(en);

        if (en.world.isClient) {
            return data.getSyncedMaxHealth(); // for client, health needs to be synced
        }

        return data.getUnit()
            .healthData()
            .getAverageValue();

    }

    public static int getCurrentHealth(LivingEntity entity) {

        float multi = entity.getHealth() / entity.getMaxHealth();

        float max = getMaxHealth(entity);

        return (int) (max * multi);

    }

    public static float getCombinedMaxHealth(LivingEntity en) {
        EntityCap.UnitData data = Load.Unit(en);
        return getMaxHealth(en);

    }

    public static float getCombinedCurrentHealth(LivingEntity en) {
        EntityCap.UnitData data = Load.Unit(en);
        return getCurrentHealth(en);
    }
}
