package com.robertx22.age_of_exile.database.data.stats.datapacks.serializers;

import com.robertx22.age_of_exile.database.data.stats.datapacks.base.DatapackStat;
import com.robertx22.age_of_exile.database.data.stats.datapacks.base.IStatSerializer;
import com.robertx22.age_of_exile.database.data.stats.datapacks.stats.GiveSpellStat;

import java.util.HashMap;

public class StatSerializers {

    public static StatSerializers INSTANCE = new StatSerializers();

    public HashMap<String, IStatSerializer<? extends DatapackStat>> map = new HashMap<>();

    private StatSerializers() {
        map.put(GiveSpellStat.SER_ID, new SpellStatSer());
    }

}
