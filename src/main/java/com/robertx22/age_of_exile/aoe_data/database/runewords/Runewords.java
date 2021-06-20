package com.robertx22.age_of_exile.aoe_data.database.runewords;

import com.robertx22.age_of_exile.aoe_data.database.runewords.adders.ArmorRunewords;
import com.robertx22.age_of_exile.aoe_data.database.runewords.adders.JewelryRunewords;
import com.robertx22.age_of_exile.aoe_data.database.runewords.adders.WeaponRunewords;
import com.robertx22.age_of_exile.database.registry.ExileRegistryInit;

public class Runewords implements ExileRegistryInit {

    @Override
    public void registerAll() {

        new ArmorRunewords().registerAll();
        new WeaponRunewords().registerAll();
        new JewelryRunewords().registerAll();

    }
}
