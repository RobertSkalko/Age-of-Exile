package com.robertx22.age_of_exile.saveclasses;

import com.robertx22.age_of_exile.capability.player.RPGPlayerData;
import com.robertx22.age_of_exile.uncommon.datasaving.Load;
import com.robertx22.age_of_exile.uncommon.enumclasses.Elements;
import info.loenwind.autosave.annotations.Storable;
import info.loenwind.autosave.annotations.Store;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.BlockPos;

import java.util.HashMap;

@Storable
public class DeathStatsData {
    @Store
    public BlockPos deathPos;
    @Store
    public String deathDim;

    @Store
    public HashMap<Elements, Float> dmg = new HashMap<Elements, Float>();

    @Store
    public boolean died = false;

    public static void record(PlayerEntity player, Elements ele, float amount) {
        RPGPlayerData stats = Load.playerRPGData(player);
        Elements element = ele == null ? Elements.Physical : ele;
        stats.deathStats.record(element, amount);
    }

    private void record(Elements ele, float amount) {

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
