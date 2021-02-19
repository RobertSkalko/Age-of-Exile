package com.robertx22.age_of_exile.capability.player;

import com.robertx22.age_of_exile.capability.bases.ICommonPlayerCap;
import com.robertx22.age_of_exile.uncommon.datasaving.Load;
import com.robertx22.age_of_exile.vanilla_mc.packets.sync_cap.PlayerCaps;
import info.loenwind.autosave.annotations.Storable;
import info.loenwind.autosave.annotations.Store;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.CompoundTag;

import java.util.HashMap;

@Storable
public class OnePlayerCharData {

    // these are just for preview on the character screen
    @Store
    public int lvl = 1;
    // these are just for preview on the character screen

    @Store
    public HashMap<PlayerCaps, CompoundTag> map = new HashMap<>();

    public void save(PlayerEntity player) {

        for (PlayerCaps cap : PlayerCaps.values()) {
            if (cap.shouldSaveToPlayerCharacter()) {
                CompoundTag nbt = new CompoundTag();
                cap.getCap(player)
                    .toTag(nbt);
                map.put(cap, nbt);
            }

        }

        this.lvl = Load.Unit(player)
            .getLevel();

    }

    public void load(PlayerEntity player) {

        map.entrySet()
            .forEach(x -> {
                ICommonPlayerCap cap = x.getKey()
                    .getCap(player);
                cap.fromTag(x.getValue());
                cap.syncToClient(player);
            });

    }

}
