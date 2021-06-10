package com.robertx22.age_of_exile.aoe_data.database.races;

import com.robertx22.age_of_exile.database.data.races.PlayerRace;
import com.robertx22.age_of_exile.database.registry.ISlashRegistryInit;

import java.util.Arrays;

public class Races implements ISlashRegistryInit {

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
