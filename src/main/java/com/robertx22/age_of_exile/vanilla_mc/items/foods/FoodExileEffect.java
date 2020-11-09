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

    WATER_DAMAGE("Aqua", new OptScaleExactStat(10, new ElementalDamageBonus(Elements.Water)), new OptScaleExactStat(20, new ElementalResist(Elements.Fire))),
    FIRE_DAMAGE("Ignis", new OptScaleExactStat(10, new ElementalDamageBonus(Elements.Fire)), new OptScaleExactStat(20, new ElementalResist(Elements.Water))),
    THUNDER_DAMAGE("Sky", new OptScaleExactStat(10, new ElementalDamageBonus(Elements.Thunder)), new OptScaleExactStat(20, new ElementalResist(Elements.Nature))),
    NATURE_DAMAGE("Terra", new OptScaleExactStat(10, new ElementalDamageBonus(Elements.Nature)), new OptScaleExactStat(20, new ElementalResist(Elements.Thunder))),

    MAGIC_SHIELD_REGEN("Magic", new OptScaleExactStat(20, MagicShieldRegen.getInstance(), ModType.LOCAL_INCREASE)),
    MANA_REGEN("Mana", new OptScaleExactStat(20, ManaRegen.getInstance(), ModType.LOCAL_INCREASE)),
    HEALTH_REGEN("Health", new OptScaleExactStat(20, HealthRegen.getInstance(), ModType.LOCAL_INCREASE)),

    HEALING("Holy", new OptScaleExactStat(20, HealPower.getInstance())),
    SPELL_DAMAGE("Spell", new OptScaleExactStat(15, SpellDamage.getInstance())),
    CRITICAL("Critical", new OptScaleExactStat(5, CriticalHit.getInstance()), new OptScaleExactStat(10, CriticalDamage.getInstance())),
    TREASURE_QUALITY("Treasure", new OptScaleExactStat(10, TreasureQuality.getInstance()));

    public String word;
    public List<OptScaleExactStat> stats;

    FoodExileEffect(String word, List<OptScaleExactStat> stats) {
        this.word = word;
        this.stats = stats;
    }

    FoodExileEffect(String word, OptScaleExactStat... stat) {
        this.word = word;
        this.stats = Arrays.asList(stat);
    }
}
