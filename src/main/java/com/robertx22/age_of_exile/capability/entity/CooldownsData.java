package com.robertx22.age_of_exile.capability.entity;

import info.loenwind.autosave.annotations.Storable;
import info.loenwind.autosave.annotations.Store;

import java.util.HashMap;

@Storable
public class CooldownsData {

    @Store
    private HashMap<String, Integer> map = new HashMap<>();

    public void onTicksPass(int ticks) {
        new HashMap<>(map).entrySet()
            .forEach(x -> {
                int cd = x.getValue() - ticks;

                if (cd > 0) {
                    map.put(x.getKey(), cd);
                } else {
                    map.remove(x.getKey());
                }

            });
    }

    public void setOnCooldown(String id, int ticks) {
        map.put(id, ticks);
    }

    public int getCooldownTicks(String id) {
        return map.getOrDefault(id, 0);
    }

    public boolean isOnCooldown(String id) {
        return getCooldownTicks(id) > 0;
    }
}
