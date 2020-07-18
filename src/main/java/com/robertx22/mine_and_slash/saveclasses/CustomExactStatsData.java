package com.robertx22.mine_and_slash.saveclasses;

import com.robertx22.mine_and_slash.uncommon.enumclasses.ModType;
import info.loenwind.autosave.annotations.Storable;
import info.loenwind.autosave.annotations.Store;

import java.util.HashMap;

@Storable
public class CustomExactStatsData {

    public CustomExactStatsData() {

    }

    @Store
    public HashMap<String, ExactStatData> stats = new HashMap<>();

    public void add(String hashmapGUID, String statGUID, float value, ModType type) {
        try {
            stats.put(hashmapGUID, ExactStatData.scaleTo(value, type, statGUID, 0));
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void remove(String hashmapGUID) {
        stats.remove(hashmapGUID);
    }

}
