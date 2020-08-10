package com.robertx22.mine_and_slash.database.registrators;

import com.robertx22.mine_and_slash.database.data.StatModifier;
import com.robertx22.mine_and_slash.database.data.gearitemslots.bases.BaseGearType;
import com.robertx22.mine_and_slash.database.data.gearitemslots.curios.LifeNecklace;
import com.robertx22.mine_and_slash.database.data.gearitemslots.curios.LifeRing;
import com.robertx22.mine_and_slash.database.data.gearitemslots.leather.BaseLeatherBoots;
import com.robertx22.mine_and_slash.database.data.gearitemslots.leather.BaseLeatherChest;
import com.robertx22.mine_and_slash.database.data.gearitemslots.leather.BaseLeatherHelmet;
import com.robertx22.mine_and_slash.database.data.gearitemslots.leather.BaseLeatherPants;
import com.robertx22.mine_and_slash.database.data.gearitemslots.weapons.melee.BaseAxe;
import com.robertx22.mine_and_slash.database.data.gearitemslots.weapons.melee.BaseSword;
import com.robertx22.mine_and_slash.database.data.gearitemslots.weapons.melee.BaseWand;
import com.robertx22.mine_and_slash.database.data.stats.types.generated.ElementalResist;
import com.robertx22.mine_and_slash.database.registry.ISlashRegistryInit;
import com.robertx22.mine_and_slash.uncommon.enumclasses.Elements;
import com.robertx22.mine_and_slash.uncommon.enumclasses.ModType;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class BaseGearTypes implements ISlashRegistryInit {

    public static BaseGearType NEWBIE_WAND = new BaseWand("wood_stick_wand", LevelRanges.STARTER, "Wooden Stick Wand") {
    };
    public static BaseGearType LOW_WAND = new BaseWand("ancient_wand", LevelRanges.LOW, "Ancient Wand") {
    };
    public static BaseGearType MID_WAND = new BaseWand("sage_wand", LevelRanges.MIDDLE, "Sage Wand") {
    };
    public static BaseGearType HIGH_WAND = new BaseWand("crystal_wand", LevelRanges.HIGH, "Crystal Wand") {
    };
    public static BaseGearType END_WAND = new BaseWand("ancestor_wand", LevelRanges.ENDGAME, "Ancient Wand") {
    };
    //////////////////////////////////////
    public static BaseGearType NEWBIE_SWORD = new BaseSword("stick_sword", LevelRanges.STARTER, "Stick Sword") {
    };
    public static BaseGearType LOW_SWORD = new BaseSword("chipped_sword", LevelRanges.LOW, "Chipped Sword") {
    };
    public static BaseGearType MID_SWORD = new BaseSword("gemstone_sword", LevelRanges.MIDDLE, "Gemstone Sword") {
    };
    public static BaseGearType HIGH_SWORD = new BaseSword("clear_sword", LevelRanges.HIGH, "Clear Sword") {
    };
    public static BaseGearType END_SWORD = new BaseSword("royal_sword", LevelRanges.ENDGAME, "Royal Sword") {
    };
    //////////////////////////////////////
    public static BaseGearType NEWBIE_AXE = new BaseAxe("woodcutter_axe", LevelRanges.STARTER, "Woodcutter Axe") {
    };
    public static BaseGearType LOW_AXE = new BaseAxe("primitive_axe", LevelRanges.LOW, "Primitive Axe") {
    };
    public static BaseGearType MID_AXE = new BaseAxe("brutal_axe", LevelRanges.MIDDLE, "Brutal Axe") {
    };
    public static BaseGearType HIGH_AXE = new BaseAxe("soldier_axe", LevelRanges.HIGH, "Soldier Axe") {
    };
    public static BaseGearType END_AXE = new BaseAxe("gladiator_axe", LevelRanges.ENDGAME, "Gladiator Axe") {
    };

    static int minResist = 7;
    static int maxResist = 15;

    //////////////////////////////////////
    public static BaseGearType START_TO_LOW_HP_NECKLACE = new LifeNecklace("life_give_amulet", LevelRanges.START_TO_LOW, "Life Giving Amulet") {
    };
    public static BaseGearType MID_TO_END_HP_NECKLACE = new LifeNecklace("lifeblood_amulet", LevelRanges.MID_TO_END, "Lifeblood Amulet") {
    };
    //////////////////////////////////////
    public static BaseGearType START_TO_LOW_HP_RING_COLD = new LifeRing("cold_res_ring_low", LevelRanges.START_TO_LOW, "Aquamarine Ring") {
        @Override
        public List<StatModifier> baseStats() {
            return Arrays.asList(new StatModifier(minResist, maxResist, new ElementalResist(Elements.Water), ModType.FLAT));
        }
    };
    public static BaseGearType MID_TO_END_HP_RING_COLD = new LifeRing("cold_res_ring_end", LevelRanges.MID_TO_END, "Sapphire Ring") {
        @Override
        public List<StatModifier> baseStats() {
            return Arrays.asList(new StatModifier(minResist, maxResist, new ElementalResist(Elements.Water), ModType.FLAT));
        }
    };
    //////////////////////////////////////
    public static BaseGearType START_TO_LOW_HP_RING_FIRE = new LifeRing("fire_res_ring_low", LevelRanges.START_TO_LOW, "Coral Ring") {
        @Override
        public List<StatModifier> baseStats() {
            return Arrays.asList(new StatModifier(minResist, maxResist, new ElementalResist(Elements.Fire), ModType.FLAT));
        }
    };
    public static BaseGearType MID_TO_END_HP_RING_FIRE = new LifeRing("fire_res_ring_end", LevelRanges.MID_TO_END, "Ruby Ring") {
        @Override
        public List<StatModifier> baseStats() {
            return Arrays.asList(new StatModifier(minResist, maxResist, new ElementalResist(Elements.Fire), ModType.FLAT));
        }
    };
    //////////////////////////////////////
    public static BaseGearType START_TO_LOW_HP_RING_THUNDER = new LifeRing("thunder_res_ring_low", LevelRanges.START_TO_LOW, "Agate Ring") {
        @Override
        public List<StatModifier> baseStats() {
            return Arrays.asList(new StatModifier(minResist, maxResist, new ElementalResist(Elements.Thunder), ModType.FLAT));
        }
    };
    public static BaseGearType MID_TO_END_HP_RING_THUNDER = new LifeRing("thunder_res_ring_end", LevelRanges.MID_TO_END, "Topaz Ring") {
        @Override
        public List<StatModifier> baseStats() {
            return Arrays.asList(new StatModifier(minResist, maxResist, new ElementalResist(Elements.Thunder), ModType.FLAT));
        }
    };
    //////////////////////////////////////
    public static BaseGearType START_TO_LOW_HP_RING_POISON = new LifeRing("poison_res_ring_low", LevelRanges.START_TO_LOW, "Opal Ring") {
        @Override
        public List<StatModifier> baseStats() {
            return Arrays.asList(new StatModifier(minResist, maxResist, new ElementalResist(Elements.Nature), ModType.FLAT));
        }
    };
    public static BaseGearType MID_TO_END_HP_RING_POISON = new LifeRing("poison_res_ring_end", LevelRanges.MID_TO_END, "Emerald Ring") {
        @Override
        public List<StatModifier> baseStats() {
            return Arrays.asList(new StatModifier(minResist, maxResist, new ElementalResist(Elements.Nature), ModType.FLAT));
        }
    };

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
    public static BaseGearType NEWBIE_LEATHER_HELMET = new BaseLeatherHelmet("leather_helmet0", LevelRanges.STARTER, "Old Helmet") {
    };
    public static BaseGearType LOW_LEATHER_HELMET = new BaseLeatherHelmet("leather_helmet1", LevelRanges.LOW, "Horned Helmet") {
    };
    public static BaseGearType MID_LEATHER_HELMET = new BaseLeatherHelmet("leather_helmet2", LevelRanges.MIDDLE, "Scout Helmet") {
    };
    public static BaseGearType HIGH_LEATHER_HELMET = new BaseLeatherHelmet("leather_helmet3", LevelRanges.HIGH, "Protective Helmet") {
    };
    public static BaseGearType END_LEATHER_HELMET = new BaseLeatherHelmet("leather_helmet4", LevelRanges.ENDGAME, "Full Leather Helmet") {
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
                }

            }
        };

        All.forEach(x -> x.addToSerializables());

    }
}
