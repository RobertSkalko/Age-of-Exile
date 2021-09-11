package com.robertx22.age_of_exile.aoe_data.database.skill_gems;

import com.robertx22.age_of_exile.database.registry.ExileDB;
import com.robertx22.library_of_exile.registry.ExileRegistryInit;

public class SkillGemsAdder implements ExileRegistryInit {

    @Override
    public void registerAll() {

        ExileDB.Spells()
            .getSerializable()
            .forEach(x -> {
                SkillGemBuilder.spell(x, x.GUID(), x.locName + " Gem",
                    x.config.style.attribute, 1);
            });
    }
}
