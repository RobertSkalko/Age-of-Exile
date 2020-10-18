package com.robertx22.age_of_exile.database.data;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.robertx22.age_of_exile.aoe_data.datapacks.bases.ISerializable;
import com.robertx22.age_of_exile.aoe_data.datapacks.bases.ISerializedRegistryEntry;
import com.robertx22.age_of_exile.capability.entity.EntityCap;
import com.robertx22.age_of_exile.database.data.stats.types.generated.ElementalResist;
import com.robertx22.age_of_exile.database.data.stats.types.generated.PhysConvertToEle;
import com.robertx22.age_of_exile.database.data.stats.types.misc.ExtraMobDropsStat;
import com.robertx22.age_of_exile.database.data.stats.types.resources.mana.ManaBurn;
import com.robertx22.age_of_exile.database.registry.SlashRegistry;
import com.robertx22.age_of_exile.database.registry.SlashRegistryType;
import com.robertx22.age_of_exile.saveclasses.gearitem.gear_bases.IApplyableStats;
import com.robertx22.age_of_exile.saveclasses.gearitem.gear_bases.Rarity;
import com.robertx22.age_of_exile.uncommon.enumclasses.Elements;
import com.robertx22.age_of_exile.uncommon.utilityclasses.EntityTypeUtils;
import net.minecraft.entity.EntityType;
import net.minecraft.util.registry.Registry;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

public class EntityConfig implements ISerializedRegistryEntry<EntityConfig>, ISerializable<EntityConfig> {

    public EntityConfig() {

    }

    public EntityConfig(String id, float loot) {
        this.identifier = id;
        this.loot_multi = loot;
        this.exp_multi = loot;
    }

    public EntityConfig(EntityType type, float loot) {
        this.identifier = Registry.ENTITY_TYPE.getId(type)
            .toString();
        this.loot_multi = loot;
        this.exp_multi = loot;
    }

    public static EntityConfig EMPTY = new EntityConfig();

    public String identifier = "";

    public SpecialMobStats stats = new SpecialMobStats();

    public double loot_multi = 1F;

    public double exp_multi = 1F;

    public int min_rarity = 0;

    public int max_rarity = 4;

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
    public SlashRegistryType getSlashRegistryType() {
        return SlashRegistryType.ENTITY_CONFIGS;
    }

    @Override
    public String GUID() {
        return identifier;
    }

    @Override
    public int Weight() {
        return 100;
    }

    @Override
    public int getRarityRank() {
        return 0;
    }

    @Override
    public Rarity getRarity() {
        return SlashRegistry.MobRarities()
            .get(getRarityRank());
    }

    public static class MobStatData implements IApplyableStats {

        public StatModifier mod;

        public boolean scaleToLevel = true;

        public MobStatData() {

        }

        public MobStatData(StatModifier mod, boolean scaleToLevel) {
            this.mod = mod;
            this.scaleToLevel = scaleToLevel;
        }

        public MobStatData(StatModifier mod) {
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

    public static class SpecialMobStats {

        public List<MobStatData> stats = new ArrayList<>();

        public SpecialMobStats(MobStatData... stats) {
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

    public static SpecialMobStats FIRE = new SpecialMobStats(new MobStatData(new StatModifier(50, 50, new PhysConvertToEle(Elements.Fire)), false), new MobStatData(new StatModifier(10, 10, ExtraMobDropsStat.getInstance()), false));
    public static SpecialMobStats WATER = new SpecialMobStats(new MobStatData(new StatModifier(50, 50, new PhysConvertToEle(Elements.Water)), false), new MobStatData(new StatModifier(10, 10, ExtraMobDropsStat.getInstance()), false));
    public static SpecialMobStats NATURE = new SpecialMobStats(new MobStatData(new StatModifier(50, 50, new PhysConvertToEle(Elements.Nature)), false), new MobStatData(new StatModifier(10, 10, ExtraMobDropsStat.getInstance()), false));
    public static SpecialMobStats THUNDER = new SpecialMobStats(new MobStatData(new StatModifier(50, 50, new PhysConvertToEle(Elements.Thunder)), false), new MobStatData(new StatModifier(10, 10, ExtraMobDropsStat.getInstance())));

    public static SpecialMobStats FIRE_RES = new SpecialMobStats(new MobStatData(new StatModifier(50, 50, new ElementalResist(Elements.Fire)), false), new MobStatData(new StatModifier(10, 10, ExtraMobDropsStat.getInstance()), false));
    public static SpecialMobStats WATER_RES = new SpecialMobStats(new MobStatData(new StatModifier(50, 50, new ElementalResist(Elements.Water)), false), new MobStatData(new StatModifier(10, 10, ExtraMobDropsStat.getInstance()), false));
    public static SpecialMobStats NATURE_RES = new SpecialMobStats(new MobStatData(new StatModifier(50, 50, new ElementalResist(Elements.Nature)), false), new MobStatData(new StatModifier(10, 10, ExtraMobDropsStat.getInstance()), false));
    public static SpecialMobStats THUNDER_RES = new SpecialMobStats(new MobStatData(new StatModifier(50, 50, new ElementalResist(Elements.Thunder)), false), new MobStatData(new StatModifier(10, 10, ExtraMobDropsStat.getInstance()), false));

    public static SpecialMobStats LESS_FIRE_RES = new SpecialMobStats(new MobStatData(new StatModifier(-25, -25, new ElementalResist(Elements.Fire)), false), new MobStatData(new StatModifier(10, 10, ExtraMobDropsStat.getInstance()), false));
    public static SpecialMobStats LESS_WATER_RES = new SpecialMobStats(new MobStatData(new StatModifier(-25, -25, new ElementalResist(Elements.Water)), false), new MobStatData(new StatModifier(10, 10, ExtraMobDropsStat.getInstance()), false));
    public static SpecialMobStats LESS_NATURE_RES = new SpecialMobStats(new MobStatData(new StatModifier(-25, -25, new ElementalResist(Elements.Nature)), false), new MobStatData(new StatModifier(10, 10, ExtraMobDropsStat.getInstance()), false));
    public static SpecialMobStats LESS_THUNDER_RES = new SpecialMobStats(new MobStatData(new StatModifier(-25, -25, new ElementalResist(Elements.Thunder)), false), new MobStatData(new StatModifier(10, 10, ExtraMobDropsStat.getInstance()), false));

    public static SpecialMobStats LESS_DROPS = new SpecialMobStats(new MobStatData(new StatModifier(-15, -15, ExtraMobDropsStat.getInstance()), false));

    public static SpecialMobStats MANA_BURN = new SpecialMobStats(new MobStatData(new StatModifier(1, 1, ManaBurn.getInstance())), new MobStatData(new StatModifier(15, 15, ExtraMobDropsStat.getInstance()), false));

    public static SpecialMobStats fire() {
        return new SpecialMobStats(FIRE, FIRE_RES, LESS_WATER_RES);
    }

    public static SpecialMobStats water() {
        return new SpecialMobStats(WATER, WATER_RES, LESS_FIRE_RES);
    }

    public static SpecialMobStats nature() {
        return new SpecialMobStats(NATURE, NATURE_RES, LESS_THUNDER_RES);
    }

    public static SpecialMobStats thunder() {
        return new SpecialMobStats(THUNDER, THUNDER_RES, LESS_NATURE_RES);
    }

}