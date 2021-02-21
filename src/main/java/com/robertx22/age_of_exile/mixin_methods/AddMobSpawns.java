package com.robertx22.age_of_exile.mixin_methods;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.world.biome.SpawnSettings;
import net.minecraft.world.biome.SpawnSettings.SpawnEntry;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.robertx22.age_of_exile.mmorpg.ModRegistry.ENTITIES;

public class AddMobSpawns {

    public static class Spawns {

        List<SpawnEntry> all = new ArrayList<>();

        public SpawnEntry FIRE_MAGE = of(ENTITIES.FIRE_MAGE, 10, 1, 1);
        public SpawnEntry WATER_MAGE = of(ENTITIES.WATER_MAGE, 10, 1, 1);
        public SpawnEntry THUNDER_MAGE = of(ENTITIES.THUNDER_MAGE, 10, 1, 1);
        public SpawnEntry NATURE_MAGE = of(ENTITIES.NATURE_MAGE, 10, 1, 1);
        public SpawnEntry HEALER_MAGE = of(ENTITIES.HEALER_MAGE, 5, 1, 1);

        SpawnEntry of(EntityType type, int weight, int min, int max) {
            SpawnEntry entry = new SpawnEntry(type, weight, min, max);
            all.add(entry);
            return entry;
        }
    }

    public static Spawns SPAWNS = null;

    public static Map<SpawnGroup, List<SpawnSettings.SpawnEntry>> addMySpawns(Map<SpawnGroup, List<SpawnSettings.SpawnEntry>> map) {

        List<SpawnEntry> list = new ArrayList<>(map.get(SpawnGroup.MONSTER));
        list.addAll(SPAWNS.all);
        map = new HashMap<>(map); // map is imutable too..
        map.put(SpawnGroup.MONSTER, list);
        return map;
    }

    public static boolean shouldAddMySpawns(Map<SpawnGroup, List<SpawnSettings.SpawnEntry>> map) {

        List<SpawnEntry> list = new ArrayList<>(map.get(SpawnGroup.MONSTER));
        return !list.contains(SPAWNS.FIRE_MAGE);
    }

}
