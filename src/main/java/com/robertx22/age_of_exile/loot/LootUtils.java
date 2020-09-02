package com.robertx22.age_of_exile.loot;

import com.robertx22.age_of_exile.capability.entity.EntityCap.UnitData;
import com.robertx22.age_of_exile.config.forge.ModConfig;
import com.robertx22.age_of_exile.database.data.rarities.IGearRarity;
import com.robertx22.age_of_exile.uncommon.utilityclasses.RandomUtils;
import com.robertx22.library_of_exile.utils.EntityUtils;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.mob.SlimeEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.MathHelper;

public class LootUtils {

    // prevents lvl 50 players farming lvl 1 mobs
    public static float getLevelDistancePunishmentMulti(UnitData mob, UnitData player) {

        int diff = Math.abs(mob.getLevel() - player.getLevel());

        float multi = 1;

        if (diff < 5) {
            return multi;
        } else {
            int num = diff - 5;
            multi = (float) (1F - num * ModConfig.get().Server.LEVEL_DISTANCE_PENALTY_PER_LVL);
        }

        if (multi < 0) {
            return 0;
        }
        return multi;

    }

    public static ItemStack RandomDamagedGear(ItemStack stack, IGearRarity rar) {
        if (stack.isDamageable()) {

            float dmgMulti = (float) RandomUtils.RandomRange(
                rar.SpawnDurabilityHit().min, rar.SpawnDurabilityHit().max) / (float) 100;

            dmgMulti = MathHelper.clamp(dmgMulti, 0, 0.95F);

            stack.setDamage((int) (dmgMulti * stack.getMaxDamage()));

        }

        return stack;
    }

    public static float getMobHealthBasedLootMulti(UnitData mob, LivingEntity entity) {

        float multi = 1;

        float hp = EntityUtils.getVanillaMaxHealth(entity);

        multi += (1 + hp / 20F) - 1;

        if (entity instanceof SlimeEntity) {
            multi *= 0.1F;
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
