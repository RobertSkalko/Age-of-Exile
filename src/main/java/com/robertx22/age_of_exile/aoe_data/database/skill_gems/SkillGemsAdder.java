package com.robertx22.age_of_exile.aoe_data.database.skill_gems;

import com.robertx22.age_of_exile.database.data.StatModifier;
import com.robertx22.age_of_exile.database.data.skill_gem.SkillGemType;
import com.robertx22.age_of_exile.database.data.stats.datapacks.stats.spell_related.GiveSpellStat;
import com.robertx22.age_of_exile.database.registry.Database;
import com.robertx22.age_of_exile.database.registry.ISlashRegistryInit;

public class SkillGemsAdder implements ISlashRegistryInit {

    @Override
    public void registerAll() {

        new SupportSkillGemsAdder().registerAll();

        Database.Spells()
            .getSerializable()
            .forEach(x -> {
                SkillGemBuilder.of(x.GUID() + "_gem", x.locName + " Gem",
                    x.config.style.attribute, SkillGemType.SKILL_GEM, 1,
                    new StatModifier(1F, 1F, new GiveSpellStat(x))
                );
            });
    }
}
