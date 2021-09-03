package com.robertx22.age_of_exile.aoe_data.database.perks;

import com.robertx22.library_of_exile.registry.ExileRegistryInit;

public class AllPerks implements ExileRegistryInit {

    @Override
    public void registerAll() {
        new Perks().registerAll();
        new GameChangerPerks().registerAll();
        new StartPerks().registerAll();
        new BigEleDotAndLeechPerks().registerAll();

    }
}
