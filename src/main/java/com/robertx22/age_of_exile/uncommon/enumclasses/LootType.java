package com.robertx22.age_of_exile.uncommon.enumclasses;

import com.robertx22.age_of_exile.uncommon.localization.Words;
import com.robertx22.library_of_exile.registry.IGUID;

public enum LootType implements IGUID {

    Gear("Gear", "gear", Words.Gear, 0),
    DungeonKey("Dungeon Key", "dungeon_keys", Words.DungeonKey, 0),
    LevelingRewards("Leveling Rewards", "lvl_rewards", Words.LevelRewards, 0),
    Rune("Rune", "rune", Words.Rune, 2),
    Currency("Currency", "currency", Words.Currency, 3),
    Ingredient("Ingredient", "ingredient", Words.Ingredient, 4),
    All("All", "all", Words.All, 0);

    public int custommodeldata;

    private LootType(String name, String id, Words word, int custommodeldata) {
        this.thename = name;
        this.id = id;
        this.word = word;
        this.custommodeldata = custommodeldata;
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
