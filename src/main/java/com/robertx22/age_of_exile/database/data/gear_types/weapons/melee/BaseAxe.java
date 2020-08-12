package com.robertx22.age_of_exile.database.data.gear_types.weapons.melee;

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

public class BaseAxe extends BaseWeapon {
    public BaseAxe(String guid, LevelRange levelRange, String locname) {
        super(guid, levelRange, locname);
        this.attacksPerSecond = Constants.AXE_ATK_SPEED;
    }

    @Override
    public List<StatModifier> baseStats() {
        return Arrays.asList(
            new StatModifier(2, 3, 4, 9, new WeaponDamage(Elements.Physical), ModType.FLAT),
            new StatModifier(4, 15, CriticalHit.getInstance(), ModType.FLAT)

        );
    }

    @Override
    public GearSlot getGearSlot() {
        return GearSlots.AXE;
    }

    @Override
    public StatRequirement getStatRequirements() {
        return new StatRequirement().strength(0.5F);
    }

    @Override
    public List<StatModifier> implicitStats() {
        return Arrays.asList();
    }

    @Override
    public TagList getTags() {
        return new TagList(SlotTag.warrior_casting_weapon, SlotTag.axe, SlotTag.weapon_family, SlotTag.melee_weapon, SlotTag.strength);
    }

    @Override
    public WeaponTypes weaponType() {
        return WeaponTypes.Axe;
    }

    @Override
    public int Weight() {
        return 1000;
    }

}