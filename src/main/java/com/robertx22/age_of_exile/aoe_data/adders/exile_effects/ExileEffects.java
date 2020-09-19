package com.robertx22.age_of_exile.aoe_data.adders.exile_effects;

import com.robertx22.age_of_exile.database.registry.ISlashRegistryInit;

public class ExileEffects implements ISlashRegistryInit {

    @Override
    public void registerAll() {
        new NegativeEffects().registerAll();
        new BeneficialEffects().registerAll();
    }
}
