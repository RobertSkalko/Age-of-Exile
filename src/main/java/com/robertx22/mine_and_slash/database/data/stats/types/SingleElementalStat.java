package com.robertx22.mine_and_slash.database.data.stats.types;

import com.robertx22.mine_and_slash.database.data.stats.Stat;
import com.robertx22.mine_and_slash.uncommon.enumclasses.Elements;
import com.robertx22.mine_and_slash.uncommon.interfaces.IElementalGenerated;

import java.util.ArrayList;
import java.util.List;

public abstract class SingleElementalStat extends Stat implements IElementalGenerated<Stat> {

    public Elements element;

    public SingleElementalStat(Elements element) {
        this.element = element;

    }

    @Override
    public Elements getElement() {
        return this.element;
    }

    public abstract Stat newGeneratedInstance(Elements element);

    @Override
    public List<Stat> generateAllPossibleStatVariations() {
        List<Stat> list = new ArrayList<>();
        Elements.getAllSingleElementals()
            .forEach(x -> list.add(newGeneratedInstance(x)));
        return list;

    }

}
