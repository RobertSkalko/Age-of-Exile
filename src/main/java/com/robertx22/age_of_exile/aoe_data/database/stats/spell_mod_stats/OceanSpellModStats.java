package com.robertx22.age_of_exile.aoe_data.database.stats.spell_mod_stats;

import com.robertx22.age_of_exile.aoe_data.base.DataGenKey;
import com.robertx22.age_of_exile.aoe_data.database.spells.Spells;
import com.robertx22.age_of_exile.database.data.stats.datapacks.stats.MarkerStat;
import com.robertx22.age_of_exile.database.registry.ISlashRegistryInit;

public class OceanSpellModStats implements ISlashRegistryInit {

    public static DataGenKey<MarkerStat> HEART_MAGIC_SHIELD_RESTORE_KEY = new DataGenKey<>("heart_of_magic");
    public static DataGenKey<MarkerStat> HEART_CHILL_ENEMIES_KEY = new DataGenKey<>("heart_of_chill");

    public static DataGenKey<MarkerStat> ICE_FLOWER_DMG_KEY = new DataGenKey<>("ice_flower_dmg");
    public static DataGenKey<MarkerStat> ICE_FLOWER_RESTORE_KEY = new DataGenKey<>("ice_flower_restore");

    // keys end

    public static MarkerStat HEART_MAGIC_SHIELD_RESTORE;
    public static MarkerStat HEART_CHILL_ENEMIES;

    public static MarkerStat ICE_FLOWER_DMG;
    public static MarkerStat ICE_FLOWER_RESTORE;

    @Override
    public void registerAll() {

        // init

        HEART_MAGIC_SHIELD_RESTORE = new MarkerStat(HEART_MAGIC_SHIELD_RESTORE_KEY, Spells.HEART_OF_ICE, "Heart of Magic");
        HEART_CHILL_ENEMIES = new MarkerStat(HEART_CHILL_ENEMIES_KEY, Spells.HEART_OF_ICE, "Heart of Chill");

        ICE_FLOWER_DMG = new MarkerStat(ICE_FLOWER_DMG_KEY, Spells.ICE_FLOWER, "Heartless");
        ICE_FLOWER_RESTORE = new MarkerStat(ICE_FLOWER_RESTORE_KEY, Spells.ICE_FLOWER, "Forgiving");

        // added to serialization automatically if used

    }
}
