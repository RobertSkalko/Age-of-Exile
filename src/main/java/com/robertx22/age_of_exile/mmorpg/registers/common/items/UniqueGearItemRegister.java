package com.robertx22.age_of_exile.mmorpg.registers.common.items;

import com.robertx22.age_of_exile.database.data.unique_items.bases.*;
import com.robertx22.age_of_exile.database.registrators.LevelRanges;
import com.robertx22.age_of_exile.vanilla_mc.items.gearitems.bases.BaseArmorItem;
import com.robertx22.age_of_exile.vanilla_mc.items.gearitems.bases.armor_materials.ArmorTier;
import com.robertx22.age_of_exile.vanilla_mc.items.gearitems.bases.armor_materials.ArmorType;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.Item;

public class UniqueGearItemRegister extends BaseItemRegistrator {

    public ArmorSet OAK_SET = new ArmorSet("oak", LevelRanges.HIGH, ArmorType.PLATE);
    public ArmorSet BONE_SET = new ArmorSet("bone", LevelRanges.HIGH, ArmorType.LEATHER);
    public ArmorSet BLAZE_SET = new ArmorSet("blaze", LevelRanges.HIGH, ArmorType.CLOTH);
    public ArmorSet SLIME_SET = new ArmorSet("slime", LevelRanges.HIGH, ArmorType.CLOTH);
    public ArmorSet DARK_CRYSTAL_SET = new ArmorSet("dark_crystal", LevelRanges.HIGH, ArmorType.PLATE);
    public ArmorSet VOID_SET = new ArmorSet("void", LevelRanges.HIGH, ArmorType.CLOTH);

    public Item BEAST_BLOOD = item(new BaseArmorItem(ArmorTier.from(LevelRanges.ENDGAME), ArmorType.PLATE, "Plate Chest", EquipmentSlot.CHEST, true), "uniques/armor/beast_blood");
    public Item EXEC_PRIDE = item(new BaseArmorItem(ArmorTier.from(LevelRanges.ENDGAME), ArmorType.LEATHER, "Leather Chest", EquipmentSlot.CHEST, true), "uniques/armor/exec_pride");
    public Item HARBINGER_CHEST = item(new BaseArmorItem(ArmorTier.from(LevelRanges.MIDDLE), ArmorType.LEATHER, "Leather Chest", EquipmentSlot.CHEST, true), "uniques/armor/harbinger");
    public Item INNFER_CONFLUX_ROBE = item(new BaseArmorItem(ArmorTier.from(LevelRanges.ENDGAME), ArmorType.CLOTH, "Cloth Chest", EquipmentSlot.CHEST, true), "uniques/armor/inner_conflux");
    public Item MS_REG_ARMOR_CHEST = item(new BaseArmorItem(ArmorTier.from(LevelRanges.MIDDLE), ArmorType.PLATE, "Plate chest", EquipmentSlot.CHEST, true), "uniques/armor/ms_armor_chest");
    public Item KINGMAKER_CHEST = item(new BaseArmorItem(ArmorTier.from(LevelRanges.HIGH), ArmorType.PLATE, "Plate chest", EquipmentSlot.CHEST, true), "uniques/armor/kingmaker");

    public Item FIFTH_RIDER_HELMET = item(new BaseArmorItem(ArmorTier.from(LevelRanges.HIGH), ArmorType.PLATE, "Plate Helmet", EquipmentSlot.HEAD, true), "uniques/armor/fifth_rider");
    public Item GLUTTONY_HELMET = item(new BaseArmorItem(ArmorTier.from(LevelRanges.LOW), ArmorType.PLATE, "Plate Helmet", EquipmentSlot.HEAD, true), "uniques/armor/gluttony_helmet");
    public Item JESTER_HAT = item(new BaseArmorItem(ArmorTier.from(LevelRanges.HIGH), ArmorType.CLOTH, "Cloth Hat", EquipmentSlot.HEAD, true), "uniques/armor/jester_hat");
    public Item FROST_CROWN = item(new BaseArmorItem(ArmorTier.from(LevelRanges.MIDDLE), ArmorType.CLOTH, "Cloth Hat", EquipmentSlot.HEAD, true), "uniques/armor/frost_crown");
    public Item CROWN_OF_ELEMENTS = item(new BaseArmorItem(ArmorTier.from(LevelRanges.ENDGAME), ArmorType.CLOTH, "Cloth Hat", EquipmentSlot.HEAD, true), "uniques/armor/crown_of_elements");

