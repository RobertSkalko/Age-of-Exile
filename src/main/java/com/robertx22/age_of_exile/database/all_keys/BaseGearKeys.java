package com.robertx22.age_of_exile.database.all_keys;

import com.robertx22.age_of_exile.database.all_keys.base.BaseGearKey;

public interface BaseGearKeys {
    BaseGearKey RING = of("ring");
    BaseGearKey NECKLACE = of("necklace");
    BaseGearKey SHIELD = of("shield");
    BaseGearKey SWORD = of("sword");
    BaseGearKey AXE = of("axe");
    BaseGearKey SCEPTER = of("scepter");
    BaseGearKey BOW = of("bow");
    BaseGearKey CROSSBOW = of("crossbow");
    BaseGearKey STAFF = of("staff");
    BaseGearKey BOOTS = of("boots");
    BaseGearKey PANTS = of("pants");
    BaseGearKey CHEST = of("chest");
    BaseGearKey HELMET = of("helmet");

    static BaseGearKey of(String id) {
        return new BaseGearKey(id);
    }

    static void init() {
    }
}
