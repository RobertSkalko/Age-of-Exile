package com.robertx22.age_of_exile.aoe_data.database.perks;

import com.robertx22.age_of_exile.database.registry.ISlashRegistryInit;

public class AllPerks implements ISlashRegistryInit {

    @Override
    public void registerAll() {
        new NewPerks().registerAll();
        new Perks().registerAll();
        new GameChangerPerks().registerAll();
        new BigPerks().registerAll();
        new ComboPerks().registerAll();
        new StartPerks().registerAll();
        new BigEleDotAndLeechPerks().registerAll();

    }
}
