package com.robertx22.age_of_exile.aoe_data.database.tiers;

import com.robertx22.library_of_exile.registry.ExileRegistryInit;

public class DifficultyAdders implements ExileRegistryInit {

    @Override
    public void registerAll() {

        DifficultyBuilder.of("normal", 0, "Normal")
            .stats(1, 1, 1)
            .loot(1, 0)
            .deathsAllowed(5)
            .deathFavorPenalty(-10)
            .build();

        DifficultyBuilder.of("heroic", 1, "Heroic")
            .stats(1.5F, 1.5F, 1.25F)
            .loot(1.25F, 10)
            .deathsAllowed(4)
            .requiresPlayerLevel(25)
            .deathFavorPenalty(-20)
            .build();

        DifficultyBuilder.of("mythic", 2, "Mythic")
            .stats(2F, 2F, 1.5F)
            .loot(1.5F, 25)
            .deathsAllowed(3)
            .requiresPlayerLevel(40)
            .deathFavorPenalty(-50)
            .build();

    }
}
