package com.robertx22.age_of_exile.aoe_data.database.perks;

import com.robertx22.age_of_exile.aoe_data.database.stats.old.DatapackStats;
import com.robertx22.age_of_exile.database.OptScaleExactStat;
import com.robertx22.age_of_exile.database.data.perks.Perk;
import com.robertx22.age_of_exile.uncommon.enumclasses.ModType;
import com.robertx22.library_of_exile.registry.ExileRegistryInit;

public class StartPerks implements ExileRegistryInit {

    public static String MAGE = "mage";
    public static String WARRIOR = "warrior";
    public static String HUNTER = "hunter";
    public static String TEMPLAR = "templar";
    public static String DUELIST = "duelist";
    public static String BATTLE_MAGE = "battle_mage";
    public static String SCION = "scion";

    @Override
    public void registerAll() {

        of(MAGE, "Mage",
            new OptScaleExactStat(30, DatapackStats.INT, ModType.FLAT),
            new OptScaleExactStat(15, DatapackStats.STR, ModType.FLAT),
            new OptScaleExactStat(15, DatapackStats.DEX, ModType.FLAT)
        );
        of(WARRIOR, "Warrior",
            new OptScaleExactStat(15, DatapackStats.INT, ModType.FLAT),
            new OptScaleExactStat(30, DatapackStats.STR, ModType.FLAT),
            new OptScaleExactStat(15, DatapackStats.DEX, ModType.FLAT)
        );
        of(HUNTER, "Hunter",
            new OptScaleExactStat(15, DatapackStats.INT, ModType.FLAT),
            new OptScaleExactStat(15, DatapackStats.STR, ModType.FLAT),
            new OptScaleExactStat(30, DatapackStats.DEX, ModType.FLAT)
        );

        of(TEMPLAR, "Templar",
            new OptScaleExactStat(23, DatapackStats.INT, ModType.FLAT),
            new OptScaleExactStat(23, DatapackStats.STR, ModType.FLAT),
            new OptScaleExactStat(14, DatapackStats.DEX, ModType.FLAT)
        );

        of(DUELIST, "Duelist",
            new OptScaleExactStat(14, DatapackStats.INT, ModType.FLAT),
            new OptScaleExactStat(23, DatapackStats.STR, ModType.FLAT),
            new OptScaleExactStat(23, DatapackStats.DEX, ModType.FLAT)
        );

        of(BATTLE_MAGE, "Battle Mage",
            new OptScaleExactStat(23, DatapackStats.INT, ModType.FLAT),
            new OptScaleExactStat(14, DatapackStats.STR, ModType.FLAT),
            new OptScaleExactStat(23, DatapackStats.DEX, ModType.FLAT)
        );

        of(SCION, "Scion",
            new OptScaleExactStat(20, DatapackStats.INT, ModType.FLAT),
            new OptScaleExactStat(20, DatapackStats.STR, ModType.FLAT),
            new OptScaleExactStat(20, DatapackStats.DEX, ModType.FLAT)
        );
    }

    Perk of(String id, String locname, OptScaleExactStat... stats) {

        Perk perk = PerkBuilder.bigStat(id, locname, stats);

        perk.is_entry = true;
        perk.type = Perk.PerkType.START;
        perk.one_of_a_kind = "start";
        return perk;
    }
}
