package com.robertx22.age_of_exile.aoe_data.database.base_gear_types.adders;

import com.robertx22.age_of_exile.aoe_data.database.base_gear_types.BaseGearBuilder;
import com.robertx22.age_of_exile.aoe_data.database.gear_slots.GearSlots;
import com.robertx22.age_of_exile.aoe_data.database.stats.Stats;
import com.robertx22.age_of_exile.database.data.StatModifier;
import com.robertx22.age_of_exile.database.data.gear_types.bases.BaseGearType;
import com.robertx22.age_of_exile.database.data.gear_types.bases.BaseGearType.SlotTag;
import com.robertx22.age_of_exile.database.data.gear_types.bases.TagList;
import com.robertx22.age_of_exile.mmorpg.ModRegistry;
import com.robertx22.age_of_exile.uncommon.enumclasses.ModType;
import com.robertx22.age_of_exile.uncommon.enumclasses.WeaponTypes;
import com.robertx22.library_of_exile.registry.DataGenKey;
import com.robertx22.library_of_exile.registry.ExileRegistryInit;

public class BaseGearWeapons implements ExileRegistryInit {

    public static DataGenKey<BaseGearType> SWORD;
    public static DataGenKey<BaseGearType> AXE;
    public static DataGenKey<BaseGearType> SCEPTER;
    public static DataGenKey<BaseGearType> BOW;
    public static DataGenKey<BaseGearType> CROSSBOW;
    public static DataGenKey<BaseGearType> STAFF;

    @Override
    public void registerAll() {

        BOW = BaseGearBuilder.weapon(GearSlots.BOW, WeaponTypes.bow)
            .tags(new TagList(SlotTag.ranger_casting_weapon, SlotTag.bow, SlotTag.weapon_family, SlotTag.ranged_weapon, SlotTag.dexterity))
            .build();

        CROSSBOW = BaseGearBuilder.weapon(GearSlots.CROSBOW, WeaponTypes.crossbow)
            .tags(new TagList(SlotTag.crossbow, SlotTag.weapon_family, SlotTag.ranged_weapon, SlotTag.dexterity))
            .build();

        AXE = BaseGearBuilder.weapon(GearSlots.AXE, WeaponTypes.axe)
            .tags(new TagList(SlotTag.warrior_casting_weapon, SlotTag.axe, SlotTag.weapon_family, SlotTag.melee_weapon, SlotTag.strength))
            .build();

        SWORD = BaseGearBuilder.weapon(GearSlots.SWORD, WeaponTypes.sword)
            .tags(new TagList(SlotTag.warrior_casting_weapon, SlotTag.sword, SlotTag.melee_weapon, SlotTag.weapon_family, SlotTag.strength, SlotTag.dexterity))
            .build();

        SCEPTER = BaseGearBuilder.weapon(GearSlots.SCEPTER, WeaponTypes.scepter)
            .essenceItem(ModRegistry.GEAR_MATERIALS.LIFE)
            .tags(new TagList(SlotTag.mage_weapon, SlotTag.scepter, SlotTag.weapon_family, SlotTag.melee_weapon, SlotTag.intelligence))
            .implicitStat(new StatModifier(3, 10, Stats.HEAL_STRENGTH.get(), ModType.FLAT))
            .build();

        STAFF = BaseGearBuilder.weapon(GearSlots.STAFF, WeaponTypes.staff)
            .tags(new TagList(SlotTag.mage_weapon, SlotTag.staff, SlotTag.weapon_family, SlotTag.melee_weapon, SlotTag.intelligence))
            .build();

    }
}
