package com.robertx22.age_of_exile.aoe_data.database.skill_gems;

import com.robertx22.age_of_exile.aoe_data.database.skill_gems.support.DexSupportGems;
import com.robertx22.age_of_exile.aoe_data.database.skill_gems.support.IntSupportGems;
import com.robertx22.age_of_exile.aoe_data.database.skill_gems.support.StrSupportGems;
import com.robertx22.age_of_exile.database.registry.Database;
import com.robertx22.age_of_exile.database.registry.ExileRegistryInit;

public class SkillGemsAdder implements ExileRegistryInit {

    @Override
    public void registerAll() {

        new IntSupportGems().registerAll();
        new StrSupportGems().registerAll();
        new DexSupportGems().registerAll();

        Database.Spells()
            .getSerializable()
            .forEach(x -> {
                SkillGemBuilder.spell(x, x.GUID(), x.locName + " Gem",
                    x.config.style.attribute, 1);
            });
    }
}
