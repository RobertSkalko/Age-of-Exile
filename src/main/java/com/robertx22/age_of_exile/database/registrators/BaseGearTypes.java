package com.robertx22.age_of_exile.database.registrators;

import com.robertx22.age_of_exile.database.data.gear_types.bases.BaseGearType;
import com.robertx22.age_of_exile.database.data.gear_types.cloth.BaseClothBoots;
import com.robertx22.age_of_exile.database.data.gear_types.cloth.BaseClothChest;
import com.robertx22.age_of_exile.database.data.gear_types.cloth.BaseClothHelmet;
import com.robertx22.age_of_exile.database.data.gear_types.cloth.BaseClothPants;
import com.robertx22.age_of_exile.database.data.gear_types.plate.BasePlateBoots;
import com.robertx22.age_of_exile.database.data.gear_types.plate.BasePlateChest;
import com.robertx22.age_of_exile.database.data.gear_types.plate.BasePlateHelmet;
import com.robertx22.age_of_exile.database.data.gear_types.plate.BasePlatePants;
import com.robertx22.age_of_exile.database.registry.ISlashRegistryInit;

import java.util.ArrayList;
import java.util.List;

public class BaseGearTypes implements ISlashRegistryInit {

    //////////////////////////////////////
    public static BaseGearType NEWBIE_CLOTH_BOOTS = new BaseClothBoots("cloth_boots0", LevelRanges.STARTER, "Old Shoes") {
    };
    public static BaseGearType LOW_CLOTH_BOOTS = new BaseClothBoots("cloth_boots1", LevelRanges.LOW, "Simple Shoes") {
    };
    public static BaseGearType MID_CLOTH_BOOTS = new BaseClothBoots("cloth_boots2", LevelRanges.MIDDLE, "Full Shoes") {
    };
    public static BaseGearType HIGH_CLOTH_BOOTS = new BaseClothBoots("cloth_boots3", LevelRanges.HIGH, "Expensive Shoes") {
    };
    public static BaseGearType END_CLOTH_BOOTS = new BaseClothBoots("cloth_boots4", LevelRanges.ENDGAME, "Arcana Shoes") {
    };
    //////////////////////////////////////
    public static BaseGearType NEWBIE_CLOTH_PANTS = new BaseClothPants("cloth_pants0", LevelRanges.STARTER, "Old Leggings") {
    };
    public static BaseGearType LOW_CLOTH_PANTS = new BaseClothPants("cloth_pants1", LevelRanges.LOW, "Cloth Leggings") {
    };
    public static BaseGearType MID_CLOTH_PANTS = new BaseClothPants("cloth_pants2", LevelRanges.MIDDLE, "Cloth Leggings") {
    };
    public static BaseGearType HIGH_CLOTH_PANTS = new BaseClothPants("cloth_pants3", LevelRanges.HIGH, "Essence Leggings") {
    };
    public static BaseGearType END_CLOTH_PANTS = new BaseClothPants("cloth_pants4", LevelRanges.ENDGAME, "Arcana Leggings") {
    };

    //////////////////////////////////////
    public static BaseGearType NEWBIE_CLOTH_CHEST = new BaseClothChest("cloth_chest0", LevelRanges.STARTER, "Peasant Robe") {
    };
    public static BaseGearType LOW_CLOTH_CHEST = new BaseClothChest("cloth_chest1", LevelRanges.LOW, "Simple Robe") {
    };
    public static BaseGearType MID_CLOTH_CHEST = new BaseClothChest("cloth_chest2", LevelRanges.MIDDLE, "High Elf Robe") {
    };
    public static BaseGearType HIGH_CLOTH_CHEST = new BaseClothChest("cloth_chest3", LevelRanges.HIGH, "Cloaked Robe") {
    };
    public static BaseGearType END_CLOTH_CHEST = new BaseClothChest("cloth_chest4", LevelRanges.ENDGAME, "Arcana Robe") {
    };
    //////////////////////////////////////
    public static BaseGearType NEWBIE_CLOTH_HELMET = new BaseClothHelmet("cloth_helmet0", LevelRanges.STARTER, "Old Hat") {
    };
    public static BaseGearType LOW_CLOTH_HELMET = new BaseClothHelmet("cloth_helmet1", LevelRanges.LOW, "Cloth Hat") {
    };
    public static BaseGearType MID_CLOTH_HELMET = new BaseClothHelmet("cloth_helmet2", LevelRanges.MIDDLE, "Magi Hat") {
    };
    public static BaseGearType HIGH_CLOTH_HELMET = new BaseClothHelmet("cloth_helmet3", LevelRanges.HIGH, "Sacrificial Hat") {
    };
    public static BaseGearType END_CLOTH_HELMET = new BaseClothHelmet("cloth_helmet4", LevelRanges.ENDGAME, "Arcana Hat") {
    };

