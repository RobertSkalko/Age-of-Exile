package com.robertx22.age_of_exile.mmorpg.registers.common.items;

import com.robertx22.age_of_exile.database.all_keys.base.AllDataKeys;
import com.robertx22.age_of_exile.database.all_keys.base.DataKey;
import com.robertx22.age_of_exile.database.data.runewords.RuneWord;
import com.robertx22.age_of_exile.database.data.runewords.RuneWordItem;
import com.robertx22.age_of_exile.mmorpg.registers.deferred_wrapper.Def;
import com.robertx22.age_of_exile.mmorpg.registers.deferred_wrapper.RegObj;

import java.util.HashMap;

public class RunewordItems {

    public static HashMap<String, RegObj<RuneWordItem>> MAP = new HashMap<>();

    public static void init() {

        for (DataKey<?> key : AllDataKeys.ALL.get(RuneWord.class)) {
            RegObj<RuneWordItem> def = Def.item(() -> new RuneWordItem(), RuneWordItem.getIdPath(key.id));
            MAP.put(key.id, def);
        }

    }
}
