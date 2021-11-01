package com.robertx22.age_of_exile.database.data.stats.types.generated;

import com.robertx22.age_of_exile.database.data.stats.IUsableStat;
import com.robertx22.age_of_exile.database.data.stats.Stat;
import com.robertx22.age_of_exile.database.data.stats.StatScaling;
import com.robertx22.age_of_exile.database.data.stats.effects.defense.ElementalResistEffect;
import com.robertx22.age_of_exile.database.data.stats.types.ElementalStat;
import com.robertx22.age_of_exile.mmorpg.SlashRef;
import com.robertx22.age_of_exile.uncommon.enumclasses.Elements;
import com.robertx22.age_of_exile.uncommon.wrappers.MapWrapper;

import java.util.List;

public class ElementalResist extends ElementalStat implements IUsableStat {

    public static MapWrapper<Elements, ElementalResist> MAP = new MapWrapper();

    @Override
    public List<Stat> generateAllPossibleStatVariations() {
        List<Stat> list = super.generateAllPossibleStatVariations();
        list.forEach(x -> MAP.put(x.getElement(), (ElementalResist) x));
        return list;
    }

    public ElementalResist(Elements element) {
        super(element);
        this.min = -300;

        this.group = StatGroup.ELEMENTAL;
        this.is_perc = false;
        this.scaling = StatScaling.NORMAL;

        this.format = element.format.getName();
        this.icon = element.icon;
        this.statEffect = new ElementalResistEffect();

    }

    @Override
    public Stat newGeneratedInstance(Elements element) {
        return new ElementalResist(element);
    }

    @Override
    public String GUID() {
        return this.getElement().guidName + "_resist";
    }

    @Override
    public String locDescForLangFile() {
        return "Stops X percent damage of that element";
    }

    @Override
    public String locDescLangFileGUID() {
        return SlashRef.MODID + ".stat_desc." + "ele_resist";
    }

    @Override
    public String locNameForLangFile() {
        return this.getElement().dmgName + " Defense";
    }

    @Override
    public float getMaxMulti() {
        return 0.8F;
    }

    @Override
    public float valueNeededToReachMaximumPercentAtLevelOne() {
        return 20;
    }

}

