package com.robertx22.age_of_exile.uncommon.effectdatas;

import com.robertx22.age_of_exile.database.data.spells.components.Spell;
import com.robertx22.age_of_exile.uncommon.datasaving.Load;
import com.robertx22.age_of_exile.uncommon.effectdatas.interfaces.IHasSpellEffect;
import com.robertx22.age_of_exile.uncommon.effectdatas.interfaces.WeaponTypes;
import net.minecraft.entity.LivingEntity;

public class SpellDamageEffect extends DamageEffect implements IHasSpellEffect {

    public Spell spell;

    public SpellDamageEffect(LivingEntity source, LivingEntity target, int dmg, Spell spell) {
        super(null, source, target, dmg, Load.Unit(source), Load.Unit(target), EffectTypes.SPELL, WeaponTypes.None);
        this.spell = spell;
    }

    @Override
    public Spell getSpell() {
        return spell;
    }

    @Override
    protected void activate() {
        super.activate();

    }
}
