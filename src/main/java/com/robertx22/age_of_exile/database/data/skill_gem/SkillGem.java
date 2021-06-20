package com.robertx22.age_of_exile.database.data.skill_gem;

import com.robertx22.age_of_exile.aoe_data.datapacks.bases.JsonExileRegistry;
import com.robertx22.age_of_exile.database.data.IAutoGson;
import com.robertx22.age_of_exile.database.data.StatModifier;
import com.robertx22.age_of_exile.database.data.perks.StatAttribute;
import com.robertx22.age_of_exile.database.data.spells.components.Spell;
import com.robertx22.age_of_exile.database.registry.Database;
import com.robertx22.age_of_exile.database.registry.ExileRegistryTypes;
import com.robertx22.age_of_exile.mmorpg.Ref;
import com.robertx22.age_of_exile.saveclasses.ExactStatData;
import com.robertx22.age_of_exile.saveclasses.gearitem.gear_bases.StatRequirement;
import com.robertx22.age_of_exile.uncommon.interfaces.IAutoLocName;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class SkillGem implements JsonExileRegistry<SkillGem>, IAutoGson<SkillGem>, IAutoLocName {
    public static SkillGem SERIALIZER = new SkillGem();

    public String identifier = "";

    public float mana_multi = 1;

    public String spell_id = "";
    public int weight = 1000;

    public SkillGemType type = SkillGemType.SKILL_GEM;

    public StatRequirement req = new StatRequirement();

    public StatAttribute attribute = StatAttribute.DEX;

    public List<StatModifier> stats = new ArrayList<>();

    public List<SupportGemTags> tags = new ArrayList<>();

    public transient String locname = "";

    public boolean isSpell() {
        return Database.Spells()
            .isRegistered(spell_id);
    }

    public Spell getSpell() {
        return Database.Spells()
            .get(spell_id);
    }

    @Override
    public int Weight() {
        return weight;
    }

    public List<ExactStatData> getConstantStats(SkillGemData data) {
        int perc = data.getRarity().stat_percents.max;
        return stats.stream()
            .map(x -> x.ToExactStat(perc, data.lvl))
            .collect(Collectors.toList());
    }

    public List<ExactStatData> getRandomStats(SkillGemData data) {
        return data.random_stats.stream()
            .map(x -> x.ToExactStat(data.stat_perc, data.lvl))
            .collect(Collectors.toList());
    }

    @Override
    public Class<SkillGem> getClassForSerialization() {
        return SkillGem.class;
    }

    @Override
    public ExileRegistryTypes getExileRegistryType() {
        return ExileRegistryTypes.SKILL_GEM;
    }

    @Override
    public String GUID() {
        return identifier;
    }

    @Override
    public AutoLocGroup locNameGroup() {
        return AutoLocGroup.Misc;
    }

    @Override
    public String locNameLangFileGUID() {
        return Ref.MODID + ".skill_gem." + GUID();
    }

    @Override
    public String locNameForLangFile() {
        return locname;
    }
}
