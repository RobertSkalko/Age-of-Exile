package com.robertx22.age_of_exile.saveclasses.unit;

public enum ResourceType {
    health("health", "Health"),
    mana("mana", "Mana"),
    shield("shield", "Shield"),
    block("block", "Block"),
    energy("energy", "Energy"),
    blood("blood", "Blood");

    public String id;

    ResourceType(String id, String locname) {
        this.id = id;
        this.locname = locname;
    }

    public static ResourceType ofId(String id) {

        for (ResourceType type : ResourceType.values()) {
            if (type.id.equals(id)) {
                return type;
            }
        }

        return null;
    }

    public String locname;

}
