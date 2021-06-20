package com.robertx22.age_of_exile.database.data.base_stats;

import com.robertx22.age_of_exile.aoe_data.datapacks.bases.JsonExileRegistry;
import com.robertx22.age_of_exile.database.OptScaleExactStat;
import com.robertx22.age_of_exile.database.data.IAutoGson;
import com.robertx22.age_of_exile.database.data.stats.Stat;
import com.robertx22.age_of_exile.database.registry.ExileRegistryTypes;
import com.robertx22.age_of_exile.saveclasses.ExactStatData;
import com.robertx22.age_of_exile.saveclasses.gearitem.gear_bases.IApplyableStats;
import com.robertx22.age_of_exile.saveclasses.unit.stat_ctx.SimpleStatCtx;
import com.robertx22.age_of_exile.saveclasses.unit.stat_ctx.StatContext;
import com.robertx22.age_of_exile.uncommon.datasaving.Load;
import com.robertx22.age_of_exile.uncommon.enumclasses.ModType;
import net.minecraft.entity.LivingEntity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class BaseStatsConfig implements JsonExileRegistry<BaseStatsConfig>, IAutoGson<BaseStatsConfig>, IApplyableStats {
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
    public List<StatContext> getStatAndContext(LivingEntity en) {
        List<ExactStatData> stats = new ArrayList<>();
        base_stats.forEach(x -> stats.add(x.toExactStat(Load.Unit(en)
            .getLevel())));
        return Arrays.asList(new SimpleStatCtx(StatContext.StatCtxType.BASE_STAT, stats));
    }

    @Override
    public int Weight() {
        return 1000;
    }

    @Override
    public Class<BaseStatsConfig> getClassForSerialization() {
        return BaseStatsConfig.class;
    }

    @Override
    public ExileRegistryTypes getExileRegistryType() {
        return ExileRegistryTypes.BASE_STATS;
    }

    @Override
    public String GUID() {
        return id;
    }
}
