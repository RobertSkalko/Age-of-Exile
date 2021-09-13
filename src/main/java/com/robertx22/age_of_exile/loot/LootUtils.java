package com.robertx22.age_of_exile.loot;

import com.robertx22.age_of_exile.capability.entity.EntityCap.UnitData;
import com.robertx22.age_of_exile.config.forge.ModConfig;
import com.robertx22.library_of_exile.utils.EntityUtils;
import com.robertx22.library_of_exile.utils.RandomUtils;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.mob.SlimeEntity;
import net.minecraft.util.math.MathHelper;

public class LootUtils {

    public static boolean isMaxLevelDistancePenalty(int level, int playerLevel) {
        return ModConfig.get().Server.LEVEL_DISTANCE_PENALTY_MIN_MULTI >= getLevelDistancePunishmentMulti(level, playerLevel);

    }

    // prevents lvl 50 players farming lvl 1 mobs
    public static float getLevelDistancePunishmentMulti(int level, int playerLevel) {

        int diff = Math.abs(level - playerLevel);

        int num = diff - ModConfig.get().Server.LEVELS_NEEDED_TO_START_LVL_PENALTY;

        if (num < 0) {
            num = 0;
        }

        float multi = (float) (1F - num * ModConfig.get().Server.LEVEL_DISTANCE_PENALTY_PER_LVL);

        return (float) MathHelper.clamp(multi, ModConfig.get().Server.LEVEL_DISTANCE_PENALTY_MIN_MULTI, 1F);
    }

    public static float getMobHealthBasedLootMulti(UnitData mob, LivingEntity entity) {

        float multi = 1;

        float hp = EntityUtils.getVanillaMaxHealth(entity);

        multi += (1 + hp / 40F) - 1;

        if (entity instanceof SlimeEntity) {
            multi *= 0.05F;
        }

        return multi;
    }

    public static int WhileRoll(float chance) {
        int amount = 0;

        while (chance > 0) {

            float maxChance = 75F;

            float currentChance = chance;

            if (currentChance > maxChance) {
                currentChance = maxChance;
            }

            chance -= currentChance;

            if (RandomUtils.roll(currentChance)) {
                amount++;
            }

        }
        return amount;

    }

}
