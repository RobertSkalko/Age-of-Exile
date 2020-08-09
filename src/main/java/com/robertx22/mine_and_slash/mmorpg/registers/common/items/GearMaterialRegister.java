package com.robertx22.mine_and_slash.mmorpg.registers.common.items;

import com.robertx22.mine_and_slash.database.data.level_ranges.LevelRange;
import com.robertx22.mine_and_slash.database.registrators.LevelRanges;
import com.robertx22.mine_and_slash.vanilla_mc.items.misc.GearMaterialItem;

import java.util.HashMap;
import java.util.Locale;

public class GearMaterialRegister extends BaseItemRegistrator {

    public enum TYPE {CLOTH, LEATHER, ORE}

    public HashMap<TYPE, HashMap<Integer, GearMaterialItem>> MAP = new HashMap<>();

    public GearMaterialItem LEATHER_0 = of(TYPE.LEATHER, 0, LevelRanges.STARTER);
    public GearMaterialItem LEATHER_1 = of(TYPE.LEATHER, 1, LevelRanges.LOW);
    public GearMaterialItem LEATHER_2 = of(TYPE.LEATHER, 2, LevelRanges.MIDDLE);
    public GearMaterialItem LEATHER_3 = of(TYPE.LEATHER, 3, LevelRanges.HIGH);
    public GearMaterialItem LEATHER_4 = of(TYPE.LEATHER, 4, LevelRanges.ENDGAME);

    public GearMaterialItem CLOTH_0 = of(TYPE.CLOTH, 0, LevelRanges.STARTER);
    public GearMaterialItem CLOTH_1 = of(TYPE.CLOTH, 1, LevelRanges.LOW);
    public GearMaterialItem CLOTH_2 = of(TYPE.CLOTH, 2, LevelRanges.MIDDLE);
    public GearMaterialItem CLOTH_3 = of(TYPE.CLOTH, 3, LevelRanges.HIGH);
    public GearMaterialItem CLOTH_4 = of(TYPE.CLOTH, 4, LevelRanges.ENDGAME);

    public GearMaterialItem ORE_0 = of(TYPE.ORE, 0, LevelRanges.STARTER);
    public GearMaterialItem ORE_1 = of(TYPE.ORE, 1, LevelRanges.LOW);
    public GearMaterialItem ORE_2 = of(TYPE.ORE, 2, LevelRanges.MIDDLE);
    public GearMaterialItem ORE_3 = of(TYPE.ORE, 3, LevelRanges.HIGH);
    public GearMaterialItem ORE_4 = of(TYPE.ORE, 4, LevelRanges.ENDGAME);

    public GearMaterialItem of(TYPE type, int tier, LevelRange range) {

        String suffix = "";

        if (tier == 0) {
            suffix = "Of Potential";
        }
        if (tier == 1) {
            suffix = "Of Maturity";
        }
        if (tier == 2) {
            suffix = "Of Achievement";
        }
        if (tier == 3) {
            suffix = "Of Mastery";
        }
        if (tier == 4) {
            suffix = "Of Transcendence";
        }
        String typeid = type.name()
            .toLowerCase(Locale.ROOT);

        String first = typeid.substring(0, 1);
        String afterfirst = typeid.substring(1);
        String name = first.toUpperCase() + afterfirst + " " + suffix;

        String lowercase = type.name()
            .toLowerCase(Locale.ROOT);

        GearMaterialItem item = new GearMaterialItem(tier, type, lowercase + "/" + lowercase + tier, name, range);

        if (!MAP.containsKey(type)) {
            MAP.put(type, new HashMap<>());
        }
        MAP.get(type)
            .put(tier, item);

        return item(item);

    }

}
