package com.robertx22.age_of_exile.database.data.skill_gem;

import com.robertx22.age_of_exile.aoe_data.datapacks.bases.ISerializedRegistryEntry;
import com.robertx22.age_of_exile.database.data.IAutoGson;
import com.robertx22.age_of_exile.database.data.StatModifier;
import com.robertx22.age_of_exile.database.registry.SlashRegistryType;
import com.robertx22.age_of_exile.saveclasses.ExactStatData;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class SpellGem implements ISerializedRegistryEntry<SpellGem>, IAutoGson<SpellGem> {
    public static SpellGem SERIALIZER = new SpellGem();

    public String identifier = "";

    public float mana_multi = 1;

    public List<StatModifier> stats = new ArrayList<>();
    
    public transient String locname = "";

    public List<ExactStatData> getStats(SpellGemData data) {
        return stats.stream()
            .map(x -> x.ToExactStat(data.stat_perc, data.lvl))
            .collect(Collectors.toList());
    }

    @Override
    public Class<SpellGem> getClassForSerialization() {
        return SpellGem.class;
    }

    @Override
    public SlashRegistryType getSlashRegistryType() {
        return SlashRegistryType.SPELL_GEM;
    }

    @Override
    public String GUID() {
        return identifier;
    }
}
