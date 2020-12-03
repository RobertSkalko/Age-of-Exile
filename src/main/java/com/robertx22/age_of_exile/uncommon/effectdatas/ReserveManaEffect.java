package com.robertx22.age_of_exile.uncommon.effectdatas;

import com.robertx22.age_of_exile.database.data.spells.components.Spell;
import com.robertx22.age_of_exile.uncommon.effectdatas.interfaces.IHasSpellEffect;
import net.minecraft.entity.LivingEntity;

public class ReserveManaEffect extends EffectData implements IHasSpellEffect {
    Spell spell;

    public float manaReserved = 0;

    public ReserveManaEffect(Spell spell, LivingEntity source) {
        super(source, source);
        this.spell = spell;
        this.manaReserved = spell.aura_data.mana_reserved;
    }

    @Override
    protected void activate() {
    }

    @Override
    public Spell getSpell() {
        return spell;
    }
}
