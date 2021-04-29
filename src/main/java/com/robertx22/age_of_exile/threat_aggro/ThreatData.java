package com.robertx22.age_of_exile.threat_aggro;

import info.loenwind.autosave.annotations.Storable;
import info.loenwind.autosave.annotations.Store;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.player.PlayerEntity;

import java.util.Comparator;
import java.util.HashMap;

@Storable
public class ThreatData {

    @Store
    public HashMap<String, Integer> map = new HashMap<>();

    public void addThreat(PlayerEntity player, MobEntity mob, int threat) {

        if (player.distanceTo(mob) > 10) {
            threat *= 0.75F; // ranged do less threat
        }

        String key = player.getUuid()
            .toString();
        int cur = map.getOrDefault(key, 0);
        map.put(key, cur + threat);

        String highest = getHighest();
        if (!highest.isEmpty()) {
            if (key.equals(highest)) {
                if (mob.getTarget() != player) {
                    mob.setTarget(player);
                }
            }
        }

    }

    public String getHighest() {

        if (map.isEmpty()) {
            return "";
        }

        return map.entrySet()
            .stream()
            .max(Comparator.comparingInt(x -> x.getValue()))
            .get()
            .getKey();

    }

}
