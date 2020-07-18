package com.robertx22.mine_and_slash.database.registrators;

import com.robertx22.mine_and_slash.database.base.IRandomDefault;
import com.robertx22.mine_and_slash.database.data.affixes.Affix;
import com.robertx22.mine_and_slash.database.data.affixes.data.*;
import com.robertx22.exiled_lib.registry.ISlashRegistryInit;
import com.robertx22.exiled_lib.registry.SlashRegistry;

import java.util.List;

public class Prefixes implements IRandomDefault<Affix>, ISlashRegistryInit {

    @Override
    public void registerAll() {

        new WeaponPrefixes().registerAll();
        new ArmorPrefixes().registerAll();
        new JewelryPrefixes().registerAll();
        new EvasionPrefixes().registerAll();
        new MagicShieldPrefixes().registerAll();

    }

    public static final Prefixes INSTANCE = new Prefixes();

    @Override
    public List<Affix> All() {
        return SlashRegistry.Affixes()
            .getWrapped()
            .ofAffixType(Affix.Type.prefix).list;
    }

}
