package com.robertx22.age_of_exile.saveclasses;

import com.robertx22.age_of_exile.capability.player.PlayerDeathData;
import com.robertx22.age_of_exile.mmorpg.ModRegistry;
import com.robertx22.age_of_exile.uncommon.enumclasses.Elements;
import info.loenwind.autosave.annotations.Storable;
import info.loenwind.autosave.annotations.Store;
import net.minecraft.entity.player.PlayerEntity;

import java.util.HashMap;

@Storable
public class PlayerDeathStatistics {

    @Store
    public HashMap<Elements, Float> dmg = new HashMap<Elements, Float>();

    @Store
    public boolean died = false;

    public static void record(PlayerEntity player, Elements ele, float amount) {
        PlayerDeathData stats = ModRegistry.COMPONENTS.PLAYER_DEATH_DATA.get(player);
        stats.deathStats.record(ele, amount);
    }

    public void record(Elements ele, float amount) {

        if (died) {
            clear();
            died = false;
        }

        float total = dmg.getOrDefault(ele, 0F) + amount;
        dmg.put(ele, total);
    }

    public void clear() {
        dmg.clear();
    }

}
