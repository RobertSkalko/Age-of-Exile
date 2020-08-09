package com.robertx22.mine_and_slash.database.registrators;

import com.robertx22.mine_and_slash.database.data.StatModifier;
import com.robertx22.mine_and_slash.database.data.gearitemslots.bases.BaseGearType;
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

                }

            }
        };

        All.forEach(x -> x.addToSerializables());

    }
}
