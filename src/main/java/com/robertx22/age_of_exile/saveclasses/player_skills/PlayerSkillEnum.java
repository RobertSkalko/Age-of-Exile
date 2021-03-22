package com.robertx22.age_of_exile.saveclasses.player_skills;

import com.robertx22.age_of_exile.gui.screens.wiki.WikiType;
import com.robertx22.age_of_exile.uncommon.localization.Words;
import net.minecraft.enchantment.EnchantmentTarget;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.util.Formatting;

import java.util.Arrays;
import java.util.List;
import java.util.function.Supplier;
import java.util.stream.Collectors;

public enum PlayerSkillEnum {

    MINING("mining", WikiType.MINING_BLOCK_EXP, Words.Mining, Words.MiningDesc, Formatting.GRAY, EnchantmentTarget.DIGGER, () -> Items.IRON_PICKAXE),
    FARMING("farming", WikiType.FARMING_EXP, Words.Farming, Words.FarmingDesc, Formatting.YELLOW, EnchantmentTarget.DIGGER, () -> Items.IRON_HOE),
    SALVAGING("salvaging", null, Words.Salvaging, Words.SalvagingDesc, Formatting.LIGHT_PURPLE, null, () -> Items.BOOK),
    //ALCHEMY("alchemy", Words.Alchemy, Formatting.LIGHT_PURPLE, null, () -> Items.BLAZE_POWDER),
    INSCRIBING("inscribing", null, Words.Inscribing, Words.InscribingDesc, Formatting.AQUA, null, () -> Items.INK_SAC),
    //COOKING("cooking", Words.Cooking, Formatting.RED, null, () -> Items.BREAD),
    FISHING("fishing", null, Words.Fishing, Words.FishingDesc, Formatting.BLUE, EnchantmentTarget.FISHING_ROD, () -> Items.FISHING_ROD),
    EXPLORATION("exploration", null, Words.Exploration, Words.ExplorationDesc, Formatting.GOLD, null, () -> Items.CHEST),
    //TINKERING("tinkering", Words.Tinkering, Formatting.GREEN, null, () -> Items.CRAFTING_TABLE),
    ALL("all", null, Words.Skill, Words.Skill, Formatting.GREEN, null, () -> Items.AIR),
    NONE("none", null, Words.Skill, Words.Skill, Formatting.BLACK, null, () -> Items.BEDROCK);

    public String id;
    public Words word;
    public Words desc;
    public Formatting format;

    public EnchantmentTarget enchantmentTarget;
    public Supplier<Item> craftItem;
    public WikiType wiki;

    PlayerSkillEnum(String id, WikiType wiki, Words word, Words desc, Formatting format, EnchantmentTarget enchtarget, Supplier<Item> craftitem) {
        this.id = id;
        this.word = word;
        this.enchantmentTarget = enchtarget;
        this.format = format;
        this.desc = desc;
        this.wiki = wiki;
        this.craftItem = craftitem;
    }

    public static List<PlayerSkillEnum> getAll() {
        return Arrays.stream(PlayerSkillEnum.values())
            .filter(x -> x != NONE && x != ALL)
            .collect(Collectors.toList());

    }
}
