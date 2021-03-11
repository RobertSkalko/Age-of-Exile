package com.robertx22.age_of_exile.aoe_data.database.affixes.adders.tools;

import com.robertx22.age_of_exile.database.registry.ISlashRegistryInit;

public class ToolAffixes implements ISlashRegistryInit {
    @Override
    public void registerAll() {
        new AllToolAffixes().registerAll();
        new HoeRodAffixes().registerAll();
    }
}
