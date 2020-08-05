package com.robertx22.mine_and_slash.database.data.gearitemslots.offhand;

import com.robertx22.mine_and_slash.database.data.StatModifier;
import com.robertx22.mine_and_slash.database.data.gearitemslots.bases.BaseGearType;
import com.robertx22.mine_and_slash.database.data.gearitemslots.bases.BaseOffHand;
import com.robertx22.mine_and_slash.database.data.gearitemslots.bases.TagList;
import com.robertx22.mine_and_slash.database.data.stats.types.offense.SpellDamage;
import com.robertx22.mine_and_slash.database.data.stats.types.resources.MagicShield;
import com.robertx22.mine_and_slash.saveclasses.gearitem.gear_bases.StatRequirement;
import com.robertx22.mine_and_slash.uncommon.enumclasses.ModType;
import net.minecraft.item.Item;
import net.minecraft.item.Items;

import java.util.Arrays;
import java.util.List;

public class SpiritShield extends BaseOffHand {
    public static BaseGearType INSTANCE = new SpiritShield();

    private SpiritShield() {

    }

    @Override
    public List<StatModifier> baseStats() {
        return Arrays.asList(new StatModifier(2, 5, MagicShield.getInstance(), ModType.FLAT));
    }

    @Override
    public List<StatModifier> implicitStats() {
        return Arrays.asList(new StatModifier(3, 8, SpellDamage.getInstance(), ModType.FLAT));
    }

    @Override
    public StatRequirement getStatRequirements() {
        return new StatRequirement().intelligence(0.5F);
    }

    @Override
    public TagList getTags() {
        return new TagList(SlotTag.shield, SlotTag.cloth, SlotTag.offhand_family, SlotTag.magic_shield_stat, SlotTag.intelligence);
    }

    @Override
    public Item getItem() {
        return Items.SHIELD;
    }

    @Override
    public String GUID() {
        return "spirit_shield";
    }

    @Override
    public String locNameForLangFile() {
        return "Spirit Shield";
    }
}
