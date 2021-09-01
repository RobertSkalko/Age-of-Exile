package com.robertx22.age_of_exile.aoe_data.database.base_gear_types.adders;

import com.robertx22.age_of_exile.aoe_data.database.GearDataHelper;
import com.robertx22.age_of_exile.aoe_data.database.base_gear_types.BaseGearBuilder;
import com.robertx22.age_of_exile.aoe_data.database.gear_slots.GearSlots;
import com.robertx22.age_of_exile.database.data.gear_types.bases.BaseGearType;
import com.robertx22.age_of_exile.database.data.gear_types.bases.TagList;
import com.robertx22.age_of_exile.database.data.level_ranges.LevelRange;
import com.robertx22.age_of_exile.uncommon.enumclasses.Elements;
import com.robertx22.age_of_exile.uncommon.enumclasses.PlayStyle;
import com.robertx22.age_of_exile.uncommon.enumclasses.WeaponTypes;
import com.robertx22.library_of_exile.registry.DataGenKey;
import com.robertx22.library_of_exile.registry.ExileRegistryInit;

import java.util.HashMap;

public class BaseToolsAdder implements ExileRegistryInit, GearDataHelper {

    public static HashMap<LevelRange, DataGenKey<BaseGearType>> PICKAXE;
    public static HashMap<LevelRange, DataGenKey<BaseGearType>> FISHING_ROD;
    public static HashMap<LevelRange, DataGenKey<BaseGearType>> HOE;

    @Override
    public void registerAll() {

        PICKAXE = BaseGearBuilder.of(GearSlots.PICKAXE, "pickaxe", "Pickaxe")
            .weaponType(WeaponTypes.sword)
            .tags(new TagList(BaseGearType.SlotTag.pickaxe, BaseGearType.SlotTag.tool_family))
            .baseStat(getAttackDamageStat(WeaponTypes.sword, GearDataHelper.Number.HALF, Elements.Physical))
            .attackStyle(PlayStyle.melee)
            .addFullLevelRange()
            .build();

        FISHING_ROD = BaseGearBuilder.of(GearSlots.FISHING_ROD, "fishing_rod", "Fishing Rod")
            .weaponType(WeaponTypes.sword)
            .tags(new TagList(BaseGearType.SlotTag.fishing_rod, BaseGearType.SlotTag.tool_family))
            .baseStat(getAttackDamageStat(WeaponTypes.sword, GearDataHelper.Number.HALF, Elements.Physical))
            .attackStyle(PlayStyle.melee)
            .addFullLevelRange()
            .build();

        HOE = BaseGearBuilder.of(GearSlots.HOE, "hoe", "Hoe")
            .weaponType(WeaponTypes.sword)
            .tags(new TagList(BaseGearType.SlotTag.hoe, BaseGearType.SlotTag.tool_family))
            .baseStat(getAttackDamageStat(WeaponTypes.sword, GearDataHelper.Number.HALF, Elements.Physical))
            .attackStyle(PlayStyle.melee)
            .addFullLevelRange()
            .build();

    }
}

