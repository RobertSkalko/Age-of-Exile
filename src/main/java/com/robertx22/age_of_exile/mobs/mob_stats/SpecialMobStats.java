package com.robertx22.age_of_exile.mobs.mob_stats;

import com.robertx22.age_of_exile.database.data.StatModifier;
import com.robertx22.age_of_exile.database.data.stats.types.generated.WeaponDamage;
import com.robertx22.age_of_exile.database.data.stats.types.misc.ExtraMobDropsStat;
import com.robertx22.age_of_exile.database.data.stats.types.resources.HealEffectivenessOnSelf;
import com.robertx22.age_of_exile.database.data.stats.types.resources.ManaBurn;
import com.robertx22.age_of_exile.uncommon.enumclasses.Elements;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import static com.robertx22.age_of_exile.mmorpg.ModRegistry.ENTITIES;

public class SpecialMobStats {

    public List<StatModifier> stats = new ArrayList<>();

    public SpecialMobStats(StatModifier... stats) {
        this.stats.addAll(Arrays.asList(stats));
    }

    public SpecialMobStats add(SpecialMobStats other) {
        this.stats.addAll(other.stats);
        return this;
    }

    public static SpecialMobStats INSTANCE;

    public static SpecialMobStats EMPTY = new SpecialMobStats();

    public static SpecialMobStats FIRE = new SpecialMobStats(new StatModifier(1, 1, new WeaponDamage(Elements.Fire)), new StatModifier(10, 10, ExtraMobDropsStat.getInstance()));
    public static SpecialMobStats WATER = new SpecialMobStats(new StatModifier(1, 1, new WeaponDamage(Elements.Water)), new StatModifier(10, 10, ExtraMobDropsStat.getInstance()));
    public static SpecialMobStats NATURE = new SpecialMobStats(new StatModifier(1, 1, new WeaponDamage(Elements.Nature)), new StatModifier(10, 10, ExtraMobDropsStat.getInstance()));
    public static SpecialMobStats THUNDER = new SpecialMobStats(new StatModifier(1, 1, new WeaponDamage(Elements.Thunder)), new StatModifier(10, 10, ExtraMobDropsStat.getInstance()));

    public static SpecialMobStats MANA_BURN = new SpecialMobStats(new StatModifier(1, 1, ManaBurn.getInstance()), new StatModifier(15, 15, ExtraMobDropsStat.getInstance()));

    private HashMap<EntityType, SpecialMobStats> MAP = new HashMap<>();

    public static void init() {
        INSTANCE = new SpecialMobStats();
        INSTANCE.MAP.clear();

        INSTANCE.register(ENTITIES.FIRE_SLIME, FIRE);
        INSTANCE.register(ENTITIES.WATER_SLIME, WATER);
        INSTANCE.register(ENTITIES.NATURE_SLIME, NATURE);
        INSTANCE.register(ENTITIES.THUNDER_SLIME, THUNDER);
        INSTANCE.register(ENTITIES.ARCANE_SLIME, MANA_BURN);

        INSTANCE.register(ENTITIES.FIRE_SPIDER, FIRE);
        INSTANCE.register(ENTITIES.WATER_SPIDER, WATER);
        INSTANCE.register(ENTITIES.NATURE_SPIDER, NATURE);
        INSTANCE.register(ENTITIES.THUNDER_SPIDER, THUNDER);
        INSTANCE.register(ENTITIES.ARCANE_SPIDER, MANA_BURN);

        INSTANCE.register(ENTITIES.FIRE_CHICKEN, FIRE);
        INSTANCE.register(ENTITIES.WATER_CHICKEN, WATER);
        INSTANCE.register(ENTITIES.NATURE_CHICKEN, NATURE);
        INSTANCE.register(ENTITIES.THUNDER_CHICKEN, THUNDER);

        INSTANCE.register(ENTITIES.HEALER_MAGE, new SpecialMobStats(new StatModifier(-90, -90, HealEffectivenessOnSelf.getInstance())));

        INSTANCE.register(ENTITIES.FIRE_ZOMBIE, new SpecialMobStats(new StatModifier(-15, -15, ExtraMobDropsStat.getInstance())).add(FIRE));
        INSTANCE.register(ENTITIES.WATER_ZOMBIE, new SpecialMobStats(new StatModifier(-15, -15, ExtraMobDropsStat.getInstance())).add(WATER));
        INSTANCE.register(ENTITIES.NATURE_ZOMBIE, new SpecialMobStats(new StatModifier(-15, -15, ExtraMobDropsStat.getInstance())).add(NATURE));
        INSTANCE.register(ENTITIES.THUNDER_ZOMBIE, new SpecialMobStats(new StatModifier(-15, -15, ExtraMobDropsStat.getInstance())).add(THUNDER));
        INSTANCE.register(ENTITIES.ARCANE_ZOMBIE, new SpecialMobStats(new StatModifier(-15, -15, ExtraMobDropsStat.getInstance())).add(MANA_BURN));

    }

    public void register(EntityType type, SpecialMobStats stats) {
        MAP.put(type, stats);
    }

    public static SpecialMobStats getStatsFor(Entity entity) {

        EntityType<?> type = entity.getType();

        if (INSTANCE.MAP.containsKey(type)) {
            return INSTANCE.MAP.get(type);
        }
        return INSTANCE.EMPTY;
    }
}
