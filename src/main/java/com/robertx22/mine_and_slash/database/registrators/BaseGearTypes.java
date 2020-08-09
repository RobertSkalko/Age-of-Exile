package com.robertx22.mine_and_slash.database.registrators;

import com.robertx22.mine_and_slash.database.data.StatModifier;
import com.robertx22.mine_and_slash.database.data.gearitemslots.bases.BaseGearType;
import com.robertx22.mine_and_slash.database.data.gearitemslots.weapons.melee.BaseAxe;
import com.robertx22.mine_and_slash.database.data.gearitemslots.weapons.melee.BaseSword;
import com.robertx22.mine_and_slash.database.data.gearitemslots.weapons.melee.BaseWand;
import com.robertx22.mine_and_slash.database.data.stats.types.resources.ManaOnHit;
import com.robertx22.mine_and_slash.database.registry.ISlashRegistryInit;
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
    public static BaseGearType HIGH_WAND = new BaseWand("energizing_wand", LevelRanges.HIGH, "Energizing Wand") {
        @Override
        public List<StatModifier> implicitStats() {
            return Arrays.asList(new StatModifier(0.5F, 1.5F, ManaOnHit.getInstance(), ModType.FLAT));
        }
    };
    public static BaseGearType END_WAND = new BaseWand("ancenstor_wand", LevelRanges.ENDGAME, "Ancient Wand") {
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

                }

            }
        };

        All.forEach(x -> x.addToSerializables());

    }
}
