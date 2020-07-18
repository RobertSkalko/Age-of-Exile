package com.robertx22.mine_and_slash.uncommon.wrappers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MapWrapper<KEY, VALUE> {

    public HashMap<KEY, VALUE> MAP = new HashMap<>();

    public void put(KEY k, VALUE v) {
        MAP.put(k, v);
    }

    public VALUE get(KEY k) {
        return MAP.get(k);
    }


    public List<VALUE> getList() {
        return new ArrayList<>(MAP.values());
    }
}
