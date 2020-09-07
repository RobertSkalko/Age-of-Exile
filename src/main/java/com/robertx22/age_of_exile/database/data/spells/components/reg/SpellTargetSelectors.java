package com.robertx22.age_of_exile.database.data.spells.components.reg;

import com.robertx22.age_of_exile.database.data.spells.components.selectors.*;

import java.util.HashMap;

public class SpellTargetSelectors {

    public static HashMap<String, BaseTargetSelector> MAP = new HashMap<>();

    public static BaseTargetSelector SELF = of(new SelfSelector());
    public static BaseTargetSelector TARGET = of(new TargetSelector());
    public static BaseTargetSelector AOE = of(new AoeSelector());
    public static BaseTargetSelector IN_FRONT = of(new InFrontSelector());

    private static BaseTargetSelector of(BaseTargetSelector s) {
        MAP.put(s.GUID(), s);
        return s;

    }

}
