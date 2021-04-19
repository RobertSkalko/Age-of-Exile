package com.robertx22.age_of_exile.database.data.stats.datapacks.serializers;

import com.robertx22.age_of_exile.database.data.stats.datapacks.base.DatapackStat;
import com.robertx22.age_of_exile.database.data.stats.datapacks.base.IStatSerializer;
import com.robertx22.age_of_exile.database.data.stats.datapacks.stats.*;
import com.robertx22.age_of_exile.database.data.stats.datapacks.stats.spell_related.*;

import java.util.HashMap;

public class StatSerializers {

    public static StatSerializers INSTANCE = new StatSerializers();

    public HashMap<String, IStatSerializer<? extends DatapackStat>> map = new HashMap<>();

    private StatSerializers() {
        map.put(AddPerPercentOfOther.SER_ID, new OneAppliesToOtherSer());
        map.put(MoreXPerYOf.SER_ID, new MoreXPerYOfSer());
        map.put(ConvertFromOneToOtherStat.SER_ID, new TransferStatSer());
        map.put(MarkerStat.SER_ID, new MarkerSer());
        map.put(AttributeStat.SER_ID, new AttributeStatSer());

        map.put(PerSpellExtraProjectilesStat.SER_ID, new SpellStatSer<>(x -> new PerSpellExtraProjectilesStat(x)));
        map.put(PerSpellCooldownStat.SER_ID, new SpellStatSer<>(x -> new PerSpellCooldownStat(x)));
        map.put(PerSpellDamageStat.SER_ID, new SpellStatSer<>(x -> new PerSpellDamageStat(x)));
        map.put(PerSpellManaCostStat.SER_ID, new SpellStatSer<>(x -> new PerSpellManaCostStat(x)));
        map.put(PerSpellProjectileSpeedStat.SER_ID, new SpellStatSer<>(x -> new PerSpellProjectileSpeedStat(x)));
        map.put(PerSpellRestorationStat.SER_ID, new SpellStatSer<>(x -> new PerSpellRestorationStat(x)));

    }

}
