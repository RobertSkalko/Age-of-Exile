package com.robertx22.mine_and_slash.database.data.gearitemslots.cloth;

import com.robertx22.mine_and_slash.database.data.StatModifier;
import com.robertx22.mine_and_slash.database.data.gearitemslots.bases.BaseGearType;
import com.robertx22.mine_and_slash.database.data.gearitemslots.bases.TagList;
import com.robertx22.mine_and_slash.database.data.gearitemslots.bases.armor.BaseBoots;
import com.robertx22.mine_and_slash.database.data.stats.types.resources.MagicShield;
import com.robertx22.mine_and_slash.mmorpg.ModRegistry;
import com.robertx22.mine_and_slash.saveclasses.gearitem.gear_bases.StatRequirement;
import com.robertx22.mine_and_slash.uncommon.enumclasses.ModType;
import net.minecraft.item.Item;

import java.util.Arrays;
import java.util.List;

public class SilkPants extends BaseBoots {
    public static BaseGearType INSTANCE = new SilkPants();

    private SilkPants() {

    }

    @Override
    public List<StatModifier> baseStats() {
        return Arrays.asList(
            new StatModifier(3, 7, MagicShield.getInstance(), ModType.FLAT)
        );
    }

    @Override
    public StatRequirement getStatRequirements() {
        return new StatRequirement().intelligence(0.5F);
    }

    @Override
    public List<StatModifier> implicitStats() {
        return Arrays.asList();
    }

    @Override
    public TagList getTags() {
        return new TagList(SlotTag.cloth, SlotTag.pants, SlotTag.armor_family, SlotTag.magic_shield_stat, SlotTag.intelligence);
    }

    @Override
    public Item getItem() {
        return ModRegistry.GEAR_ITEMS.SILK_PANTS;
    }

    @Override
    public String GUID() {
        return "silk_pants";
    }

    @Override
    public String locNameForLangFile() {
        return "Silk Pants";
    }
}

