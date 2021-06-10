package com.robertx22.age_of_exile.database.data.exile_effects;

public enum EffectTags {
    immobilizing("Immobilizing"),
    offensive("Offensive"),
    curse("Curse"),
    food("Food"),
    defensive("Defensive"),
    positive("Positive"),
    song("Song"),
    negative("Negative"),
    heal_over_time("Heal over Time");

    String name;

    EffectTags(String name) {
        this.name = name;
    }

    public String getLocName() {
        return name;
    }
}
