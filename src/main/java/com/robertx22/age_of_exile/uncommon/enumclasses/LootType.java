package com.robertx22.age_of_exile.uncommon.enumclasses;

import com.robertx22.age_of_exile.database.data.IGUID;

public enum LootType implements IGUID {

    Gear("Gear", "gear"),
    Gem("Gem", "gem"),
    LevelingRewards("Leveling Rewards", "lvl_rewards"),
    Rune("Rune", "rune"),
    Currency("Currency", "currency"),
    All("All", "all");

    private LootType(String name, String id) {
        this.thename = name;
        this.id = id;
    }

    public static LootType of(String str) {
        for (LootType type : values()) {
            if (type.id.equals(str)) {
                return type;
            }
        }
        return null;
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
