package com.robertx22.age_of_exile.aoe_data.database.player_skills;

import com.robertx22.age_of_exile.aoe_data.database.player_skills.skills.crafting.AlchemyAdder;
import com.robertx22.age_of_exile.aoe_data.database.player_skills.skills.crafting.BlacksmithingAdder;
import com.robertx22.age_of_exile.aoe_data.database.player_skills.skills.crafting.CookingAdder;
import com.robertx22.age_of_exile.aoe_data.database.player_skills.skills.crafting.InscribingAdder;
import com.robertx22.age_of_exile.aoe_data.database.player_skills.skills.gathering.*;
import com.robertx22.library_of_exile.registry.ExileRegistryInit;

public class PlayerSkillsAdder implements ExileRegistryInit {

    @Override
    public void registerAll() {

        SalvagingAdder.createSkill();
        ExplorationAdder.createSkill();
        MiningAdder.createSkill();
        FarmingAdder.createSkill();
        FishingAdder.createSkill();

        InscribingAdder.createSkill();
        CookingAdder.createSkill();
        AlchemyAdder.createSkill();
        BlacksmithingAdder.createSkill();

    }
}
