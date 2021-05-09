package com.robertx22.age_of_exile.aoe_data;

import com.robertx22.age_of_exile.aoe_data.datapacks.bases.ISerializedRegistryEntry;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.function.Function;

public class DataHolder<Key, Item extends ISerializedRegistryEntry> {

    List<Key> keys;

    public DataHolder(Key[] keys, Function<Key, Item> fun) {
        this(Arrays.asList(keys), fun);
    }

    public DataHolder(List<Key> keys, Function<Key, Item> fun) {
        this.keys = keys;

        keys.forEach(x -> {
            Item item = fun.apply(x);
            map.put(x, item);
        });
    }

    public void addToSerializables() {
        map.values()
            .forEach(x -> x.addToSerializables());
    }

    private HashMap<Key, Item> map = new HashMap<>();

    public Item get(Key key) {

        if (!map.containsKey(key)) {
            try {
                throw new RuntimeException("Key missing: " + key.toString());
            } catch (RuntimeException e) {
                e.printStackTrace();
            }
        }

        return map.get(key);
    }

}
