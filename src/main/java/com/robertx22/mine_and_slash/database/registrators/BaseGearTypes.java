package com.robertx22.mine_and_slash.database.registrators;

import com.robertx22.mine_and_slash.database.data.StatModifier;
import com.robertx22.mine_and_slash.database.data.gearitemslots.bases.BaseGearType;
import com.robertx22.mine_and_slash.database.data.gearitemslots.cloth.BaseClothBoots;
import com.robertx22.mine_and_slash.database.data.gearitemslots.cloth.BaseClothChest;
import com.robertx22.mine_and_slash.database.data.gearitemslots.cloth.BaseClothHelmet;
import com.robertx22.mine_and_slash.database.data.gearitemslots.cloth.BaseClothPants;
import com.robertx22.mine_and_slash.database.data.gearitemslots.curios.LifeNecklace;
import com.robertx22.mine_and_slash.database.data.gearitemslots.curios.LifeRing;
import com.robertx22.mine_and_slash.database.data.gearitemslots.curios.OccultRing;
import com.robertx22.mine_and_slash.database.data.gearitemslots.leather.BaseLeatherBoots;
import com.robertx22.mine_and_slash.database.data.gearitemslots.leather.BaseLeatherChest;
import com.robertx22.mine_and_slash.database.data.gearitemslots.leather.BaseLeatherHelmet;
import com.robertx22.mine_and_slash.database.data.gearitemslots.leather.BaseLeatherPants;
import com.robertx22.mine_and_slash.database.data.gearitemslots.offhand.BaseArmorShield;
import com.robertx22.mine_and_slash.database.data.gearitemslots.offhand.BaseDodgeShield;
import com.robertx22.mine_and_slash.database.data.gearitemslots.offhand.BaseMagicShield;
import com.robertx22.mine_and_slash.database.data.gearitemslots.plate.BasePlateBoots;
import com.robertx22.mine_and_slash.database.data.gearitemslots.plate.BasePlateChest;
import com.robertx22.mine_and_slash.database.data.gearitemslots.plate.BasePlateHelmet;
import com.robertx22.mine_and_slash.database.data.gearitemslots.plate.BasePlatePants;
import com.robertx22.mine_and_slash.database.data.gearitemslots.weapons.BaseBow;
import com.robertx22.mine_and_slash.database.data.gearitemslots.weapons.melee.BaseAxe;
import com.robertx22.mine_and_slash.database.data.gearitemslots.weapons.melee.BaseScepter;
import com.robertx22.mine_and_slash.database.data.gearitemslots.weapons.melee.BaseSword;
import com.robertx22.mine_and_slash.database.data.gearitemslots.weapons.melee.BaseWand;
import com.robertx22.mine_and_slash.database.data.stats.types.generated.ElementalResist;
import com.robertx22.mine_and_slash.database.registry.ISlashRegistryInit;
import com.robertx22.mine_and_slash.mmorpg.ModRegistry;
import com.robertx22.mine_and_slash.uncommon.enumclasses.Elements;
import com.robertx22.mine_and_slash.uncommon.enumclasses.ModType;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class BaseGearTypes implements ISlashRegistryInit {

    public static BaseGearType NEWBIE_WAND = new BaseWand("wand0", LevelRanges.STARTER, "Rotten Wand") {
    };
    public static BaseGearType LOW_WAND = new BaseWand("wand1", LevelRanges.LOW, "Ancient Wand") {
    };
    public static BaseGearType MID_WAND = new BaseWand("wand2", LevelRanges.MIDDLE, "Sage Wand") {
    };
    public static BaseGearType HIGH_WAND = new BaseWand("wand3", LevelRanges.HIGH, "Crystal Wand") {
    };
    public static BaseGearType END_WAND = new BaseWand("wand4", LevelRanges.ENDGAME, "Ancestral Wand") {
    };

    public static BaseGearType NEWBIE_SCEPTER = new BaseScepter("scepter0", LevelRanges.STARTER, "Rotten Scepter") {
    };
    public static BaseGearType LOW_SCEPTER = new BaseScepter("scepter1", LevelRanges.LOW, "Ancient Scepter") {
    };
    public static BaseGearType MID_SCEPTER = new BaseScepter("scepter2", LevelRanges.MIDDLE, "Sage Scepter") {
    };
    public static BaseGearType HIGH_SCEPTER = new BaseScepter("scepter3", LevelRanges.HIGH, "Crystal Scepter") {
    };
    public static BaseGearType END_SCEPTER = new BaseScepter("scepter4", LevelRanges.ENDGAME, "Ancestral Scepter") {
    };

    //////////////////////////////////////
    public static BaseGearType NEWBIE_SWORD = new BaseSword("sword0", LevelRanges.STARTER, "Stick Sword") {
    };
    public static BaseGearType LOW_SWORD = new BaseSword("sword1", LevelRanges.LOW, "Short Sword") {
    };
    public static BaseGearType MID_SWORD = new BaseSword("sword2", LevelRanges.MIDDLE, "Gemstone Sword") {
    };
    public static BaseGearType HIGH_SWORD = new BaseSword("sword3", LevelRanges.HIGH, "Clear Sword") {
    };
    public static BaseGearType END_SWORD = new BaseSword("sword4", LevelRanges.ENDGAME, "Royal Sword") {
    };

    //////////////////////////////////////
    public static BaseGearType NEWBIE_BOW = new BaseBow("bow0", LevelRanges.STARTER, "Old Bow") {
    };
    public static BaseGearType LOW_BOW = new BaseBow("bow1", LevelRanges.LOW, "Short Bow") {
    };
    public static BaseGearType MID_BOW = new BaseBow("bow2", LevelRanges.MIDDLE, "Durable Bow") {
    };
    public static BaseGearType HIGH_BOW = new BaseBow("bow3", LevelRanges.HIGH, "Clear Bow") {
    };
    public static BaseGearType END_BOW = new BaseBow("bow4", LevelRanges.ENDGAME, "Royal Bow") {
    };

    //////////////////////////////////////
    public static BaseGearType NEWBIE_AXE = new BaseAxe("axe0", LevelRanges.STARTER, "Woodcutter Axe") {
    };
    public static BaseGearType LOW_AXE = new BaseAxe("axe1", LevelRanges.LOW, "Primitive Axe") {
    };
    public static BaseGearType MID_AXE = new BaseAxe("axe2", LevelRanges.MIDDLE, "Brutal Axe") {
    };
    public static BaseGearType HIGH_AXE = new BaseAxe("axe3", LevelRanges.HIGH, "Soldier Axe") {
    };
    public static BaseGearType END_AXE = new BaseAxe("axe4", LevelRanges.ENDGAME, "Royal Axe") {
    };
    //////////////////////////////////////
    public static BaseGearType NEWBIE_ARMOR_SHIELD = new BaseArmorShield("tower_shield0", LevelRanges.STARTER, "Old Tower Shield") {
    };
    public static BaseGearType LOW_ARMOR_SHIELD = new BaseArmorShield("tower_shield1", LevelRanges.LOW, "Rusty Tower Shield") {
    };
    public static BaseGearType MID_ARMOR_SHIELD = new BaseArmorShield("tower_shield2", LevelRanges.MIDDLE, "Sturdy Tower Shield") {
    };
    public static BaseGearType HIGH_ARMOR_SHIELD = new BaseArmorShield("tower_shield3", LevelRanges.HIGH, "Soldier Tower Shield") {
    };
    public static BaseGearType END_ARMOR_SHIELD = new BaseArmorShield("tower_shield4", LevelRanges.ENDGAME, "Royal Tower Shield") {
    };
    //////////////////////////////////////
    public static BaseGearType NEWBIE_MAGIC_SHIELD = new BaseMagicShield("spirit_shield0", LevelRanges.STARTER, "Old Spirit Shield") {
    };
    public static BaseGearType LOW_MAGIC_SHIELD = new BaseMagicShield("spirit_shield1", LevelRanges.LOW, "Rusty Spirit Shield") {
    };
    public static BaseGearType MID_MAGIC_SHIELD = new BaseMagicShield("spirit_shield2", LevelRanges.MIDDLE, "Sturdy Spirit Shield") {
    };
    public static BaseGearType HIGH_MAGIC_SHIELD = new BaseMagicShield("spirit_shield3", LevelRanges.HIGH, "Ancient Spirit Shield") {
    };
    public static BaseGearType END_MAGIC_SHIELD = new BaseMagicShield("spirit_shield4", LevelRanges.ENDGAME, "Ancestral Spirit Shield") {
    };

    //////////////////////////////////////
    public static BaseGearType NEWBIE_DODGE_SHIELD = new BaseDodgeShield("buckler_shield0", LevelRanges.STARTER, "Old Buckler") {
    };
    public static BaseGearType LOW_DODGE_SHIELD = new BaseDodgeShield("buckler_shield1", LevelRanges.LOW, "Rusty Buckler") {
    };
    public static BaseGearType MID_DODGE_SHIELD = new BaseDodgeShield("buckler_shield2", LevelRanges.MIDDLE, "Sturdy Buckler") {
    };
    public static BaseGearType HIGH_DODGE_SHIELD = new BaseDodgeShield("buckler_shield3", LevelRanges.HIGH, "Ancient Buckler") {
    };
    public static BaseGearType END_DODGE_SHIELD = new BaseDodgeShield("buckler_shield4", LevelRanges.ENDGAME, "Royal Buckler") {
    };

    static int minResist = 7;
    static int maxResist = 15;

    //////////////////////////////////////
    public static BaseGearType START_TO_LOW_HP_NECKLACE = new LifeNecklace("life_necklace0", LevelRanges.START_TO_LOW, "Life Giving Amulet") {
    }.setEssenceItem(ModRegistry.GEAR_MATERIALS.LIFE);
    public static BaseGearType MID_TO_END_HP_NECKLACE = new LifeNecklace("life_necklace1", LevelRanges.MID_TO_END, "Lifeblood Amulet") {
    }.setEssenceItem(ModRegistry.GEAR_MATERIALS.LIFE);
    //////////////////////////////////////
    public static BaseGearType START_TO_LOW_HP_RING_COLD = new LifeRing("cold_res_ring_low", LevelRanges.START_TO_LOW, "Aquamarine Ring") {
        @Override
        public List<StatModifier> baseStats() {
            return Arrays.asList(new StatModifier(minResist, maxResist, new ElementalResist(Elements.Water), ModType.FLAT));
        }
    }.setEssenceItem(ModRegistry.GEAR_MATERIALS.WATER);
    public static BaseGearType MID_TO_END_HP_RING_COLD = new LifeRing("cold_res_ring_end", LevelRanges.MID_TO_END, "Sapphire Ring") {
        @Override
        public List<StatModifier> baseStats() {
            return Arrays.asList(new StatModifier(minResist, maxResist, new ElementalResist(Elements.Water), ModType.FLAT));
        }
    }.setEssenceItem(ModRegistry.GEAR_MATERIALS.WATER);
    //////////////////////////////////////
    public static BaseGearType START_TO_LOW_HP_RING_FIRE = new LifeRing("fire_res_ring_low", LevelRanges.START_TO_LOW, "Coral Ring") {
        @Override
        public List<StatModifier> baseStats() {
            return Arrays.asList(new StatModifier(minResist, maxResist, new ElementalResist(Elements.Fire), ModType.FLAT));
        }
    }.setEssenceItem(ModRegistry.GEAR_MATERIALS.FIRE);
    public static BaseGearType MID_TO_END_HP_RING_FIRE = new LifeRing("fire_res_ring_end", LevelRanges.MID_TO_END, "Ruby Ring") {
        @Override
        public List<StatModifier> baseStats() {
            return Arrays.asList(new StatModifier(minResist, maxResist, new ElementalResist(Elements.Fire), ModType.FLAT));
        }
    }.setEssenceItem(ModRegistry.GEAR_MATERIALS.FIRE);
    //////////////////////////////////////
    public static BaseGearType START_TO_LOW_HP_RING_THUNDER = new LifeRing("thunder_res_ring_low", LevelRanges.START_TO_LOW, "Agate Ring") {
        @Override
        public List<StatModifier> baseStats() {
            return Arrays.asList(new StatModifier(minResist, maxResist, new ElementalResist(Elements.Thunder), ModType.FLAT));
        }
    }.setEssenceItem(ModRegistry.GEAR_MATERIALS.THUNDER);
    public static BaseGearType MID_TO_END_HP_RING_THUNDER = new LifeRing("thunder_res_ring_end", LevelRanges.MID_TO_END, "Topaz Ring") {
        @Override
        public List<StatModifier> baseStats() {
            return Arrays.asList(new StatModifier(minResist, maxResist, new ElementalResist(Elements.Thunder), ModType.FLAT));
        }
    }.setEssenceItem(ModRegistry.GEAR_MATERIALS.THUNDER);
    //////////////////////////////////////
    public static BaseGearType START_TO_LOW_HP_RING_POISON = new LifeRing("poison_res_ring_low", LevelRanges.START_TO_LOW, "Opal Ring") {
        @Override
        public List<StatModifier> baseStats() {
            return Arrays.asList(new StatModifier(minResist, maxResist, new ElementalResist(Elements.Nature), ModType.FLAT));
        }
    }.setEssenceItem(ModRegistry.GEAR_MATERIALS.NATURE);
    public static BaseGearType MID_TO_END_HP_RING_POISON = new LifeRing("poison_res_ring_end", LevelRanges.MID_TO_END, "Emerald Ring") {
        @Override
        public List<StatModifier> baseStats() {
            return Arrays.asList(new StatModifier(minResist, maxResist, new ElementalResist(Elements.Nature), ModType.FLAT));
        }
    }.setEssenceItem(ModRegistry.GEAR_MATERIALS.NATURE);

    //////////////////////////////////////
    public static BaseGearType START_TO_LOW_HP_RING_MANA_REG = new OccultRing("mana_reg_ring0", LevelRanges.START_TO_LOW, "Occult Ring") {

    }.setEssenceItem(ModRegistry.GEAR_MATERIALS.ARCANA);
    public static BaseGearType MID_TO_END_HP_RING_MANA_REG = new OccultRing("mana_reg_ring1", LevelRanges.MID_TO_END, "Arcana Ring") {
    }.setEssenceItem(ModRegistry.GEAR_MATERIALS.ARCANA);

    //////////////////////////////////////
    public static BaseGearType NEWBIE_LEATHER_BOOTS = new BaseLeatherBoots("leather_boots0", LevelRanges.STARTER, "Old Boots") {
    };
    public static BaseGearType LOW_LEATHER_BOOTS = new BaseLeatherBoots("leather_boots1", LevelRanges.LOW, "Simple Boots") {
    };
    public static BaseGearType MID_LEATHER_BOOTS = new BaseLeatherBoots("leather_boots2", LevelRanges.MIDDLE, "Full Boots") {
    };
    public static BaseGearType HIGH_LEATHER_BOOTS = new BaseLeatherBoots("leather_boots3", LevelRanges.HIGH, "Expensive Boots") {
    };
    public static BaseGearType END_LEATHER_BOOTS = new BaseLeatherBoots("leather_boots4", LevelRanges.ENDGAME, "Shiny Boots") {
    };
    //////////////////////////////////////
    public static BaseGearType NEWBIE_LEATHER_PANTS = new BaseLeatherPants("leather_pants0", LevelRanges.STARTER, "Old Pants") {
    };
    public static BaseGearType LOW_LEATHER_PANTS = new BaseLeatherPants("leather_pants1", LevelRanges.LOW, "Leather Shorts") {
    };
    public static BaseGearType MID_LEATHER_PANTS = new BaseLeatherPants("leather_pants2", LevelRanges.MIDDLE, "Leather Pants") {
    };
    public static BaseGearType HIGH_LEATHER_PANTS = new BaseLeatherPants("leather_pants3", LevelRanges.HIGH, "Gilded Leather Pants") {
    };
    public static BaseGearType END_LEATHER_PANTS = new BaseLeatherPants("leather_pants4", LevelRanges.ENDGAME, "Full Leather Pants") {
    };

    //////////////////////////////////////
    public static BaseGearType NEWBIE_LEATHER_CHEST = new BaseLeatherChest("leather_chest0", LevelRanges.STARTER, "Old Vest") {
    };
    public static BaseGearType LOW_LEATHER_CHEST = new BaseLeatherChest("leather_chest1", LevelRanges.LOW, "Leather Vest") {
    };
    public static BaseGearType MID_LEATHER_CHEST = new BaseLeatherChest("leather_chest2", LevelRanges.MIDDLE, "Iron Leather Vest") {
    };
    public static BaseGearType HIGH_LEATHER_CHEST = new BaseLeatherChest("leather_chest3", LevelRanges.HIGH, "Sturdy Leather Vest") {
    };
    public static BaseGearType END_LEATHER_CHEST = new BaseLeatherChest("leather_chest4", LevelRanges.ENDGAME, "Full Leather Vest") {
    };
    //////////////////////////////////////
    public static BaseGearType NEWBIE_LEATHER_HELMET = new BaseLeatherHelmet("leather_helmet0", LevelRanges.STARTER, "Old Hat") {
    };
    public static BaseGearType LOW_LEATHER_HELMET = new BaseLeatherHelmet("leather_helmet1", LevelRanges.LOW, "Leather Hat") {
    };
    public static BaseGearType MID_LEATHER_HELMET = new BaseLeatherHelmet("leather_helmet2", LevelRanges.MIDDLE, "Iron Leather Hat") {
    };
    public static BaseGearType HIGH_LEATHER_HELMET = new BaseLeatherHelmet("leather_helmet3", LevelRanges.HIGH, "Sturdy Leather Hat") {
    };
    public static BaseGearType END_LEATHER_HELMET = new BaseLeatherHelmet("leather_helmet4", LevelRanges.ENDGAME, "Full Leather Hat") {
    };

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
    public static BaseGearType HIGH_CLOTH_CHEST = new BaseClothChest("cloth_chest3", LevelRanges.HIGH, "Arcana Cloak") {
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
    public static BaseGearType NEWBIE_PLATE_BOOTS = new BasePlateBoots("plate_boots0", LevelRanges.STARTER, "Old Shoes") {
    };
    public static BaseGearType LOW_PLATE_BOOTS = new BasePlateBoots("plate_boots1", LevelRanges.LOW, "Simple Shoes") {
    };
    public static BaseGearType MID_PLATE_BOOTS = new BasePlateBoots("plate_boots2", LevelRanges.MIDDLE, "Full Shoes") {
    };
    public static BaseGearType HIGH_PLATE_BOOTS = new BasePlateBoots("plate_boots3", LevelRanges.HIGH, "Expensive Shoes") {
    };
    public static BaseGearType END_PLATE_BOOTS = new BasePlateBoots("plate_boots4", LevelRanges.ENDGAME, "Arcana Shoes") {
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
    public static BaseGearType HIGH_PLATE_HELMET = new BasePlateHelmet("plate_helmet3", LevelRanges.HIGH, "Gladiator Burgonet") {
    };
    public static BaseGearType END_PLATE_HELMET = new BasePlateHelmet("plate_helmet4", LevelRanges.ENDGAME, "Royal Burgonet") {
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
