package com.robertx22.mine_and_slash.database.data.stats.types.generated;

import com.robertx22.mine_and_slash.database.data.stats.Stat;
import com.robertx22.mine_and_slash.database.data.stats.effects.offense.ChanceToApplyEffectEffect;
import com.robertx22.mine_and_slash.vanilla_mc.potion_effects.bases.BasePotionEffect;
import com.robertx22.mine_and_slash.vanilla_mc.potion_effects.druid.PoisonEffect;
import com.robertx22.mine_and_slash.vanilla_mc.potion_effects.ember_mage.BurnEffect;
import com.robertx22.mine_and_slash.vanilla_mc.potion_effects.ocean_mystic.FrostEffect;
import com.robertx22.mine_and_slash.vanilla_mc.potion_effects.shaman.StaticEffect;
import com.robertx22.mine_and_slash.uncommon.enumclasses.Elements;
import com.robertx22.mine_and_slash.uncommon.interfaces.IStatEffect;
import com.robertx22.mine_and_slash.uncommon.interfaces.IStatEffects;

public class ChanceToApplyEffect extends Stat implements IStatEffects {

    public static ChanceToApplyEffect BURN = new ChanceToApplyEffect(BurnEffect.INSTANCE);
    public static ChanceToApplyEffect CHILL = new ChanceToApplyEffect(FrostEffect.INSTANCE);
    public static ChanceToApplyEffect POISON = new ChanceToApplyEffect(PoisonEffect.INSTANCE);
    public static ChanceToApplyEffect STATIC = new ChanceToApplyEffect(StaticEffect.INSTANCE);

    BasePotionEffect effect;

    private ChanceToApplyEffect(BasePotionEffect effect) {
        this.effect = effect;
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
        return "Chance to apply effect on basic weapon hits.";
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
        return new ChanceToApplyEffectEffect(effect);
    }
}
