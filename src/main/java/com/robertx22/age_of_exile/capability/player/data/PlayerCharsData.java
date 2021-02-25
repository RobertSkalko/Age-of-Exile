package com.robertx22.age_of_exile.capability.player.data;

import com.robertx22.age_of_exile.vanilla_mc.packets.sync_cap.PlayerCaps;
import info.loenwind.autosave.annotations.Storable;
import info.loenwind.autosave.annotations.Store;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.CompoundTag;

import java.util.HashMap;

@Storable
public class PlayerCharsData {

    @Store
    public HashMap<Integer, OnePlayerCharData> characters = new HashMap<>();

    public void load(Integer num, PlayerEntity p) {

        OnePlayerCharData data = new OnePlayerCharData();
        data.save(p);

        if (characters.containsKey(num)) {
            // load saved character and replace it with current one
            characters.get(num)
                .load(p);
            characters.put(num, data);
        } else {

            //new character

            for (PlayerCaps cap : PlayerCaps.values()) {
                if (cap.shouldSaveToPlayerCharacter()) {
                    cap.getCap(p)
                        .fromTag(new CompoundTag());
                }
            }

            characters.put(num, data);

        }
    }

}
