package com.robertx22.age_of_exile.database.data.perks;

public enum StatAttribute {
    INT("int"), DEX("dex"), STR("str");

    public String id;

    StatAttribute(String id) {
        this.id = id;
    }
}
