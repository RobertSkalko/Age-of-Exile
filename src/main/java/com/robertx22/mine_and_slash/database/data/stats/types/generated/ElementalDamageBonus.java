package com.robertx22.mine_and_slash.database.data.stats.types.generated;

import com.robertx22.mine_and_slash.database.data.stats.Stat;
import com.robertx22.mine_and_slash.database.data.stats.StatScaling;
import com.robertx22.mine_and_slash.database.data.stats.effects.offense.AllEleDmgEffectIfElement;
import com.robertx22.mine_and_slash.database.data.stats.types.ElementalStat;
import com.robertx22.mine_and_slash.mmorpg.Ref;
import com.robertx22.mine_and_slash.uncommon.enumclasses.Elements;
import com.robertx22.mine_and_slash.uncommon.interfaces.IStatEffect;
import com.robertx22.mine_and_slash.uncommon.interfaces.IStatEffects;
import com.robertx22.mine_and_slash.uncommon.wrappers.MapWrapper;

import java.util.List;

public class ElementalDamageBonus extends ElementalStat implements IStatEffects {

    public static MapWrapper<Elements, ElementalDamageBonus> MAP = new MapWrapper();

    @Override
    public StatScaling getScaling() {
        return StatScaling.NONE;
    }

    @Override
    public List<Stat> generateAllPossibleStatVariations() {
        List<Stat> list = super.generateAllPossibleStatVariations();
        list.forEach(x -> MAP.put(x.getElement(), (ElementalDamageBonus) x));
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

