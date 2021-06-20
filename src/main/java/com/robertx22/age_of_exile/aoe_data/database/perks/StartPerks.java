package com.robertx22.age_of_exile.aoe_data.database.perks;

import com.robertx22.age_of_exile.database.OptScaleExactStat;
import com.robertx22.age_of_exile.database.data.perks.Perk;
import com.robertx22.age_of_exile.database.data.stats.types.core_stats.Dexterity;
import com.robertx22.age_of_exile.database.data.stats.types.core_stats.Intelligence;
import com.robertx22.age_of_exile.database.data.stats.types.core_stats.Strength;
import com.robertx22.age_of_exile.database.registry.ExileRegistryInit;
import com.robertx22.age_of_exile.uncommon.enumclasses.ModType;

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
            new OptScaleExactStat(30, Intelligence.INSTANCE, ModType.FLAT),
            new OptScaleExactStat(15, Strength.INSTANCE, ModType.FLAT),
            new OptScaleExactStat(15, Dexterity.INSTANCE, ModType.FLAT)
        );
        of(WARRIOR, "Warrior",
            new OptScaleExactStat(15, Intelligence.INSTANCE, ModType.FLAT),
            new OptScaleExactStat(30, Strength.INSTANCE, ModType.FLAT),
            new OptScaleExactStat(15, Dexterity.INSTANCE, ModType.FLAT)
        );
        of(HUNTER, "Hunter",
            new OptScaleExactStat(15, Intelligence.INSTANCE, ModType.FLAT),
            new OptScaleExactStat(15, Strength.INSTANCE, ModType.FLAT),
            new OptScaleExactStat(30, Dexterity.INSTANCE, ModType.FLAT)
        );

        of(TEMPLAR, "Templar",
            new OptScaleExactStat(23, Intelligence.INSTANCE, ModType.FLAT),
            new OptScaleExactStat(23, Strength.INSTANCE, ModType.FLAT),
            new OptScaleExactStat(14, Dexterity.INSTANCE, ModType.FLAT)
        );

        of(DUELIST, "Duelist",
            new OptScaleExactStat(14, Intelligence.INSTANCE, ModType.FLAT),
            new OptScaleExactStat(23, Strength.INSTANCE, ModType.FLAT),
            new OptScaleExactStat(23, Dexterity.INSTANCE, ModType.FLAT)
        );

        of(BATTLE_MAGE, "Battle Mage",
            new OptScaleExactStat(23, Intelligence.INSTANCE, ModType.FLAT),
            new OptScaleExactStat(14, Strength.INSTANCE, ModType.FLAT),
            new OptScaleExactStat(23, Dexterity.INSTANCE, ModType.FLAT)
        );

        of(SCION, "Scion",
            new OptScaleExactStat(20, Intelligence.INSTANCE, ModType.FLAT),
            new OptScaleExactStat(20, Strength.INSTANCE, ModType.FLAT),
            new OptScaleExactStat(20, Dexterity.INSTANCE, ModType.FLAT)
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
