package com.robertx22.age_of_exile.uncommon.enumclasses;

import com.robertx22.age_of_exile.database.data.IGUID;
import com.robertx22.age_of_exile.uncommon.localization.Words;

public enum LootType implements IGUID {

    Gear("Gear", "gear", Words.Gear),
    Gem("Gem", "gem", Words.Gem),
    LevelingRewards("Leveling Rewards", "lvl_rewards", Words.LevelRewards),
    Rune("Rune", "rune", Words.Rune),
    Currency("Currency", "currency", Words.Currency),
    All("All", "all", Words.All);

    private LootType(String name, String id, Words word) {
        this.thename = name;
        this.id = id;
        this.word = word;
    }

    public static LootType of(String str) {
        for (LootType type : values()) {
            if (type.id.equals(str)) {
                return type;
            }
        }
        return null;
    }

    public Words word;

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
