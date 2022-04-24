package com.robertx22.age_of_exile.aoe_data.database.unique_gears;

import com.robertx22.age_of_exile.aoe_data.database.unique_gears.fabled.FabledUniques;
import com.robertx22.age_of_exile.aoe_data.database.unique_gears.runewords.ArmorRunewords;
import com.robertx22.age_of_exile.aoe_data.database.unique_gears.runewords.JewelryRunewords;
import com.robertx22.age_of_exile.aoe_data.database.unique_gears.runewords.WeaponRunewords;
import com.robertx22.age_of_exile.aoe_data.database.unique_gears.runic_spells.ArmorRunicSpells;
import com.robertx22.age_of_exile.aoe_data.database.unique_gears.runic_spells.JewelryRunicSpells;
import com.robertx22.age_of_exile.aoe_data.database.unique_gears.runic_spells.WeaponRunicSpells;
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

        new ArmorRunicSpells().registerAll();
        new JewelryRunicSpells().registerAll();
        new WeaponRunicSpells().registerAll();

        new ArmorRunewords().registerAll();
        new JewelryRunewords().registerAll();
        new WeaponRunewords().registerAll();

    }
}
