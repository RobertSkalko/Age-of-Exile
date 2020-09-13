package com.robertx22.age_of_exile.database.data.stats.types.generated;

import com.robertx22.age_of_exile.database.data.stats.Stat;
import com.robertx22.age_of_exile.database.data.stats.StatScaling;
import com.robertx22.age_of_exile.database.data.stats.effects.offense.AllEleDmgEffectIfElement;
import com.robertx22.age_of_exile.database.data.stats.types.ElementalStat;
import com.robertx22.age_of_exile.mmorpg.Ref;
import com.robertx22.age_of_exile.uncommon.enumclasses.Elements;
import com.robertx22.age_of_exile.uncommon.interfaces.IStatEffect;
import com.robertx22.age_of_exile.uncommon.interfaces.IStatEffects;

import java.util.List;

public class ElementalDamageBonus extends ElementalStat implements IStatEffects {

    @Override
    public StatScaling getScaling() {
        return StatScaling.NONE;
    }

    @Override
    public List<Stat> generateAllPossibleStatVariations() {
        List<Stat> list = super.generateAllPossibleStatVariations();
        return list;

    }

    public ElementalDamageBonus(Elements element) {
        super(element);
    }

    @Override
    public Stat newGeneratedInstance(Elements element) {
        return new ElementalDamageBonus(element);
    }

    @Override
    public String GUID() {
        return "all_" + this.getElement().guidName + "_damage";
    }

    @Override
    public String getIconPath() {
        return "all_ele_dmg/" + element.guidName;
    }

    @Override
    public String locDescForLangFile() {
        return "Increases All dmg of that element, both spells and attacks";
    }

    @Override
    public String locDescLangFileGUID() {
        return Ref.MODID + ".stat_desc." + "all_ele_dmg";
    }

    @Override
    public boolean IsPercent() {
        return true;
    }

    @Override
    public IStatEffect getEffect() {
        return new AllEleDmgEffectIfElement();
    }

    @Override
    public String locNameForLangFile() {
        return this.getElement()
            .dmgName + " Damage";
    }

}

