package com.robertx22.age_of_exile.uncommon.effectdatas;

import com.robertx22.age_of_exile.capability.entity.EntityCap;
import com.robertx22.age_of_exile.database.data.spells.spell_classes.bases.BaseSpell;
import com.robertx22.age_of_exile.uncommon.effectdatas.interfaces.IHasSpellEffect;
import com.robertx22.age_of_exile.uncommon.effectdatas.interfaces.WeaponTypes;
import com.robertx22.age_of_exile.uncommon.interfaces.IStatEffect;
import com.robertx22.age_of_exile.uncommon.utilityclasses.RandomUtils;
import com.robertx22.age_of_exile.vanilla_mc.potion_effects.bases.PotionEffectUtils;
import net.minecraft.entity.LivingEntity;

public class SpellDamageEffect extends DamageEffect implements IHasSpellEffect {

    public BaseSpell spell;

    public SpellDamageEffect(LivingEntity source, LivingEntity target, int dmg, EntityCap.UnitData sourceData,
                             EntityCap.UnitData targetData, BaseSpell spell) {
        super(null, source, target, dmg, sourceData, targetData, EffectTypes.SPELL, WeaponTypes.None);

        this.spell = spell;
        this.element = spell.getElement();
    }

    @Override
    public BaseSpell getSpell() {
        return spell;
    }

    public DamageEffect doNotActivateSynergies() {
        this.activateSynergies = false;
        return this;

    }

    @Override
    protected void activate() {
        super.activate();

        spell.onDamageEffects.forEach(x -> {
            if (RandomUtils.roll(x.chance)) {
                LivingEntity to = x.appliedTo == IStatEffect.EffectSides.Source ? source : target;
                PotionEffectUtils.apply(x.effect, source, to);
            }
        });

    }
}
