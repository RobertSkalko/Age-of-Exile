package com.robertx22.age_of_exile.loot;

import com.robertx22.age_of_exile.capability.entity.EntityData;
import com.robertx22.age_of_exile.config.forge.ServerContainer;
import com.robertx22.library_of_exile.utils.EntityUtils;
import com.robertx22.library_of_exile.utils.RandomUtils;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.monster.SlimeEntity;
import net.minecraft.util.math.MathHelper;

public class LootUtils {

    public static boolean preventLootDueToLevelPenalty(int level, int playerLevel) {
        return ServerContainer.get().LEVEL_DISTANCE_PENALTY_MIN_MULTI.get() >= getLevelDistancePunishmentMulti(level, playerLevel);

    }

    // prevents lvl 50 players farming lvl 1 mobs
    public static float getLevelDistancePunishmentMulti(int level, int playerLevel) {

        if (playerLevel == level) {
            return 1F;
        }

        int num = Math.abs(playerLevel - level);

        float multi = (float) (1F - num * ServerContainer.get().LEVEL_DISTANCE_PENALTY_PER_LVL.get());

        return (float) MathHelper.clamp(multi, ServerContainer.get().LEVEL_DISTANCE_PENALTY_MIN_MULTI.get(), 1F);
    }

    public static float getMobHealthBasedLootMulti(EntityData mob, LivingEntity entity) {

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
