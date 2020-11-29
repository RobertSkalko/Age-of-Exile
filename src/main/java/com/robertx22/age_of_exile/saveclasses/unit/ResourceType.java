package com.robertx22.age_of_exile.saveclasses.unit;

public enum ResourceType {
    HEALTH("health", "Health"),
    MANA("mana", "Mana"),
    BLOOD("blood", "Blood"),
    MAGIC_SHIELD("magic_shield", "Magic Shield");

    public String id;

    ResourceType(String id, String locname) {
        this.id = id;
        this.locname = locname;
    }

    public String locname;

}
