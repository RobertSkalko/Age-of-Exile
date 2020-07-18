package com.robertx22.mine_and_slash.database.data.stats.types.generated;

import com.robertx22.mine_and_slash.database.data.stats.Stat;
import com.robertx22.mine_and_slash.database.data.stats.effects.offense.ElementalFocusEffect;
import com.robertx22.mine_and_slash.database.data.stats.types.SingleElementalStat;
import com.robertx22.mine_and_slash.mmorpg.Ref;
import com.robertx22.mine_and_slash.uncommon.enumclasses.Elements;
import com.robertx22.mine_and_slash.uncommon.interfaces.IStatEffect;
import com.robertx22.mine_and_slash.uncommon.interfaces.IStatEffects;
import com.robertx22.mine_and_slash.uncommon.wrappers.MapWrapper;

import java.util.List;

public class ElementalFocus extends SingleElementalStat implements IStatEffects {

    public static MapWrapper<Elements, ElementalFocus> MAP = new MapWrapper();

    @Override
    public List<Stat> generateAllPossibleStatVariations() {
        List<Stat> list = super.generateAllPossibleStatVariations();
        list.forEach(x -> MAP.put(x.getElement(), (ElementalFocus) x));
        return list;

    }

    public ElementalFocus(Elements element) {
        super(element);

    }

    @Override
    public String locNameForLangFile() {
        return element.name() + " Focus";
    }

    @Override
    public String GUID() {
        return element.guidName + "_focus";
    }

    @Override
    public Stat newGeneratedInstance(Elements element) {
        return new ElementalFocus(element);
    }

    @Override
    public boolean IsPercent() {
        return true;
    }

    @Override
    public Elements getElement() {
        return element;
    }

    @Override
    public String locDescForLangFile() {
        return "Increases Dmg for that element by a percent but decreases dmg from all other elements.";
    }

    @Override
    public String locDescLangFileGUID() {
        return Ref.MODID + ".stat_desc." + "elemental_focus";
    }

    @Override
    public IStatEffect getEffect() {
        return new ElementalFocusEffect();
    }

}
