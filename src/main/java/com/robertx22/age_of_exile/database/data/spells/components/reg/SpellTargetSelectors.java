package com.robertx22.age_of_exile.database.data.spells.components.reg;

import com.robertx22.age_of_exile.database.data.spells.components.selectors.*;

import java.util.HashMap;

public class SpellTargetSelectors {

    public static HashMap<String, BaseTargetSelector> MAP = new HashMap<>();

    public static SelfSelector SELF = of(new SelfSelector());
    public static TargetSelector TARGET = of(new TargetSelector());
    public static AoeSelector AOE = of(new AoeSelector());
    public static InFrontSelector IN_FRONT = of(new InFrontSelector());

    private static <T extends BaseTargetSelector> T of(T s) {
        MAP.put(s.GUID(), s);
        return s;

    }

}