    //////////////////////////////////////
    public static BaseGearType NEWBIE_PLATE_BOOTS = new BasePlateBoots("plate_boots0", LevelRanges.STARTER, "Old Footwear") {
    };
    public static BaseGearType LOW_PLATE_BOOTS = new BasePlateBoots("plate_boots1", LevelRanges.LOW, "Simple Footwear") {
    };
    public static BaseGearType MID_PLATE_BOOTS = new BasePlateBoots("plate_boots2", LevelRanges.MIDDLE, "Full Footwear") {
    };
    public static BaseGearType HIGH_PLATE_BOOTS = new BasePlateBoots("plate_boots3", LevelRanges.HIGH, "Gladiator Footwear") {
    };
    public static BaseGearType END_PLATE_BOOTS = new BasePlateBoots("plate_boots4", LevelRanges.ENDGAME, "Royal Footwear") {
    };
    //////////////////////////////////////
    public static BaseGearType NEWBIE_PLATE_PANTS = new BasePlatePants("plate_pants0", LevelRanges.STARTER, "Old Greaves") {
    };
    public static BaseGearType LOW_PLATE_PANTS = new BasePlatePants("plate_pants1", LevelRanges.LOW, "Plate Greaves") {
    };
    public static BaseGearType MID_PLATE_PANTS = new BasePlatePants("plate_pants2", LevelRanges.MIDDLE, "Full Plate Greaves") {
    };
    public static BaseGearType HIGH_PLATE_PANTS = new BasePlatePants("plate_pants3", LevelRanges.HIGH, "Soldier Greaves") {
    };
    public static BaseGearType END_PLATE_PANTS = new BasePlatePants("plate_pants4", LevelRanges.ENDGAME, "Royal Greaves") {
    };

    //////////////////////////////////////
    public static BaseGearType NEWBIE_PLATE_CHEST = new BasePlateChest("plate_chest0", LevelRanges.STARTER, "Old Chestplate") {
    };
    public static BaseGearType LOW_PLATE_CHEST = new BasePlateChest("plate_chest1", LevelRanges.LOW, "Peasant Chestplate") {
    };
    public static BaseGearType MID_PLATE_CHEST = new BasePlateChest("plate_chest2", LevelRanges.MIDDLE, "Full Plate Chestplate") {
    };
    public static BaseGearType HIGH_PLATE_CHEST = new BasePlateChest("plate_chest3", LevelRanges.HIGH, "Soldier Chestplate") {
    };
    public static BaseGearType END_PLATE_CHEST = new BasePlateChest("plate_chest4", LevelRanges.ENDGAME, "Royal Chestplate") {
    };
    //////////////////////////////////////
    public static BaseGearType NEWBIE_PLATE_HELMET = new BasePlateHelmet("plate_helmet0", LevelRanges.STARTER, "Old Helmet") {
    };
    public static BaseGearType LOW_PLATE_HELMET = new BasePlateHelmet("plate_helmet1", LevelRanges.LOW, "Horned Helmet") {
    };
    public static BaseGearType MID_PLATE_HELMET = new BasePlateHelmet("plate_helmet2", LevelRanges.MIDDLE, "Soldier Helmet") {
    };
    public static BaseGearType HIGH_PLATE_HELMET = new BasePlateHelmet("plate_helmet3", LevelRanges.HIGH, "Gladiator Helmet") {
    };
    public static BaseGearType END_PLATE_HELMET = new BasePlateHelmet("plate_helmet4", LevelRanges.ENDGAME, "Royal Helmet") {
    };

