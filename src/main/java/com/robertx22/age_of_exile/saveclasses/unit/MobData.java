package com.robertx22.age_of_exile.saveclasses.unit;

import com.robertx22.age_of_exile.database.data.mob_affixes.MobAffix;
import com.robertx22.age_of_exile.database.data.rarities.MobRarity;
import com.robertx22.age_of_exile.database.registry.ExileDB;
import info.loenwind.autosave.annotations.Storable;
import info.loenwind.autosave.annotations.Store;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Storable
public class MobData {

    @Store
    public List<String> affixes = new ArrayList<>();

    @Store
    public int player_hits = 0;

    public List<MobAffix> getAffixes() {
        try {
            return affixes.stream()
                .filter(x -> ExileDB.MobAffixes()
                    .isRegistered(x))
                .map(x -> {
                    return ExileDB.MobAffixes()
                        .get(x);
                })
                .collect(Collectors.toList());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Arrays.asList();
    }

    public void randomizeAffixes(MobRarity rarity) {

        int amount = rarity.affixes;

        this.affixes.clear();

        if (amount > 0) {

            this.affixes.add(ExileDB.MobAffixes()
                .random()
                .GUID());
        }
    }
}
