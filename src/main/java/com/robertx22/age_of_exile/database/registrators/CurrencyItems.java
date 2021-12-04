package com.robertx22.age_of_exile.database.registrators;

import com.robertx22.age_of_exile.database.data.currency.base.CurrencyItem;
import com.robertx22.library_of_exile.registry.ExileRegistryInit;
import net.minecraftforge.registries.ForgeRegistries;

public class CurrencyItems implements ExileRegistryInit {

    @Override
    public void registerAll() {
        ForgeRegistries.ITEMS.forEach(x -> {
            if (x instanceof CurrencyItem) {
                CurrencyItem cur = (CurrencyItem) x;
                cur.unregisterFromExileRegistry();
                cur.registerToExileRegistry();
            }
        });
    }

}
