package com.robertx22.age_of_exile.aoe_data.database.perks;

import com.robertx22.age_of_exile.database.OptScaleExactStat;
import com.robertx22.age_of_exile.database.data.perks.Perk;
import com.robertx22.age_of_exile.database.data.stats.types.core_stats.Dexterity;
import com.robertx22.age_of_exile.database.data.stats.types.core_stats.Intelligence;
import com.robertx22.age_of_exile.database.data.stats.types.core_stats.Strength;
import com.robertx22.age_of_exile.database.registry.ISlashRegistryInit;
import com.robertx22.age_of_exile.uncommon.enumclasses.ModType;

public class StartPerks implements ISlashRegistryInit {

    @Override
    public void registerAll() {

        of("mage", "Mage",
            new OptScaleExactStat(30, Intelligence.INSTANCE, ModType.FLAT),
            new OptScaleExactStat(15, Strength.INSTANCE, ModType.FLAT),
            new OptScaleExactStat(15, Dexterity.INSTANCE, ModType.FLAT)
        );
        of("warrior", "Warrior",
            new OptScaleExactStat(15, Intelligence.INSTANCE, ModType.FLAT),
            new OptScaleExactStat(30, Strength.INSTANCE, ModType.FLAT),
            new OptScaleExactStat(15, Dexterity.INSTANCE, ModType.FLAT)
        );
        of("hunter", "Hunter",
            new OptScaleExactStat(15, Intelligence.INSTANCE, ModType.FLAT),
            new OptScaleExactStat(15, Strength.INSTANCE, ModType.FLAT),
            new OptScaleExactStat(30, Dexterity.INSTANCE, ModType.FLAT)
        );

        of("templar", "Templar",
            new OptScaleExactStat(23, Intelligence.INSTANCE, ModType.FLAT),
            new OptScaleExactStat(23, Strength.INSTANCE, ModType.FLAT),
            new OptScaleExactStat(14, Dexterity.INSTANCE, ModType.FLAT)
        );

        of("duelist", "Duelist",
            new OptScaleExactStat(14, Intelligence.INSTANCE, ModType.FLAT),
            new OptScaleExactStat(23, Strength.INSTANCE, ModType.FLAT),
            new OptScaleExactStat(23, Dexterity.INSTANCE, ModType.FLAT)
        );

        of("battle_mage", "Battle Mage",
            new OptScaleExactStat(23, Intelligence.INSTANCE, ModType.FLAT),
            new OptScaleExactStat(14, Strength.INSTANCE, ModType.FLAT),
            new OptScaleExactStat(23, Dexterity.INSTANCE, ModType.FLAT)
        );

        of("scion", "Scion",
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
