package com.robertx22.mine_and_slash.loot;

import com.robertx22.mine_and_slash.capability.entity.EntityCap.UnitData;
import com.robertx22.mine_and_slash.database.base.Rarities;
import com.robertx22.mine_and_slash.database.data.rarities.GearRarity;
import com.robertx22.mine_and_slash.database.data.stats.types.resources.Health;
import com.robertx22.mine_and_slash.uncommon.utilityclasses.RandomUtils;
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
            chance = chance * (1F - diff * 0.05F);
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

        float first = chance;

        float after_rarity = first * Rarities.Mobs.get(mob.getRarity())
            .LootMultiplier();

        float hp = entity.getMaximumHealth() - mob.getUnit()
            .peekAtStat(Health.getInstance())
            .getAverageValue();

        float after_mob_health = after_rarity * (1 + hp / 20);

        if (entity instanceof SlimeEntity) {
            after_mob_health /= 15;
        }

        return after_mob_health;
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
