package com.robertx22.age_of_exile.database.data.random_skill_gem_stats;

import com.robertx22.age_of_exile.database.data.StatModifier;
import com.robertx22.age_of_exile.database.data.skill_gem.SupportGemTags;
import com.robertx22.age_of_exile.database.registry.ExileRegistryTypes;
import com.robertx22.library_of_exile.registry.ExileRegistryType;
import com.robertx22.library_of_exile.registry.IAutoGson;
import com.robertx22.library_of_exile.registry.JsonExileRegistry;

import java.util.ArrayList;
import java.util.List;

public class RandomSkillGemStats implements JsonExileRegistry<RandomSkillGemStats>, IAutoGson<RandomSkillGemStats> {

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
    public ExileRegistryType getExileRegistryType() {
        return ExileRegistryTypes.RANDOM_SKILL_GEM_STATS;
    }

    @Override
    public Class<RandomSkillGemStats> getClassForSerialization() {
        return RandomSkillGemStats.class;
    }

    @Override
    public String GUID() {
        return id;
    }

    @Override
    public int Weight() {
        return weight;
    }
}
