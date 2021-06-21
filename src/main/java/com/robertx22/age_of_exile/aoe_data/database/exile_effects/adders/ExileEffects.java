package com.robertx22.age_of_exile.aoe_data.database.exile_effects.adders;

import com.robertx22.library_of_exile.registry.ExileRegistryInit;

public class ExileEffects implements ExileRegistryInit {

    @Override
    public void registerAll() {
        new NegativeEffects().registerAll();
        new BeneficialEffects().registerAll();
        new NeutralEffects().registerAll();
    }
}
