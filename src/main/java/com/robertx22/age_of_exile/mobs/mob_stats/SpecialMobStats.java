package com.robertx22.age_of_exile.mobs.mob_stats;

import com.robertx22.age_of_exile.capability.entity.EntityCap;
import com.robertx22.age_of_exile.database.data.StatModifier;
import com.robertx22.age_of_exile.database.data.stats.types.generated.ElementalResist;
import com.robertx22.age_of_exile.database.data.stats.types.generated.PhysConvertToEle;
import com.robertx22.age_of_exile.database.data.stats.types.misc.ExtraMobDropsStat;
import com.robertx22.age_of_exile.database.data.stats.types.resources.HealEffectivenessOnSelf;
import com.robertx22.age_of_exile.database.data.stats.types.resources.ManaBurn;
import com.robertx22.age_of_exile.saveclasses.gearitem.gear_bases.IApplyableStats;
import com.robertx22.age_of_exile.uncommon.enumclasses.Elements;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import static com.robertx22.age_of_exile.mmorpg.ModRegistry.ENTITIES;

public class SpecialMobStats {

    public List<Data> stats = new ArrayList<>();

    public static class Data implements IApplyableStats {

        public StatModifier mod;
        public boolean scaleToLevel = true;

        public Data(StatModifier mod, boolean scaleToLevel) {
            this.mod = mod;
            this.scaleToLevel = scaleToLevel;
        }

        public Data(StatModifier mod) {
            this.mod = mod;
        }

        @Override
        public void applyStats(EntityCap.UnitData data) {
            if (scaleToLevel) {
                mod.ToExactStat(100, data.getLevel())
                    .applyStats(data);
            } else {
                mod.ToExactStat(100, 1)
                    .applyStats(data);
            }
        }
    }

    public SpecialMobStats(Data... stats) {
        this.stats.addAll(Arrays.asList(stats));
    }

    private SpecialMobStats() {

    }

    public SpecialMobStats(SpecialMobStats... stats) {
        for (SpecialMobStats stat : stats) {
            this.stats.addAll(stat.stats);
        }
    }

    public static SpecialMobStats INSTANCE;

    public static SpecialMobStats EMPTY = new SpecialMobStats();

    public static SpecialMobStats FIRE = new SpecialMobStats(new Data(new StatModifier(50, 50, new PhysConvertToEle(Elements.Fire))), new Data(new StatModifier(10, 10, ExtraMobDropsStat.getInstance())));
    public static SpecialMobStats WATER = new SpecialMobStats(new Data(new StatModifier(50, 50, new PhysConvertToEle(Elements.Water))), new Data(new StatModifier(10, 10, ExtraMobDropsStat.getInstance())));
    public static SpecialMobStats NATURE = new SpecialMobStats(new Data(new StatModifier(50, 50, new PhysConvertToEle(Elements.Nature))), new Data(new StatModifier(10, 10, ExtraMobDropsStat.getInstance())));
    public static SpecialMobStats THUNDER = new SpecialMobStats(new Data(new StatModifier(50, 50, new PhysConvertToEle(Elements.Thunder))), new Data(new StatModifier(10, 10, ExtraMobDropsStat.getInstance())));

    public static SpecialMobStats FIRE_RES = new SpecialMobStats(new Data(new StatModifier(50, 50, new ElementalResist(Elements.Fire)), false), new Data(new StatModifier(10, 10, ExtraMobDropsStat.getInstance())));
    public static SpecialMobStats WATER_RES = new SpecialMobStats(new Data(new StatModifier(50, 50, new ElementalResist(Elements.Water)), false), new Data(new StatModifier(10, 10, ExtraMobDropsStat.getInstance())));
    public static SpecialMobStats NATURE_RES = new SpecialMobStats(new Data(new StatModifier(50, 50, new ElementalResist(Elements.Nature)), false), new Data(new StatModifier(10, 10, ExtraMobDropsStat.getInstance())));
    public static SpecialMobStats THUNDER_RES = new SpecialMobStats(new Data(new StatModifier(50, 50, new ElementalResist(Elements.Thunder)), false), new Data(new StatModifier(10, 10, ExtraMobDropsStat.getInstance())));

