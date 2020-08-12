package com.robertx22.age_of_exile.loot;

import com.robertx22.age_of_exile.capability.entity.EntityCap.UnitData;
import com.robertx22.age_of_exile.database.base.Rarities;
import com.robertx22.age_of_exile.database.data.rarities.GearRarity;
import com.robertx22.age_of_exile.uncommon.utilityclasses.EntityUtils;
import com.robertx22.age_of_exile.uncommon.utilityclasses.RandomUtils;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.mob.SlimeEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.MathHelper;

public class LootUtils {

    // prevents lvl 50 players farming lvl 1 mobs
    public static float ApplyLevelDistancePunishment(UnitData mob, UnitData player, float chance) {

        int diff = Math.abs(mob.getLevel() - player.getLevel());

        if (diff < 5) {
            return chance;
        } else {
            chance = chance * (1F - diff * 0.03F);
        }

        if (chance < 0) {
            return 0;
        }
        return chance;

    }

    public static ItemStack RandomDamagedGear(ItemStack stack, GearRarity rar) {
        if (stack.isDamageable()) {

            float dmgMulti = (float) RandomUtils.RandomRange(
                rar.SpawnDurabilityHit().min, rar.SpawnDurabilityHit().max) / (float) 100;

            dmgMulti = MathHelper.clamp(dmgMulti, 0, 0.95F);

            stack.setDamage((int) (dmgMulti * stack.getMaxDamage()));

        }

        return stack;
    }

    public static float applyLootMultipliers(float chance, UnitData mob, LivingEntity entity) {

        chance *= Rarities.Mobs.get(mob.getRarity())
            .LootMultiplier();

        float hp = EntityUtils.getVanillaMaxHealth(entity);

        chance *= (1 + hp / 20F);

        if (entity instanceof SlimeEntity) {
            chance /= 15;
        }

        return chance;
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
