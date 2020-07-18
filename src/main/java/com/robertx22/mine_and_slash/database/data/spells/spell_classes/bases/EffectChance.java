package com.robertx22.mine_and_slash.database.data.spells.spell_classes.bases;

import com.robertx22.mine_and_slash.vanilla_mc.potion_effects.bases.BasePotionEffect;
import com.robertx22.mine_and_slash.uncommon.interfaces.IStatEffect;

public class EffectChance {

    public BasePotionEffect effect;
    public float chance;
    public IStatEffect.EffectSides appliedTo;

    public EffectChance(BasePotionEffect effect, float chance, IStatEffect.EffectSides appliedTo) {
        this.effect = effect;
        this.chance = chance;
        this.appliedTo = appliedTo;
    }
}
