package com.robertx22.age_of_exile.database.data.base_stats;

import com.robertx22.age_of_exile.aoe_data.datapacks.bases.ISerializedRegistryEntry;
import com.robertx22.age_of_exile.capability.entity.EntityCap;
import com.robertx22.age_of_exile.database.OptScaleExactStat;
import com.robertx22.age_of_exile.database.data.IAutoGson;
import com.robertx22.age_of_exile.database.data.stats.Stat;
import com.robertx22.age_of_exile.database.registry.SlashRegistryType;
import com.robertx22.age_of_exile.saveclasses.gearitem.gear_bases.IApplyableStats;
import com.robertx22.age_of_exile.uncommon.enumclasses.ModType;

import java.util.ArrayList;
import java.util.List;

public class BaseStatsConfig implements ISerializedRegistryEntry<BaseStatsConfig>, IAutoGson<BaseStatsConfig>, IApplyableStats {
    public static BaseStatsConfig SERIALIZER = new BaseStatsConfig();

    public String id;
    List<OptScaleExactStat> base_stats = new ArrayList<>();

    public void scaled(Stat stat, float val) {
        base_stats.add(new OptScaleExactStat(val, val, stat, ModType.FLAT).scale());

    }

    public void nonScaled(Stat stat, float val) {
        base_stats.add(new OptScaleExactStat(val, val, stat, ModType.FLAT));
    }

    @Override
    public void applyStats(EntityCap.UnitData data) {
        base_stats.forEach(x -> x.applyStats(data));

    }

    @Override
    public Class<BaseStatsConfig> getClassForSerialization() {
        return BaseStatsConfig.class;
    }

    @Override
    public SlashRegistryType getSlashRegistryType() {
        return SlashRegistryType.BASE_STATS;
    }

    @Override
    public String GUID() {
        return id;
    }
}
