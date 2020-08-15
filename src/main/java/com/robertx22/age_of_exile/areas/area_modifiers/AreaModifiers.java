package com.robertx22.age_of_exile.areas.area_modifiers;

import com.robertx22.age_of_exile.database.data.affixes.Affix;
import com.robertx22.age_of_exile.mmorpg.ModRegistry;
import com.robertx22.age_of_exile.uncommon.utilityclasses.RandomUtils;
import net.minecraft.entity.EntityType;
import net.minecraft.world.biome.Biome;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class AreaModifiers {

    public static AreaModifiers INSTANCE;

    public Map<String, AreaModifier> MAP = new HashMap<>();

    public AreaModifier PLAIN = of("plain", 0, Affix.Type.prefix, "Plain",
        Arrays.asList(),
        x -> true);

    public AreaModifier CHILLING = of("chilling", 1000, Affix.Type.prefix, "Chilling",
        Arrays.asList(ModRegistry.ENTITIES.WATER_SLIME),
        x -> x.getTemperature() < 0.2F);

    public AreaModifier INFERNAL = of("infernal", 1000, Affix.Type.prefix, "Infernal",
        Arrays.asList(ModRegistry.ENTITIES.FIRE_SLIME),
        x -> x.getTemperature() > 0.8F);

    public AreaModifier POISONOUS = of("poisonous", 1000, Affix.Type.prefix, "Poisonous",
        Arrays.asList(ModRegistry.ENTITIES.NATURE_SLIME),
        x -> x.getCategory() == Biome.Category.SWAMP);

    public AreaModifier THUNDERING = of("thundering", 1000, Affix.Type.prefix, "Thundering",
        Arrays.asList(ModRegistry.ENTITIES.THUNDER_SLIME),
        x -> x.getCategory() == Biome.Category.EXTREME_HILLS);

    AreaModifier of(String id, int weight, Affix.Type affixType, String locName, List<EntityType> mobSpawns, Predicate<Biome> canUseBiome) {

        AreaModifier mod = new AreaModifier(id, weight, affixType, locName, mobSpawns, canUseBiome);

        MAP.put(id, mod);

        return mod;
    }

    public AreaModifier getRandomFor(Biome biome) {

        List<AreaModifier> list = MAP.values()
            .stream()
            .filter(x -> x.canUseBiome.test(biome))
            .collect(Collectors.toList());

        if (list.isEmpty()) {
            return null;
        }

        return RandomUtils.weightedRandom(list);

    }
}
