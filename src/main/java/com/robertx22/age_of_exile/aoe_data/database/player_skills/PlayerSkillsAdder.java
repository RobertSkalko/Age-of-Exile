package com.robertx22.age_of_exile.aoe_data.database.player_skills;

import com.robertx22.age_of_exile.aoe_data.database.player_skills.skills.crafting.*;
import com.robertx22.library_of_exile.registry.ExileRegistryInit;

public class PlayerSkillsAdder implements ExileRegistryInit {

    @Override
    public void registerAll() {

        InscribingAdder.createSkill();
        CookingAdder.createSkill();
        AlchemyAdder.createSkill();

        JewelCraftAdder.createSkill();
        WeaponCraftAdder.createSkill();
        ArmorCraftAdder.createSkill();

    }
}
