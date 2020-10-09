package com.robertx22.age_of_exile.areas.area_modifiers;

import com.robertx22.age_of_exile.areas.area_modifiers.AreaRequirement.WhitelistType;
import com.robertx22.age_of_exile.database.data.StatModifier;
import com.robertx22.age_of_exile.database.data.affixes.Affix;
import com.robertx22.age_of_exile.database.data.stats.types.generated.AttackDamage;
import com.robertx22.age_of_exile.database.data.stats.types.misc.ExtraMobDropsStat;
import com.robertx22.age_of_exile.database.data.stats.types.resources.LifeOnHit;
import com.robertx22.age_of_exile.uncommon.enumclasses.Elements;
import com.robertx22.age_of_exile.uncommon.enumclasses.ModType;
import com.robertx22.age_of_exile.uncommon.utilityclasses.RandomUtils;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.Biome.Category;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.robertx22.age_of_exile.mmorpg.ModRegistry.ENTITIES;

public class AreaModifiers {

    public static AreaModifiers INSTANCE;

    public Map<String, AreaModifier> MAP = new HashMap<>();

    public AreaModifier PLAIN = of("plain", 0, Affix.Type.prefix, "",
        Arrays.asList(),
        new AreaRequirement().whitelistAll());

    public AreaModifier CHILLING = of("chilling", 1000, Affix.Type.prefix, "Chilling",
        Arrays.asList(ENTITIES.WATER_SKELETON, ENTITIES.WATER_CHICKEN, ENTITIES.WATER_SLIME, ENTITIES.WATER_SPIDER, ENTITIES.WATER_ZOMBIE, ENTITIES.WATER_MAGE),
        new AreaRequirement().temp(0, 0.3F, WhitelistType.ALLOW_ALSO)
            .whitelist(Category.ICY, Category.TAIGA))
        .addStats(
            new StatModifier(1, 1, new AttackDamage(Elements.Water), ModType.FLAT),
            new StatModifier(10, 10, ExtraMobDropsStat.getInstance(), ModType.FLAT)
        );

    public AreaModifier INFERNAL = of("infernal", 1000, Affix.Type.prefix, "Infernal",
        Arrays.asList(ENTITIES.FIRE_SKELETON, ENTITIES.FIRE_CHICKEN, ENTITIES.FIRE_SLIME, ENTITIES.FIRE_SPIDER, ENTITIES.FIRE_ZOMBIE, ENTITIES.FIRE_MAGE),
        new AreaRequirement().temp(0.8F, Integer.MAX_VALUE, WhitelistType.ALLOW_ALSO)
            .whitelist(Category.DESERT, Category.MESA)
            .blacklist(Category.JUNGLE)).addStats(
        new StatModifier(1, 1, new AttackDamage(Elements.Fire), ModType.FLAT),
        new StatModifier(10, 10, ExtraMobDropsStat.getInstance(), ModType.FLAT)
    );

    public AreaModifier POISONOUS = of("poisonous", 1000, Affix.Type.prefix, "Poisonous",
        Arrays.asList(ENTITIES.NATURE_SKELETON, ENTITIES.NATURE_CHICKEN, ENTITIES.NATURE_SLIME, ENTITIES.NATURE_SPIDER, ENTITIES.NATURE_ZOMBIE, ENTITIES.NATURE_MAGE),
        new AreaRequirement().whitelist(Category.SWAMP, Category.JUNGLE))
        .addStats(
            new StatModifier(1, 1, new AttackDamage(Elements.Nature), ModType.FLAT),
            new StatModifier(10, 10, ExtraMobDropsStat.getInstance(), ModType.FLAT)
        );

    public AreaModifier THUNDERING = of("thundering", 1000, Affix.Type.prefix, "Thundering",
        Arrays.asList(ENTITIES.THUNDER_SKELETON, ENTITIES.THUNDER_CHICKEN, ENTITIES.THUNDER_SLIME, ENTITIES.THUNDER_SPIDER, ENTITIES.THUNDER_ZOMBIE, ENTITIES.THUNDER_MAGE),
        new AreaRequirement().whitelist(Category.EXTREME_HILLS))
        .addStats(
            new StatModifier(1, 1, new AttackDamage(Elements.Thunder), ModType.FLAT),
            new StatModifier(10, 10, ExtraMobDropsStat.getInstance(), ModType.FLAT)
        );
    public AreaModifier OF_SLIMES = of("of_slimes", 500, Affix.Type.suffix, "Of Slimes",
        Arrays.asList(ENTITIES.THUNDER_SLIME, ENTITIES.WATER_SLIME, ENTITIES.NATURE_SLIME),
        new AreaRequirement().whitelist(Category.BEACH));

