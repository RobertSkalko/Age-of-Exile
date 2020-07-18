package com.robertx22.mine_and_slash.uncommon.testing.tests;

import com.robertx22.mine_and_slash.database.base.Rarities;
import com.robertx22.mine_and_slash.loot.blueprints.GearBlueprint;
import com.robertx22.mine_and_slash.loot.generators.util.GearCreationUtils;
import com.robertx22.mine_and_slash.saveclasses.item_classes.GearItemData;
import org.apache.commons.lang3.time.StopWatch;

import java.util.HashMap;
import java.util.Map.Entry;

public class ItemGenRarityTest {

    int amount = 1000;

    public void GenManyItems() {

        StopWatch watch = new StopWatch();
        watch.start();

        HashMap<Integer, Integer> RarityandNumber = new HashMap<Integer, Integer>();

        GearBlueprint schema = new GearBlueprint(1);

        for (int i = 0; i < amount; i++) {

            GearItemData data = GearCreationUtils.CreateData(schema);

            if (RarityandNumber.containsKey(data.rarity)) {
                RarityandNumber.put(data.rarity, RarityandNumber.get(data.rarity) + 1);
            } else {
                RarityandNumber.put(data.rarity, 1);
            }

        }

        watch.stop();

        for (Entry<Integer, Integer> entry : RarityandNumber.entrySet()) {
            float percent = (float) entry.getValue() / (float) amount * 100;
            System.out.println("Rarity: " + Rarities.Gears.get(entry.getKey())
                .locName() + " has this many items: " + entry.getValue() + " , percent of total: " + Float.toString(
                percent));
        }
        System.out.println(
            "It took " + watch.getTime() + " miliseconds for " + amount + " items to addPieces, in other words it" +
                " took this many miliseconds for one item: " + watch
                .getTime() / amount);

    }
}
