package com.robertx22.age_of_exile.aoe_data.database.stats.spell_mod_stats;

import com.robertx22.age_of_exile.aoe_data.base.DataGenKey;
import com.robertx22.age_of_exile.aoe_data.database.spells.Spells;
import com.robertx22.age_of_exile.database.data.stats.datapacks.stats.MarkerStat;
import com.robertx22.age_of_exile.database.registry.ISlashRegistryInit;

public class FireSpellModStats implements ISlashRegistryInit {

    public static DataGenKey<MarkerStat> THROW_FLAMES_BURN_KEY = new DataGenKey<>("mod_throw_flames_burn");
    public static DataGenKey<MarkerStat> THROW_FLAMES_FASTER_KEY = new DataGenKey<>("mod_throw_flames_fast");
    public static DataGenKey<MarkerStat> THROW_FLAMES_LIFESTEAL_KEY = new DataGenKey<>("mod_throw_flames_lifesteal");

    public static DataGenKey<MarkerStat> MAGMA_FLOWER_HEAL_KEY = new DataGenKey<>("mod_magma_flower_heal");
    public static DataGenKey<MarkerStat> MAGMA_FLOWER_BURN_KEY = new DataGenKey<>("mod_magma_flower_burn");

    // keys end

    public static MarkerStat THROW_FLAMES_BURN;
    public static MarkerStat THROW_FLAMES_FASTER;
    public static MarkerStat THROW_FLAMES_LIFESTEAL;

    public static MarkerStat MAGMA_FLOWER_HEAL;
    public static MarkerStat MAGMA_FLOWER_BURN;

    @Override
    public void registerAll() {

        // init
        THROW_FLAMES_BURN = new MarkerStat(THROW_FLAMES_BURN_KEY, Spells.THROW_FLAMES, "Burning");
        THROW_FLAMES_FASTER = new MarkerStat(THROW_FLAMES_FASTER_KEY, Spells.THROW_FLAMES, "Speed");
        THROW_FLAMES_LIFESTEAL = new MarkerStat(THROW_FLAMES_LIFESTEAL_KEY, Spells.THROW_FLAMES, "Life Stealing");

        MAGMA_FLOWER_HEAL = new MarkerStat(MAGMA_FLOWER_HEAL_KEY, Spells.THROW_FLAMES, "Blooming");
        MAGMA_FLOWER_BURN = new MarkerStat(MAGMA_FLOWER_BURN_KEY, Spells.THROW_FLAMES, "Burning");

        // added to serialization automatically if used

    }
}

