package com.robertx22.age_of_exile.mmorpg.registers.common.items;

import com.robertx22.age_of_exile.mmorpg.registers.deferred_wrapper.Def;
import com.robertx22.age_of_exile.mmorpg.registers.deferred_wrapper.RegObj;
import com.robertx22.age_of_exile.vanilla_mc.items.gemrunes.RuneItem;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class RuneItems {

    public static HashMap<String, RegObj<RuneItem>> MAP = new HashMap<>();
    public static List<RegObj<RuneItem>> ALL = new ArrayList<>();

    public static void init() {

        for (RuneItem.RuneType type : RuneItem.RuneType.values()) {

            RuneItem item = new RuneItem(type);

            RegObj<RuneItem> def = Def.item(() -> item);

            MAP.put(type.id, def);
            ALL.add(def);

        }

    }

    public static RuneItem get(RuneItem.RuneType type) {
        return MAP.get(type.id)
            .get();
    }

}
