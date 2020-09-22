package com.robertx22.age_of_exile.database.data.stats.types.generated;

import com.robertx22.age_of_exile.database.data.stats.Stat;
import com.robertx22.age_of_exile.database.data.stats.StatScaling;
import com.robertx22.age_of_exile.database.data.stats.effects.offense.TurnPhysIntoEleDmgEffect;
import com.robertx22.age_of_exile.database.data.stats.types.ElementalStat;
import com.robertx22.age_of_exile.mmorpg.Ref;
import com.robertx22.age_of_exile.uncommon.enumclasses.Elements;
import com.robertx22.age_of_exile.uncommon.interfaces.IStatEffect;
import com.robertx22.age_of_exile.uncommon.interfaces.IStatEffects;
import com.robertx22.age_of_exile.uncommon.wrappers.MapWrapper;

import java.util.List;

public class PhysConvertToEle extends ElementalStat implements IStatEffects {

    public static MapWrapper<Elements, PhysConvertToEle> MAP = new MapWrapper();

    @Override
    public List<Stat> generateAllPossibleStatVariations() {
        List<Stat> list = super.generateAllSingleVariations();
        list.forEach(x -> MAP.put(x.getElement(), (PhysConvertToEle) x));
        return list;

    }

    public PhysConvertToEle(Elements element) {
        super(element);
        this.scaling = StatScaling.NONE;
    }

    @Override
    public Stat newGeneratedInstance(Elements element) {
        return new PhysConvertToEle(element);
    }

    @Override
    public String GUID() {
        return "convert_" + this.getElement().guidName + "_to_phys";
    }

    @Override
    public String getIconPath() {
        return "all_ele_dmg/" + element.guidName;
    }

    @Override
    public String locDescForLangFile() {
        return "Turns % of phys atk dmg into ele";
    }

    @Override
    public String locDescLangFileGUID() {
        return Ref.MODID + ".stat_desc." + "turn_phys_to_ele";
    }

    @Override
    public boolean IsPercent() {
        return true;
    }

    @Override
    public IStatEffect getEffect() {
        return new TurnPhysIntoEleDmgEffect();
    }

    @Override
    public String locNameForLangFile() {
        return "Physical to " + this.getElement()
            .dmgName + " Damage";
    }

}


