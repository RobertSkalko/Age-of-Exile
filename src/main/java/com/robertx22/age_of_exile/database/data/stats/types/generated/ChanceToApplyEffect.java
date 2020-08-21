package com.robertx22.age_of_exile.database.data.stats.types.generated;

import com.robertx22.age_of_exile.database.data.stats.Stat;
import com.robertx22.age_of_exile.database.data.stats.effects.offense.ChanceToApplyEffectEffect;
import com.robertx22.age_of_exile.uncommon.effectdatas.EffectData;
import com.robertx22.age_of_exile.uncommon.enumclasses.Elements;
import com.robertx22.age_of_exile.uncommon.interfaces.IStatEffect;
import com.robertx22.age_of_exile.uncommon.interfaces.IStatEffects;
import com.robertx22.age_of_exile.vanilla_mc.potion_effects.bases.BasePotionEffect;
import com.robertx22.age_of_exile.vanilla_mc.potion_effects.druid.PoisonEffect;
import com.robertx22.age_of_exile.vanilla_mc.potion_effects.ember_mage.BurnEffect;
import com.robertx22.age_of_exile.vanilla_mc.potion_effects.ocean_mystic.FrostEffect;
import com.robertx22.age_of_exile.vanilla_mc.potion_effects.shaman.StaticEffect;

public class ChanceToApplyEffect extends Stat implements IStatEffects {

    public static ChanceToApplyEffect BURN = new ChanceToApplyEffect(BurnEffect.INSTANCE);
    public static ChanceToApplyEffect CHILL = new ChanceToApplyEffect(FrostEffect.INSTANCE);
    public static ChanceToApplyEffect POISON = new ChanceToApplyEffect(PoisonEffect.INSTANCE);
    public static ChanceToApplyEffect STATIC = new ChanceToApplyEffect(StaticEffect.INSTANCE);

    BasePotionEffect effect;

    private ChanceToApplyEffect(BasePotionEffect effect) {
        this.effect = effect;
        this.add$To$toTooltip = false;
        this.add$plusminus$toTooltip = false;
        this.minimumValue = 0;
    }

    @Override
    public boolean IsPercent() {
        return true;
    }

    @Override
    public Elements getElement() {
        return null;
    }

    @Override
    public String locDescForLangFile() {
        return "Chance to apply effect on weapon or spell hits.";
    }

    @Override
    public String locNameForLangFile() {
        return "Chance to Apply " + effect.locNameForLangFile();
    }

    @Override
    public String GUID() {
        return "chance_of_" + effect.GUID();
    }

    @Override
    public IStatEffect getEffect() {
        return new ChanceToApplyEffectEffect(effect, EffectData.EffectTypes.BASIC_ATTACK, EffectData.EffectTypes.SPELL);
    }
}
