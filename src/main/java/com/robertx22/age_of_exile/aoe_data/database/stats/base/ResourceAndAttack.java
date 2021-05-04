package com.robertx22.age_of_exile.aoe_data.database.stats.base;

import com.robertx22.age_of_exile.saveclasses.unit.ResourceType;
import com.robertx22.age_of_exile.uncommon.enumclasses.AttackType;

import java.util.ArrayList;
import java.util.List;

public class ResourceAndAttack extends AutoHashClass {

    public ResourceType resource;
    public AttackType attackType;

    public ResourceAndAttack(ResourceType resource, AttackType attackType) {
        this.resource = resource;
        this.attackType = attackType;
    }

    public static List<ResourceAndAttack> allCombos() {
        List<ResourceAndAttack> list = new ArrayList<>();

        for (AttackType type : AttackType.getAllUsed()) {
            list.add(new ResourceAndAttack(ResourceType.health, type));
            list.add(new ResourceAndAttack(ResourceType.mana, type));
        }
        return list;

    }

}