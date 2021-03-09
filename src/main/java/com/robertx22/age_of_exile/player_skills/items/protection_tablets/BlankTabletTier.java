package com.robertx22.age_of_exile.player_skills.items.protection_tablets;

public enum BlankTabletTier {

    NORMAL(""), SUPREME("Superior ");

    BlankTabletTier(String prefixName) {
        this.prefixName = prefixName;
    }

    public String prefixName;
}