    public Item ANGEL_PROT_NECKLACE = item(new BaseUniqueNecklace("Necklace"), "uniques/jewelry/angel_prot_necklace");
    public Item GHAST_TEAR_NECKLACE = item(new BaseUniqueNecklace("Necklace"), "uniques/jewelry/ghast_necklace");
    public Item BIRTH_MIRACLE_NECKLACE = item(new BaseUniqueNecklace("Necklace"), "uniques/jewelry/birth_miracle");
    public Item SKULL_OF_SPIRITS_NECKLACE = item(new BaseUniqueNecklace("Necklace"), "uniques/jewelry/skull_of_spirits");
    public Item SNAKE_EYE_NECKLACE = item(new BaseUniqueNecklace("Necklace"), "uniques/jewelry/snake_eye");
    public Item HUNGER_NECKLACE = item(new BaseUniqueNecklace("Necklace"), "uniques/jewelry/hunger_neck");

    public Item GREED_PERSIST_RING = item(new BaseUniqueRing("Ring"), "uniques/jewelry/greed_persist");
    public Item WITCH_BREW_RING = item(new BaseUniqueRing("Ring"), "uniques/jewelry/witch_brew");
    public Item GOLD_RING = item(new BaseUniqueRing("Ring"), "uniques/jewelry/gold_ring");
    public Item LOOP_OF_INFINITY_RING = item(new BaseUniqueRing("Ring"), "uniques/jewelry/loop_of_infinity");
    public Item LUNAR_RING = item(new BaseUniqueRing("Ring"), "uniques/jewelry/lunar_ring");
    public Item SOLAR_RING = item(new BaseUniqueRing("Ring"), "uniques/jewelry/solar_ring");
    public Item TOUCH_OF_ETERNITY_RING = item(new BaseUniqueRing("Ring"), "uniques/jewelry/touch_of_eternity");

    public Item DIVINE_MIGHT_WAND = item(new BaseUniqueWand("Wand"), "uniques/weapon/divine_might");
    public Item EZE_OF_ZEGRATH_WAND = item(new BaseUniqueWand("Wand"), "uniques/weapon/eye_of_zegrath");
    public Item WILL_OF_FLORA_WAND = item(new BaseUniqueWand("Wand"), "uniques/weapon/will_of_flora");
    public Item ROT_PHYS = item(new BaseUniqueWand("Wand"), "uniques/weapon/rot_phys");
    public Item CRYSTAL_WAND = item(new BaseUniqueWand("Wand"), "uniques/weapon/crystal_wand");
    public Item FRIGID_STAFF = item(new BaseUniqueWand("Wand"), "uniques/weapon/frigid_staff");
    public Item WORLDBEARER_STAFF = item(new BaseUniqueWand("Wand"), "uniques/weapon/worldbearer");

    public Item INCA_THUNDER_SWORD = item(new BaseUniqueSword("Sword"), "uniques/weapon/inca_thunder");
    public Item WATER_ELE_SWORD = item(new BaseUniqueSword("Sword"), "uniques/weapon/water_ele_sword");

    public Item OBSI_MIGHT_AXE = item(new BaseUniqueAxe("Axe"), "uniques/weapon/obsi_might");
    public Item JUDGEMENT_AXE = item(new BaseUniqueAxe("Axe"), "uniques/weapon/judgement");

    public Item VOID_SWORD = item(new BaseUniqueSword("Sword"), "uniques/weapon/void_sword");

    public Item BONECHILL_RING_MI = item(new BaseUniqueRing("Ring"), "uniques/jewelry/bonechill_ring");
    public Item BONECHILL_AMULET_MI = item(new BaseUniqueRing("Amulet"), "uniques/jewelry/bonechill_amulet");

}
