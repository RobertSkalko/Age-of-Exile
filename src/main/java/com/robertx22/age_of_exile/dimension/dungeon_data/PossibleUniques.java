package com.robertx22.age_of_exile.dimension.dungeon_data;

import com.robertx22.age_of_exile.database.data.rarities.GearRarity;
import com.robertx22.age_of_exile.database.data.unique_items.UniqueGear;
import com.robertx22.age_of_exile.database.registry.Database;
import com.robertx22.age_of_exile.uncommon.utilityclasses.RandomUtils;
import info.loenwind.autosave.annotations.Storable;
import info.loenwind.autosave.annotations.Store;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Storable
public class PossibleUniques {

    @Store
    public List<String> uniques = new ArrayList<>();

    public List<UniqueGear> getUniques() {
        return uniques.stream()
            .map(x -> Database.UniqueGears()
                .get(x))
            .collect(Collectors.toList());

    }

    public void randomize(int tier) {

        uniques.clear();

        List<GearRarity> list = Database.GearRarities()
            .getFiltered(x -> x.is_unique_item && tier > x.drops_after_tier);

        int amount = RandomUtils.RandomRange(3, 5);

        List<UniqueGear> gears = Database.UniqueGears()
            .getFiltered(x -> {
                GearRarity rar = Database.GearRarities()
                    .get(x.uniqueRarity);
                return list.contains(rar);
            });

        if (!gears.isEmpty()) {
            for (int u = 0; u < amount; u++) {
                UniqueGear uniq = RandomUtils.weightedRandom(gears);
                gears.remove(uniq); // no same twice
                uniques.add(uniq.GUID());
            }
        }

    }

}
