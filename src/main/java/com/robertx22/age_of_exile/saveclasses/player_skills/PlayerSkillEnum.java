package com.robertx22.age_of_exile.saveclasses.player_skills;

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

    MINING("mining", Words.Mining, Formatting.GRAY, EnchantmentTarget.DIGGER, () -> Items.IRON_PICKAXE),
    FARMING("farming", Words.Farming, Formatting.YELLOW, EnchantmentTarget.DIGGER, () -> Items.IRON_HOE),
    ALCHEMY("alchemy", Words.Alchemy, Formatting.LIGHT_PURPLE, null, () -> Items.BLAZE_POWDER),
    INSCRIBING("inscribing", Words.Inscribing, Formatting.AQUA, null, () -> Items.INK_SAC),
    COOKING("cooking", Words.Cooking, Formatting.RED, null, () -> Items.BREAD),
    FISHING("fishing", Words.Fishing, Formatting.BLUE, EnchantmentTarget.FISHING_ROD, () -> Items.FISHING_ROD),
    EXPLORATION("exploration", Words.Exploration, Formatting.GOLD, null, () -> Items.CHEST),
    TINKERING("tinkering", Words.Tinkering, Formatting.GREEN, null, () -> Items.CRAFTING_TABLE),
    NONE("none", Words.Elemental_Attack_Damage, Formatting.BLACK, null, () -> Items.BEDROCK);

    public String id;
    public Words word;
    public Formatting format;

    public EnchantmentTarget enchantmentTarget;
    public Supplier<Item> craftItem;

    PlayerSkillEnum(String id, Words word, Formatting format, EnchantmentTarget enchtarget, Supplier<Item> craftitem) {
        this.id = id;
        this.word = word;
        this.enchantmentTarget = enchtarget;
        this.format = format;
        this.craftItem = craftitem;
    }

    public static List<PlayerSkillEnum> getAll() {
        return Arrays.stream(PlayerSkillEnum.values())
            .filter(x -> x != NONE)
            .collect(Collectors.toList());

    }
}
