package com.robertx22.age_of_exile.aoe_data.database.player_skills;

import com.robertx22.age_of_exile.database.OptScaleExactStat;
import com.robertx22.age_of_exile.database.data.player_skills.BlockBreakExp;
import com.robertx22.age_of_exile.database.data.player_skills.ItemCraftExp;
import com.robertx22.age_of_exile.database.data.player_skills.PlayerSkill;
import com.robertx22.age_of_exile.database.data.player_skills.SkillStatReward;
import com.robertx22.age_of_exile.database.data.stats.types.misc.BonusExp;
import com.robertx22.age_of_exile.database.data.stats.types.offense.TotalDamage;
import com.robertx22.age_of_exile.database.data.stats.types.resources.health.Health;
import com.robertx22.age_of_exile.database.data.stats.types.resources.health.HealthRegen;
import com.robertx22.age_of_exile.database.data.stats.types.resources.magic_shield.MagicShield;
import com.robertx22.age_of_exile.database.data.stats.types.resources.magic_shield.MagicShieldRegen;
import com.robertx22.age_of_exile.database.data.stats.types.resources.mana.Mana;
import com.robertx22.age_of_exile.database.data.stats.types.resources.mana.ManaRegen;
import com.robertx22.age_of_exile.saveclasses.player_skills.PlayerSkillEnum;
import com.robertx22.age_of_exile.uncommon.enumclasses.ModType;
import net.minecraft.block.Block;
import net.minecraft.item.Item;

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

    public PlayerSkillBuilder totalDamage(int lvl, float num) {
        stat(new SkillStatReward(lvl,
            new OptScaleExactStat(num, TotalDamage.getInstance(), ModType.FLAT)
        ));
        return this;
    }

    public PlayerSkillBuilder hpMsMana(int lvl, float num) {
        stat(new SkillStatReward(lvl,
            new OptScaleExactStat(num, Health.getInstance(), ModType.LOCAL_INCREASE),
            new OptScaleExactStat(num, MagicShield.getInstance(), ModType.LOCAL_INCREASE),
            new OptScaleExactStat(num, Mana.getInstance(), ModType.LOCAL_INCREASE)

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

    public PlayerSkillBuilder blockExp(Block block, int exp) {
        skill.block_break_exp.add(new BlockBreakExp(exp, block));
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

    public PlayerSkillBuilder addDefaultBonusExpRewards() {
        stat(new SkillStatReward(5, new OptScaleExactStat(5, BonusExp.getInstance())));
        stat(new SkillStatReward(15, new OptScaleExactStat(10, BonusExp.getInstance())));
        stat(new SkillStatReward(25, new OptScaleExactStat(10, BonusExp.getInstance())));
        stat(new SkillStatReward(35, new OptScaleExactStat(15, BonusExp.getInstance())));
        stat(new SkillStatReward(45, new OptScaleExactStat(15, BonusExp.getInstance())));
        return this;
    }

    public PlayerSkill build() {
        skill.addToSerializables();
        return skill;
    }

}
