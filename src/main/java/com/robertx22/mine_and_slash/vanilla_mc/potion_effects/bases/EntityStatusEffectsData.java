package com.robertx22.mine_and_slash.vanilla_mc.potion_effects.bases;

import com.robertx22.mine_and_slash.vanilla_mc.potion_effects.bases.data.ExtraPotionData;
import net.minecraft.entity.effect.StatusEffect;

import java.util.HashMap;

public class EntityStatusEffectsData {

    HashMap<StatusEffect, ExtraPotionData> map = new HashMap<>();

    public boolean has(StatusEffect effect) {
        return map.containsKey(effect) && map.get(effect) != null;
    }

    public ExtraPotionData get(StatusEffect effect) {
        return map.get(effect);
    }

    public void set(StatusEffect effect, ExtraPotionData data) {
        map.put(effect, data);
    }

}
