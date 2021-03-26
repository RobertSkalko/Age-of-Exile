package com.robertx22.age_of_exile.aoe_data.database.player_skills;

import com.robertx22.age_of_exile.aoe_data.database.player_skills.skills.crafting.CookingAdder;
import com.robertx22.age_of_exile.aoe_data.database.player_skills.skills.crafting.InscribingAdder;
import com.robertx22.age_of_exile.aoe_data.database.player_skills.skills.gathering.*;
import com.robertx22.age_of_exile.database.registry.ISlashRegistryInit;

public class PlayerSkillsAdder implements ISlashRegistryInit {

    @Override
    public void registerAll() {

        SalvagingAdder.createSkill();
        ExplorationAdder.createSkill();
        MiningAdder.createSkill();
        FarmingAdder.createSkill();
        FishingAdder.createSkill();

        InscribingAdder.createSkill();
        CookingAdder.createSkill();




        /*
        TinkeringAdder.createSkill();
        AlchemyAdder.createSkill();
        CookingAdder.createSkill();
        InscribingAdder.createSkill();

         */

    }
}
