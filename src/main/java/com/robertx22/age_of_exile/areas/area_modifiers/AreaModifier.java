package com.robertx22.age_of_exile.areas.area_modifiers;

import com.robertx22.age_of_exile.areas.base_areas.BaseArea;
import com.robertx22.age_of_exile.database.data.IGUID;
import com.robertx22.age_of_exile.database.data.StatModifier;
import com.robertx22.age_of_exile.database.data.affixes.Affix;
import com.robertx22.age_of_exile.uncommon.interfaces.IWeighted;
import net.minecraft.entity.EntityType;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class AreaModifier implements IGUID, IWeighted {

    String id;
    Affix.Type affixType;
    String locName;
    int weight;
    List<EntityType> mobSpawns;
    public AreaRequirement requirement;
    public List<StatModifier> stats = new ArrayList<>();

    public AreaModifier(String id, int weight, Affix.Type affixType, String locName, List<EntityType> mobSpawns, AreaRequirement req) {
        this.id = id;
        this.weight = weight;
        this.affixType = affixType;
        this.locName = locName;
        this.mobSpawns = mobSpawns;
        this.requirement = req;
    }

    public AreaModifier addStats(StatModifier... stats) {
        this.stats.addAll(Arrays.asList(stats));
        return this;
    }

    public boolean canMobSpawn(EntityType type) {
        return mobSpawns.contains(type);
    }

    public String getFinalLocNameFor(BaseArea area) {
        if (affixType.isPrefix()) {
            return locName + " " + area.locname;
        } else {
            return area.locname + " " + locName;
        }
    }

    @Override
    public String GUID() {
        return id;
    }

    @Override
    public int Weight() {
        return weight;
    }
}
