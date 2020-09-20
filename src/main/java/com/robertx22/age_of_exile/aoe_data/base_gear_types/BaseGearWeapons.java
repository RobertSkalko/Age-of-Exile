package com.robertx22.age_of_exile.aoe_data.base_gear_types;

import com.robertx22.age_of_exile.aoe_data.base.DataGenKey;
import com.robertx22.age_of_exile.database.data.StatModifier;
import com.robertx22.age_of_exile.database.data.gear_types.bases.BaseGearType;
import com.robertx22.age_of_exile.database.data.gear_types.bases.BaseGearType.SlotTag;
import com.robertx22.age_of_exile.database.data.gear_types.bases.TagList;
import com.robertx22.age_of_exile.database.data.stats.types.generated.WeaponDamage;
import com.robertx22.age_of_exile.database.data.stats.types.offense.CriticalHit;
import com.robertx22.age_of_exile.database.registrators.GearSlots;
import com.robertx22.age_of_exile.database.registry.ISlashRegistryInit;
import com.robertx22.age_of_exile.saveclasses.gearitem.gear_bases.StatRequirement;
import com.robertx22.age_of_exile.uncommon.effectdatas.interfaces.WeaponTypes;
import com.robertx22.age_of_exile.uncommon.enumclasses.Elements;
import com.robertx22.age_of_exile.uncommon.enumclasses.ModType;

import java.util.HashMap;

public class BaseGearWeapons implements ISlashRegistryInit {

    HashMap<DataGenKey<BaseGearType>, BaseGearType> SWORD;
    HashMap<DataGenKey<BaseGearType>, BaseGearType> AXE;

    @Override
    public void registerAll() {

        AXE = BaseGearBuilder.of(GearSlots.AXE, "axe", "Axe")
            .weaponType(WeaponTypes.Axe)
            .req(new StatRequirement().strength(0.5f))
            .tags(new TagList(SlotTag.warrior_casting_weapon, SlotTag.axe, SlotTag.weapon_family, SlotTag.melee_weapon, SlotTag.strength))
            .baseStat(new StatModifier(2, 3, 4, 9, new WeaponDamage(Elements.Physical), ModType.FLAT))
            .baseStat(new StatModifier(4, 15, CriticalHit.getInstance(), ModType.FLAT))
            .addWarriorLevelRanges()
            .build();

        SWORD = BaseGearBuilder.of(GearSlots.SWORD, "sword", "Sword")
            .weaponType(WeaponTypes.Sword)
            .req(new StatRequirement().strength(0.3F)
                .dexterity(0.1F))
            .tags(new TagList(SlotTag.warrior_casting_weapon, SlotTag.sword, SlotTag.melee_weapon, SlotTag.weapon_family, SlotTag.strength, SlotTag.dexterity))
            .baseStat(new StatModifier(2, 3, 2, 4, new WeaponDamage(Elements.Physical), ModType.FLAT))
            .baseStat(new StatModifier(2, 6, CriticalHit.getInstance(), ModType.FLAT))
            .addWarriorLevelRanges()
            .build();

    }
}
