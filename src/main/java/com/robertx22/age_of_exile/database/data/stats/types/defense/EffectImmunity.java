package com.robertx22.age_of_exile.database.data.stats.types.defense;

import com.robertx22.age_of_exile.database.data.stats.Stat;
import com.robertx22.age_of_exile.database.data.stats.name_regex.StatNameRegex;
import com.robertx22.age_of_exile.uncommon.enumclasses.Elements;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffects;

public class EffectImmunity extends Stat {

    public static EffectImmunity POISON = new EffectImmunity(StatusEffects.POISON, "poison", "Poison");
    public static EffectImmunity WITHER = new EffectImmunity(StatusEffects.WITHER, "wither", "Wither");
    public static EffectImmunity SLOW = new EffectImmunity(StatusEffects.SLOWNESS, "slow", "Slow");
    public static EffectImmunity HUNGER = new EffectImmunity(StatusEffects.HUNGER, "hunger", "Hunger");

    StatusEffect effect;
    String id;
    String name;

    public EffectImmunity(StatusEffect effect, String id, String name) {
        this.effect = effect;
        this.id = id;
        this.name = name;
    }

    public boolean isImmuneTo(StatusEffect effect) {
        return effect == this.effect;
    }

    @Override
    public StatNameRegex getStatNameRegex() {
        return StatNameRegex.JUST_NAME;
    }

    @Override
    public boolean IsPercent() {
        return false;
    }

    @Override
    public Elements getElement() {
        return null;
    }

    @Override
    public String locDescForLangFile() {
        return "This effect can't apply to you.";
    }

    @Override
    public String locNameForLangFile() {
        return "Immune to " + name + " Effect";
    }

    @Override
    public String GUID() {
        return this.id + "_immunity";
    }
}
