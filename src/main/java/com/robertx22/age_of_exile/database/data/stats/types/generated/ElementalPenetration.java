package com.robertx22.age_of_exile.database.data.stats.types.generated;

import com.robertx22.age_of_exile.database.data.stats.Stat;
import com.robertx22.age_of_exile.database.data.stats.effects.offense.ElementalPenetrationEffect;
import com.robertx22.age_of_exile.database.data.stats.types.ElementalStat;
import com.robertx22.age_of_exile.mmorpg.Ref;
import com.robertx22.age_of_exile.uncommon.enumclasses.Elements;
import com.robertx22.age_of_exile.uncommon.interfaces.IStatEffect;
import com.robertx22.age_of_exile.uncommon.interfaces.IStatEffects;
import com.robertx22.age_of_exile.uncommon.wrappers.MapWrapper;

import java.util.List;

public class ElementalPenetration extends ElementalStat implements IStatEffects {
    public static MapWrapper<Elements, ElementalPenetration> MAP = new MapWrapper();

    @Override
    public List<Stat> generateAllPossibleStatVariations() {
        List<Stat> list = super.generateAllPossibleStatVariations();
        list.forEach(x -> MAP.put(x.getElement(), (ElementalPenetration) x));
        return list;

    }

    @Override
    public Stat.StatGroup statGroup() {
        return Stat.StatGroup.Penetration;
    }

    public ElementalPenetration(Elements element) {
        super(element);
        this.min_val = 0;

    }

    @Override
    public Stat newGeneratedInstance(Elements element) {

        return new ElementalPenetration(element);
    }

    @Override
    public String getIconPath() {
        return "pene/" + element.guidName;
    }

    @Override
    public String GUID() {
        return this.getElement().guidName + "_penetration";
    }

    @Override
    public String locDescForLangFile() {
        return "Ignores that much resists, it works as subtraction.";
    }

    @Override
    public String locDescLangFileGUID() {
        return Ref.MODID + ".stat_desc." + "ele_pene";
    }

    @Override
    public boolean IsPercent() {
        return true;
    }

    @Override
    public String locNameForLangFile() {
        return this.getElement()
            .name() + " Penetration";
    }

    @Override
    public IStatEffect getEffect() {
        return new ElementalPenetrationEffect();
    }

}
