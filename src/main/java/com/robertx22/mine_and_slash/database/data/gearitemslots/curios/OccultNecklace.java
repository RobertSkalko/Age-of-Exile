package com.robertx22.mine_and_slash.database.data.gearitemslots.curios;

import com.robertx22.mine_and_slash.database.data.StatModifier;
import com.robertx22.mine_and_slash.database.data.gearitemslots.bases.BaseCurio;
import com.robertx22.mine_and_slash.database.data.gearitemslots.bases.BaseGearType;
import com.robertx22.mine_and_slash.database.data.stats.types.resources.ManaRegen;
import com.robertx22.mine_and_slash.mmorpg.registers.common.ModItems;
import com.robertx22.mine_and_slash.saveclasses.gearitem.gear_bases.StatRequirement;
import com.robertx22.mine_and_slash.uncommon.enumclasses.ModType;
import net.minecraft.item.Item;

import java.util.Arrays;
import java.util.List;

public class OccultNecklace extends BaseCurio {
    public static BaseGearType INSTANCE = new OccultNecklace();

    private OccultNecklace() {

    }

    @Override
    public List<StatModifier> baseStats() {
        return Arrays.asList(new StatModifier(1, 2, ManaRegen.getInstance(), ModType.FLAT));
    }

    @Override
    public StatRequirement getStatRequirements() {
        return new StatRequirement();
    }

    @Override
    public List<StatModifier> implicitStats() {
        return Arrays.asList();
    }

    @Override
    public String GUID() {
        return "occult_necklace";
    }

    @Override
    public List<SlotTag> getTags() {
        return Arrays.asList(SlotTag.Necklace);
    }

    @Override
    public Item getItem() {
        return ModItems.MANA_REG_NECKLACE.get();
    }

    @Override
    public String locNameForLangFile() {
        return "Occult Necklace";
    }
}
