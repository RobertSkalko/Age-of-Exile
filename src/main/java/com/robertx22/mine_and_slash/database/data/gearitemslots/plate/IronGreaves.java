package com.robertx22.mine_and_slash.database.data.gearitemslots.plate;

import com.robertx22.mine_and_slash.database.data.StatModifier;
import com.robertx22.mine_and_slash.database.data.gearitemslots.bases.BaseGearType;
import com.robertx22.mine_and_slash.database.data.gearitemslots.bases.armor.BaseBoots;
import com.robertx22.mine_and_slash.database.data.stats.types.defense.Armor;
import com.robertx22.mine_and_slash.mmorpg.registers.common.ModItems;
import com.robertx22.mine_and_slash.saveclasses.gearitem.gear_bases.StatRequirement;
import com.robertx22.mine_and_slash.uncommon.enumclasses.ModType;
import net.minecraft.item.Item;

import java.util.Arrays;
import java.util.List;

public class IronGreaves extends BaseBoots {
    public static BaseGearType INSTANCE = new IronGreaves();

    private IronGreaves() {

    }

    @Override
    public List<StatModifier> baseStats() {
        return Arrays.asList(
            new StatModifier(10, 30, Armor.getInstance(), ModType.FLAT)
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
    public List<SlotTag> getTags() {
        return Arrays.asList(SlotTag.Plate, SlotTag.Boots);
    }

    @Override
    public Item getItem() {
        return ModItems.PLATE_BOOTS.get();
    }

    @Override
    public String GUID() {
        return "plate_boots";
    }

    @Override
    public String locNameForLangFile() {
        return "Iron Greaves";
    }
}
