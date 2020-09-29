package com.robertx22.age_of_exile.database.data.stats.datapacks.serializers;

import com.robertx22.age_of_exile.database.data.stats.datapacks.base.DatapackStat;
import com.robertx22.age_of_exile.database.data.stats.datapacks.base.IStatSerializer;
import com.robertx22.age_of_exile.database.data.stats.datapacks.stats.*;

import java.util.HashMap;

public class StatSerializers {

    public static StatSerializers INSTANCE = new StatSerializers();

    public HashMap<String, IStatSerializer<? extends DatapackStat>> map = new HashMap<>();

    private StatSerializers() {
        map.put(GiveSpellStat.SER_ID, new SpellStatSer());
        map.put(OneAppliesToOtherStat.SER_ID, new OneAppliesToOtherSer());
        map.put(SpecificSpellExtraProjectilesStat.SER_ID, new ExtraProjectileSer());

        map.put(SpecificSpellExtraProjectilesStat.SER_ID, new UnlockSpellStatSerializer<>(x -> new SpecificSpellExtraProjectilesStat(x)));
        map.put(SpecificSpellCooldownStat.SER_ID, new UnlockSpellStatSerializer<>(x -> new SpecificSpellCooldownStat(x)));
        map.put(SpecificSpellDamageStat.SER_ID, new UnlockSpellStatSerializer<>(x -> new SpecificSpellDamageStat(x)));
        map.put(SpecificSpellManaCostStat.SER_ID, new UnlockSpellStatSerializer<>(x -> new SpecificSpellManaCostStat(x)));

    }

}
