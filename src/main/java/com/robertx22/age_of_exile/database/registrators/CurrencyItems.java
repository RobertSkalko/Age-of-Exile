package com.robertx22.age_of_exile.database.registrators;

import com.robertx22.age_of_exile.database.data.currency.base.CurrencyItem;
import com.robertx22.age_of_exile.database.registry.ISlashRegistryInit;
import net.minecraft.util.registry.Registry;


public class CurrencyItems implements ISlashRegistryInit {

    @Override
    public void registerAll() {
        Registry.ITEM.forEach(x -> {
            if (x instanceof CurrencyItem) {
                CurrencyItem cur = (CurrencyItem) x;
                cur.unregisterFromSlashRegistry();
                cur.registerToSlashRegistry();
            }
        });
    }

}
