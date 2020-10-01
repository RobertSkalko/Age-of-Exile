package com.robertx22.age_of_exile.aoe_data.database.stats.spell_mod_stats;

import com.robertx22.age_of_exile.aoe_data.base.DataGenKey;
import com.robertx22.age_of_exile.aoe_data.database.spells.Spells;
import com.robertx22.age_of_exile.database.data.stats.datapacks.stats.MarkerStat;
import com.robertx22.age_of_exile.database.registry.ISlashRegistryInit;

public class OceanSpellModStats implements ISlashRegistryInit {

    public static DataGenKey<MarkerStat> CHILLING_TIDES_KEY = new DataGenKey<>("chilling_tides");
    public static DataGenKey<MarkerStat> CRASHING_ROCKS_KEY = new DataGenKey<>("crashing_rocks");
    public static DataGenKey<MarkerStat> BURNING_CURRENTS_KEY = new DataGenKey<>("burning_currents");

    public static DataGenKey<MarkerStat> FAST_FROSTBALL_KEY = new DataGenKey<>("fast_frostball");
    public static DataGenKey<MarkerStat> FROSTBALL_EXTRA_DMG_KEY = new DataGenKey<>("frostball_chill");

    public static DataGenKey<MarkerStat> HEART_MAGIC_SHIELD_RESTORE_KEY = new DataGenKey<>("heart_of_magic");
    public static DataGenKey<MarkerStat> HEART_CHILL_ENEMIES_KEY = new DataGenKey<>("heart_of_chill");

    public static DataGenKey<MarkerStat> ICE_FLOWER_DMG_KEY = new DataGenKey<>("ice_flower_dmg");
    public static DataGenKey<MarkerStat> ICE_FLOWER_RESTORE_KEY = new DataGenKey<>("ice_flower_restore");

    // keys end

    public static MarkerStat CHILLING_TIDES;
    public static MarkerStat CRASHING_ROCKS;
    public static MarkerStat BURNING_CURRENTS;

    public static MarkerStat FAST_FROSTBALL;
    public static MarkerStat FROSTBALL_EXTRA_DMG;

    public static MarkerStat HEART_MAGIC_SHIELD_RESTORE;
    public static MarkerStat HEART_CHILL_ENEMIES;

    public static MarkerStat ICE_FLOWER_DMG;
    public static MarkerStat ICE_FLOWER_RESTORE;

    @Override
    public void registerAll() {

        // init
        CHILLING_TIDES = new MarkerStat(CHILLING_TIDES_KEY, Spells.TIDAL_WAVE, "Chilling Tides");
        CRASHING_ROCKS = new MarkerStat(CRASHING_ROCKS_KEY, Spells.TIDAL_WAVE, "Crashing Rocks");
        BURNING_CURRENTS = new MarkerStat(BURNING_CURRENTS_KEY, Spells.TIDAL_WAVE, "Burning Currents");

        FAST_FROSTBALL = new MarkerStat(FAST_FROSTBALL_KEY, Spells.FROSTBALL, "Rapid Fire");
        FROSTBALL_EXTRA_DMG = new MarkerStat(FROSTBALL_EXTRA_DMG_KEY, Spells.FROSTBALL, "Punishing");

        HEART_MAGIC_SHIELD_RESTORE = new MarkerStat(HEART_MAGIC_SHIELD_RESTORE_KEY, Spells.HEART_OF_ICE, "Heart of Magic");
        HEART_CHILL_ENEMIES = new MarkerStat(HEART_CHILL_ENEMIES_KEY, Spells.HEART_OF_ICE, "Heart of Chill");

        ICE_FLOWER_DMG = new MarkerStat(ICE_FLOWER_DMG_KEY, Spells.ICE_FLOWER, "Heartless");
        ICE_FLOWER_RESTORE = new MarkerStat(ICE_FLOWER_RESTORE_KEY, Spells.ICE_FLOWER, "Forgiving");

        // added to serialization automatically if used

        /*

        CHILLING_TIDES.addToSerializables();
        CRASHING_ROCKS.addToSerializables();
        BURNING_CURRENTS.addToSerializables();

        FAST_FROSTBALL.addToSerializables();
        TRIPLE_FROSTBALL.addToSerializables();
        FROSTBALL_EXTRA_DMG.addToSerializables();

        HEART_MAGIC_SHIELD_RESTORE.addToSerializables();
        HEART_CHILL_ENEMIES.addToSerializables();

        ICE_FLOWER_DMG.addToSerializables();
        ICE_FLOWER_RESTORE.addToSerializables();

         */

    }
}
