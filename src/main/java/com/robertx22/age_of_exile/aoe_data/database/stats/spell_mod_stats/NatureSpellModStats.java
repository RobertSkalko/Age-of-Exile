package com.robertx22.age_of_exile.aoe_data.database.stats.spell_mod_stats;

import com.robertx22.age_of_exile.aoe_data.base.DataGenKey;
import com.robertx22.age_of_exile.aoe_data.database.spells.Spells;
import com.robertx22.age_of_exile.database.data.stats.datapacks.stats.MarkerStat;
import com.robertx22.age_of_exile.database.registry.ISlashRegistryInit;

public class NatureSpellModStats implements ISlashRegistryInit {

    public static DataGenKey<MarkerStat> POISON_BUSH_KEY = new DataGenKey<>("mod_poison_bush");
    public static DataGenKey<MarkerStat> SLOW_BUSH_KEY = new DataGenKey<>("mod_slow_bush");
    public static DataGenKey<MarkerStat> FAST_BUSH_KEY = new DataGenKey<>("mod_fast_bush");

    public static DataGenKey<MarkerStat> THORN_ARMOR_THORNS_KEY = new DataGenKey<>("mod_thorn_armor_thorns");

    // keys end

    public static MarkerStat POISON_BUSH;
    public static MarkerStat SLOW_BUSH;
    public static MarkerStat FAST_BUSH;

    public static MarkerStat THORN_ARMOR_THORNS;

    @Override
    public void registerAll() {

        // init
        POISON_BUSH = new MarkerStat(POISON_BUSH_KEY, Spells.POISON_BUSH, "Toxic");
        SLOW_BUSH = new MarkerStat(SLOW_BUSH_KEY, Spells.POISON_BUSH, "Trapping");
        FAST_BUSH = new MarkerStat(FAST_BUSH_KEY, Spells.POISON_BUSH, "Speed");

        THORN_ARMOR_THORNS = new MarkerStat(THORN_ARMOR_THORNS_KEY, Spells.THORN_ARMOR, "Spread Thorns");

        // added to serialization automatically if used

    }
}
