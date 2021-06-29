package com.robertx22.age_of_exile.aoe_data.database.tiers;

import com.robertx22.age_of_exile.database.data.tiers.base.Difficulty;

public class DifficultyBuilder {
    Difficulty diff = new Difficulty();

    public static DifficultyBuilder of(String id, int rank, String locname) {
        DifficultyBuilder b = new DifficultyBuilder();
        b.diff.rank = rank;
        b.diff.id = id;
        b.diff.locname = locname;
        return b;
    }

    public DifficultyBuilder stats(float hp, float dmg, float stat) {
        diff.hp_multi = hp;
        diff.dmg_multi = dmg;
        diff.stat_multi = stat;
        return this;
    }

    public DifficultyBuilder requiresPlayerLevel(int lvl) {
        diff.req_player_lvl = lvl;
        return this;
    }

    public DifficultyBuilder deathsAllowed(int deaths) {
        diff.deaths_allowed = deaths;
        return this;
    }

    public DifficultyBuilder deathFavorPenalty(int penalty) {
        diff.death_favor_penalty = penalty;
        return this;
    }

    public DifficultyBuilder loot(float multi, float higherRarChance) {
        diff.loot_multi = multi;
        diff.higher_rar_chance = higherRarChance;
        return this;
    }

    public Difficulty build() {
        diff.addToSerializables();
        return diff;
    }
}
