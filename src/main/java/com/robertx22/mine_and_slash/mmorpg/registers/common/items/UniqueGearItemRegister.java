package com.robertx22.mine_and_slash.mmorpg.registers.common.items;

import com.robertx22.mine_and_slash.database.data.unique_items.IUnique;
import com.robertx22.mine_and_slash.database.data.unique_items.armor.BeastBloodChest;
import com.robertx22.mine_and_slash.database.data.unique_items.armor.InnerConfluxRobe;
import com.robertx22.mine_and_slash.database.data.unique_items.armor.JesterHat;
import com.robertx22.mine_and_slash.database.data.unique_items.bases.*;
import com.robertx22.mine_and_slash.database.data.unique_items.jewelry.necklace.BirthingMiracleNecklace;
import com.robertx22.mine_and_slash.database.data.unique_items.jewelry.necklace.SkullOfSpiritsNecklace;
import com.robertx22.mine_and_slash.database.data.unique_items.jewelry.ring.GreedsPersistenceRing;
import com.robertx22.mine_and_slash.database.data.unique_items.weapons.axe.ObsidianMightAxe;
import com.robertx22.mine_and_slash.database.data.unique_items.weapons.sword.WaterElementalSword;
import com.robertx22.mine_and_slash.mmorpg.Ref;
import net.minecraft.item.Item;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class UniqueGearItemRegister {

    public static Item WATER_ELEMENTAL_SWORD = of(new BaseUniqueSword(), new WaterElementalSword());
    public static Item BIRTHING_MIRACLE_NECKLACE = of(new BaseUniqueNecklace(), new BirthingMiracleNecklace());
    public static Item SKULL_OF_SPIRITS_NECKLACE = of(new BaseUniqueNecklace(), new SkullOfSpiritsNecklace());
    public static Item GREEDS_PERSISTENCE_RING = of(new BaseUniqueRing(), new GreedsPersistenceRing());
    public static Item INNER_CONFLUX_ROBE = of(new BaseUniqueChest(), new InnerConfluxRobe());
    public static Item BEST_BLOOD_CHEST = of(new BaseUniqueChest(), new BeastBloodChest());

    public static Item JESTER_HAT = of(new BaseUniqueHelmet(), new JesterHat());
    public static Item OBSIDIAN_MIGHT_AXE = of(new BaseUniqueAxe(), new ObsidianMightAxe());

    static Item of(Item c, IUnique uniq) {
        return Registry.register(Registry.ITEM, new Identifier(Ref.MODID, uniq.getGeneratedResourceID()), c);
    }
}
