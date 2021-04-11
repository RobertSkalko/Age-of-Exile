package com.robertx22.age_of_exile.aoe_data.database.base_gear_types.adders;

import com.robertx22.age_of_exile.aoe_data.base.DataGenKey;
import com.robertx22.age_of_exile.aoe_data.database.DataHelper;
import com.robertx22.age_of_exile.aoe_data.database.base_gear_types.BaseGearBuilder;
import com.robertx22.age_of_exile.aoe_data.database.gear_slots.GearSlots;
import com.robertx22.age_of_exile.database.data.gear_types.bases.BaseGearType;
import com.robertx22.age_of_exile.database.data.gear_types.bases.TagList;
import com.robertx22.age_of_exile.database.data.level_ranges.LevelRange;
import com.robertx22.age_of_exile.database.registry.ISlashRegistryInit;
import com.robertx22.age_of_exile.mmorpg.ModRegistry;
import com.robertx22.age_of_exile.saveclasses.gearitem.gear_bases.StatRequirement;
import com.robertx22.age_of_exile.uncommon.effectdatas.AttackPlayStyle;
import com.robertx22.age_of_exile.uncommon.effectdatas.interfaces.WeaponTypes;
import com.robertx22.age_of_exile.uncommon.enumclasses.Elements;

import java.util.HashMap;

public class BaseToolsAdder implements ISlashRegistryInit {

    public static HashMap<LevelRange, DataGenKey<BaseGearType>> PICKAXE;
    public static HashMap<LevelRange, DataGenKey<BaseGearType>> FISHING_ROD;
    public static HashMap<LevelRange, DataGenKey<BaseGearType>> HOE;

    @Override
    public void registerAll() {

        PICKAXE = BaseGearBuilder.of(GearSlots.PICKAXE, "pickaxe", "Pickaxe", ModRegistry.GEAR_ITEMS.PICKAXE)
            .weaponType(WeaponTypes.Sword)
            .req(new StatRequirement())
            .tags(new TagList(BaseGearType.SlotTag.pickaxe, BaseGearType.SlotTag.tool_family))
            .baseStat(getAttackDamageStat(WeaponTypes.Sword, DataHelper.Number.HALF, Elements.Physical))
            .attackStyle(AttackPlayStyle.MELEE)
            .addToolLevelRanges()
            .build();

        FISHING_ROD = BaseGearBuilder.of(GearSlots.FISHING_ROD, "fishing_rod", "Fishing Rod", ModRegistry.GEAR_ITEMS.FISHING_RODS)
            .weaponType(WeaponTypes.Sword)
            .req(new StatRequirement())
            .tags(new TagList(BaseGearType.SlotTag.fishing_rod, BaseGearType.SlotTag.tool_family))
            .baseStat(getAttackDamageStat(WeaponTypes.Sword, DataHelper.Number.HALF, Elements.Physical))
            .attackStyle(AttackPlayStyle.MELEE)
            .addToolLevelRanges()
            .build();

        HOE = BaseGearBuilder.of(GearSlots.HOE, "hoe", "Hoe", ModRegistry.GEAR_ITEMS.HOES)
            .weaponType(WeaponTypes.Sword)
            .req(new StatRequirement())
            .tags(new TagList(BaseGearType.SlotTag.hoe, BaseGearType.SlotTag.tool_family))
            .baseStat(getAttackDamageStat(WeaponTypes.Sword, DataHelper.Number.HALF, Elements.Physical))
            .attackStyle(AttackPlayStyle.MELEE)
            .addToolLevelRanges()
            .build();

    }
}

