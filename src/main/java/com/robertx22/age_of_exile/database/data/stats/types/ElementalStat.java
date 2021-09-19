package com.robertx22.age_of_exile.database.data.stats.types;

import com.robertx22.age_of_exile.capability.entity.EntityData;
import com.robertx22.age_of_exile.database.data.stats.Stat;
import com.robertx22.age_of_exile.database.data.stats.types.core_stats.base.ITransferToOtherStats;
import com.robertx22.age_of_exile.saveclasses.unit.InCalcStatData;
import com.robertx22.age_of_exile.uncommon.enumclasses.Elements;
import com.robertx22.age_of_exile.uncommon.interfaces.IElementalGenerated;

import java.util.ArrayList;
import java.util.List;

public abstract class ElementalStat extends Stat implements IElementalGenerated<Stat>, ITransferToOtherStats {

    public Elements element;

    public ElementalStat(Elements element) {
        this.element = element;

        this.show = element != Elements.Elemental;

        if (getElement() != null) {
            this.format = getElement().format.getName();
            this.icon = getElement().icon;
        }
    }

    @Override
    public Elements getElement() {
        return this.element;
    }

    public abstract Stat newGeneratedInstance(Elements element);

    @Override
    public List<Stat> generateAllPossibleStatVariations() {
        List<Stat> list = new ArrayList<>();
        Elements.getEverythingBesidesPhysical()
            .forEach(x -> list.add(newGeneratedInstance(x)));
        return list;

    }

    @Override
    public void transferStats(EntityData unit, InCalcStatData thisstat) {
        if (this.element == Elements.Elemental) {
            for (Elements ele : Elements.getAllSingleElementals()) {
                thisstat.addFullyTo(unit.getUnit()
                    .getStatInCalculation(newGeneratedInstance(ele)));
            }
            thisstat.clear();
        }
    }

}
