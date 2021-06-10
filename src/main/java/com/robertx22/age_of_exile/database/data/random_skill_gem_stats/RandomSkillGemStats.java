package com.robertx22.age_of_exile.database.data.random_skill_gem_stats;

import com.robertx22.age_of_exile.aoe_data.datapacks.bases.ISerializedRegistryEntry;
import com.robertx22.age_of_exile.database.data.IAutoGson;
import com.robertx22.age_of_exile.database.data.StatModifier;
import com.robertx22.age_of_exile.database.data.skill_gem.SupportGemTags;
import com.robertx22.age_of_exile.database.registry.SlashRegistryType;

import java.util.ArrayList;
import java.util.List;

public class RandomSkillGemStats implements ISerializedRegistryEntry<RandomSkillGemStats>, IAutoGson<RandomSkillGemStats> {

    public static RandomSkillGemStats SERIALIZER = new RandomSkillGemStats();

    public String id;
    public int weight;
    public List<StatModifier> stats = new ArrayList<>();

    public List<SupportGemTags> tags = new ArrayList<>();

    public RandomSkillGemStats() {

    }

    public RandomSkillGemStats(String id, int weight, List<StatModifier> stats, List<SupportGemTags> tags) {
        this.id = id;
        this.weight = weight;
        this.stats = stats;
        this.tags = tags;
    }

    @Override
    public SlashRegistryType getSlashRegistryType() {
        return SlashRegistryType.RANDOM_SKILL_GEM_STATS;
    }

    @Override
    public Class<RandomSkillGemStats> getClassForSerialization() {
        return RandomSkillGemStats.class;
    }

    @Override
    public String GUID() {
        return id;
    }
}
