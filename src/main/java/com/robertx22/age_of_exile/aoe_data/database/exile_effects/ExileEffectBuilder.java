package com.robertx22.age_of_exile.aoe_data.database.exile_effects;

import com.robertx22.age_of_exile.aoe_data.database.stats.base.EffectCtx;
import com.robertx22.age_of_exile.database.OptScaleExactStat;
import com.robertx22.age_of_exile.database.data.exile_effects.EffectTags;
import com.robertx22.age_of_exile.database.data.exile_effects.EffectType;
import com.robertx22.age_of_exile.database.data.exile_effects.ExileEffect;
import com.robertx22.age_of_exile.database.data.exile_effects.VanillaStatData;
import com.robertx22.age_of_exile.database.data.spells.components.Spell;
import com.robertx22.age_of_exile.database.data.stats.Stat;
import com.robertx22.age_of_exile.database.data.stats.StatScaling;
import com.robertx22.age_of_exile.uncommon.enumclasses.ModType;

public class ExileEffectBuilder {

    private ExileEffect effect = new ExileEffect();

    public static ExileEffectBuilder of(EffectCtx ctx) {
        ExileEffectBuilder b = new ExileEffectBuilder();
        b.effect.type = ctx.type;
        b.effect.id = ctx.effectId;
        b.effect.locName = ctx.locname;

        if (ctx.type == EffectType.beneficial) {
            b.addTags(EffectTags.positive);
        }
        if (ctx.type == EffectType.negative) {
            b.addTags(EffectTags.negative);
        }
        return b;
    }

    public static ExileEffectBuilder food(EffectCtx ctx) {
        ExileEffectBuilder b = of(ctx);
        b.addTags(EffectTags.food);
        b.maxStacks(1);
        return b;
    }

    public ExileEffectBuilder addTags(EffectTags... tags) {
        for (EffectTags tag : tags) {
            if (!effect.tags.contains(tag.name())) {
                this.effect.tags.add(tag.name());
            }
        }
        return this;
    }

    public ExileEffectBuilder oneOfAKind(String kind) {
        this.effect.one_of_a_kind_id = kind;
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

    public ExileEffectBuilder stat(float first, Stat stat) {
        return stat(first, stat, ModType.FLAT);
    }

    public ExileEffect build() {
        effect.addToSerializables();
        return effect;
    }

}
