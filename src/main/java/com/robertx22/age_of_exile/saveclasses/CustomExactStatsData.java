package com.robertx22.age_of_exile.saveclasses;

import com.robertx22.age_of_exile.capability.entity.EntityCap;
import com.robertx22.age_of_exile.database.data.StatModifier;
import com.robertx22.age_of_exile.saveclasses.gearitem.gear_bases.IApplyableStats;
import com.robertx22.age_of_exile.uncommon.enumclasses.ModType;
import info.loenwind.autosave.annotations.Storable;
import info.loenwind.autosave.annotations.Store;

import java.util.HashMap;

@Storable
public class CustomExactStatsData implements IApplyableStats {

    public CustomExactStatsData() {

    }

    @Store
    public HashMap<String, ExactStatData> stats = new HashMap<>();

    @Store
    public HashMap<String, StatModifier> mods = new HashMap<>();

    public void addExactStat(String hashmapGUID, String statGUID, float v1, float v2, ModType type) {
        try {
            stats.put(hashmapGUID, ExactStatData.noScaling(v1, v2, type, statGUID));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void removeExactStat(String hashmapGUID) {
        stats.remove(hashmapGUID);
    }

    public void addMod(String hashmapGUID, String statGUID, float v1, float v2, ModType type) {
        try {
            mods.put(hashmapGUID, new StatModifier(v1, v2, statGUID, type));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void removeMod(String hashmapGUID) {
        mods.remove(hashmapGUID);
    }

    @Override
    public void applyStats(EntityCap.UnitData data) {

        this.stats.values()
            .forEach(x -> x.applyStats(data));

        this.mods.values()
            .forEach(x -> x.ToExactStat(100, data.getLevel())
                .applyStats(data));

    }
}
