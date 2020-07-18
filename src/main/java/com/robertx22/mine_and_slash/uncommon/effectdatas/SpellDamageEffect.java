package com.robertx22.mine_and_slash.uncommon.effectdatas;

import com.robertx22.mine_and_slash.capability.entity.EntityCap;
import com.robertx22.mine_and_slash.database.data.spells.spell_classes.bases.BaseSpell;
import com.robertx22.mine_and_slash.uncommon.effectdatas.interfaces.IHasSpellEffect;
import com.robertx22.mine_and_slash.uncommon.effectdatas.interfaces.WeaponTypes;
import com.robertx22.mine_and_slash.uncommon.interfaces.IStatEffect;
import com.robertx22.mine_and_slash.uncommon.utilityclasses.RandomUtils;
import com.robertx22.mine_and_slash.vanilla_mc.potion_effects.bases.PotionEffectUtils;
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
