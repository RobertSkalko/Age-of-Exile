package com.robertx22.mine_and_slash.vanilla_mc.potion_effects.bases;

import com.robertx22.mine_and_slash.vanilla_mc.potion_effects.bases.data.ExtraPotionData;
import info.loenwind.autosave.annotations.Storable;
import info.loenwind.autosave.annotations.Store;
import net.minecraft.entity.effect.StatusEffect;

import java.util.HashMap;

@Storable
public class EntityStatusEffectsData {

    @Store
    HashMap<String, ExtraPotionData> map = new HashMap<>();

    public boolean has(StatusEffect effect) {

        if (effect instanceof BasePotionEffect) {
            String id = ((BasePotionEffect) effect).GUID();

            return map.containsKey(id) && map.get(id) != null;
        }
        return false;
    }

    public ExtraPotionData get(StatusEffect effect) {

        if (effect instanceof BasePotionEffect) {
            BasePotionEffect p = (BasePotionEffect) effect;

            return map.get(p.GUID());
        }
        return null;
    }

    public void set(BasePotionEffect effect, ExtraPotionData data) {

        if (effect == null) {
            return;
        }

        String id = effect.GUID();

        if (data == null) {
            map.remove(id);
            return;
        }

        map.put(id, data);
    }

}
