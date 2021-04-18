package com.robertx22.age_of_exile.mmorpg.registers.common.items;

import com.robertx22.age_of_exile.database.data.unique_items.bases.*;
import com.robertx22.age_of_exile.database.registrators.LevelRanges;
import com.robertx22.age_of_exile.vanilla_mc.items.gearitems.bases.armor_materials.ArmorType;
import net.minecraft.item.Item;

public class UniqueGearItemRegister extends BaseItemRegistrator {

    public ArmorSet OAK_SET = new ArmorSet("oak", LevelRanges.HIGH, ArmorType.PLATE);
    public ArmorSet BONE_SET = new ArmorSet("bone", LevelRanges.HIGH, ArmorType.LEATHER);
    public ArmorSet BLAZE_SET = new ArmorSet("blaze", LevelRanges.HIGH, ArmorType.CLOTH);
    public ArmorSet SLIME_SET = new ArmorSet("slime", LevelRanges.HIGH, ArmorType.CLOTH);
    public ArmorSet DARK_CRYSTAL_SET = new ArmorSet("dark_crystal", LevelRanges.HIGH, ArmorType.PLATE);
    public ArmorSet VOID_SET = new ArmorSet("void", LevelRanges.HIGH, ArmorType.CLOTH);
    public ArmorSet PHARAOH_SET = new ArmorSet("pharaoh", LevelRanges.ENDGAME, ArmorType.CLOTH);

    public Item ANGEL_PROT_NECKLACE = item(new BaseUniqueNecklace("Necklace"), "uniques/jewelry/angel_prot_necklace");
    public Item GHAST_TEAR_NECKLACE = item(new BaseUniqueNecklace("Necklace"), "uniques/jewelry/ghast_necklace");
    public Item BIRTH_MIRACLE_NECKLACE = item(new BaseUniqueNecklace("Necklace"), "uniques/jewelry/birth_miracle");
    public Item SKULL_OF_SPIRITS_NECKLACE = item(new BaseUniqueNecklace("Necklace"), "uniques/jewelry/skull_of_spirits");
    public Item SNAKE_EYE_NECKLACE = item(new BaseUniqueNecklace("Necklace"), "uniques/jewelry/snake_eye");
    public Item HUNGER_NECKLACE = item(new BaseUniqueNecklace("Necklace"), "uniques/jewelry/hunger_neck");

    public Item WITCH_BREW_RING = item(new BaseUniqueRing("Ring"), "uniques/jewelry/witch_brew");
    public Item GOLD_RING = item(new BaseUniqueRing("Ring"), "uniques/jewelry/gold_ring");
    public Item AZUNA_RING = item(new BaseUniqueRing("Ring"), "uniques/jewelry/azuna_ring");
    public Item LOOP_OF_INFINITY_RING = item(new BaseUniqueRing("Ring"), "uniques/jewelry/loop_of_infinity");

    public Item DIVINE_MIGHT_WAND = item(new BaseUniqueWand("Wand"), "uniques/weapon/divine_might");
    public Item EZE_OF_ZEGRATH_WAND = item(new BaseUniqueWand("Wand"), "uniques/weapon/eye_of_zegrath");
    public Item WILL_OF_FLORA_WAND = item(new BaseUniqueWand("Wand"), "uniques/weapon/will_of_flora");
    public Item ROT_PHYS = item(new BaseUniqueWand("Wand"), "uniques/weapon/rot_phys");
    public Item CRYSTAL_WAND = item(new BaseUniqueWand("Wand"), "uniques/weapon/crystal_wand");
    public Item FRIGID_STAFF = item(new BaseUniqueWand("Wand"), "uniques/weapon/frigid_staff");
    public Item WORLDBEARER_STAFF = item(new BaseUniqueWand("Wand"), "uniques/weapon/worldbearer");

    public Item INCA_THUNDER_SWORD = item(new BaseUniqueSword(), "uniques/weapon/inca_thunder");
    public Item WATER_ELE_SWORD = item(new BaseUniqueSword(), "uniques/weapon/water_ele_sword");

    public Item OBSI_MIGHT_AXE = item(new BaseUniqueAxe("Axe"), "uniques/weapon/obsi_might");
    public Item JUDGEMENT_AXE = item(new BaseUniqueAxe("Axe"), "uniques/weapon/judgement");

    public Item VOID_SWORD = item(new BaseUniqueSword(), "uniques/weapon/void_sword");

    public Item BONECHILL_RING_MI = item(new BaseUniqueRing("Ring"), "uniques/jewelry/bonechill_ring");
    public Item BONECHILL_AMULET_MI = item(new BaseUniqueRing("Amulet"), "uniques/jewelry/bonechill_amulet");

}
