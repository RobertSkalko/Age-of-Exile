package com.robertx22.age_of_exile.uncommon.effectdatas;

import com.robertx22.age_of_exile.database.data.spells.components.Spell;
import com.robertx22.age_of_exile.uncommon.effectdatas.interfaces.IHasSpellEffect;
import net.minecraft.entity.LivingEntity;

public class AuraStatEffect extends EffectData implements IHasSpellEffect {
    Spell spell;

    public float aura_stat_multi = 1;

    public AuraStatEffect(Spell spell, LivingEntity source) {
        super(source, source);
        this.spell = spell;
    }

    @Override
    protected void activate() {
    }

    @Override
    public Spell getSpell() {
        return spell;
    }
}
