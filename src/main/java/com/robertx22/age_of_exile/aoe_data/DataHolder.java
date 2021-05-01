package com.robertx22.age_of_exile.aoe_data;

import com.robertx22.age_of_exile.aoe_data.datapacks.bases.ISerializedRegistryEntry;

import java.util.HashMap;
import java.util.List;
import java.util.function.Function;

public class DataHolder<Key, Item extends ISerializedRegistryEntry> {

    List<Key> keys;

    public DataHolder(List<Key> keys, Function<Key, Item> fun) {
        this.keys = keys;

        keys.forEach(x -> {
            Item item = fun.apply(x);
            item.addToSerializables();
            map.put(x, item);
        });
    }

    private HashMap<Key, Item> map = new HashMap<>();

    public Item get(Key key) {
        return map.get(key);
    }

}
