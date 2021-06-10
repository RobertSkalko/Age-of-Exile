package com.robertx22.age_of_exile.saveclasses.unit;

import com.robertx22.age_of_exile.database.data.mob_affixes.MobAffix;
import com.robertx22.age_of_exile.database.data.rarities.MobRarity;
import com.robertx22.age_of_exile.database.registry.Database;
import info.loenwind.autosave.annotations.Storable;
import info.loenwind.autosave.annotations.Store;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Storable
public class MobAffixesData {

    @Store
    public List<String> affixes = new ArrayList<>();

    public List<MobAffix> getAffixes() {
        try {
            return affixes.stream()
                .filter(x -> Database.MobAffixes()
                    .isRegistered(x))
                .map(x -> {
                    return Database.MobAffixes()
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

            this.affixes.add(Database.MobAffixes()
                .random()
                .GUID());
        }
    }
}
