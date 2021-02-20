package com.robertx22.age_of_exile.database.data.races;

import com.robertx22.age_of_exile.aoe_data.datapacks.bases.ISerializedRegistryEntry;
import com.robertx22.age_of_exile.database.OptScaleExactStat;
import com.robertx22.age_of_exile.database.data.IAutoGson;
import com.robertx22.age_of_exile.database.registry.SlashRegistryType;
import com.robertx22.age_of_exile.uncommon.interfaces.IAutoLocDesc;
import com.robertx22.age_of_exile.uncommon.interfaces.IAutoLocName;

import java.util.ArrayList;
import java.util.List;

public class PlayerRace implements ISerializedRegistryEntry<PlayerRace>, IAutoGson<PlayerRace>, IAutoLocName, IAutoLocDesc {
    public static PlayerRace SERIALIZER = new PlayerRace();

    public transient String locname = "";
    public transient String locdesc = "";

    public String id = "";

    public List<ExtraStatPerStat> extra_stats = new ArrayList<>();

    public List<OptScaleExactStat> base_stats = new ArrayList<>();

    public static PlayerRace of(String id, String name, String desc, List<ExtraStatPerStat> extraStats, List<OptScaleExactStat> baseStats) {

        PlayerRace r = new PlayerRace();
        r.locdesc = desc;
        r.locname = name;
        r.id = id;
        r.base_stats = baseStats;
        r.extra_stats = extraStats;
        return r;

    }

    @Override
    public Class<PlayerRace> getClassForSerialization() {
        return PlayerRace.class;
    }

    @Override
    public SlashRegistryType getSlashRegistryType() {
        return SlashRegistryType.RACES;
    }

    @Override
    public String GUID() {
        return id;
    }

    @Override
    public AutoLocGroup locDescGroup() {
        return AutoLocGroup.Races;
    }

    @Override
    public String locDescLangFileGUID() {
        return "races." + id;
    }

    @Override
    public String locDescForLangFile() {
        return locdesc;
    }

    @Override
    public AutoLocGroup locNameGroup() {
        return AutoLocGroup.Races;
    }

    @Override
    public String locNameLangFileGUID() {
        return "races." + id;
    }

    @Override
    public String locNameForLangFile() {
        return locname;
    }
}
