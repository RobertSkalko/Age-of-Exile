package com.robertx22.age_of_exile.database.data.gear_types.weapons.melee;

import com.robertx22.age_of_exile.database.data.StatModifier;
import com.robertx22.age_of_exile.database.data.gear_slots.GearSlot;
import com.robertx22.age_of_exile.database.data.gear_types.bases.BaseWeapon;
import com.robertx22.age_of_exile.database.data.gear_types.bases.TagList;
import com.robertx22.age_of_exile.database.data.level_ranges.LevelRange;
import com.robertx22.age_of_exile.database.data.stats.types.generated.WeaponDamage;
import com.robertx22.age_of_exile.database.data.stats.types.offense.CriticalHit;
import com.robertx22.age_of_exile.database.data.stats.types.offense.SpellDamage;
import com.robertx22.age_of_exile.database.registrators.GearSlots;
import com.robertx22.age_of_exile.saveclasses.gearitem.gear_bases.StatRequirement;
import com.robertx22.age_of_exile.uncommon.effectdatas.interfaces.WeaponTypes;
import com.robertx22.age_of_exile.uncommon.enumclasses.Elements;
import com.robertx22.age_of_exile.uncommon.enumclasses.ModType;

import java.util.Arrays;
import java.util.List;

public abstract class BaseWand extends BaseWeapon {

    public BaseWand(String guid, LevelRange levelRange, String locname) {
        super(guid, levelRange, locname);
        this.attacksPerSecond = Constants.WAND_ATK_SPEED;
    }

    @Override
    public List<StatModifier> baseStats() {
        return Arrays.asList(
            new StatModifier(2, 3, 3, 6, new WeaponDamage(Elements.Physical), ModType.FLAT),
            new StatModifier(3, 10, CriticalHit.getInstance(), ModType.FLAT)
        );
    }

    @Override
    public GearSlot getGearSlot() {
        return GearSlots.WAND;
    }

    @Override
    public List<StatModifier> implicitStats() {
        return Arrays.asList(new StatModifier(3, 10, SpellDamage.getInstance(), ModType.FLAT));
    }

    @Override
    public StatRequirement getStatRequirements() {
        return new StatRequirement().intelligence(0.5F);
    }

    @Override
    public TagList getTags() {
        return new TagList(SlotTag.mage_casting_weapon, SlotTag.mage_weapon, SlotTag.wand, SlotTag.weapon_family, SlotTag.melee_weapon, SlotTag.intelligence);
    }

    @Override
    public WeaponTypes weaponType() {
        return WeaponTypes.Wand;
    }

    @Override
    public int Weight() {
        return 2000;
    }

}