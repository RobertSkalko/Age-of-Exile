package com.robertx22.age_of_exile.capability.entity;

import com.robertx22.age_of_exile.database.registry.Database;
import info.loenwind.autosave.annotations.Storable;
import info.loenwind.autosave.annotations.Store;

import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

@Storable
public class CooldownsData {

    public static String IN_COMBAT = "in_combat";

    @Store
    private HashMap<String, Integer> map = new HashMap<>();

    public void onTicksPass(int ticks) {
        if (map.isEmpty()) {
            return;
        }
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

    public List<String> getAllSpellsOnCooldown() {
        return map.keySet()
            .stream()
            .filter(x -> Database.Spells()
                .isRegistered(x))
            .collect(Collectors.toList());
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
