package com.robertx22.age_of_exile.database.data.scroll_buff;

import com.robertx22.age_of_exile.aoe_data.datapacks.bases.ISerializedRegistryEntry;
import com.robertx22.age_of_exile.database.data.IAutoGson;
import com.robertx22.age_of_exile.database.data.StatModifier;
import com.robertx22.age_of_exile.database.registry.SlashRegistryType;
import com.robertx22.age_of_exile.uncommon.interfaces.IAutoLocName;
import com.robertx22.age_of_exile.uncommon.interfaces.IWeighted;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ScrollBuff implements ISerializedRegistryEntry<ScrollBuff>, IAutoGson<ScrollBuff>, IAutoLocName, IWeighted {
    public static ScrollBuff SERIALIZER = new ScrollBuff();

    public String id = "";
    public transient String locname = "";
    public int weight = 1000;
    public List<StatModifier> stats = new ArrayList<>();

    public static ScrollBuff of(String id, String locname, StatModifier... stats) {
        ScrollBuff b = new ScrollBuff();
        b.id = id;
        b.locname = locname;
        b.stats.addAll(Arrays.asList(stats));
        b.addToSerializables();
        return b;
    }

    @Override
    public int Weight() {
        return weight;
    }

    @Override
    public Class<ScrollBuff> getClassForSerialization() {
        return ScrollBuff.class;
    }

    @Override
    public SlashRegistryType getSlashRegistryType() {
        return SlashRegistryType.SCROLL_BUFFS;
    }

    @Override
    public String GUID() {
        return id;
    }

    @Override
    public AutoLocGroup locNameGroup() {
        return AutoLocGroup.Misc;
    }

    @Override
    public String locNameLangFileGUID() {
        return getSlashRegistryType().id + "." + id;
    }

    @Override
    public String locNameForLangFile() {
        return locname;
    }
}
