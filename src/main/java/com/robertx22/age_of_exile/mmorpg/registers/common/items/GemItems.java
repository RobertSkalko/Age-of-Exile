package com.robertx22.age_of_exile.mmorpg.registers.common.items;

import com.robertx22.age_of_exile.mmorpg.registers.deferred_wrapper.Def;
import com.robertx22.age_of_exile.mmorpg.registers.deferred_wrapper.RegObj;
import com.robertx22.age_of_exile.vanilla_mc.items.gemrunes.GemItem;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class GemItems {

    public static HashMap<GemItem.GemType, HashMap<GemItem.GemRank, RegObj<GemItem>>> MAP = new HashMap<>();
    public static List<RegObj<GemItem>> ALL = new ArrayList<>();

    public static void init() {

        for (GemItem.GemType type : GemItem.GemType.values()) {
            for (GemItem.GemRank rank : GemItem.GemRank.values()) {

                GemItem item = new GemItem(type, rank);

                RegObj<GemItem> def = Def.item(() -> item);

                if (!MAP.containsKey(type)) {
                    MAP.put(type, new HashMap<>());
                }

                MAP.get(type)
                    .put(rank, def);

                ALL.add(def);

            }
        }

    }

}
