package com.robertx22.age_of_exile.aoe_data.database.spells;

import com.robertx22.age_of_exile.aoe_data.database.spells.impl.*;
import com.robertx22.age_of_exile.aoe_data.database.spells.reworked_spells.BardSpells;
import com.robertx22.age_of_exile.aoe_data.database.spells.reworked_spells.ElementalistSpells;
import com.robertx22.age_of_exile.aoe_data.database.spells.reworked_spells.RangerSpells;
import com.robertx22.age_of_exile.aoe_data.database.spells.reworked_spells.WarriorSpells;
import com.robertx22.age_of_exile.aoe_data.database.spells.schools.HolySpells;
import com.robertx22.age_of_exile.aoe_data.database.spells.schools.HunterSpells;
import com.robertx22.age_of_exile.aoe_data.database.spells.schools.NatureSpells;
import com.robertx22.age_of_exile.aoe_data.database.spells.schools.WaterSpells;
import com.robertx22.library_of_exile.registry.ExileRegistryInit;

public class Spells implements ExileRegistryInit {

    @Override
    public void registerAll() {

        new UtilitySpells().registerAll();
        new TestSpells().registerAll();
        new CurseSpells().registerAll();
        new IntSpells().registerAll();
        new StrSpells().registerAll();

        new ElementalistSpells().registerAll();
        new WarriorSpells().registerAll();
        new BardSpells().registerAll();
        new RangerSpells().registerAll();

        new NatureSpells().registerAll();
        new WaterSpells().registerAll();
        new HolySpells().registerAll();
        new HunterSpells().registerAll();

    }
}
