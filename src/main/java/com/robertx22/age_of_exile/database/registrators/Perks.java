package com.robertx22.age_of_exile.database.registrators;

import com.robertx22.age_of_exile.database.OptScaleExactStat;
import com.robertx22.age_of_exile.database.data.perks.Perk;
import com.robertx22.age_of_exile.database.data.perks.PerkBuilder;
import com.robertx22.age_of_exile.database.data.stats.types.core_stats.Dexterity;
import com.robertx22.age_of_exile.database.data.stats.types.core_stats.Intelligence;
import com.robertx22.age_of_exile.database.data.stats.types.core_stats.Strength;
import com.robertx22.age_of_exile.database.registry.ISlashRegistryInit;
import com.robertx22.age_of_exile.database.registry.SlashRegistry;
import com.robertx22.age_of_exile.uncommon.enumclasses.ModType;

public class Perks implements ISlashRegistryInit {

    @Override
    public void registerAll() {

        Perk start = new Perk();
        start.is_entry = true;
        start.identifier = "start";
        start.type = Perk.PerkType.START;
        start.addToSerializables();

        PerkBuilder.stat("int", new OptScaleExactStat(1, Intelligence.INSTANCE, ModType.FLAT));
        PerkBuilder.stat("dex", new OptScaleExactStat(1, Dexterity.INSTANCE, ModType.FLAT));
        PerkBuilder.stat("str", new OptScaleExactStat(1, Strength.INSTANCE, ModType.FLAT));

        SlashRegistry.Spells()
            .getList()
            .forEach(x -> {
                PerkBuilder.spell(x);
            });
    }
}
