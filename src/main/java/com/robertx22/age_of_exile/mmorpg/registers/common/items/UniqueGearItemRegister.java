package com.robertx22.age_of_exile.mmorpg.registers.common.items;

import com.robertx22.age_of_exile.database.data.unique_items.bases.*;
import net.minecraft.item.Item;

public class UniqueGearItemRegister extends BaseItemRegistrator {

    public Item BEAST_BLOOD = item(new BaseUniqueChest("Plate Chest"), "uniques/armor/beast_blood");
    public Item EXEC_PRIDE = item(new BaseUniqueChest("Leather Chest"), "uniques/armor/exec_pride");
    public Item INNFER_CONFLUX_ROBE = item(new BaseUniqueChest("Cloth Chest"), "uniques/armor/inner_conflux");

    public Item JESTER_HAT = item(new BaseUniqueHelmet("Cloth Hat"), "uniques/armor/jester_hat");

    public Item ANGEL_PROT_NECKLACE = item(new BaseUniqueNecklace("Necklace"), "uniques/jewelry/angel_prot_necklace");
    public Item BIRTH_MIRACLE_NECKLACE = item(new BaseUniqueNecklace("Necklace"), "uniques/jewelry/birth_miracle");
    public Item SKULL_OF_SPIRITS_NECKLACE = item(new BaseUniqueNecklace("Necklace"), "uniques/jewelry/skull_of_spirits");

    public Item GREED_PERSIST_RING = item(new BaseUniqueRing("Ring"), "uniques/jewelry/greed_persist");
    public Item LOOP_OF_INFINITY_RING = item(new BaseUniqueRing("Ring"), "uniques/jewelry/loop_of_infinity");

    public Item DIVINE_MIGHT_WAND = item(new BaseUniqueWand("Wand"), "uniques/weapon/divine_might");
    public Item EZE_OF_ZEGRATH_WAND = item(new BaseUniqueWand("Wand"), "uniques/weapon/eye_of_zegrath");
    public Item WILL_OF_FLORA_WAND = item(new BaseUniqueWand("Wand"), "uniques/weapon/will_of_flora");

    public Item INCA_THUNDER_SWORD = item(new BaseUniqueSword("Sword"), "uniques/weapon/inca_thunder");
    public Item WATER_ELE_SWORD = item(new BaseUniqueSword("Sword"), "uniques/weapon/water_ele_sword");

    public Item OBSI_MIGHT_AXE = item(new BaseUniqueSword("Axe"), "uniques/weapon/obsi_might");

}
