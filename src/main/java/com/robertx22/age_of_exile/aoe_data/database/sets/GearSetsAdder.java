package com.robertx22.age_of_exile.aoe_data.database.sets;

import com.robertx22.age_of_exile.database.OptScaleExactStat;
import com.robertx22.age_of_exile.database.data.set.GearSet;
import com.robertx22.age_of_exile.database.data.stats.types.core_stats.Vitality;
import com.robertx22.age_of_exile.database.data.stats.types.core_stats.Wisdom;
import com.robertx22.age_of_exile.database.data.stats.types.special.SpecialStats;
import com.robertx22.age_of_exile.database.registry.ISlashRegistryInit;
import com.robertx22.age_of_exile.uncommon.enumclasses.ModType;

public class GearSetsAdder implements ISlashRegistryInit {

    public static String BONE_SET = "bone_set";
    public static String BLAZE_SET = "blaze_set";
    public static String OAK_SET = "oak_set";
    public static String SLIME_SET = "slime_set";
    public static String DARK_CRYSTAL_SET = "dark_crystal_set";
    public static String VOID_SET = "void_set";
    public static String PHARAOH_SET = "pharaoh_set";
    public static String SEASONS_SET = "seasons_set";

    @Override
    public void registerAll() {

        GearSet.Builder.of(SEASONS_SET, "Enduring The Seasons")
            .stat(2,
                new OptScaleExactStat(10, Vitality.INSTANCE, ModType.FLAT),
                new OptScaleExactStat(10, Wisdom.INSTANCE, ModType.FLAT))
            .build();

        GearSet.Builder.of(BONE_SET, "Bone Spike")
            .stat(2, new OptScaleExactStat(25, SpecialStats.RANGED_CRIT_DMG_AGAINST_LIVING))
            .build();

        GearSet.Builder.of(BLAZE_SET, "The Blaze")
            .stat(2, new OptScaleExactStat(20, SpecialStats.CRIT_BURN))
            .build();

        GearSet.Builder.of(OAK_SET, "The Great Tree")
            .stat(2, new OptScaleExactStat(25, SpecialStats.HEAL_CLEANSE))
            .build();

        GearSet.Builder.of(SLIME_SET, "Sticky Slime")
            .stat(2, new OptScaleExactStat(5, SpecialStats.ABSORB_ELE_DMG_INTO_HP))
            .build();

        GearSet.Builder.of(DARK_CRYSTAL_SET, "Dark Crystal")
            .stat(2, new OptScaleExactStat(20, SpecialStats.DAY_NIGHT_DMG))
            .build();

        GearSet.Builder.of(VOID_SET, "Call of the Void")
            .stat(2, new OptScaleExactStat(10, SpecialStats.DARK_DMG_IGNORE_RES))
            .build();

        GearSet.Builder.of(PHARAOH_SET, "Curse of the Pharaoh")
            .stat(2, new OptScaleExactStat(50, SpecialStats.MUMMY_CURSE))
            .build();

    }
}
