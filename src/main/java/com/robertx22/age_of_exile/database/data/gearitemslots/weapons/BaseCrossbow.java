package com.robertx22.age_of_exile.database.data.gearitemslots.weapons;

import com.robertx22.age_of_exile.database.data.StatModifier;
import com.robertx22.age_of_exile.database.data.gearitemslots.bases.BaseWeapon;
import com.robertx22.age_of_exile.database.data.gearitemslots.bases.TagList;
import com.robertx22.age_of_exile.database.data.level_ranges.LevelRange;
import com.robertx22.age_of_exile.database.data.stats.types.generated.WeaponDamage;
import com.robertx22.age_of_exile.database.data.stats.types.offense.CriticalHit;
import com.robertx22.age_of_exile.saveclasses.gearitem.gear_bases.StatRequirement;
import com.robertx22.age_of_exile.uncommon.effectdatas.interfaces.WeaponTypes;
import com.robertx22.age_of_exile.uncommon.enumclasses.Elements;
import com.robertx22.age_of_exile.uncommon.enumclasses.ModType;

import java.util.Arrays;
import java.util.List;

public class BaseCrossbow extends BaseWeapon {
    public BaseCrossbow(String guid, LevelRange levelRange, String locname) {
        super(guid, levelRange, locname);

    }

    @Override
    public List<StatModifier> baseStats() {
        return Arrays.asList(
            new StatModifier(2, 4, 3, 7, new WeaponDamage(Elements.Physical), ModType.FLAT),
            new StatModifier(3, 6, CriticalHit.getInstance(), ModType.FLAT)

        );
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
        return new TagList(SlotTag.ranger_casting_weapon, SlotTag.crossbow, SlotTag.weapon_family, SlotTag.ranged_weapon, SlotTag.dexterity);
    }

    @Override
    public WeaponTypes weaponType() {
        return WeaponTypes.CrossBow;
    }

    @Override
    public int Weight() {
        return 750;
    }

}