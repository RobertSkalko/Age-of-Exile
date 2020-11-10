package com.robertx22.age_of_exile.aoe_data.database.player_skills;

import com.robertx22.age_of_exile.aoe_data.database.player_skills.skills.MiningAdder;
import com.robertx22.age_of_exile.database.registry.ISlashRegistryInit;

public class PlayerSkillsAdder implements ISlashRegistryInit {

    @Override
    public void registerAll() {

        MiningAdder.createSkill();

    }
}
