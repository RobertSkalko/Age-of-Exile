package com.robertx22.age_of_exile.database.base;

import com.robertx22.age_of_exile.database.data.stats.Stat;
import com.robertx22.age_of_exile.database.registry.SlashRegistry;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

public class AllPreGenMapStats {

    private HashMap<Class, PreGenStatSet> map = new HashMap<>();

    int num = 0;

    public <T> HashSet<T> get(Class<T> theclass) {

        num++;

        if (num > 5000 || map.get(theclass) == null) {

            List<T> list = new ArrayList<>();

            for (Stat stat : SlashRegistry.Stats()
                .getAll()
                .values()) {
                if (theclass.isInstance(stat) || stat.getClass()
                    .isInstance(theclass)) {
                    list.add((T) stat);
                }

            }

            map.put(theclass, new PreGenStatSet<T>(list));

        }

        return map.get(theclass).list;

    }

}
