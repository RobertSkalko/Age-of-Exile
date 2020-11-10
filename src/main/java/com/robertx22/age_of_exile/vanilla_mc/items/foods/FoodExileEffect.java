package com.robertx22.age_of_exile.vanilla_mc.items.foods;

import com.robertx22.age_of_exile.database.OptScaleExactStat;
import com.robertx22.age_of_exile.database.data.stats.types.generated.ElementalDamageBonus;
import com.robertx22.age_of_exile.database.data.stats.types.generated.ElementalResist;
import com.robertx22.age_of_exile.database.data.stats.types.loot.TreasureQuality;
import com.robertx22.age_of_exile.database.data.stats.types.offense.CriticalDamage;
import com.robertx22.age_of_exile.database.data.stats.types.offense.CriticalHit;
import com.robertx22.age_of_exile.database.data.stats.types.offense.SpellDamage;
import com.robertx22.age_of_exile.database.data.stats.types.resources.HealPower;
import com.robertx22.age_of_exile.database.data.stats.types.resources.health.HealthRegen;
import com.robertx22.age_of_exile.database.data.stats.types.resources.magic_shield.MagicShieldRegen;
import com.robertx22.age_of_exile.database.data.stats.types.resources.mana.ManaRegen;
import com.robertx22.age_of_exile.uncommon.enumclasses.Elements;
import com.robertx22.age_of_exile.uncommon.enumclasses.ModType;

import java.util.Arrays;
import java.util.List;

public enum FoodExileEffect {

    WATER_DAMAGE("Aqua", "water", EffectColor.BLUE, new OptScaleExactStat(10, new ElementalDamageBonus(Elements.Water)), new OptScaleExactStat(20, new ElementalResist(Elements.Water))),
    FIRE_DAMAGE("Ignis", "fire", EffectColor.RED, new OptScaleExactStat(10, new ElementalDamageBonus(Elements.Fire)), new OptScaleExactStat(20, new ElementalResist(Elements.Fire))),
    THUNDER_DAMAGE("Sky", "thunder", EffectColor.YELLOW, new OptScaleExactStat(10, new ElementalDamageBonus(Elements.Thunder)), new OptScaleExactStat(20, new ElementalResist(Elements.Thunder))),
    NATURE_DAMAGE("Terra", "nature", EffectColor.GREEN, new OptScaleExactStat(10, new ElementalDamageBonus(Elements.Nature)), new OptScaleExactStat(20, new ElementalResist(Elements.Nature))),

    MAGIC_SHIELD_REGEN("Magic", "magic_shield_regen", EffectColor.PURPLE, new OptScaleExactStat(20, MagicShieldRegen.getInstance(), ModType.LOCAL_INCREASE)),
    MANA_REGEN("Mana", "mana_regen", EffectColor.BLUE, new OptScaleExactStat(20, ManaRegen.getInstance(), ModType.LOCAL_INCREASE)),
    HEALTH_REGEN("Health", "health_regen", EffectColor.RED, new OptScaleExactStat(20, HealthRegen.getInstance(), ModType.LOCAL_INCREASE)),

    HEALING("Holy", "healing", EffectColor.YELLOW, new OptScaleExactStat(20, HealPower.getInstance())),
    SPELL_DAMAGE("Spell", "spell_damage", EffectColor.PURPLE, new OptScaleExactStat(15, SpellDamage.getInstance())),
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
        RED("red"), GREEN("green"), BLUE("blue"), PURPLE("purple"), YELLOW("yellow");

        public String id;

        EffectColor(String id) {
            this.id = id;
        }
    }
}
