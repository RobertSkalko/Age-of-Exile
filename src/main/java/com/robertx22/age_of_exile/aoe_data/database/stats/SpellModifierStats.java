package com.robertx22.age_of_exile.aoe_data.database.stats;

import com.robertx22.age_of_exile.aoe_data.database.spells.Spells;
import com.robertx22.age_of_exile.database.data.stats.datapacks.stats.MarkerStat;
import com.robertx22.age_of_exile.database.registry.ISlashRegistryInit;

public class SpellModifierStats implements ISlashRegistryInit {

    public static MarkerStat CHILLING_TIDES = new MarkerStat("chilling_tides", Spells.TIDAL_WAVE, "Chilling Tides");
    public static MarkerStat CRASHING_ROCKS = new MarkerStat("crashing_rocks", Spells.TIDAL_WAVE, "Crashing Rocks");
    public static MarkerStat BURNING_CURRENTS = new MarkerStat("burning_currents", Spells.TIDAL_WAVE, "Burning Currents");

    @Override
    public void registerAll() {

        CHILLING_TIDES.addToSerializables();
        CRASHING_ROCKS.addToSerializables();
        BURNING_CURRENTS.addToSerializables();

    }
}
