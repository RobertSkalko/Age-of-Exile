package com.robertx22.age_of_exile.database.data.gear_types.weapons;

import com.robertx22.age_of_exile.database.data.StatModifier;
import com.robertx22.age_of_exile.database.data.gear_slots.GearSlot;
import com.robertx22.age_of_exile.database.data.gear_types.bases.BaseWeapon;
import com.robertx22.age_of_exile.database.data.gear_types.bases.TagList;
import com.robertx22.age_of_exile.database.data.level_ranges.LevelRange;
import com.robertx22.age_of_exile.database.data.stats.types.generated.WeaponDamage;
import com.robertx22.age_of_exile.database.data.stats.types.offense.CriticalHit;
import com.robertx22.age_of_exile.database.registrators.GearSlots;
import com.robertx22.age_of_exile.saveclasses.gearitem.gear_bases.StatRequirement;
import com.robertx22.age_of_exile.uncommon.effectdatas.interfaces.WeaponTypes;
import com.robertx22.age_of_exile.uncommon.enumclasses.Elements;
import com.robertx22.age_of_exile.uncommon.enumclasses.ModType;

import java.util.Arrays;
import java.util.List;

public class BaseBow extends BaseWeapon {

    public BaseBow(String guid, LevelRange levelRange, String locname) {
        super(guid, levelRange, locname);
    }

    @Override
    public List<StatModifier> baseStats() {
        return Arrays.asList(
            new StatModifier(1, 3, 2, 8, new WeaponDamage(Elements.Physical), ModType.FLAT),
            new StatModifier(5, 18, CriticalHit.getInstance(), ModType.FLAT)
        );
    }

    @Override
    public GearSlot getGearSlot() {
        return GearSlots.BOW;
    }

    @Override
    public StatRequirement getStatRequirements() {
        return new StatRequirement().dexterity(0.5F);
    }

    @Override
    public List<StatModifier> implicitStats() {
        return Arrays.asList();
    }

    @Override
    public TagList getTags() {
        return new TagList(SlotTag.ranger_casting_weapon, SlotTag.bow, SlotTag.weapon_family, SlotTag.ranged_weapon, SlotTag.dexterity);
    }

    @Override
    public WeaponTypes weaponType() {
        return WeaponTypes.Bow;
    }

    @Override
    public int Weight() {
        return 750;
    }

}