    @Override
    public void registerAll() {

        List<BaseGearType> All = new ArrayList<BaseGearType>() {
            {
                {
                    add(NEWBIE_WAND);
                    add(LOW_WAND);
                    add(MID_WAND);
                    add(HIGH_WAND);
                    add(END_WAND);

                    add(NEWBIE_SCEPTER);
                    add(LOW_SCEPTER);
                    add(MID_SCEPTER);
                    add(HIGH_SCEPTER);
                    add(END_SCEPTER);

                    add(NEWBIE_BOW);
                    add(LOW_BOW);
                    add(MID_BOW);
                    add(HIGH_BOW);
                    add(END_BOW);

                    add(NEWBIE_CROSSBOW);
                    add(LOW_CROSSBOW);
                    add(MID_CROSSBOW);
                    add(HIGH_CROSSBOW);
                    add(END_CROSSBOW);

                    add(NEWBIE_SWORD);
                    add(LOW_SWORD);
                    add(MID_SWORD);
                    add(HIGH_SWORD);
                    add(END_SWORD);

                    add(NEWBIE_AXE);
                    add(LOW_AXE);
                    add(MID_AXE);
                    add(HIGH_AXE);
                    add(END_AXE);

                    add(NEWBIE_LEATHER_BOOTS);
                    add(LOW_LEATHER_BOOTS);
                    add(MID_LEATHER_BOOTS);
                    add(HIGH_LEATHER_BOOTS);
                    add(END_LEATHER_BOOTS);

                    add(NEWBIE_LEATHER_CHEST);
                    add(LOW_LEATHER_CHEST);
                    add(MID_LEATHER_CHEST);
                    add(HIGH_LEATHER_CHEST);
                    add(END_LEATHER_CHEST);

                    add(NEWBIE_LEATHER_PANTS);
                    add(LOW_LEATHER_PANTS);
                    add(MID_LEATHER_PANTS);
                    add(HIGH_LEATHER_PANTS);
                    add(END_LEATHER_PANTS);

                    add(NEWBIE_LEATHER_HELMET);
                    add(LOW_LEATHER_HELMET);
                    add(MID_LEATHER_HELMET);
                    add(HIGH_LEATHER_HELMET);
                    add(END_LEATHER_HELMET);

                    add(NEWBIE_CLOTH_BOOTS);
                    add(LOW_CLOTH_BOOTS);
                    add(MID_CLOTH_BOOTS);
                    add(HIGH_CLOTH_BOOTS);
                    add(END_CLOTH_BOOTS);

                    add(NEWBIE_CLOTH_CHEST);
                    add(LOW_CLOTH_CHEST);
                    add(MID_CLOTH_CHEST);
                    add(HIGH_CLOTH_CHEST);
                    add(END_CLOTH_CHEST);

                    add(NEWBIE_CLOTH_PANTS);
                    add(LOW_CLOTH_PANTS);
                    add(MID_CLOTH_PANTS);
                    add(HIGH_CLOTH_PANTS);
                    add(END_CLOTH_PANTS);

                    add(NEWBIE_CLOTH_HELMET);
                    add(LOW_CLOTH_HELMET);
                    add(MID_CLOTH_HELMET);
                    add(HIGH_CLOTH_HELMET);
                    add(END_CLOTH_HELMET);

                    add(NEWBIE_PLATE_BOOTS);
                    add(LOW_PLATE_BOOTS);
                    add(MID_PLATE_BOOTS);
                    add(HIGH_PLATE_BOOTS);
                    add(END_PLATE_BOOTS);

                    add(NEWBIE_PLATE_CHEST);
                    add(LOW_PLATE_CHEST);
                    add(MID_PLATE_CHEST);
                    add(HIGH_PLATE_CHEST);
                    add(END_PLATE_CHEST);

                    add(NEWBIE_PLATE_PANTS);
                    add(LOW_PLATE_PANTS);
                    add(MID_PLATE_PANTS);
                    add(HIGH_PLATE_PANTS);
                    add(END_PLATE_PANTS);

                    add(NEWBIE_PLATE_HELMET);
                    add(LOW_PLATE_HELMET);
                    add(MID_PLATE_HELMET);
                    add(HIGH_PLATE_HELMET);
                    add(END_PLATE_HELMET);

                    add(NEWBIE_ARMOR_SHIELD);
                    add(LOW_ARMOR_SHIELD);
                    add(MID_ARMOR_SHIELD);
                    add(HIGH_ARMOR_SHIELD);
                    add(END_ARMOR_SHIELD);

                    add(NEWBIE_MAGIC_SHIELD);
                    add(LOW_MAGIC_SHIELD);
                    add(MID_MAGIC_SHIELD);
                    add(HIGH_MAGIC_SHIELD);
                    add(END_MAGIC_SHIELD);

                    add(NEWBIE_DODGE_SHIELD);
                    add(LOW_DODGE_SHIELD);
                    add(MID_DODGE_SHIELD);
                    add(HIGH_DODGE_SHIELD);
                    add(END_DODGE_SHIELD);

                    add(START_TO_LOW_HP_NECKLACE);
                    add(MID_TO_END_HP_NECKLACE);

                    add(START_TO_LOW_ALL_RES_NECKLACE);
                    add(MID_TO_END_ALL_RES_NECKLACE);

                    add(START_TO_LOW_HP_RING_COLD);
                    add(MID_TO_END_HP_RING_COLD);

                    add(START_TO_LOW_HP_RING_FIRE);
                    add(MID_TO_END_HP_RING_FIRE);

                    add(START_TO_LOW_HP_RING_THUNDER);
                    add(MID_TO_END_HP_RING_THUNDER);

                    add(START_TO_LOW_HP_RING_POISON);
                    add(MID_TO_END_HP_RING_POISON);

                    add(START_TO_LOW_HP_RING_MANA_REG);
                    add(MID_TO_END_HP_RING_MANA_REG);

                }

            }
        };

        All.forEach(x -> x.addToSerializables());

    }
}
