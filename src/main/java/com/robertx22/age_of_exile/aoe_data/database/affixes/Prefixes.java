package com.robertx22.age_of_exile.aoe_data.database.affixes;

import com.robertx22.age_of_exile.aoe_data.database.affixes.adders.ArmorPrefixes;
import com.robertx22.age_of_exile.aoe_data.database.affixes.adders.ManaArmorAffixes;
import com.robertx22.age_of_exile.aoe_data.database.affixes.adders.WeaponPrefixes;
import com.robertx22.age_of_exile.aoe_data.database.affixes.adders.jewelry.JewelryPrefixes;
import com.robertx22.age_of_exile.database.base.IRandomDefault;
import com.robertx22.age_of_exile.database.data.affixes.Affix;
import com.robertx22.age_of_exile.database.registry.ExileDB;
import com.robertx22.age_of_exile.database.registry.ExileFilters;
import com.robertx22.library_of_exile.registry.ExileRegistryInit;

import java.util.List;

public class Prefixes implements IRandomDefault<Affix>, ExileRegistryInit {

    @Override
    public void registerAll() {

        new WeaponPrefixes().registerAll();
        new ArmorPrefixes().registerAll();
        new JewelryPrefixes().registerAll();
        new ManaArmorAffixes().registerAll();

    }

    public static final Prefixes INSTANCE = new Prefixes();

    @Override
    public List<Affix> All() {
        return ExileDB.Affixes()
            .getWrapped()
            .of(ExileFilters.ofAffixType(Affix.Type.prefix))
            .list;
    }

}
