package com.robertx22.mine_and_slash.database.registrators;

import com.robertx22.mine_and_slash.database.data.unique_items.weapons.axe.ObsidianMightAxe;
import com.robertx22.mine_and_slash.database.data.unique_items.weapons.sword.IncarnationOfThunderSword;
import com.robertx22.mine_and_slash.database.data.unique_items.weapons.sword.WaterElementalSword;
import com.robertx22.mine_and_slash.database.data.unique_items.weapons.wand.DivineMightScepter;
import com.robertx22.mine_and_slash.database.data.unique_items.weapons.wand.EyeOfZegrathWand;
import com.robertx22.mine_and_slash.database.data.unique_items.weapons.wand.WillOfFloraWand;
import com.robertx22.mine_and_slash.database.registry.ISlashRegistryInit;

public class UniqueGears implements ISlashRegistryInit {

    @Override
    public void registerAll() {

        /*
        new BirthingMiracleNecklace().addToSerializables();
        new SkullOfSpiritsNecklace().addToSerializables();
        new GreedsPersistenceRing().addToSerializables();
        new JesterHat().addToSerializables();
        new InnerConfluxRobe().addToSerializables();
        new BeastBloodChest().addToSerializables();
        new LoopOfInfinityRing().addToSerializables();
        new ExecutionerLeatherChest().addToSerializables();

         */

        new WaterElementalSword().addToSerializables();
        new ObsidianMightAxe().addToSerializables();
        new WillOfFloraWand().addToSerializables();
        new EyeOfZegrathWand().addToSerializables();
        new IncarnationOfThunderSword().addToSerializables();
        new DivineMightScepter().addToSerializables();

    }
}
