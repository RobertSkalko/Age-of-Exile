package com.robertx22.age_of_exile.aoe_data.database.stats;

import com.robertx22.age_of_exile.aoe_data.database.stats.base.AutoHashClass;
import com.robertx22.age_of_exile.aoe_data.database.stats.base.EffectCtx;

import java.util.Objects;

public class SpellAndEffect extends AutoHashClass {

    public String spell;
    public EffectCtx effect;

    public SpellAndEffect(String spell, EffectCtx effect) {
        this.spell = spell;
        this.effect = effect;
    }

    @Override
    public int hashCode() {
        return Objects.hash(spell, effect.resourcePath);
    }

}
