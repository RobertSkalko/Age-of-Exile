package com.robertx22.age_of_exile.capability.player;

import com.robertx22.age_of_exile.uncommon.datasaving.Load;
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
            characters.get(num).map = data.map;
        } else {
            //new character
            Load.Unit(p)
                .fromTag(new CompoundTag());
            Load.spells(p)
                .fromTag(new CompoundTag());
            Load.perks(p)
                .fromTag(new CompoundTag());

            characters.put(num, data);

        }
    }

}
