package com.robertx22.mine_and_slash.uncommon.enumclasses;

import com.robertx22.mine_and_slash.database.data.IGUID;

public enum LootType implements IGUID {

    NormalItem("Normal Item", "normal_item"),
    SkillGem("Skill Gem", "skill_gem"),
    UniqueItem("Unique Item", "unique_item"),
    Currency("Currency", "currency"),
    CompatibleItem("Compatible Item", "compatible_item"),
    All("All", "all"),
    Blueprint("Blueprint", "blueprint");

    private LootType(String name, String id) {
        this.thename = name;
        this.id = id;
    }

    String id;
    private String thename;

    public String getName() {
        return thename;
    }

    @Override
    public String GUID() {
        return id;
    }
}
