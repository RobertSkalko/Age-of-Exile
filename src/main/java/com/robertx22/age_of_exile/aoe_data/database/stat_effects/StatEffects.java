package com.robertx22.age_of_exile.aoe_data.database.stat_effects;

import com.robertx22.age_of_exile.aoe_data.DataHolder;
import com.robertx22.age_of_exile.aoe_data.database.exile_effects.adders.BeneficialEffects;
import com.robertx22.age_of_exile.aoe_data.database.exile_effects.adders.NegativeEffects;
import com.robertx22.age_of_exile.aoe_data.database.stats.base.EffectCtx;
import com.robertx22.age_of_exile.database.data.stats.types.resources.health.Health;
import com.robertx22.age_of_exile.database.data.stats.types.resources.mana.Mana;
import com.robertx22.age_of_exile.database.registry.ISlashRegistryInit;
import com.robertx22.age_of_exile.saveclasses.unit.ResourceType;
import com.robertx22.age_of_exile.uncommon.effectdatas.rework.EventData;
import com.robertx22.age_of_exile.uncommon.effectdatas.rework.RestoreType;
import com.robertx22.age_of_exile.uncommon.effectdatas.rework.action.*;
import com.robertx22.age_of_exile.uncommon.effectdatas.rework.number_provider.NumberProvider;
import com.robertx22.age_of_exile.uncommon.interfaces.EffectSides;

import java.util.Arrays;

public class StatEffects implements ISlashRegistryInit {

    public static DataHolder<EffectCtx, StatEffect> GIVE_SELF_EFFECT = new DataHolder<>(
        Arrays.asList(BeneficialEffects.BLOODLUST
        ),
        x -> new GiveExileStatusEffect(x.effectId, EffectSides.Source, 10)
    );

    public static DataHolder<ResourceType, StatEffect> LEECH_RESTORE_RESOURCE_BASED_ON_STAT_DATA = new DataHolder<>(
        Arrays.asList(
            ResourceType.mana,
            ResourceType.health
        )
        , x -> new RestoreResourceAction("restore_" + x.id + "_per_stat_data", NumberProvider.ofStatData(), x, RestoreType.leech)
    );

    public static DataHolder<ResourceType, StatEffect> LEECH_PERCENT_OF_DAMAGE_AS_RESOURCE = new DataHolder<>(
        Arrays.asList(
            ResourceType.mana,
            ResourceType.health
        )
        , x -> new RestoreResourceAction("leech_" + x.id, NumberProvider.ofPercentOfDataNumber(EventData.NUMBER), x, RestoreType.leech)
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
        , x -> new GiveExileStatusEffect(x.effectId, EffectSides.Target, 5));

    public static StatEffect SET_IS_CRIT = new SetBooleanEffect(EventData.CRIT);
    public static StatEffect INCREASE_VALUE = new IncreaseNumberByPercentEffect(EventData.NUMBER);
    public static StatEffect INCREASE_SECONDS = new IncreaseNumberByPercentEffect(EventData.SECONDS);
    public static StatEffect SET_ACCURACY = new SetDataNumberAction(EventData.ACCURACY);
    public static StatEffect ADD_STAT_DATA_TO_NUMBER = new AddToNumberEffect("add_stat_data_to_num", EventData.NUMBER, NumberProvider.ofStatData());

    public static DataHolder<String, StatEffect> ADD_PERC_OF_STAT_TO_NUMBER = new DataHolder<>(
        Arrays.asList(
            Health.getInstance()
                .GUID(),
            Mana.getInstance()
                .GUID()
        )
        , x -> new AddToNumberEffect("add_perc_of_" + x + "_to_num", EventData.NUMBER, NumberProvider.ofPercentOfStat(x)));

    public static void loadClass() {
    }

    @Override
    public void registerAll() {

        GIVE_SELF_EFFECT.addToSerializables();
        GIVE_EFFECT_TO_TARGET.addToSerializables();
        SET_IS_CRIT.addToSerializables();
        INCREASE_VALUE.addToSerializables();
        SET_ACCURACY.addToSerializables();
        LEECH_RESTORE_RESOURCE_BASED_ON_STAT_DATA.addToSerializables();
        ADD_PERC_OF_STAT_TO_NUMBER.addToSerializables();
        ADD_STAT_DATA_TO_NUMBER.addToSerializables();
        INCREASE_SECONDS.addToSerializables();

    }
}
