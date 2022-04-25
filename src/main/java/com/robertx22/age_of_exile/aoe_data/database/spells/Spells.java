package com.robertx22.age_of_exile.aoe_data.database.spells;

import com.robertx22.age_of_exile.aoe_data.database.spells.impl.IntSpells;
import com.robertx22.age_of_exile.aoe_data.database.spells.impl.StrSpells;
import com.robertx22.age_of_exile.aoe_data.database.spells.impl.TestSpells;
import com.robertx22.age_of_exile.aoe_data.database.spells.impl.UtilitySpells;
import com.robertx22.age_of_exile.aoe_data.database.spells.reworked_spells.BardSpells;
import com.robertx22.age_of_exile.aoe_data.database.spells.reworked_spells.ElementalistSpells;
import com.robertx22.age_of_exile.aoe_data.database.spells.reworked_spells.RangerSpells;
import com.robertx22.age_of_exile.aoe_data.database.spells.reworked_spells.ShamanSpells;
import com.robertx22.age_of_exile.aoe_data.database.spells.schools.HolySpells;
import com.robertx22.age_of_exile.aoe_data.database.spells.schools.HunterSpells;
import com.robertx22.age_of_exile.aoe_data.database.spells.schools.NatureSpells;
import com.robertx22.age_of_exile.aoe_data.database.spells.schools.WaterSpells;
import com.robertx22.library_of_exile.registry.ExileRegistryInit;

public class Spells implements ExileRegistryInit {

    @Override
    public void registerAll() {

        new ElementalistSpells().registerAll();
        new ShamanSpells().registerAll();
        new BardSpells().registerAll();
        new RangerSpells().registerAll();

        new UtilitySpells().registerAll();
        new TestSpells().registerAll();
        new IntSpells().registerAll();
        new StrSpells().registerAll();

        new NatureSpells().registerAll();
        new WaterSpells().registerAll();
        new HolySpells().registerAll();
        new HunterSpells().registerAll();

    }
}
