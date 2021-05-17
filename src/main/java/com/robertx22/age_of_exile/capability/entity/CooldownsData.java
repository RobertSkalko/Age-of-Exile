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
    private HashMap<String, Data> map = new HashMap<>();

    @Storable
    public static class Data {
        @Store
        public int ticks = 0;
        @Store

        public int need = 0;

        public Data() {
        }

        public Data(int ticks, int need) {
            this.ticks = ticks;
            this.need = need;
        }
    }

    public void onTicksPass(int ticks) {
        if (map.isEmpty()) {
            return;
        }
        new HashMap<>(map).entrySet()
            .forEach(x -> {
                x.getValue().ticks -= ticks;
                if (x.getValue().ticks < 1) {
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
        map.put(id, new Data(ticks, ticks));
    }

    public int getCooldownTicks(String id) {
        return map.getOrDefault(id, new Data(0, 0)).ticks;
    }

    public int getNeededTicks(String id) {
        return map.getOrDefault(id, new Data(0, 0)).need;
    }

    public boolean isOnCooldown(String id) {
        return getCooldownTicks(id) > 0;
    }

}
