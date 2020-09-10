package com.robertx22.age_of_exile.database.data.spells.modifiers;

import com.robertx22.age_of_exile.database.data.IAutoGson;
import com.robertx22.age_of_exile.database.registry.SlashRegistryType;
import com.robertx22.age_of_exile.datapacks.bases.ISerializedRegistryEntry;

import java.util.ArrayList;
import java.util.List;

public class SpellModifier implements ISerializedRegistryEntry<SpellModifier>, IAutoGson<SpellModifier> {
    public static SpellModifier SERIALIZER = new SpellModifier();
    public String identifier;

    public List<SpellModStatData> mods = new ArrayList<>();

    public String for_spell;

    public transient String locName;
    public transient String iconForPerk;

    @Override
    public SlashRegistryType getSlashRegistryType() {
        return SlashRegistryType.SPELL_MODIFIER;
    }

    @Override
    public String GUID() {
        return identifier;
    }

    @Override
    public Class<SpellModifier> getClassForSerialization() {
        return SpellModifier.class;
    }
}


