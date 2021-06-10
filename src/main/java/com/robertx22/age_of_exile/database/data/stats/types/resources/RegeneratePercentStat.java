package com.robertx22.age_of_exile.database.data.stats.types.resources;

import com.robertx22.age_of_exile.capability.entity.EntityCap;
import com.robertx22.age_of_exile.database.data.stats.Stat;
import com.robertx22.age_of_exile.database.data.stats.StatScaling;
import com.robertx22.age_of_exile.database.data.stats.effects.base.BaseRegenEffect;
import com.robertx22.age_of_exile.database.data.stats.types.resources.health.Health;
import com.robertx22.age_of_exile.database.data.stats.types.resources.mana.Mana;
import com.robertx22.age_of_exile.saveclasses.unit.ResourceType;
import com.robertx22.age_of_exile.saveclasses.unit.StatData;
import com.robertx22.age_of_exile.uncommon.effectdatas.RestoreResourceEvent;
import com.robertx22.age_of_exile.uncommon.effectdatas.rework.EventData;
import com.robertx22.age_of_exile.uncommon.effectdatas.rework.RestoreType;
import com.robertx22.age_of_exile.uncommon.enumclasses.Elements;
import com.robertx22.age_of_exile.uncommon.interfaces.EffectSides;

import java.util.function.Function;

public class RegeneratePercentStat extends Stat {

    public static RegeneratePercentStat HEALTH = new RegeneratePercentStat(Health.getInstance(), ResourceType.health, x -> x.getUnit()
        .healthData()
        .getAverageValue());
    public static RegeneratePercentStat MANA = new RegeneratePercentStat(Mana.getInstance(), ResourceType.mana, x -> x.getUnit()
        .manaData()
        .getAverageValue());

    Stat statRestored;
    ResourceType type;

    Function<EntityCap.UnitData, Float> maxGetter;

    private RegeneratePercentStat(Stat statRestored, ResourceType rtype, Function<EntityCap.UnitData, Float> getmax) {
        this.statRestored = statRestored;
        this.scaling = StatScaling.NONE;
        this.type = rtype;
        this.maxGetter = getmax;

        this.group = StatGroup.RESTORATION;

        this.statEffect = new BaseRegenEffect() {

            @Override
            public EffectSides Side() {
                return EffectSides.Source;
            }

            @Override
            public int GetPriority() {
                return 0;
            }

            @Override
            public RestoreResourceEvent activate(RestoreResourceEvent effect, StatData data, Stat stat) {
                effect.data.getNumber(EventData.NUMBER).number += maxGetter.apply(effect.targetData) * data.getAverageValue() / 100F;
                return effect;
            }

            @Override
            public boolean canActivate(RestoreResourceEvent effect, StatData data, Stat stat) {
                return effect.data.getResourceType() == type && effect.data.getRestoreType() == RestoreType.regen;
            }
        };

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
        return "Restore % of your total per regen tick.";
    }

    @Override
    public String locNameForLangFile() {
        return statRestored.locNameForLangFile() + " Per Regen";
    }

    @Override
    public String GUID() {
        return statRestored.GUID() + "_per_sec";
    }

}
