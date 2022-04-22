package com.robertx22.age_of_exile.aoe_data.database.base_gear_types;

import com.robertx22.age_of_exile.aoe_data.database.GearDataHelper;
import com.robertx22.age_of_exile.aoe_data.database.gear_slots.GearSlots;
import com.robertx22.age_of_exile.aoe_data.database.stats.Stats;
import com.robertx22.age_of_exile.database.data.StatModifier;
import com.robertx22.age_of_exile.database.data.gear_types.bases.BaseGearType.SlotTag;
import com.robertx22.age_of_exile.database.data.gear_types.bases.TagList;
import com.robertx22.age_of_exile.database.data.stats.types.defense.Armor;
import com.robertx22.age_of_exile.uncommon.enumclasses.ModType;
import com.robertx22.age_of_exile.uncommon.enumclasses.PlayStyle;
import com.robertx22.age_of_exile.uncommon.enumclasses.WeaponTypes;
import com.robertx22.library_of_exile.registry.ExileRegistryInit;

public class BaseGearsAdder implements ExileRegistryInit, GearDataHelper {

    @Override
    public void registerAll() {

        BaseGearBuilder.of(BaseGearTypes.BOOTS, GearSlots.BOOTS, "Boots")
            .tags(new TagList(SlotTag.armor_stat, SlotTag.boots, SlotTag.armor_family, SlotTag.strength))
            .baseStat(
                getStat(ArmorStat.ARMOR, ArmorType.HEAVY, ArmorSlot.BOOTS)
            )
            .build();

        BaseGearBuilder.of(BaseGearTypes.PANTS, GearSlots.PANTS, "Pants")
            .tags(new TagList(SlotTag.armor_stat, SlotTag.pants, SlotTag.armor_family, SlotTag.strength))
            .baseStat(
                getStat(ArmorStat.ARMOR, ArmorType.HEAVY, ArmorSlot.PANTS)
            )
            .build();

        BaseGearBuilder.of(BaseGearTypes.CHEST, GearSlots.CHEST, "Chest")
            .tags(new TagList(SlotTag.armor_stat, SlotTag.chest, SlotTag.armor_family, SlotTag.strength))
            .baseStat(
                getStat(ArmorStat.ARMOR, ArmorType.HEAVY, ArmorSlot.CHEST)
            )
            .build();

        BaseGearBuilder.of(BaseGearTypes.HELMET, GearSlots.HELMET, "Helmet")
            .tags(new TagList(SlotTag.armor_stat, SlotTag.helmet, SlotTag.armor_family, SlotTag.strength))
            .baseStat(
                getStat(ArmorStat.ARMOR, ArmorType.HEAVY, ArmorSlot.HELMET)
            )
            .build();

        BaseGearBuilder.of(BaseGearTypes.RING, GearSlots.RING, "Ring")
            .tags(new TagList(SlotTag.ring, SlotTag.jewelry_family))
            .baseStat(
                getStat(ArmorStat.ELE_DEF, ArmorType.HEAVY, ArmorSlot.CHEST)
            )
            .build();

        BaseGearBuilder.of(BaseGearTypes.NECKLACE, GearSlots.NECKLACE, "Necklace")
            .tags(new TagList(SlotTag.necklace, SlotTag.jewelry_family))
            .baseStat(
                getStat(ArmorStat.ELE_DEF, ArmorType.HEAVY, ArmorSlot.CHEST)
            )
            .build();

        BaseGearBuilder.of(BaseGearTypes.SHIELD, GearSlots.SHIELD, "Tower Shield")
            .tags(new TagList(SlotTag.shield, SlotTag.offhand_family, SlotTag.armor_stat, SlotTag.strength))
            .baseStat(new StatModifier(6, 12, Armor.getInstance(), ModType.FLAT))
            .baseStat(new StatModifier(-25, -25, Stats.STYLE_DAMAGE.get(PlayStyle.magic)))
            .build();

        BaseGearBuilder.weapon(BaseGearTypes.BOW, GearSlots.BOW, WeaponTypes.bow)
            .tags(new TagList(SlotTag.ranger_casting_weapon, SlotTag.bow, SlotTag.weapon_family, SlotTag.ranged_weapon, SlotTag.dexterity))
            .build();

        BaseGearBuilder.weapon(BaseGearTypes.CROSSBOW, GearSlots.CROSBOW, WeaponTypes.crossbow)
            .tags(new TagList(SlotTag.crossbow, SlotTag.ranger_casting_weapon, SlotTag.weapon_family, SlotTag.ranged_weapon, SlotTag.dexterity))
            .build();

        BaseGearBuilder.weapon(BaseGearTypes.AXE, GearSlots.AXE, WeaponTypes.axe)
            .tags(new TagList(SlotTag.warrior_casting_weapon, SlotTag.axe, SlotTag.weapon_family, SlotTag.melee_weapon, SlotTag.strength))
            .build();

        BaseGearBuilder.weapon(BaseGearTypes.SWORD, GearSlots.SWORD, WeaponTypes.sword)
            .tags(new TagList(SlotTag.warrior_casting_weapon, SlotTag.sword, SlotTag.melee_weapon, SlotTag.weapon_family, SlotTag.strength, SlotTag.dexterity))
            .build();

        BaseGearBuilder.weapon(BaseGearTypes.SCEPTER, GearSlots.SCEPTER, WeaponTypes.scepter)
            .tags(new TagList(SlotTag.mage_weapon, SlotTag.scepter, SlotTag.weapon_family, SlotTag.melee_weapon, SlotTag.intelligence))
            .build();

        BaseGearBuilder.weapon(BaseGearTypes.STAFF, GearSlots.STAFF, WeaponTypes.staff)
            .tags(new TagList(SlotTag.mage_weapon, SlotTag.staff, SlotTag.weapon_family, SlotTag.melee_weapon, SlotTag.intelligence))
            .build();

    }
}
