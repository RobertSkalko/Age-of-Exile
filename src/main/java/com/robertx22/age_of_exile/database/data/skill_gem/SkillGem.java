package com.robertx22.age_of_exile.database.data.skill_gem;

import com.robertx22.age_of_exile.database.data.perks.StatAttribute;
import com.robertx22.age_of_exile.database.data.spells.components.Spell;
import com.robertx22.age_of_exile.database.registry.ExileDB;
import com.robertx22.age_of_exile.database.registry.ExileRegistryTypes;
import com.robertx22.age_of_exile.mmorpg.Ref;
import com.robertx22.age_of_exile.uncommon.interfaces.IAutoLocName;
import com.robertx22.library_of_exile.registry.ExileRegistryType;
import com.robertx22.library_of_exile.registry.IAutoGson;
import com.robertx22.library_of_exile.registry.JsonExileRegistry;

import java.util.ArrayList;
import java.util.List;

public class SkillGem implements JsonExileRegistry<SkillGem>, IAutoGson<SkillGem>, IAutoLocName {
    public static SkillGem SERIALIZER = new SkillGem();

    public String identifier = "";

    public float mana_multi = 1;

    public String spell_id = "";
    public int weight = 1000;

    public SkillGemType type = SkillGemType.SKILL_GEM;

    public StatAttribute attribute = StatAttribute.DEX;

    public List<SupportGemTags> tags = new ArrayList<>();

    public transient String locname = "";

    public boolean isSpell() {
        return ExileDB.Spells()
            .isRegistered(spell_id);
    }

    public Spell getSpell() {
        return ExileDB.Spells()
            .get(spell_id);
    }

    @Override
    public int Weight() {
        return weight;
    }

    @Override
    public Class<SkillGem> getClassForSerialization() {
        return SkillGem.class;
    }

    @Override
    public ExileRegistryType getExileRegistryType() {
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
