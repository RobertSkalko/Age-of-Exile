package com.robertx22.age_of_exile.aoe_data.database.player_skills;

import com.robertx22.age_of_exile.database.OptScaleExactStat;
import com.robertx22.age_of_exile.database.data.player_skills.BlockBreakExp;
import com.robertx22.age_of_exile.database.data.player_skills.PlayerSkill;
import com.robertx22.age_of_exile.database.data.player_skills.SkillDropReward;
import com.robertx22.age_of_exile.database.data.player_skills.SkillStatReward;
import com.robertx22.age_of_exile.saveclasses.player_skills.PlayerSkillEnum;
import net.minecraft.block.Block;

import java.util.Arrays;

public class PlayerSkillBuilder {

    PlayerSkill skill = new PlayerSkill();

    public static PlayerSkillBuilder of(PlayerSkillEnum se) {
        PlayerSkillBuilder b = new PlayerSkillBuilder();
        b.skill.id = se.id;
        b.skill.type_enum = se;
        return b;
    }

    public PlayerSkillBuilder dropReward(int level, SkillDropReward reward) {
        if (skill.drop_rewards.containsKey(level)) {
            throw new RuntimeException("Duplicate level value");
        }
        skill.drop_rewards.put(level, reward);
        return this;
    }

    public PlayerSkillBuilder stat(int level, OptScaleExactStat... stats) {
        if (skill.stat_rewards.containsKey(level)) {
            throw new RuntimeException("Duplicate level value");
        }
        skill.stat_rewards.put(level, new SkillStatReward(Arrays.asList(stats)));
        return this;
    }

    public PlayerSkillBuilder blockExp(Block block, float exp) {
        skill.block_break_exp.add(new BlockBreakExp(exp, block));
        return this;
    }

    public PlayerSkill build() {
        skill.addToSerializables();
        return skill;
    }

}
