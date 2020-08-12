package com.robertx22.age_of_exile.database.data.gearitemslots.weapons.melee;

import com.robertx22.age_of_exile.database.data.StatModifier;
import com.robertx22.age_of_exile.database.data.gearitemslots.bases.BaseWeapon;
import com.robertx22.age_of_exile.database.data.gearitemslots.bases.TagList;
import com.robertx22.age_of_exile.database.data.level_ranges.LevelRange;
import com.robertx22.age_of_exile.database.data.stats.types.generated.WeaponDamage;
import com.robertx22.age_of_exile.database.data.stats.types.offense.CriticalHit;
import com.robertx22.age_of_exile.database.data.stats.types.resources.Lifesteal;
import com.robertx22.age_of_exile.saveclasses.gearitem.gear_bases.StatRequirement;
import com.robertx22.age_of_exile.uncommon.effectdatas.interfaces.WeaponTypes;
import com.robertx22.age_of_exile.uncommon.enumclasses.Elements;
import com.robertx22.age_of_exile.uncommon.enumclasses.ModType;

import java.util.Arrays;
import java.util.List;

public class BaseSword extends BaseWeapon {

    public BaseSword(String guid, LevelRange levelRange, String locname) {
        super(guid, levelRange, locname);
        this.attacksPerSecond = Constants.SWORD_ATK_SPEED;
    }

    @Override
    public List<StatModifier> baseStats() {
        return Arrays.asList(
            new StatModifier(2, 3, 2, 4, new WeaponDamage(Elements.Physical), ModType.FLAT),
            new StatModifier(2, 6, CriticalHit.getInstance(), ModType.FLAT)

        );
    }

    @Override
    public List<StatModifier> implicitStats() {
        return Arrays.asList(new StatModifier(1, 3, Lifesteal.getInstance(), ModType.FLAT));
    }

    @Override
    public StatRequirement getStatRequirements() {
        return new StatRequirement().strength(0.3F)
            .dexterity(0.1F);
    }

    @Override
    public TagList getTags() {
        return new TagList(SlotTag.warrior_casting_weapon, SlotTag.sword, SlotTag.melee_weapon, SlotTag.weapon_family, SlotTag.strength, SlotTag.dexterity);
    }

    @Override
    public WeaponTypes weaponType() {
        return WeaponTypes.Sword;
    }

    @Override
    public int Weight() {
        return 1500;
    }

}
