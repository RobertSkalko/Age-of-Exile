package com.robertx22.age_of_exile.database.data.stats.datapacks.serializers;

import com.robertx22.age_of_exile.database.data.stats.datapacks.base.DatapackStat;
import com.robertx22.age_of_exile.database.data.stats.datapacks.base.IStatSerializer;
import com.robertx22.age_of_exile.database.data.stats.datapacks.stats.ExtraSpellProjectilesStat;
import com.robertx22.age_of_exile.database.data.stats.datapacks.stats.GiveSpellStat;
import com.robertx22.age_of_exile.database.data.stats.datapacks.stats.OneAppliesToOtherStat;

import java.util.HashMap;

public class StatSerializers {

    public static StatSerializers INSTANCE = new StatSerializers();

    public HashMap<String, IStatSerializer<? extends DatapackStat>> map = new HashMap<>();

    private StatSerializers() {
        map.put(GiveSpellStat.SER_ID, new SpellStatSer());
        map.put(OneAppliesToOtherStat.SER_ID, new OneAppliesToOtherSer());
        map.put(ExtraSpellProjectilesStat.SER_ID, new ExtraProjectileSer());
    }

}
