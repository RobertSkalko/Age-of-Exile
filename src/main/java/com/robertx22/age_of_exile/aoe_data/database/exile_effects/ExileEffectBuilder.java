package com.robertx22.age_of_exile.aoe_data.database.exile_effects;

import com.robertx22.age_of_exile.database.OptScaleExactStat;
import com.robertx22.age_of_exile.database.data.exile_effects.EffectType;
import com.robertx22.age_of_exile.database.data.exile_effects.ExileEffect;
import com.robertx22.age_of_exile.database.data.exile_effects.ExileEffect.EffectTags;
import com.robertx22.age_of_exile.database.data.exile_effects.VanillaStatData;
import com.robertx22.age_of_exile.database.data.spells.components.Spell;
import com.robertx22.age_of_exile.database.data.stats.Stat;
import com.robertx22.age_of_exile.database.data.stats.StatScaling;
import com.robertx22.age_of_exile.uncommon.enumclasses.ModType;

public class ExileEffectBuilder {

    private ExileEffect effect = new ExileEffect();

    public static ExileEffectBuilder of(String id, String locname, EffectType type) {
        ExileEffectBuilder b = new ExileEffectBuilder();
        b.effect.type = type;
        b.effect.id = id;
        b.effect.locName = locname;
        return b;
    }

    public ExileEffectBuilder addTags(EffectTags... tags) {
        for (EffectTags tag : tags) {
            this.effect.tags.add(tag.name());
        }
        return this;
    }

    public ExileEffectBuilder stat(OptScaleExactStat stat) {
        this.effect.stats.add(stat);
        return this;
    }

    public ExileEffectBuilder vanillaStat(VanillaStatData stat) {
        this.effect.mc_stats.add(stat);
        return this;
    }

    public ExileEffectBuilder maxStacks(int stacks) {
        this.effect.max_stacks = stacks;
        return this;
    }

    public ExileEffectBuilder spell(Spell stat) {
        this.effect.spell = stat.getAttached();
        return this;
    }

    public ExileEffectBuilder stat(float first, Stat stat, ModType type) {
        OptScaleExactStat data = new OptScaleExactStat(first, first, stat, type);
        data.scale_to_lvl = stat.getScaling() != StatScaling.NONE;
        this.effect.stats.add(data);
        return this;
    }

    public ExileEffect build() {
        effect.addToSerializables();
        return effect;
    }

}
