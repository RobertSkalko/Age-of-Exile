package com.robertx22.age_of_exile.aoe_data.database.skill_gems;

import com.robertx22.age_of_exile.database.registry.Database;
import com.robertx22.age_of_exile.database.registry.ISlashRegistryInit;

public class SkillGemsAdder implements ISlashRegistryInit {

    @Override
    public void registerAll() {

        new SupportSkillGemsAdder().registerAll();

        Database.Spells()
            .getSerializable()
            .forEach(x -> {
                SkillGemBuilder.spell(x, x.GUID() + "_gem", x.locName + " Gem",
                    x.config.style.attribute, 1);
            });
    }
}
