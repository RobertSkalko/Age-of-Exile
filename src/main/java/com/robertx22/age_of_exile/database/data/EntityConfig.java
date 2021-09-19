package com.robertx22.age_of_exile.database.data;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.robertx22.age_of_exile.aoe_data.database.stats.Stats;
import com.robertx22.age_of_exile.database.OptScaleExactStat;
import com.robertx22.age_of_exile.database.data.stats.types.generated.ElementalResist;
import com.robertx22.age_of_exile.database.data.stats.types.generated.PhysConvertToEle;
import com.robertx22.age_of_exile.database.data.stats.types.misc.ExtraMobDropsStat;
import com.robertx22.age_of_exile.database.registry.ExileRegistryTypes;
import com.robertx22.age_of_exile.uncommon.enumclasses.Elements;
import com.robertx22.age_of_exile.uncommon.utilityclasses.EntityTypeUtils;
import com.robertx22.library_of_exile.registry.ExileRegistryType;
import com.robertx22.library_of_exile.registry.JsonExileRegistry;
import com.robertx22.library_of_exile.registry.serialization.ISerializable;
import net.minecraft.entity.EntityType;
import net.minecraft.util.registry.Registry;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

public class EntityConfig implements JsonExileRegistry<EntityConfig>, ISerializable<EntityConfig> {

    public EntityConfig() {

    }

    public EntityConfig(String id, float loot) {
        this.identifier = id;
        this.loot_multi = loot;
        this.exp_multi = loot;
    }

    public EntityConfig(EntityType type, float loot) {
        this.identifier = Registry.ENTITY_TYPE.getKey(type)
            .toString();
        this.loot_multi = loot;
        this.exp_multi = loot;
    }

    public String identifier = "";

    public SpecialMobStats stats = new SpecialMobStats();

    public double loot_multi = 1F;

    public double exp_multi = 1F;

    public int min_lvl = 1;

    public int max_lvl = 1000000;

    public double dmg_multi = 1;

    public double hp_multi = 1;

    public double stat_multi = 1;

    @Override
    public String datapackFolder() {
        try {
            if (EntityTypeUtils.EntityClassification.valueOf(identifier.toUpperCase(Locale.ROOT)) != null) {
                return "mob_types/";
            }
        } catch (IllegalArgumentException e) {
        }

        if (identifier.contains(":")) {
            return "specific_mobs/";
        } else {
            return "all_mobs_in_mod/";
        }
    }

    static Gson GSON = new Gson();

    @Override
    public JsonObject toJson() {
        return new JsonParser().parse(GSON.toJson(this))
            .getAsJsonObject();
    }

    @Override
    public EntityConfig fromJson(JsonObject json) {
        return GSON.fromJson(json, EntityConfig.class);
    }

    @Override
    public ExileRegistryType getExileRegistryType() {
        return ExileRegistryTypes.ENTITY_CONFIGS;
    }

    @Override
    public String GUID() {
        return identifier;
    }

    @Override
    public int Weight() {
        return 100;
    }

    public static class SpecialMobStats {

        public List<OptScaleExactStat> stats = new ArrayList<>();

        public SpecialMobStats(OptScaleExactStat... stats) {
            this.stats.addAll(Arrays.asList(stats));
        }

        public SpecialMobStats() {

        }

        public SpecialMobStats(SpecialMobStats... stats) {
            for (SpecialMobStats stat : stats) {
                this.stats.addAll(stat.stats);
            }
        }
    }

    public static SpecialMobStats FIRE = new SpecialMobStats(new OptScaleExactStat(50, new PhysConvertToEle(Elements.Fire)), new OptScaleExactStat(10, ExtraMobDropsStat.getInstance()));
    public static SpecialMobStats WATER = new SpecialMobStats(new OptScaleExactStat(50, new PhysConvertToEle(Elements.Water)), new OptScaleExactStat(10, ExtraMobDropsStat.getInstance()));
    public static SpecialMobStats NATURE = new SpecialMobStats(new OptScaleExactStat(50, new PhysConvertToEle(Elements.Earth)), new OptScaleExactStat(10, ExtraMobDropsStat.getInstance()));

    public static SpecialMobStats FIRE_RES = new SpecialMobStats(new OptScaleExactStat(50, new ElementalResist(Elements.Fire)), new OptScaleExactStat(10, ExtraMobDropsStat.getInstance()));
    public static SpecialMobStats WATER_RES = new SpecialMobStats(new OptScaleExactStat(50, new ElementalResist(Elements.Water)), new OptScaleExactStat(10, ExtraMobDropsStat.getInstance()));
    public static SpecialMobStats NATURE_RES = new SpecialMobStats(new OptScaleExactStat(50, new ElementalResist(Elements.Earth)), new OptScaleExactStat(10, ExtraMobDropsStat.getInstance()));

    public static SpecialMobStats LESS_FIRE_RES = new SpecialMobStats(new OptScaleExactStat(-25, new ElementalResist(Elements.Fire)), new OptScaleExactStat(10, ExtraMobDropsStat.getInstance()));
    public static SpecialMobStats LESS_WATER_RES = new SpecialMobStats(new OptScaleExactStat(-25, new ElementalResist(Elements.Water)), new OptScaleExactStat(10, ExtraMobDropsStat.getInstance()));
    public static SpecialMobStats LESS_NATURE_RES = new SpecialMobStats(new OptScaleExactStat(-25, new ElementalResist(Elements.Earth)), new OptScaleExactStat(10, ExtraMobDropsStat.getInstance()));

    public static SpecialMobStats LESS_DROPS = new SpecialMobStats(new OptScaleExactStat(-15, ExtraMobDropsStat.getInstance()));

    public static SpecialMobStats SLOWER_PROJ() {
        return new SpecialMobStats(new OptScaleExactStat(-30, Stats.PROJECTILE_SPEED.get()));
    }

    ;

    public static SpecialMobStats fire() {
        return new SpecialMobStats(FIRE, FIRE_RES, LESS_WATER_RES, SLOWER_PROJ());
    }

    public static SpecialMobStats water() {
        return new SpecialMobStats(WATER, WATER_RES, LESS_FIRE_RES, SLOWER_PROJ());
    }

    public static SpecialMobStats nature() {
        return new SpecialMobStats(NATURE, NATURE_RES, SLOWER_PROJ());
    }

}