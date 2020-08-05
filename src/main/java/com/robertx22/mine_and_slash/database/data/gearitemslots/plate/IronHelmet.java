package com.robertx22.mine_and_slash.database.data.gearitemslots.plate;

import com.robertx22.mine_and_slash.database.data.StatModifier;
import com.robertx22.mine_and_slash.database.data.gearitemslots.bases.BaseGearType;
import com.robertx22.mine_and_slash.database.data.gearitemslots.bases.TagList;
import com.robertx22.mine_and_slash.database.data.gearitemslots.bases.armor.BaseHelmet;
import com.robertx22.mine_and_slash.database.data.stats.types.defense.Armor;
import com.robertx22.mine_and_slash.mmorpg.ModRegistry;
import com.robertx22.mine_and_slash.saveclasses.gearitem.gear_bases.StatRequirement;
import com.robertx22.mine_and_slash.uncommon.enumclasses.ModType;
import net.minecraft.item.Item;

import java.util.Arrays;
import java.util.List;

public class IronHelmet extends BaseHelmet {
    public static BaseGearType INSTANCE = new IronHelmet();

    private IronHelmet() {

    }

    @Override
    public List<StatModifier> baseStats() {
        return Arrays.asList(
            new StatModifier(15, 35, Armor.getInstance(), ModType.FLAT)
        );
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
        return new TagList(SlotTag.plate, SlotTag.helmet, SlotTag.armor_family, SlotTag.armor_stat, SlotTag.strength);
    }

    @Override
    public Item getItem() {
        return ModRegistry.GEAR_ITEMS.PLATE_HELMET;
    }

    @Override
    public String GUID() {
        return "plate_helmet";
    }

    @Override
    public String locNameForLangFile() {
        return "Iron Helmet";
    }
}
