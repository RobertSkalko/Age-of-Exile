package com.robertx22.age_of_exile.saveclasses.player_skills;

import com.robertx22.age_of_exile.gui.screens.wiki.WikiType;
import com.robertx22.age_of_exile.uncommon.localization.Words;
import com.robertx22.library_of_exile.registry.IGUID;
import net.minecraft.util.text.TextFormatting;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public enum PlayerSkillEnum implements IGUID {

    ALCHEMY("alchemy", WikiType.ALCHEMY_EXP, Words.Alchemy, Words.AlchemyDesc, TextFormatting.LIGHT_PURPLE),
    INSCRIBING("inscribing", WikiType.SCRIBE_EXP, Words.Inscribing, Words.InscribingDesc, TextFormatting.AQUA),
    COOKING("cooking", WikiType.COOKING_EXP, Words.Cooking, Words.CookingDesc, TextFormatting.RED),

    JEWEL_CRAFTING("jewel_craft", WikiType.COOKING_EXP, Words.JewelCrafting, Words.CookingDesc, TextFormatting.GREEN),
    ARMOR_CRAFTING("armor_craft", WikiType.COOKING_EXP, Words.ArmorCrafting, Words.CookingDesc, TextFormatting.BLUE),
    WEAPON_CRAFTING("wep_craft", WikiType.COOKING_EXP, Words.WeaponCrafting, Words.CookingDesc, TextFormatting.RED),

    MINING("mining", WikiType.MINING_BLOCK_EXP, Words.Mining, Words.MiningDesc, TextFormatting.GRAY),
    FARMING("farming", WikiType.FARMING_EXP, Words.Farming, Words.FarmingDesc, TextFormatting.YELLOW),
    SALVAGING("salvaging", null, Words.Salvaging, Words.SalvagingDesc, TextFormatting.LIGHT_PURPLE),
    FISHING("fishing", null, Words.Fishing, Words.FishingDesc, TextFormatting.BLUE),
    EXPLORATION("exploration", null, Words.Exploration, Words.ExplorationDesc, TextFormatting.GOLD),
    BLACKSMITHING("blacksmithing", WikiType.BLACKSMITH_EXP, Words.Blacksmithing, Words.BlacksmithDesc, TextFormatting.GREEN),
    ALL("all", null, Words.Skill, Words.Skill, TextFormatting.GREEN),
    NONE("none", null, Words.Skill, Words.Skill, TextFormatting.BLACK);

    public String id;
    public Words word;
    public Words desc;
    public TextFormatting format;

    public WikiType wiki;

    PlayerSkillEnum(String id, WikiType wiki, Words word, Words desc, TextFormatting format) {
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

    @Override
    public String GUID() {
        return id;
    }
}
