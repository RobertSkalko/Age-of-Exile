package com.robertx22.age_of_exile.aoe_data.database.stats.spell_mod_stats;

import com.robertx22.age_of_exile.aoe_data.base.DataGenKey;
import com.robertx22.age_of_exile.aoe_data.database.spells.Spells;
import com.robertx22.age_of_exile.database.data.stats.datapacks.stats.MarkerStat;
import com.robertx22.age_of_exile.database.registry.ISlashRegistryInit;

public class ArcaneSpellModStats implements ISlashRegistryInit {

    public static DataGenKey<MarkerStat> TP_DMG_KEY = new DataGenKey<>("mod_tp_dmg");
    public static DataGenKey<MarkerStat> TP_FASTER_KEY = new DataGenKey<>("mod_tp_fast");
    public static DataGenKey<MarkerStat> TP_ELE_RES_KEY = new DataGenKey<>("mod_tp_ele_res");

    public static DataGenKey<MarkerStat> MANA_COOLDOWN_KEY = new DataGenKey<>("mod_mana_cd");
    public static DataGenKey<MarkerStat> MANA_ELE_RES_KEY = new DataGenKey<>("mod_mana_ele_res");
    public static DataGenKey<MarkerStat> MANA_MAGIC_SHIELD_KEY = new DataGenKey<>("mod_mana_magic");

    // keys end

    public static MarkerStat TP_DMG;
    public static MarkerStat TP_FASTER;
    public static MarkerStat TP_ELE_RES;

    public static MarkerStat MANA_COOLDOWN;
    public static MarkerStat MANA_ELE_RES;
    public static MarkerStat MANA_MAGIC_SHIELD;

    @Override
    public void registerAll() {

        // init
        TP_DMG = new MarkerStat(TP_DMG_KEY, Spells.TELEPORT, "Explosive");
        TP_FASTER = new MarkerStat(TP_FASTER_KEY, Spells.TELEPORT, "Of Speed");
        TP_ELE_RES = new MarkerStat(TP_ELE_RES_KEY, Spells.TELEPORT, "Resistant");

        MANA_COOLDOWN = new MarkerStat(MANA_COOLDOWN_KEY, Spells.AWAKEN_MANA, "Faster Cooldown");
        MANA_ELE_RES = new MarkerStat(MANA_ELE_RES_KEY, Spells.AWAKEN_MANA, "Resistant");
        MANA_MAGIC_SHIELD = new MarkerStat(MANA_MAGIC_SHIELD_KEY, Spells.AWAKEN_MANA, "Of Magic");

        // added to serialization automatically if used

    }
}
