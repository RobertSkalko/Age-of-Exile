package com.robertx22.mine_and_slash.database.registrators;

import com.robertx22.mine_and_slash.database.base.IRandomDefault;
import com.robertx22.mine_and_slash.database.data.affixes.Affix;
import com.robertx22.mine_and_slash.database.data.affixes.data.JewelrySuffixes;
import com.robertx22.mine_and_slash.database.data.affixes.data.NonWeaponSuffixes;
import com.robertx22.mine_and_slash.database.data.affixes.data.WeaponSuffixes;
import com.robertx22.exiled_lib.registry.ISlashRegistryInit;
import com.robertx22.exiled_lib.registry.SlashRegistry;

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
        return SlashRegistry.Affixes()
            .getWrapped()
            .ofAffixType(Affix.Type.suffix).list;
    }

}
