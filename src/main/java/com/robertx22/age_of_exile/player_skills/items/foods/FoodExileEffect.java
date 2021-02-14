package com.robertx22.age_of_exile.player_skills.items.foods;

import com.robertx22.age_of_exile.database.OptScaleExactStat;
import com.robertx22.age_of_exile.database.data.stats.types.defense.Armor;
import com.robertx22.age_of_exile.database.data.stats.types.defense.DodgeRating;
import com.robertx22.age_of_exile.database.data.stats.types.generated.ElementalDamageBonus;
import com.robertx22.age_of_exile.database.data.stats.types.generated.ElementalResist;
import com.robertx22.age_of_exile.database.data.stats.types.loot.TreasureQuality;
import com.robertx22.age_of_exile.database.data.stats.types.offense.SpellDamage;
import com.robertx22.age_of_exile.database.data.stats.types.offense.crit.CriticalDamage;
import com.robertx22.age_of_exile.database.data.stats.types.offense.crit.CriticalHit;
import com.robertx22.age_of_exile.database.data.stats.types.resources.HealPower;
import com.robertx22.age_of_exile.database.data.stats.types.resources.RegeneratePercentStat;
import com.robertx22.age_of_exile.database.data.stats.types.resources.health.Health;
import com.robertx22.age_of_exile.database.data.stats.types.resources.health.HealthRegen;
import com.robertx22.age_of_exile.database.data.stats.types.resources.mana.Mana;
import com.robertx22.age_of_exile.database.data.stats.types.resources.mana.ManaRegen;
import com.robertx22.age_of_exile.mmorpg.ModRegistry;
import com.robertx22.age_of_exile.uncommon.enumclasses.Elements;
import com.robertx22.age_of_exile.uncommon.enumclasses.ModType;
import com.robertx22.age_of_exile.vanilla_mc.items.misc.CraftEssenceItem;

import java.util.Arrays;
import java.util.List;
import java.util.function.Supplier;

public enum FoodExileEffect {

    WATER_DAMAGE("Aqua", "water", EffectColor.BLUE, new OptScaleExactStat(10, new ElementalDamageBonus(Elements.Water)), new OptScaleExactStat(20, new ElementalResist(Elements.Water))),
    FIRE_DAMAGE("Ignis", "fire", EffectColor.RED, new OptScaleExactStat(10, new ElementalDamageBonus(Elements.Fire)), new OptScaleExactStat(20, new ElementalResist(Elements.Fire))),
    THUNDER_DAMAGE("Sky", "thunder", EffectColor.YELLOW, new OptScaleExactStat(10, new ElementalDamageBonus(Elements.Thunder)), new OptScaleExactStat(20, new ElementalResist(Elements.Thunder))),
    NATURE_DAMAGE("Terra", "nature", EffectColor.GREEN, new OptScaleExactStat(10, new ElementalDamageBonus(Elements.Nature)), new OptScaleExactStat(20, new ElementalResist(Elements.Nature))),
    PHYSICAL_DAMAGE("Physical", "physical", EffectColor.RED, new OptScaleExactStat(10, new ElementalDamageBonus(Elements.Physical)), new OptScaleExactStat(20, Armor.getInstance(), ModType.LOCAL_INCREASE)),

    DEF_PURPLE("Magicka Defense", "def_purple", EffectColor.PURPLE, new OptScaleExactStat(5, SpellDamage.getInstance()), new OptScaleExactStat(10, Health.getInstance(), ModType.GLOBAL_INCREASE)),
    DEF_BLUE("Arcana Defense", "def_blue", EffectColor.BLUE, new OptScaleExactStat(25, Mana.getInstance(), ModType.LOCAL_INCREASE), new OptScaleExactStat(10, Health.getInstance(), ModType.GLOBAL_INCREASE)),
    DEF_YELLOW("Sky Defense", "def_yellow", EffectColor.YELLOW, new OptScaleExactStat(10, HealPower.getInstance()), new OptScaleExactStat(10, Health.getInstance(), ModType.GLOBAL_INCREASE)),
    DEF_GREEN("Terra Defense", "def_green", EffectColor.GREEN, new OptScaleExactStat(20, DodgeRating.getInstance(), ModType.LOCAL_INCREASE), new OptScaleExactStat(10, Health.getInstance(), ModType.GLOBAL_INCREASE)),
    DEF_GRAY("Physical Defense", "def_gray", EffectColor.RED, new OptScaleExactStat(20, Armor.getInstance(), ModType.LOCAL_INCREASE), new OptScaleExactStat(10, Health.getInstance(), ModType.GLOBAL_INCREASE)),

    ELEMENTAL_RESISTANCE("Resistant", "elemental_resist", EffectColor.PURPLE, new OptScaleExactStat(10, new ElementalResist(Elements.Elemental))),

    MAGIC_SHIELD_REGEN("Magicka", "magic_shield_regen", EffectColor.PURPLE, new OptScaleExactStat(1, RegeneratePercentStat.MANA), new OptScaleExactStat(15, ManaRegen.getInstance(), ModType.LOCAL_INCREASE)),
    MANA_REGEN("Arcana", "mana_regen", EffectColor.BLUE, new OptScaleExactStat(1, RegeneratePercentStat.MANA), new OptScaleExactStat(15, ManaRegen.getInstance(), ModType.LOCAL_INCREASE)),
    HEALTH_REGEN("Vitala", "health_regen", EffectColor.RED, new OptScaleExactStat(1, RegeneratePercentStat.HEALTH), new OptScaleExactStat(15, HealthRegen.getInstance(), ModType.LOCAL_INCREASE)),

    HEALING("Holy", "healing", EffectColor.YELLOW, new OptScaleExactStat(20, HealPower.getInstance())),
    SPELL_DAMAGE("Enigma", "spell_damage", EffectColor.PURPLE, new OptScaleExactStat(10, SpellDamage.getInstance())),
    CRITICAL("Critical", "critical", EffectColor.GREEN, new OptScaleExactStat(5, CriticalHit.getInstance()), new OptScaleExactStat(10, CriticalDamage.getInstance())),
    TREASURE_QUALITY("Treasure", "treasure", EffectColor.YELLOW, new OptScaleExactStat(10, TreasureQuality.getInstance()));

    public String word;
    public List<OptScaleExactStat> stats;
    public EffectColor color;
    public String id;

    FoodExileEffect(String word, String id, EffectColor color, OptScaleExactStat... stat) {
        this.word = word;
        this.stats = Arrays.asList(stat);
        this.color = color;
        this.id = id;
    }

    public enum EffectColor {
        RED("red", "Red", () -> ModRegistry.GEAR_MATERIALS.FIRE),
        GREEN("green", "Green", () -> ModRegistry.GEAR_MATERIALS.NATURE),
        BLUE("blue", "Blue", () -> ModRegistry.GEAR_MATERIALS.WATER),
        PURPLE("purple", "Purple", () -> ModRegistry.GEAR_MATERIALS.ARCANA),
        YELLOW("yellow", "Yellow", () -> ModRegistry.GEAR_MATERIALS.THUNDER);

        public String id;
        public String word;
        public Supplier<CraftEssenceItem> essenceItem;

        EffectColor(String id, String word, Supplier<CraftEssenceItem> essence) {
            this.id = id;
            this.word = word;
            this.essenceItem = essence;
        }
    }
}
