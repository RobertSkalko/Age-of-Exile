package com.robertx22.age_of_exile.aoe_data.database.base_gear_types;

import com.robertx22.age_of_exile.database.data.gear_types.bases.BaseGearType;
import com.robertx22.library_of_exile.registry.DataGenKey;

public class BaseGearTypes {

    public final static DataGenKey<BaseGearType> RING = of("ring");
    public final static DataGenKey<BaseGearType> NECKLACE = of("necklace");
    public final static DataGenKey<BaseGearType> SHIELD = of("shield");
    public final static DataGenKey<BaseGearType> SWORD = of("sword");
    public final static DataGenKey<BaseGearType> AXE = of("axe");
    public final static DataGenKey<BaseGearType> SCEPTER = of("scepter");
    public final static DataGenKey<BaseGearType> BOW = of("bow");
    public final static DataGenKey<BaseGearType> CROSSBOW = of("crossbow");
    public final static DataGenKey<BaseGearType> STAFF = of("staff");
    public final static DataGenKey<BaseGearType> BOOTS = of("boots");
    public final static DataGenKey<BaseGearType> PANTS = of("pants");
    public final static DataGenKey<BaseGearType> CHEST = of("chest");
    public final static DataGenKey<BaseGearType> HELMET = of("helmet");

    static DataGenKey<BaseGearType> of(String id) {
        return new DataGenKey<>(id);
    }
}
