package com.robertx22.age_of_exile.uncommon.interfaces.data_items;

import com.robertx22.age_of_exile.database.data.tiers.base.Tier;
import com.robertx22.age_of_exile.database.registry.SlashRegistry;

import java.util.Comparator;
import java.util.Optional;

public interface ITiered {

    public abstract int getTier();

    public static int getMaxTier() {
        if (Cached.maxTier == null) {
            Optional<Tier> opt = SlashRegistry.Tiers()
                .getList()
                .stream()
                .max(Comparator.comparingInt(x -> x.id_rank));
            if (opt.isPresent()) {
                Cached.maxTier = opt.get().id_rank;
            } else {
                return 1;
            }

        }
        return Cached.maxTier;
    }

}
