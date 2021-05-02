package com.robertx22.age_of_exile.aoe_data.database.stat_effects;

import com.robertx22.age_of_exile.aoe_data.DataHolder;
import com.robertx22.age_of_exile.aoe_data.database.exile_effects.adders.BeneficialEffects;
import com.robertx22.age_of_exile.aoe_data.database.exile_effects.adders.NegativeEffects;
import com.robertx22.age_of_exile.aoe_data.database.stats.base.EffectCtx;
import com.robertx22.age_of_exile.database.registry.ISlashRegistryInit;
import com.robertx22.age_of_exile.uncommon.effectdatas.rework.EventData;
import com.robertx22.age_of_exile.uncommon.effectdatas.rework.action.*;
import com.robertx22.age_of_exile.uncommon.interfaces.IStatEffect;

import java.util.Arrays;

public class StatEffects implements ISlashRegistryInit {

    public static DataHolder<EffectCtx, StatEffect> GIVE_SELF_EFFECT = new DataHolder<>(
        Arrays.asList(BeneficialEffects.BLOODLUST
        ),
        x -> new GiveExileStatusEffect(x.effectId, IStatEffect.EffectSides.Source, 10)
    );

    public static DataHolder<EffectCtx, StatEffect> GIVE_EFFECT_TO_TARGET = new DataHolder<>(
        Arrays.asList(
            NegativeEffects.BURN,
            NegativeEffects.FROSTBURN,
            NegativeEffects.BLEED,
            NegativeEffects.POISON,
            NegativeEffects.BLIND,
            NegativeEffects.TORMENT
        )
        , x -> new GiveExileStatusEffect(x.effectId, IStatEffect.EffectSides.Target, 5));

    public static StatEffect SET_IS_CRIT = new SetBooleanEffect(EventData.CRIT);
    public static StatEffect INCREASE_VALUE = new IncreaseNumberEffect();
    public static StatEffect SET_ACCURACY = new SetDataNumberAction(EventData.ACCURACY);

    public static void loadClass() {
    }

    @Override
    public void registerAll() {

        GIVE_SELF_EFFECT.addToSerializables();
        GIVE_EFFECT_TO_TARGET.addToSerializables();
        SET_IS_CRIT.addToSerializables();
        INCREASE_VALUE.addToSerializables();
        SET_ACCURACY.addToSerializables();

    }
}