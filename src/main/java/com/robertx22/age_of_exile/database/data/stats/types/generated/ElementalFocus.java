package com.robertx22.age_of_exile.database.data.stats.types.generated;

import com.robertx22.age_of_exile.database.data.stats.Stat;
import com.robertx22.age_of_exile.database.data.stats.effects.offense.ElementalFocusEffect;
import com.robertx22.age_of_exile.database.data.stats.types.SingleElementalStat;
import com.robertx22.age_of_exile.mmorpg.Ref;
import com.robertx22.age_of_exile.uncommon.enumclasses.Elements;
import com.robertx22.age_of_exile.uncommon.interfaces.IStatEffect;
import com.robertx22.age_of_exile.uncommon.interfaces.IStatEffects;

import java.util.List;

public class ElementalFocus extends SingleElementalStat implements IStatEffects {

    @Override
    public List<Stat> generateAllPossibleStatVariations() {
        List<Stat> list = super.generateAllPossibleStatVariations();
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