    public static SpecialMobStats LESS_FIRE_RES = new SpecialMobStats(new Data(new StatModifier(-25, -25, new ElementalResist(Elements.Fire)), false), new Data(new StatModifier(10, 10, ExtraMobDropsStat.getInstance())));
    public static SpecialMobStats LESS_WATER_RES = new SpecialMobStats(new Data(new StatModifier(-25, -25, new ElementalResist(Elements.Water)), false), new Data(new StatModifier(10, 10, ExtraMobDropsStat.getInstance())));
    public static SpecialMobStats LESS_NATURE_RES = new SpecialMobStats(new Data(new StatModifier(-25, -25, new ElementalResist(Elements.Nature)), false), new Data(new StatModifier(10, 10, ExtraMobDropsStat.getInstance())));
    public static SpecialMobStats LESS_THUNDER_RES = new SpecialMobStats(new Data(new StatModifier(-25, -25, new ElementalResist(Elements.Thunder)), false), new Data(new StatModifier(10, 10, ExtraMobDropsStat.getInstance())));

    public static SpecialMobStats LESS_DROPS = new SpecialMobStats(new Data(new StatModifier(-15, -15, ExtraMobDropsStat.getInstance())));

    public static SpecialMobStats MANA_BURN = new SpecialMobStats(new Data(new StatModifier(1, 1, ManaBurn.getInstance())), new Data(new StatModifier(15, 15, ExtraMobDropsStat.getInstance())));

    private HashMap<EntityType, SpecialMobStats> MAP = new HashMap<>();

    public static void init() {
        INSTANCE = new SpecialMobStats();
        INSTANCE.MAP.clear();

        reg(ENTITIES.FIRE_CHICKEN, fire());
        reg(ENTITIES.WATER_CHICKEN, water());
        reg(ENTITIES.NATURE_CHICKEN, nature());
        reg(ENTITIES.THUNDER_CHICKEN, thunder());

        reg(ENTITIES.FIRE_SLIME, fire());
        reg(ENTITIES.WATER_SLIME, water());
        reg(ENTITIES.NATURE_SLIME, nature());
        reg(ENTITIES.THUNDER_SLIME, thunder());
        INSTANCE.register(ENTITIES.ARCANE_SLIME, MANA_BURN);

        reg(ENTITIES.FIRE_SPIDER, fire());
        reg(ENTITIES.WATER_SPIDER, water());
        reg(ENTITIES.NATURE_SPIDER, nature());
        reg(ENTITIES.THUNDER_SPIDER, thunder());

        INSTANCE.register(ENTITIES.ARCANE_SPIDER, MANA_BURN);

        reg(ENTITIES.FIRE_MAGE, fire());
        reg(ENTITIES.WATER_MAGE, water());
        reg(ENTITIES.NATURE_MAGE, nature());
        reg(ENTITIES.THUNDER_MAGE, thunder());

        reg(ENTITIES.FIRE_SKELETON, fire());
        reg(ENTITIES.WATER_SKELETON, water());
        reg(ENTITIES.NATURE_SKELETON, nature());
        reg(ENTITIES.THUNDER_SKELETON, thunder());

        INSTANCE.register(ENTITIES.HEALER_MAGE, new SpecialMobStats(new Data(new StatModifier(-90, -90, HealEffectivenessOnSelf.getInstance()))));

        reg(ENTITIES.FIRE_ZOMBIE, new SpecialMobStats(fire(), LESS_DROPS));
        reg(ENTITIES.WATER_ZOMBIE, new SpecialMobStats(water(), LESS_DROPS));
        reg(ENTITIES.NATURE_ZOMBIE, new SpecialMobStats(nature(), LESS_DROPS));
        reg(ENTITIES.THUNDER_ZOMBIE, new SpecialMobStats(thunder(), LESS_DROPS));
        INSTANCE.register(ENTITIES.ARCANE_ZOMBIE, new SpecialMobStats(MANA_BURN, LESS_DROPS));

    }

    private static void reg(EntityType type, SpecialMobStats stats) {
        INSTANCE.register(type, stats);
    }

    private static SpecialMobStats fire() {
        return new SpecialMobStats(FIRE, FIRE_RES, LESS_WATER_RES);
    }

    private static SpecialMobStats water() {
        return new SpecialMobStats(WATER, WATER_RES, LESS_FIRE_RES);
    }

    private static SpecialMobStats nature() {
        return new SpecialMobStats(NATURE, NATURE_RES, LESS_THUNDER_RES);
    }

    private static SpecialMobStats thunder() {
        return new SpecialMobStats(THUNDER, THUNDER_RES, LESS_NATURE_RES);
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
