package com.robertx22.age_of_exile.aoe_data.database.player_skills;

import com.robertx22.age_of_exile.aoe_data.database.stats.Stats;
import com.robertx22.age_of_exile.database.OptScaleExactStat;
import com.robertx22.age_of_exile.database.data.MinMax;
import com.robertx22.age_of_exile.database.data.player_skills.*;
import com.robertx22.age_of_exile.database.data.stats.types.misc.BonusXpToMobsOfTier;
import com.robertx22.age_of_exile.database.data.stats.types.professions.all.BonusSkillYield;
import com.robertx22.age_of_exile.database.data.stats.types.resources.health.Health;
import com.robertx22.age_of_exile.database.data.stats.types.resources.health.HealthRegen;
import com.robertx22.age_of_exile.database.data.stats.types.resources.mana.Mana;
import com.robertx22.age_of_exile.database.data.stats.types.resources.mana.ManaRegen;
import com.robertx22.age_of_exile.player_skills.items.foods.SkillItemTier;
import com.robertx22.age_of_exile.saveclasses.player_skills.PlayerSkillEnum;
import com.robertx22.age_of_exile.uncommon.enumclasses.ModType;
import net.minecraft.block.Block;
import net.minecraft.item.Item;

import java.util.Arrays;
import java.util.function.Function;

public class PlayerSkillBuilder {

    public PlayerSkill skill = new PlayerSkill();

    public static PlayerSkillBuilder of(int order, PlayerSkillEnum se) {
        PlayerSkillBuilder b = new PlayerSkillBuilder();
        b.skill.id = se.id;
        b.skill.type_enum = se;
        b.skill.order = order;
        return b;
    }

    public PlayerSkillBuilder stat(SkillStatReward stat) {
        skill.stat_rewards.add(stat);
        return this;
    }

    public PlayerSkillBuilder masteryStat(MasteryStatReward stat) {
        skill.mastery_stat_reward.add(stat);
        return this;
    }

    public PlayerSkillBuilder totalDamage(int lvl, float num) {
        stat(new SkillStatReward(lvl,
            new OptScaleExactStat(num, Stats.TOTAL_DAMAGE.get(), ModType.FLAT)
        ));
        return this;
    }

    public PlayerSkillBuilder hpMsMana(int lvl, float num) {
        stat(new SkillStatReward(lvl,
            new OptScaleExactStat(num, Health.getInstance(), ModType.PERCENT),
            new OptScaleExactStat(num, Mana.getInstance(), ModType.PERCENT)

        ));
        return this;
    }

    public PlayerSkillBuilder regens(int lvl, float num) {
        stat(new SkillStatReward(lvl,
            new OptScaleExactStat(num, HealthRegen.getInstance(), ModType.PERCENT),
            new OptScaleExactStat(num, ManaRegen.getInstance(), ModType.PERCENT)
        ));
        return this;
    }

    public PlayerSkillBuilder blockExp(Block block, int exp) {
        skill.block_break_exp.add(new BlockBreakExp(exp, block));
        return this;
    }

    public PlayerSkillBuilder addTieredDrops(float chance, Function<SkillItemTier, Item> fun) {
        return addTieredDrops(chance, fun, "");
    }

    public PlayerSkillBuilder addTieredDrops(float chance, Function<SkillItemTier, Item> fun, String tag) {
        for (SkillItemTier tier : SkillItemTier.values()) {
            DropRewardsBuilder skillDrops = DropRewardsBuilder.of(chance, tier);
            skillDrops.dropReward(new SkillDropReward(100, fun.apply(tier), new MinMax(1, 3)));
            SkillDropTable rew = skillDrops.build();
            rew.tag = tag;
            skill.dropTables.add(rew);
        }
        return this;
    }

    public PlayerSkillBuilder smeltExp(Item item, int exp) {
        skill.item_smelt_exp.add(new ItemCraftExp(exp, item));
        return this;
    }

    public PlayerSkillBuilder itemCraftExp(Item block, int exp) {
        skill.item_craft_exp.add(new ItemCraftExp(exp, block));
        return this;
    }

    public PlayerSkillBuilder addDefaultHpMsMana() {
        hpMsMana(10, 2);
        hpMsMana(20, 3);
        hpMsMana(30, 4);
        hpMsMana(40, 5);
        hpMsMana(50, 5);
        return this;
    }

    public PlayerSkillBuilder addBonusYieldMasteryLevelStats(PlayerSkillEnum skill) {
        this.masteryStat(
            new MasteryStatReward(Arrays.asList(new OptScaleExactStat(1, new BonusSkillYield(skill))), 10)
        );
        return this;
    }

    public PlayerSkillBuilder addDefaultBonusExpRewards() {
        for (SkillItemTier tier : SkillItemTier.values()) {
            stat(new SkillStatReward(tier.levelRange.getMiddleLevel(), new OptScaleExactStat(20, new BonusXpToMobsOfTier(tier))));
        }
        return this;
    }

    public PlayerSkill build() {
        skill.addToSerializables();
        return skill;
    }

}
