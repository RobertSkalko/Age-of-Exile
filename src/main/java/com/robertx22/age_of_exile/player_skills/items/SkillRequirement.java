package com.robertx22.age_of_exile.player_skills.items;

import com.robertx22.age_of_exile.saveclasses.player_skills.PlayerSkillEnum;
import com.robertx22.age_of_exile.uncommon.datasaving.Load;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.text.LiteralText;
import net.minecraft.text.MutableText;
import net.minecraft.util.Formatting;

public class SkillRequirement {

    public int level = 1;
    public PlayerSkillEnum skill;

    public SkillRequirement(int level, PlayerSkillEnum skill) {
        this.level = level;
        this.skill = skill;
    }

    public boolean meetsRequirement(PlayerEntity p) {
        return Load.playerSkills(p)
            .getLevel(skill) >= level;

    }

    public MutableText getUnmetReqText() {
        return new LiteralText("Requires ").append(skill.word.locName())
            .append(" level " + level)
            .formatted(Formatting.RED);
    }
}
