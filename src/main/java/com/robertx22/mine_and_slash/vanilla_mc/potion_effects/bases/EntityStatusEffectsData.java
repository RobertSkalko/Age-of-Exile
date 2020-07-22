package com.robertx22.mine_and_slash.vanilla_mc.potion_effects.bases;

import com.robertx22.mine_and_slash.vanilla_mc.potion_effects.bases.data.ExtraPotionData;
import info.loenwind.autosave.annotations.Storable;
import info.loenwind.autosave.annotations.Store;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.util.registry.Registry;

import java.util.HashMap;

@Storable
public class EntityStatusEffectsData {

    @Store
    HashMap<String, ExtraPotionData> map = new HashMap<>();

    public boolean has(StatusEffect effect) {
        return map.containsKey(Registry.STATUS_EFFECT.getId(effect)
            .toString()) && map.get(Registry.STATUS_EFFECT.getId(effect)
            .toString()) != null;
    }

    public ExtraPotionData get(StatusEffect effect) {
        return map.get(Registry.STATUS_EFFECT.getId(effect)
            .toString());
    }

    public void set(StatusEffect effect, ExtraPotionData data) {
        map.put(Registry.STATUS_EFFECT.getId(effect)
            .toString(), data);
    }

}
