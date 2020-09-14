package com.robertx22.age_of_exile.database.data.unique_items;

import com.robertx22.age_of_exile.database.data.unique_items.registrators.UniqueArmors;
import com.robertx22.age_of_exile.database.data.unique_items.registrators.UniqueJewelry;
import com.robertx22.age_of_exile.database.data.unique_items.registrators.UniqueWeapons;
import com.robertx22.age_of_exile.database.registry.ISlashRegistryInit;

public class UniqueGearReg implements ISlashRegistryInit {
    @Override
    public void registerAll() {
        new UniqueArmors().registerAll();
        new UniqueJewelry().registerAll();
        new UniqueWeapons().registerAll();
    }
}
