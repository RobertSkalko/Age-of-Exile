package com.robertx22.age_of_exile.player_skills.items;

import com.robertx22.age_of_exile.saveclasses.player_skills.PlayerSkillEnum;
import com.robertx22.age_of_exile.uncommon.datasaving.Load;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.text.IFormattableTextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;

public class SkillRequirement {

    public int level = 1;
    public PlayerSkillEnum skill;

    public SkillRequirement(int level, PlayerSkillEnum skill) {
        this.level = level;
        this.skill = skill;
    }

    public boolean meetsRequirement(PlayerEntity p) {
        return Load.playerRPGData(p).professions
            .getProfessionLevel(skill) >= level;

    }

    public IFormattableTextComponent getUnmetReqText() {
        return new StringTextComponent("Requires ").append(skill.word.locName())
            .append(" level " + level)
            .withStyle(TextFormatting.RED);
    }
}
