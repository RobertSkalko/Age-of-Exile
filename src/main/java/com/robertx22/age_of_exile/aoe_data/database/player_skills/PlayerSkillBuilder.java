package com.robertx22.age_of_exile.aoe_data.database.player_skills;

import com.robertx22.age_of_exile.database.OptScaleExactStat;
import com.robertx22.age_of_exile.database.data.MinMax;
import com.robertx22.age_of_exile.database.data.player_skills.*;
import com.robertx22.age_of_exile.database.data.stats.types.misc.BonusExp;
import com.robertx22.age_of_exile.database.data.stats.types.resources.health.Health;
import com.robertx22.age_of_exile.database.data.stats.types.resources.health.HealthRegen;
import com.robertx22.age_of_exile.database.data.stats.types.resources.magic_shield.MagicShield;
import com.robertx22.age_of_exile.database.data.stats.types.resources.magic_shield.MagicShieldRegen;
import com.robertx22.age_of_exile.database.data.stats.types.resources.mana.ManaRegen;
import com.robertx22.age_of_exile.player_skills.items.foods.SkillItemTier;
import com.robertx22.age_of_exile.saveclasses.player_skills.PlayerSkillEnum;
import com.robertx22.age_of_exile.uncommon.enumclasses.ModType;
import net.minecraft.block.Block;
import net.minecraft.item.Item;

import static com.robertx22.age_of_exile.mmorpg.ModRegistry.FOOD_ITEMS;

public class PlayerSkillBuilder {

    public PlayerSkill skill = new PlayerSkill();

    public static PlayerSkillBuilder of(int order, PlayerSkillEnum se) {
        PlayerSkillBuilder b = new PlayerSkillBuilder();
        b.skill.id = se.id;
        b.skill.type_enum = se;
        b.skill.order = order;
        return b;
    }

    public PlayerSkillBuilder dropReward(SkillDropReward reward) {
        skill.drop_rewards.add(reward);
        return this;
    }

    public PlayerSkillBuilder stat(SkillStatReward stat) {
        skill.stat_rewards.add(stat);
        return this;
    }

    public PlayerSkillBuilder hpAndMagicShield(int lvl, float num) {
        stat(new SkillStatReward(lvl,
            new OptScaleExactStat(num, Health.getInstance(), ModType.LOCAL_INCREASE),
            new OptScaleExactStat(num, MagicShield.getInstance(), ModType.LOCAL_INCREASE)
        ));
        return this;
    }

    public PlayerSkillBuilder regens(int lvl, float num) {
        stat(new SkillStatReward(lvl,
            new OptScaleExactStat(num, HealthRegen.getInstance(), ModType.LOCAL_INCREASE),
            new OptScaleExactStat(num, MagicShieldRegen.getInstance(), ModType.LOCAL_INCREASE),
            new OptScaleExactStat(num, ManaRegen.getInstance(), ModType.LOCAL_INCREASE)
        ));
        return this;
    }

    public PlayerSkillBuilder blockExp(Block block, float exp) {
        skill.block_break_exp.add(new BlockBreakExp(exp, block));
        return this;
    }

    public PlayerSkillBuilder itemCraftExp(Item block, float exp) {
        skill.item_craft_exp.add(new ItemCraftExp(exp, block));
        return this;
    }

    public PlayerSkillBuilder addDefaultBonusExpRewards() {
        stat(new SkillStatReward(5, new OptScaleExactStat(2, BonusExp.getInstance())));
        stat(new SkillStatReward(15, new OptScaleExactStat(2, BonusExp.getInstance())));
        stat(new SkillStatReward(25, new OptScaleExactStat(5, BonusExp.getInstance())));
        stat(new SkillStatReward(35, new OptScaleExactStat(5, BonusExp.getInstance())));
        stat(new SkillStatReward(45, new OptScaleExactStat(10, BonusExp.getInstance())));
        return this;
    }

    public PlayerSkillBuilder addFoodDrops() {
        dropReward(new SkillDropReward(10, 100, FOOD_ITEMS.MAT_TIER_MAP.get(SkillItemTier.SPIRITUAL), new MinMax(1, 3)));
        dropReward(new SkillDropReward(20, 75, FOOD_ITEMS.MAT_TIER_MAP.get(SkillItemTier.CELESTIAL), new MinMax(1, 3)));
        dropReward(new SkillDropReward(30, 50, FOOD_ITEMS.MAT_TIER_MAP.get(SkillItemTier.EMPYREAN), new MinMax(1, 3)));
        dropReward(new SkillDropReward(40, 25, FOOD_ITEMS.MAT_TIER_MAP.get(SkillItemTier.ANGELIC), new MinMax(1, 3)));
        dropReward(new SkillDropReward(50, 10, FOOD_ITEMS.MAT_TIER_MAP.get(SkillItemTier.DIVINE), new MinMax(1, 3)));
        return this;
    }

    public PlayerSkill build() {
        skill.addToSerializables();
        return skill;
    }

}
