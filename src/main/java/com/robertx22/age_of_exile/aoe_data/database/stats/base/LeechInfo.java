package com.robertx22.age_of_exile.aoe_data.database.stats.base;

import com.robertx22.age_of_exile.saveclasses.unit.ResourceType;
import com.robertx22.age_of_exile.uncommon.enumclasses.Elements;

import java.util.ArrayList;
import java.util.List;

public class LeechInfo extends AutoHashClass {

    public Elements element;
    public ResourceType resourceType;

    public LeechInfo(Elements element, ResourceType resource) {
        this.element = element;
        this.resourceType = resource;
    }

    public static List<LeechInfo> allCombos() {
        List<LeechInfo> list = new ArrayList<>();

        for (Elements ele : Elements.values()) {
            list.add(new LeechInfo(ele, ResourceType.health));
            list.add(new LeechInfo(ele, ResourceType.mana));
        }
        return list;
    }

    @Override
    public int hashCode() {
        return element.hashCode() + resourceType.hashCode();
    }
}
