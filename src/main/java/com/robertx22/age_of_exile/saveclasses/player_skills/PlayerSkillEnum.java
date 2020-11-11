package com.robertx22.age_of_exile.saveclasses.player_skills;

import com.robertx22.age_of_exile.uncommon.localization.Words;
import net.minecraft.util.Formatting;

public enum PlayerSkillEnum {

    MINING("mining", Words.Mining, Formatting.GRAY),
    FARMING("farming", Words.Farming, Formatting.YELLOW),
    NONE("none", Words.Elemental_Attack_Damage, Formatting.BLACK);

    public String id;
    public Words word;
    public Formatting format;

    PlayerSkillEnum(String id, Words word, Formatting format) {
        this.id = id;
        this.word = word;
        this.format = format;
    }
}
