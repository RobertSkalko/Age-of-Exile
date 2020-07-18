package com.robertx22.mine_and_slash.uncommon.interfaces;

import com.robertx22.mine_and_slash.database.data.IGUID;
import com.robertx22.mine_and_slash.uncommon.enumclasses.Elements;

import java.util.ArrayList;
import java.util.List;

public interface IElementalGenerated<T extends IGUID> extends IGenerated {

    public abstract T newGeneratedInstance(Elements element);

    default List<T> generateAllSingleVariations() {
        List<T> list = new ArrayList<>();
        Elements.getAllSingleElementals()
            .forEach(x -> list.add(newGeneratedInstance(x)));
        return list;

    }

    @Override
    public default List<T> generateAllPossibleStatVariations() {
        List<T> list = new ArrayList<>();
        Elements.getAllSingleElementals()
            .forEach(x -> list.add(newGeneratedInstance(x)));
        return list;
    }

}
