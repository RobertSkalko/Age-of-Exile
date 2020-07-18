package com.robertx22.mine_and_slash.uncommon.interfaces.data_items;

import com.robertx22.exiled_lib.registry.SlashRegistry;

import java.util.Comparator;

public interface ITiered {

    public abstract int getTier();

    public static int getMaxTier() {
        if (Cached.maxTier == null) {
            Cached.maxTier = SlashRegistry.Tiers()
                .getList()
                .stream()
                .max(Comparator.comparingInt(x -> x.id_rank))
                .get().id_rank;
        }
        return Cached.maxTier;
    }

}
