package com.robertx22.mine_and_slash.mmorpg.registers.common.items;

import com.robertx22.mine_and_slash.database.data.unique_items.IUnique;
import com.robertx22.mine_and_slash.database.data.unique_items.armor.BeastBloodChest;
import com.robertx22.mine_and_slash.database.data.unique_items.armor.ExecutionerLeatherChest;
import com.robertx22.mine_and_slash.database.data.unique_items.armor.InnerConfluxRobe;
import com.robertx22.mine_and_slash.database.data.unique_items.armor.JesterHat;
import com.robertx22.mine_and_slash.database.data.unique_items.bases.*;
import com.robertx22.mine_and_slash.database.data.unique_items.jewelry.necklace.BirthingMiracleNecklace;
import com.robertx22.mine_and_slash.database.data.unique_items.jewelry.necklace.SkullOfSpiritsNecklace;
import com.robertx22.mine_and_slash.database.data.unique_items.jewelry.ring.GreedsPersistenceRing;
import com.robertx22.mine_and_slash.database.data.unique_items.jewelry.ring.LoopOfInfinityRing;
import com.robertx22.mine_and_slash.database.data.unique_items.weapons.axe.ObsidianMightAxe;
import com.robertx22.mine_and_slash.database.data.unique_items.weapons.sword.IncarnationOfThunderSword;
import com.robertx22.mine_and_slash.database.data.unique_items.weapons.sword.WaterElementalSword;
import com.robertx22.mine_and_slash.database.data.unique_items.weapons.wand.EyeOfZegrathWand;
import com.robertx22.mine_and_slash.database.data.unique_items.weapons.wand.WillOfFloraWand;
import com.robertx22.mine_and_slash.mmorpg.Ref;
import net.minecraft.item.Item;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class UniqueGearItemRegister {

    public Item WATER_ELEMENTAL_SWORD = of(new BaseUniqueSword(), new WaterElementalSword());
    public Item BIRTHING_MIRACLE_NECKLACE = of(new BaseUniqueNecklace(), new BirthingMiracleNecklace());
    public Item SKULL_OF_SPIRITS_NECKLACE = of(new BaseUniqueNecklace(), new SkullOfSpiritsNecklace());
    public Item GREEDS_PERSISTENCE_RING = of(new BaseUniqueRing(), new GreedsPersistenceRing());
    public Item INNER_CONFLUX_ROBE = of(new BaseUniqueChest(), new InnerConfluxRobe());
    public Item BEST_BLOOD_CHEST = of(new BaseUniqueChest(), new BeastBloodChest());
    public Item LOOP_OF_INFINITY = of(new BaseUniqueRing(), new LoopOfInfinityRing());
    public Item WILL_OF_FLORA = of(new BaseUniqueWand(), new WillOfFloraWand());
    public Item EYE_OF_ZEGRATH = of(new BaseUniqueWand(), new EyeOfZegrathWand());
    public Item EXE_PRIDE = of(new BaseUniqueChest(), new ExecutionerLeatherChest());
    public Item INCA_THUNDER = of(new BaseUniqueSword(), new IncarnationOfThunderSword());

    public Item JESTER_HAT = of(new BaseUniqueHelmet(), new JesterHat());
    public Item OBSIDIAN_MIGHT_AXE = of(new BaseUniqueAxe(), new ObsidianMightAxe());

    Item of(Item c, IUnique uniq) {
        return Registry.register(Registry.ITEM, new Identifier(Ref.MODID, uniq.getGeneratedResourceID()), c);
    }
}
