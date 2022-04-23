package com.robertx22.age_of_exile.aoe_data.database.unique_gears;

import com.robertx22.age_of_exile.aoe_data.database.unique_gears.fabled.FabledUniques;
import com.robertx22.age_of_exile.aoe_data.database.unique_gears.runewords.*;
import com.robertx22.age_of_exile.aoe_data.database.unique_gears.runic_spells.ArmorRunicSpells;
import com.robertx22.age_of_exile.aoe_data.database.unique_gears.uniques.UniqueArmors;
import com.robertx22.age_of_exile.aoe_data.database.unique_gears.uniques.UniqueJewelry;
import com.robertx22.age_of_exile.aoe_data.database.unique_gears.uniques.UniqueWeapons;
import com.robertx22.library_of_exile.registry.ExileRegistryInit;

public class UniqueGearReg implements ExileRegistryInit {

    @Override
    public void registerAll() {

        new FabledUniques().registerAll();

        new UniqueArmors().registerAll();
        new UniqueJewelry().registerAll();
        new UniqueWeapons().registerAll();

        new NecklaceRunewords().registerAll();
        new HelmetRunewords().registerAll();
        new SwordRunewords().registerAll();
        new RingRuneWords().registerAll();
        new StaffRuneWords().registerAll();
        new BowRuneWords().registerAll();

        new ArmorRunicSpells().registerAll();

    }
}
