package com.robertx22.age_of_exile.capability;

import net.minecraft.entity.player.PlayerEntity;

import java.util.HashMap;

public class PlayerDamageChart {

    static HashMap<String, Float> dmg = new HashMap<>();

    public static void onDamage(PlayerEntity player, float damage) {
        float num = dmg.getOrDefault(player.getUuidAsString(), 0F) + damage;
        dmg.put(player.getUuidAsString(), num);
    }

    public static float getDamage(PlayerEntity player) {
        return dmg.getOrDefault(player.getUuidAsString(), 0F);
    }

    public static void clear(PlayerEntity p) {
        dmg.put(p.getUuidAsString(), 0F);
    }
}
