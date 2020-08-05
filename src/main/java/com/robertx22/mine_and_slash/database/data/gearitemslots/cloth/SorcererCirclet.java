package com.robertx22.mine_and_slash.database.data.gearitemslots.cloth;

import com.robertx22.mine_and_slash.database.data.StatModifier;
import com.robertx22.mine_and_slash.database.data.gearitemslots.bases.BaseGearType;
import com.robertx22.mine_and_slash.database.data.gearitemslots.bases.TagList;
import com.robertx22.mine_and_slash.database.data.gearitemslots.bases.armor.BaseBoots;
import com.robertx22.mine_and_slash.database.data.stats.types.offense.SpellDamage;
import com.robertx22.mine_and_slash.database.data.stats.types.resources.MagicShield;
import com.robertx22.mine_and_slash.mmorpg.ModRegistry;
import com.robertx22.mine_and_slash.saveclasses.gearitem.gear_bases.StatRequirement;
import com.robertx22.mine_and_slash.uncommon.enumclasses.ModType;
import net.minecraft.item.Item;

import java.util.Arrays;
import java.util.List;

public class SorcererCirclet extends BaseBoots {
    public static BaseGearType INSTANCE = new SorcererCirclet();

    private SorcererCirclet() {

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

    @Override
    public Item getItem() {
        return ModRegistry.GEAR_ITEMS.SORCERER_CIRCLET;
    }

    @Override
    public String GUID() {
        return "sorcerer_circlet";
    }

    @Override
    public String locNameForLangFile() {
        return "Sorcerer Circlet";
    }
}

