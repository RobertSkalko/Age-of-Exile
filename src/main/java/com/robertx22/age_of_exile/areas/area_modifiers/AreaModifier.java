package com.robertx22.age_of_exile.areas.area_modifiers;

import com.robertx22.age_of_exile.areas.base_areas.BaseArea;
import com.robertx22.age_of_exile.database.data.IGUID;
import com.robertx22.age_of_exile.database.data.affixes.Affix;
import com.robertx22.age_of_exile.uncommon.interfaces.IWeighted;
import net.minecraft.entity.EntityType;
import net.minecraft.world.biome.Biome;

import java.util.List;
import java.util.function.Predicate;

public class AreaModifier implements IGUID, IWeighted {

    String id;
    Affix.Type affixType;
    String locName;
    int weight;
    List<EntityType> mobSpawns;
    Predicate<Biome> canUseBiome;

    public AreaModifier(String id, int weight, Affix.Type affixType, String locName, List<EntityType> mobSpawns, Predicate<Biome> canUseBiome) {
        this.id = id;
        this.weight = weight;
        this.affixType = affixType;
        this.locName = locName;
        this.mobSpawns = mobSpawns;
        this.canUseBiome = canUseBiome;
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
