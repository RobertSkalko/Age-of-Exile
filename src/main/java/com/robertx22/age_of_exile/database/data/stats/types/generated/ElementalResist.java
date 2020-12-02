package com.robertx22.age_of_exile.database.data.stats.types.generated;

import com.robertx22.age_of_exile.database.data.stats.Stat;
import com.robertx22.age_of_exile.database.data.stats.effects.defense.ElementalResistEffect;
import com.robertx22.age_of_exile.database.data.stats.types.ElementalStat;
import com.robertx22.age_of_exile.mmorpg.Ref;
import com.robertx22.age_of_exile.uncommon.enumclasses.Elements;
import com.robertx22.age_of_exile.uncommon.interfaces.IExtraStatEffect;
import com.robertx22.age_of_exile.uncommon.interfaces.IStatEffect;
import com.robertx22.age_of_exile.uncommon.wrappers.MapWrapper;

import java.util.List;

public class ElementalResist extends ElementalStat implements IExtraStatEffect {

    public static MapWrapper<Elements, ElementalResist> MAP = new MapWrapper();

    @Override
    public List<Stat> generateAllPossibleStatVariations() {
        List<Stat> list = super.generateAllPossibleStatVariations();
        list.forEach(x -> MAP.put(x.getElement(), (ElementalResist) x));
        return list;
    }

    public ElementalResist(Elements element) {
        super(element);
        this.min_val = -300;

        this.max_val = 80;
        this.statGroup = StatGroup.ELEMENTAL;
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
    public IStatEffect getEffect() {
        return new ElementalResistEffect();
    }

    @Override
    public String locDescLangFileGUID() {
        return Ref.MODID + ".stat_desc." + "ele_resist";
    }

    @Override
    public boolean IsPercent() {
        return true;
    }

    @Override
    public String locNameForLangFile() {
        return this.getElement().dmgName + " Resist";
    }

}

