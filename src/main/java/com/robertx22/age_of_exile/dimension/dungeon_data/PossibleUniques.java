package com.robertx22.age_of_exile.dimension.dungeon_data;

import com.robertx22.age_of_exile.database.data.rarities.GearRarity;
import com.robertx22.age_of_exile.database.data.tiers.base.Difficulty;
import com.robertx22.age_of_exile.database.data.unique_items.UniqueGear;
import com.robertx22.age_of_exile.database.registry.ExileDB;
import com.robertx22.library_of_exile.utils.RandomUtils;
import info.loenwind.autosave.annotations.Storable;
import info.loenwind.autosave.annotations.Store;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Storable
public class PossibleUniques {

    @Store
    public List<String> u = new ArrayList<>();

    public List<UniqueGear> getUniques() {
        return u.stream()
            .map(x -> ExileDB.UniqueGears()
                .get(x))
            .collect(Collectors.toList());

    }

    public void randomize(Difficulty diff) {

        u.clear();

        List<GearRarity> list = ExileDB.GearRarities()
            .getFiltered(x -> x.is_unique_item && diff.rank >= x.drops_after_tier);

        int amount = RandomUtils.RandomRange(3, 5);

        List<UniqueGear> gears = ExileDB.UniqueGears()
            .getFiltered(x -> {
                GearRarity rar = ExileDB.GearRarities()
                    .get(x.uniqueRarity);
                return list.contains(rar);
            });

        if (!gears.isEmpty()) {
            for (int u = 0; u < amount; u++) {
                UniqueGear uniq = RandomUtils.weightedRandom(gears);
                gears.remove(uniq); // no same twice
                this.u.add(uniq.GUID());
            }
        }

    }

}