    public AreaModifier DESOLATE = of("desolate", 1000, Affix.Type.prefix, "Desolate",
        Arrays.asList(),
        new AreaRequirement().whitelist(Category.BEACH));

    public AreaModifier ARCANE = of("arcane", 500, Affix.Type.prefix, "Arcane",
        Arrays.asList(ENTITIES.ARCANE_SPIDER, ENTITIES.ARCANE_SLIME, ENTITIES.ARCANE_ZOMBIE),
        new AreaRequirement().whitelist(Category.FOREST, Category.MUSHROOM));

    public AreaModifier APOCALYPTIC = of("apocalyptic", 500, Affix.Type.prefix, "Apocalyptic",
        Arrays.asList(ENTITIES.FIRE_SKELETON, ENTITIES.WATER_SKELETON, ENTITIES.THUNDER_SKELETON, ENTITIES.NATURE_SKELETON, ENTITIES.HEALER_MAGE, ENTITIES.FIRE_ZOMBIE, ENTITIES.ARCANE_ZOMBIE, ENTITIES.THUNDER_ZOMBIE, ENTITIES.WATER_ZOMBIE, ENTITIES.NATURE_ZOMBIE),
        new AreaRequirement().whitelist(Category.PLAINS, Category.NETHER, Category.MUSHROOM))
        .addStats(
            new StatModifier(-15, -15, ExtraMobDropsStat.getInstance(), ModType.FLAT)
        );

    public AreaModifier SPIDERS = of("spider_den", 750, Affix.Type.prefix, "Spider Den",
        Arrays.asList(ENTITIES.ARCANE_SPIDER, ENTITIES.THUNDER_SPIDER, ENTITIES.NATURE_SPIDER, ENTITIES.FIRE_SPIDER, ENTITIES.WATER_SPIDER),
        new AreaRequirement().whitelist(Category.JUNGLE, Category.FOREST, Category.NETHER)
            .blacklist(Category.ICY, Category.PLAINS, Category.MESA, Category.RIVER));

    public AreaModifier OF_HERETICS = of("of_heretics", 500, Affix.Type.suffix, "Of Heretics",
        Arrays.asList(ENTITIES.HEALER_MAGE, ENTITIES.THUNDER_MAGE, ENTITIES.NATURE_MAGE, ENTITIES.FIRE_MAGE, ENTITIES.WATER_MAGE),
        new AreaRequirement().whitelist(Category.JUNGLE, Category.FOREST, Category.NETHER)
            .blacklist(Category.ICY, Category.PLAINS, Category.MESA, Category.RIVER));

    public AreaModifier OF_VAMPIRISM = of("of_vampirism", 500, Affix.Type.suffix, "Of Vampirism",
        Arrays.asList(), new AreaRequirement())
        .addStats(
            new StatModifier(1F, 1F, LifeOnHit.getInstance(), ModType.FLAT),
            new StatModifier(15, 15, ExtraMobDropsStat.getInstance(), ModType.FLAT)
        )
        .addEffects(new StatusEffectInstance(StatusEffects.SPEED, Integer.MAX_VALUE, 1));

    public AreaModifier OF_AMBUSHES = of("of_ambushes", 500, Affix.Type.suffix, "Of Ambushes",
        Arrays.asList(ENTITIES.FIRE_CHICKEN, ENTITIES.NATURE_CHICKEN, ENTITIES.WATER_CHICKEN, ENTITIES.THUNDER_CHICKEN), new AreaRequirement().whitelist(Category.FOREST, Category.JUNGLE))
        .addStats(
            new StatModifier(15, 15, ExtraMobDropsStat.getInstance(), ModType.FLAT)
        )
        .addEffects(new StatusEffectInstance(StatusEffects.SPEED, Integer.MAX_VALUE, 2));

    AreaModifier of(String id, int weight, Affix.Type affixType, String locName, List<EntityType> mobSpawns, AreaRequirement canUseBiome) {

        AreaModifier mod = new AreaModifier(id, weight, affixType, locName, mobSpawns, canUseBiome);

        MAP.put(id, mod);

        return mod;
    }

    public AreaModifier getRandomFor(Biome biome) {

        List<AreaModifier> list = MAP.values()
            .stream()
            .filter(x -> x.requirement.isBiomeAcceptable(biome))
            .collect(Collectors.toList());

        if (list.isEmpty()) {
            return null;
        }

        return RandomUtils.weightedRandom(list);

    }
}
