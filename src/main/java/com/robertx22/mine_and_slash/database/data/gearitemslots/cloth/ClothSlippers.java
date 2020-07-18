package com.robertx22.mine_and_slash.database.data.gearitemslots.cloth;

import com.robertx22.mine_and_slash.database.data.StatModifier;
import com.robertx22.mine_and_slash.database.data.gearitemslots.bases.BaseGearType;
import com.robertx22.mine_and_slash.database.data.gearitemslots.bases.armor.BaseBoots;
import com.robertx22.mine_and_slash.database.data.stats.types.resources.MagicShield;
import com.robertx22.mine_and_slash.mmorpg.registers.common.ModItems;
import com.robertx22.mine_and_slash.saveclasses.gearitem.gear_bases.StatRequirement;
import com.robertx22.mine_and_slash.uncommon.enumclasses.ModType;
import net.minecraft.item.Item;

import java.util.Arrays;
import java.util.List;

public class ClothSlippers extends BaseBoots {
    public static BaseGearType INSTANCE = new ClothSlippers();

    private ClothSlippers() {

    }

    @Override
    public List<StatModifier> baseStats() {
        return Arrays.asList(
            new StatModifier(1, 4, MagicShield.getInstance(), ModType.FLAT)
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
    public List<SlotTag> getTags() {
        return Arrays.asList(SlotTag.Cloth, SlotTag.Boots);
    }

    @Override
    public Item getItem() {
        return ModItems.CLOTH_SLIPPERS.get();
    }

    @Override
    public String GUID() {
        return "cloth_slippers";
    }

    @Override
    public String locNameForLangFile() {
        return "Cloth Slippers";
    }
}
