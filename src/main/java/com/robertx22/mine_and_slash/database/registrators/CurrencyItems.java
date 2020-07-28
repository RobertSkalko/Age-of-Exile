package com.robertx22.mine_and_slash.database.registrators;

import com.robertx22.mine_and_slash.database.data.currency.base.CurrencyItem;
import com.robertx22.mine_and_slash.database.registry.ISlashRegistryInit;
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
