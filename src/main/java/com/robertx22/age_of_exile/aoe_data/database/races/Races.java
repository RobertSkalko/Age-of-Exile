package com.robertx22.age_of_exile.aoe_data.database.races;

import com.robertx22.age_of_exile.database.data.races.PlayerRace;
import com.robertx22.library_of_exile.registry.ExileRegistryInit;

import java.util.Arrays;

public class Races implements ExileRegistryInit {

    @Override
    public void registerAll() {

        PlayerRace.of("empty", "empty", "",
            Arrays.asList(

            ),

            Arrays.asList(
            )
            ,
            Arrays.asList(

            )

        )
            .addToSerializables();

    }
}
