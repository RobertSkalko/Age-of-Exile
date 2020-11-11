package com.robertx22.age_of_exile.aoe_data.database.player_skills;

import com.robertx22.age_of_exile.database.OptScaleExactStat;
import com.robertx22.age_of_exile.database.data.player_skills.BlockBreakExp;
import com.robertx22.age_of_exile.database.data.player_skills.PlayerSkill;
import com.robertx22.age_of_exile.database.data.player_skills.SkillDropReward;
import com.robertx22.age_of_exile.database.data.player_skills.SkillStatReward;
import com.robertx22.age_of_exile.database.data.stats.types.misc.BonusExp;
import com.robertx22.age_of_exile.saveclasses.player_skills.PlayerSkillEnum;
import net.minecraft.block.Block;

public class PlayerSkillBuilder {

    PlayerSkill skill = new PlayerSkill();

    public static PlayerSkillBuilder of(PlayerSkillEnum se) {
        PlayerSkillBuilder b = new PlayerSkillBuilder();
        b.skill.id = se.id;
        b.skill.type_enum = se;
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

    public PlayerSkillBuilder blockExp(Block block, float exp) {
        skill.block_break_exp.add(new BlockBreakExp(exp, block));
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

    public PlayerSkill build() {
        skill.addToSerializables();
        return skill;
    }

}
