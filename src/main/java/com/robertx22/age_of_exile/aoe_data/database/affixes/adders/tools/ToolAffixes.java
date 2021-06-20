package com.robertx22.age_of_exile.aoe_data.database.affixes.adders.tools;

import com.robertx22.age_of_exile.database.registry.ExileRegistryInit;

public class ToolAffixes implements ExileRegistryInit {
    @Override
    public void registerAll() {
        new AllToolAffixes().registerAll();
        new HoeRodAffixes().registerAll();
    }
}
