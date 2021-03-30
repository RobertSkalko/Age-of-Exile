package com.robertx22.age_of_exile.saveclasses.player_skills;

import com.robertx22.age_of_exile.gui.screens.wiki.WikiType;
import com.robertx22.age_of_exile.uncommon.localization.Words;
import net.minecraft.util.Formatting;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public enum PlayerSkillEnum {

    MINING("mining", WikiType.MINING_BLOCK_EXP, Words.Mining, Words.MiningDesc, Formatting.GRAY),
    FARMING("farming", WikiType.FARMING_EXP, Words.Farming, Words.FarmingDesc, Formatting.YELLOW),
    SALVAGING("salvaging", null, Words.Salvaging, Words.SalvagingDesc, Formatting.LIGHT_PURPLE),
    ALCHEMY("alchemy", WikiType.ALCHEMY_EXP, Words.Alchemy, Words.AlchemyDesc, Formatting.LIGHT_PURPLE),
    INSCRIBING("inscribing", WikiType.SCRIBE_EXP, Words.Inscribing, Words.InscribingDesc, Formatting.AQUA),
    COOKING("cooking", WikiType.COOKING_EXP, Words.Cooking, Words.CookingDesc, Formatting.RED),
    FISHING("fishing", null, Words.Fishing, Words.FishingDesc, Formatting.BLUE),
    EXPLORATION("exploration", null, Words.Exploration, Words.ExplorationDesc, Formatting.GOLD),
    BLACKSMITHING("blacksmithing", WikiType.BLACKSMITH_EXP, Words.Blacksmithing, Words.BlacksmithDesc, Formatting.GREEN),
    ALL("all", null, Words.Skill, Words.Skill, Formatting.GREEN),
    NONE("none", null, Words.Skill, Words.Skill, Formatting.BLACK);

    public String id;
    public Words word;
    public Words desc;
    public Formatting format;

    public WikiType wiki;

    PlayerSkillEnum(String id, WikiType wiki, Words word, Words desc, Formatting format) {
        this.id = id;
        this.word = word;
        this.format = format;
        this.desc = desc;
        this.wiki = wiki;
    }

    public static List<PlayerSkillEnum> getAll() {
        return Arrays.stream(PlayerSkillEnum.values())
            .filter(x -> x != NONE && x != ALL)
            .collect(Collectors.toList());

    }
}
