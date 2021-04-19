package com.robertx22.age_of_exile.dimension.dungeon_data;

import info.loenwind.autosave.annotations.Storable;
import info.loenwind.autosave.annotations.Store;

import java.util.ArrayList;
import java.util.List;

@Storable
public class PossibleUniques {

    @Store
    public List<String> uniques = new ArrayList<>();

}
