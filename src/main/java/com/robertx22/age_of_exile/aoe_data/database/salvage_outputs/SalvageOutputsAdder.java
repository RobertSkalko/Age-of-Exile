package com.robertx22.age_of_exile.aoe_data.database.salvage_outputs;

import com.robertx22.age_of_exile.database.data.salvage_outputs.SalvageOutput;
import com.robertx22.age_of_exile.database.data.salvage_outputs.WeightedItem;
import com.robertx22.age_of_exile.database.registrators.LevelRanges;
import com.robertx22.age_of_exile.mmorpg.registers.common.items.SlashItems;
import com.robertx22.library_of_exile.registry.ExileRegistryInit;

import java.util.Arrays;

public class SalvageOutputsAdder implements ExileRegistryInit {

    @Override
    public void registerAll() {

        new SalvageOutput(
            Arrays.asList(new WeightedItem(SlashItems.T0_DUST(), 1000))
            , "t0", LevelRanges.STARTER).addToSerializables();

        new SalvageOutput(
            Arrays.asList(new WeightedItem(SlashItems.T1_DUST(), 1000))
            , "t1", LevelRanges.LOW).addToSerializables();

        new SalvageOutput(
            Arrays.asList(new WeightedItem(SlashItems.T2_DUST(), 1000))
            , "t2", LevelRanges.MIDDLE).addToSerializables();

        new SalvageOutput(
            Arrays.asList(new WeightedItem(SlashItems.T3_DUST(), 1000))
            , "t3", LevelRanges.HIGH).addToSerializables();

        new SalvageOutput(
            Arrays.asList(new WeightedItem(SlashItems.T4_DUST(), 1000))
            , "t4", LevelRanges.ENDGAME).addToSerializables();

    }
}
