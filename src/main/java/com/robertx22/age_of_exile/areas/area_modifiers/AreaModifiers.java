package com.robertx22.age_of_exile.areas.area_modifiers;

import com.robertx22.age_of_exile.database.data.StatModifier;
import com.robertx22.age_of_exile.database.data.affixes.Affix;
import com.robertx22.age_of_exile.database.data.stats.types.generated.WeaponDamage;
import com.robertx22.age_of_exile.database.data.stats.types.misc.ExtraMobDropsStat;
import com.robertx22.age_of_exile.database.data.stats.types.resources.LifeOnHit;
import com.robertx22.age_of_exile.uncommon.enumclasses.Elements;
import com.robertx22.age_of_exile.uncommon.enumclasses.ModType;
import com.robertx22.age_of_exile.uncommon.utilityclasses.RandomUtils;
import net.minecraft.entity.EntityType;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.Biome.Category;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import static com.robertx22.age_of_exile.mmorpg.ModRegistry.ENTITIES;

public class AreaModifiers {

    public static AreaModifiers INSTANCE;

    public Map<String, AreaModifier> MAP = new HashMap<>();

    public AreaModifier PLAIN = of("plain", 0, Affix.Type.prefix, "",
        Arrays.asList(),
        x -> true);

    public AreaModifier CHILLING = of("chilling", 1000, Affix.Type.prefix, "Chilling",
        Arrays.asList(ENTITIES.WATER_SLIME, ENTITIES.WATER_SPIDER, ENTITIES.WATER_ZOMBIE),
        x -> x.getTemperature() < 0.2F || x.getCategory() == Category.ICY || x.getCategory() == Category.TAIGA)
        .addStats(
            new StatModifier(1, 1, new WeaponDamage(Elements.Water), ModType.FLAT),
            new StatModifier(10, 10, ExtraMobDropsStat.getInstance(), ModType.FLAT)
        );

    public AreaModifier INFERNAL = of("infernal", 1000, Affix.Type.prefix, "Infernal",
        Arrays.asList(ENTITIES.FIRE_SLIME, ENTITIES.FIRE_SPIDER, ENTITIES.FIRE_ZOMBIE),
        x -> x.getTemperature() > 1.2F && x.getCategory() != Category.JUNGLE)
        .addStats(
            new StatModifier(1, 1, new WeaponDamage(Elements.Fire), ModType.FLAT),
            new StatModifier(10, 10, ExtraMobDropsStat.getInstance(), ModType.FLAT)
        );
    public AreaModifier POISONOUS = of("poisonous", 1000, Affix.Type.prefix, "Poisonous",
        Arrays.asList(ENTITIES.NATURE_SLIME, ENTITIES.NATURE_SPIDER),
        x -> x.getCategory() == Category.SWAMP || x.getCategory() == Category.JUNGLE)
        .addStats(
            new StatModifier(1, 1, new WeaponDamage(Elements.Nature), ModType.FLAT),
            new StatModifier(10, 10, ExtraMobDropsStat.getInstance(), ModType.FLAT)
        );

    public AreaModifier THUNDERING = of("thundering", 1000, Affix.Type.prefix, "Thundering",
        Arrays.asList(ENTITIES.THUNDER_SLIME, ENTITIES.THUNDER_SPIDER, ENTITIES.THUNDER_ZOMBIE),
        x -> x.getCategory() == Category.EXTREME_HILLS)
        .addStats(
            new StatModifier(1, 1, new WeaponDamage(Elements.Thunder), ModType.FLAT),
            new StatModifier(10, 10, ExtraMobDropsStat.getInstance(), ModType.FLAT)
        );
    public AreaModifier OF_SLIMES = of("of_slimes", 500, Affix.Type.suffix, "Of Slimes",
        Arrays.asList(ENTITIES.THUNDER_SLIME, ENTITIES.WATER_SLIME, ENTITIES.NATURE_SLIME),
        x -> x.getCategory() == Category.BEACH);

    public AreaModifier DESOLATE = of("desolate", 1000, Affix.Type.prefix, "Desolate",
        Arrays.asList(),
        x -> x.getCategory() == Category.BEACH);

    public AreaModifier ARCANE = of("arcane", 1000, Affix.Type.prefix, "Arcane",
        Arrays.asList(ENTITIES.ARCANE_SPIDER, ENTITIES.ARCANE_SLIME, ENTITIES.ARCANE_ZOMBIE),
        x -> x.getCategory() == Category.FOREST || x.getCategory() == Category.MUSHROOM);

    public AreaModifier APOCALYPTIC = of("apocalyptic", 500, Affix.Type.prefix, "Apocalyptic",
        Arrays.asList(ENTITIES.FIRE_ZOMBIE, ENTITIES.ARCANE_ZOMBIE, ENTITIES.THUNDER_ZOMBIE, ENTITIES.WATER_ZOMBIE, ENTITIES.NATURE_ZOMBIE),
        x -> x.getCategory() == Category.PLAINS || x.getCategory() == Category.NETHER || x.getCategory() == Category.FOREST || x.getCategory() == Category.MUSHROOM);

    public AreaModifier SPIDERS = of("spider_den", 750, Affix.Type.prefix, "Spider Den",
        Arrays.asList(ENTITIES.ARCANE_SPIDER, ENTITIES.THUNDER_SPIDER, ENTITIES.NATURE_SPIDER, ENTITIES.FIRE_SPIDER, ENTITIES.WATER_SPIDER),
        x -> x.getCategory() != Category.ICY && x.getCategory() != Category.PLAINS && x.getCategory() != Category.MESA && x.getCategory() != Category.RIVER);

    public AreaModifier OF_VAMPIRISM = of("of_vampirism", 500, Affix.Type.suffix, "Of Vampirism",
        Arrays.asList(),
        x -> x.getCategory() != Category.OCEAN)
        .addStats(
            new StatModifier(1.5F, 1.5F, LifeOnHit.getInstance(), ModType.FLAT),
            new StatModifier(15, 15, ExtraMobDropsStat.getInstance(), ModType.FLAT)
        );

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
