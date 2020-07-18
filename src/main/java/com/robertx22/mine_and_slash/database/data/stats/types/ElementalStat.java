package com.robertx22.mine_and_slash.database.data.stats.types;

import com.robertx22.mine_and_slash.database.data.stats.Stat;
import com.robertx22.mine_and_slash.database.data.stats.TransferMethod;
import com.robertx22.mine_and_slash.database.data.stats.types.generated.ElementalResist;
import com.robertx22.mine_and_slash.saveclasses.unit.StatData;
import com.robertx22.mine_and_slash.saveclasses.unit.Unit;
import com.robertx22.mine_and_slash.uncommon.enumclasses.Elements;
import com.robertx22.mine_and_slash.uncommon.interfaces.IElementalGenerated;
import com.robertx22.mine_and_slash.uncommon.interfaces.IStatTransfer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public abstract class ElementalStat extends Stat implements IElementalGenerated<Stat>, IStatTransfer {

    public Elements element;

    public ElementalStat(Elements element) {
        this.element = element;

    }

    @Override
    public Elements getElement() {
        return this.element;
    }

    public abstract Stat newGeneratedInstance(Elements element);

    @Override
    public boolean IsShownOnStatGui() {
        return getElement() != Elements.Elemental;
    }

    @Override
    public List<Stat> generateAllPossibleStatVariations() {
        List<Stat> list = new ArrayList<>();
        Elements.getAllExcludingPhysical()
            .forEach(x -> list.add(newGeneratedInstance(x)));
        return list;

    }

    @Override
    public void transferStats(Unit copy, Unit unit, StatData data) {
        for (TransferMethod stat : this.Transfer()) {
            copy.getCreateStat(stat.converted)
                .addFullyTo(unit.getCreateStat(stat.statThatBenefits));

        }
    }

    @Override
    public List<TransferMethod> Transfer() {

        if (this.getElement()
            .equals(Elements.Elemental)) {
            return Arrays.asList(
                new TransferMethod(new ElementalResist(Elements.Elemental), newGeneratedInstance(Elements.Nature)),
                new TransferMethod(new ElementalResist(Elements.Elemental), newGeneratedInstance(Elements.Fire)),
                new TransferMethod(new ElementalResist(Elements.Elemental), newGeneratedInstance(Elements.Thunder)),
                new TransferMethod(new ElementalResist(Elements.Elemental), newGeneratedInstance(Elements.Water))
            );
        }

        return Arrays.asList();
    }

}
