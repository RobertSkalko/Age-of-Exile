package com.robertx22.age_of_exile.capability.player.data;

import com.robertx22.age_of_exile.capability.entity.EntityData;
import com.robertx22.age_of_exile.database.data.game_balance_config.GameBalanceConfig;
import com.robertx22.age_of_exile.database.registry.ExileDB;
import com.robertx22.age_of_exile.saveclasses.ExactStatData;
import com.robertx22.age_of_exile.uncommon.datasaving.Load;
import com.robertx22.age_of_exile.uncommon.enumclasses.ModType;
import info.loenwind.autosave.annotations.Storable;
import info.loenwind.autosave.annotations.Store;
import net.minecraft.entity.player.PlayerEntity;

import java.util.HashMap;
import java.util.Map;

@Storable
public class StatPointsData {

    @Store
    public HashMap<String, Integer> map = new HashMap<>();

    public void reset() {
        this.map.clear();
    }

    public void addStats(EntityData data) {
        map.entrySet()
            .forEach(x -> {
                float val = x.getValue();
                ExactStatData stat = ExactStatData.of(val, ExileDB.Stats()
                    .get(x.getKey()), ModType.FLAT, 1);
                stat.applyStats(data);
            });

    }

    public int getFreePoints(PlayerEntity player) {
        int total = (int) (Load.Unit(player)
            .getLevel() * GameBalanceConfig.get().STAT_POINTS_PER_LEVEL);

        int spent = 0;

        for (Map.Entry<String, Integer> x : map.entrySet()) {
            spent += x.getValue();
        }

        return total - spent;
    }
}
