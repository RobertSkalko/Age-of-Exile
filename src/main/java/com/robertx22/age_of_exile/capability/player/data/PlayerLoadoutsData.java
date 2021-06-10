package com.robertx22.age_of_exile.capability.player.data;

import com.robertx22.age_of_exile.vanilla_mc.packets.sync_cap.PlayerCaps;
import info.loenwind.autosave.annotations.Storable;
import info.loenwind.autosave.annotations.Store;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.CompoundTag;

import java.util.HashMap;

@Storable
public class PlayerLoadoutsData {

    @Store
    public HashMap<Integer, OneLoadoutData> loadouts = new HashMap<>();

    public void load(Integer num, PlayerEntity p) {

        OneLoadoutData data = new OneLoadoutData();
        data.save(p);

        if (loadouts.containsKey(num)) {
            // load saved character and replace it with current one
            loadouts.get(num)
                .load(p);
            loadouts.put(num, data);
        } else {

            //new character

            for (PlayerCaps cap : PlayerCaps.values()) {
                if (cap.shouldSaveToLoadout()) {
                    cap.getCap(p)
                        .fromTag(new CompoundTag());
                }
            }

            loadouts.put(num, data);

        }
    }

}
