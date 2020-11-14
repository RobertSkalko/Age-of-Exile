package com.robertx22.age_of_exile.aoe_data.database.player_skills;

import com.robertx22.age_of_exile.aoe_data.database.player_skills.skills.crafting.AlchemyAdder;
import com.robertx22.age_of_exile.aoe_data.database.player_skills.skills.crafting.CookingAdder;
import com.robertx22.age_of_exile.aoe_data.database.player_skills.skills.crafting.InscribingAdder;
import com.robertx22.age_of_exile.aoe_data.database.player_skills.skills.gathering.ExplorationAdder;
import com.robertx22.age_of_exile.aoe_data.database.player_skills.skills.gathering.FarmingAdder;
import com.robertx22.age_of_exile.aoe_data.database.player_skills.skills.gathering.FishingAdder;
import com.robertx22.age_of_exile.aoe_data.database.player_skills.skills.gathering.MiningAdder;
import com.robertx22.age_of_exile.database.registry.ISlashRegistryInit;

public class PlayerSkillsAdder implements ISlashRegistryInit {

    @Override
    public void registerAll() {

        ExplorationAdder.createSkill();
        MiningAdder.createSkill();
        FarmingAdder.createSkill();
        AlchemyAdder.createSkill();
        CookingAdder.createSkill();
        InscribingAdder.createSkill();
        FishingAdder.createSkill();

    }
}
