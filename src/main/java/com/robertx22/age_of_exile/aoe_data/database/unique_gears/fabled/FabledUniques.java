package com.robertx22.age_of_exile.aoe_data.database.unique_gears.fabled;

import com.robertx22.age_of_exile.database.registry.ISlashRegistryInit;

public class FabledUniques implements ISlashRegistryInit {

    @Override
    public void registerAll() {
        new FabledJewelry().registerAll();
        new FabledArmor().registerAll();

    }
}
