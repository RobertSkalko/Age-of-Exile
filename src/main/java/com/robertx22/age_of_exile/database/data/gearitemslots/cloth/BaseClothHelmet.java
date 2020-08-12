package com.robertx22.age_of_exile.database.data.gearitemslots.cloth;

import com.robertx22.age_of_exile.database.data.StatModifier;
import com.robertx22.age_of_exile.database.data.gearitemslots.bases.BaseArmor;
import com.robertx22.age_of_exile.database.data.gearitemslots.bases.TagList;
import com.robertx22.age_of_exile.database.data.level_ranges.LevelRange;
import com.robertx22.age_of_exile.database.data.stats.types.offense.SpellDamage;
import com.robertx22.age_of_exile.database.data.stats.types.resources.MagicShield;
import com.robertx22.age_of_exile.saveclasses.gearitem.gear_bases.StatRequirement;
import com.robertx22.age_of_exile.uncommon.enumclasses.ModType;

import java.util.Arrays;
import java.util.List;

public class BaseClothHelmet extends BaseArmor {

    public BaseClothHelmet(String guid, LevelRange levelRange, String locname) {
        super(guid, levelRange, locname);
    }

    @Override
    public List<StatModifier> baseStats() {
        return Arrays.asList(
            new StatModifier(2, 6, MagicShield.getInstance(), ModType.FLAT)
        );
    }

    @Override
    public StatRequirement getStatRequirements() {
        return new StatRequirement().intelligence(0.5F);
    }

    @Override
    public List<StatModifier> implicitStats() {
        return Arrays.asList(new StatModifier(3, 8, SpellDamage.getInstance(), ModType.FLAT));
    }

    @Override
    public TagList getTags() {
        return new TagList(SlotTag.cloth, SlotTag.helmet, SlotTag.armor_family, SlotTag.magic_shield_stat, SlotTag.intelligence);
    }

}

