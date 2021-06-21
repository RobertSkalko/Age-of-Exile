package com.robertx22.age_of_exile.aoe_data.database.affixes;

import com.robertx22.age_of_exile.aoe_data.database.affixes.adders.NonWeaponSuffixes;
import com.robertx22.age_of_exile.aoe_data.database.affixes.adders.SocketAffixes;
import com.robertx22.age_of_exile.aoe_data.database.affixes.adders.WeaponSuffixes;
import com.robertx22.age_of_exile.aoe_data.database.affixes.adders.jewelry.JewelrySuffixes;
import com.robertx22.age_of_exile.database.base.IRandomDefault;
import com.robertx22.age_of_exile.database.data.affixes.Affix;
import com.robertx22.age_of_exile.database.registry.ExileDB;
import com.robertx22.age_of_exile.database.registry.ExileFilters;
import com.robertx22.library_of_exile.registry.ExileRegistryInit;

import java.util.List;

public class Suffixes implements IRandomDefault<Affix>, ExileRegistryInit {

    public static Suffixes INSTANCE = new Suffixes();

    @Override
    public void registerAll() {

        new WeaponSuffixes().registerAll();
        new NonWeaponSuffixes().registerAll();
        new JewelrySuffixes().registerAll();

        new SocketAffixes().registerAll();

    }

    @Override
    public List<Affix> All() {
        return ExileDB.Affixes()
            .getWrapped()
            .of(ExileFilters.ofAffixType(Affix.Type.suffix)).list;
    }

}
