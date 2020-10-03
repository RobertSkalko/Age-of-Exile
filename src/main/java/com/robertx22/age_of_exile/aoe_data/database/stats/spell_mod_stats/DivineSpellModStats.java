package com.robertx22.age_of_exile.aoe_data.database.stats.spell_mod_stats;

import com.robertx22.age_of_exile.aoe_data.base.DataGenKey;
import com.robertx22.age_of_exile.aoe_data.database.spells.Spells;
import com.robertx22.age_of_exile.database.data.stats.datapacks.stats.MarkerStat;
import com.robertx22.age_of_exile.database.registry.ISlashRegistryInit;

public class DivineSpellModStats implements ISlashRegistryInit {

    public static DataGenKey<MarkerStat> FLOWER_DMG_KEY = new DataGenKey<>("mod_holy_flower_dmg");
    public static DataGenKey<MarkerStat> FLOWER_CURE_KEY = new DataGenKey<>("mod_holy_flower_cure");
    public static DataGenKey<MarkerStat> FLOWER_RESTORE_KEY = new DataGenKey<>("mod_holy_flower_rest");

    // keys end

    public static MarkerStat FLOWER_DMG;
    public static MarkerStat FLOWER_CURE;
    public static MarkerStat FLOWER_RESTORE;

    @Override
    public void registerAll() {

        // init
        FLOWER_DMG = new MarkerStat(FLOWER_DMG_KEY, Spells.HOLY_FLOWER, "Divinity");
        FLOWER_CURE = new MarkerStat(FLOWER_CURE_KEY, Spells.HOLY_FLOWER, "Resolve");
        FLOWER_RESTORE = new MarkerStat(FLOWER_RESTORE_KEY, Spells.HOLY_FLOWER, "Mercy");

        // added to serialization automatically if used

    }
}
