package com.robertx22.age_of_exile.aoe_data.database.stats.spell_mod_stats;

import com.robertx22.age_of_exile.aoe_data.base.DataGenKey;
import com.robertx22.age_of_exile.aoe_data.database.spells.Spells;
import com.robertx22.age_of_exile.database.data.stats.datapacks.stats.MarkerStat;
import com.robertx22.age_of_exile.database.registry.ISlashRegistryInit;

public class HunterSpellModStats implements ISlashRegistryInit {

    public static DataGenKey<MarkerStat> IMBUE_CRIT_KEY = new DataGenKey<>("mob_imbue_crit");
    public static DataGenKey<MarkerStat> IMBUE_ELE_KEY = new DataGenKey<>("mod_imbue_ele");
    public static DataGenKey<MarkerStat> IMBUE_PHYS_KEY = new DataGenKey<>("mob_imbue_phys");

    // keys end

    public static MarkerStat IMBUE_CRIT;
    public static MarkerStat IMBUE_ELE;
    public static MarkerStat IMBUE_PHYS;

    @Override
    public void registerAll() {

        // init
        IMBUE_CRIT = new MarkerStat(IMBUE_CRIT_KEY, Spells.TELEPORT, "Critical");
        IMBUE_ELE = new MarkerStat(IMBUE_ELE_KEY, Spells.TELEPORT, "Elemental");
        IMBUE_PHYS = new MarkerStat(IMBUE_PHYS_KEY, Spells.TELEPORT, "Physical");

        // added to serialization automatically if used

    }
}
