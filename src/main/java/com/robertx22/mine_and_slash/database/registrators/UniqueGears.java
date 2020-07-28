package com.robertx22.mine_and_slash.database.registrators;

import com.robertx22.mine_and_slash.database.registry.ISlashRegistryInit;
import com.robertx22.mine_and_slash.database.data.unique_items.armor.BeastBloodChest;
import com.robertx22.mine_and_slash.database.data.unique_items.armor.ExecutionerLeatherChest;
import com.robertx22.mine_and_slash.database.data.unique_items.armor.InnerConfluxRobe;
import com.robertx22.mine_and_slash.database.data.unique_items.armor.JesterHat;
import com.robertx22.mine_and_slash.database.data.unique_items.jewelry.necklace.BirthingMiracleNecklace;
import com.robertx22.mine_and_slash.database.data.unique_items.jewelry.necklace.SkullOfSpiritsNecklace;
import com.robertx22.mine_and_slash.database.data.unique_items.jewelry.ring.GreedsPersistenceRing;
import com.robertx22.mine_and_slash.database.data.unique_items.jewelry.ring.LoopOfInfinityRing;
import com.robertx22.mine_and_slash.database.data.unique_items.weapons.axe.ObsidianMightAxe;
import com.robertx22.mine_and_slash.database.data.unique_items.weapons.sword.IncarnationOfThunderSword;
import com.robertx22.mine_and_slash.database.data.unique_items.weapons.sword.WaterElementalSword;
import com.robertx22.mine_and_slash.database.data.unique_items.weapons.wand.DivineMightScepter;
import com.robertx22.mine_and_slash.database.data.unique_items.weapons.wand.EyeOfZegrathWand;
import com.robertx22.mine_and_slash.database.data.unique_items.weapons.wand.WillOfFloraWand;

public class UniqueGears implements ISlashRegistryInit {

    @Override
    public void registerAll() {

        new WaterElementalSword().addToSerializables();
        new BirthingMiracleNecklace().addToSerializables();
        new SkullOfSpiritsNecklace().addToSerializables();
        new GreedsPersistenceRing().addToSerializables();
        new JesterHat().addToSerializables();
        new ObsidianMightAxe().addToSerializables();
        new InnerConfluxRobe().addToSerializables();
        new BeastBloodChest().addToSerializables();
        new LoopOfInfinityRing().addToSerializables();
        new WillOfFloraWand().addToSerializables();
        new EyeOfZegrathWand().addToSerializables();
        new ExecutionerLeatherChest().addToSerializables();
        new IncarnationOfThunderSword().addToSerializables();
        new DivineMightScepter().addToSerializables();

    }
}
