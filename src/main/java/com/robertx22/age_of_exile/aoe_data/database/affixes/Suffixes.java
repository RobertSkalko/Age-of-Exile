package com.robertx22.age_of_exile.aoe_data.database.affixes;

import com.robertx22.age_of_exile.aoe_data.database.affixes.adders.NonWeaponSuffixes;
import com.robertx22.age_of_exile.aoe_data.database.affixes.adders.WeaponSuffixes;
import com.robertx22.age_of_exile.aoe_data.database.affixes.adders.jewelry.JewelrySuffixes;
import com.robertx22.age_of_exile.database.base.IRandomDefault;
import com.robertx22.age_of_exile.database.data.affixes.Affix;
import com.robertx22.age_of_exile.database.registry.Database;
import com.robertx22.age_of_exile.database.registry.ISlashRegistryInit;

import java.util.List;

public class Suffixes implements IRandomDefault<Affix>, ISlashRegistryInit {

    public static Suffixes INSTANCE = new Suffixes();

    @Override
    public void registerAll() {

        new WeaponSuffixes().registerAll();
        new NonWeaponSuffixes().registerAll();
        new JewelrySuffixes().registerAll();

    }

    @Override
    public List<Affix> All() {
        return Database.Affixes()
            .getWrapped()
            .ofAffixType(Affix.Type.suffix).list;
    }

}
