package com.robertx22.mine_and_slash.database.data.gearitemslots.curios;

import com.robertx22.mine_and_slash.database.data.StatModifier;
import com.robertx22.mine_and_slash.database.data.gearitemslots.bases.BaseCurio;
import com.robertx22.mine_and_slash.database.data.gearitemslots.bases.BaseGearType;
import com.robertx22.mine_and_slash.database.data.gearitemslots.bases.TagList;
import com.robertx22.mine_and_slash.database.data.stats.types.resources.Health;
import com.robertx22.mine_and_slash.mmorpg.ModRegistry;
import com.robertx22.mine_and_slash.saveclasses.gearitem.gear_bases.StatRequirement;
import com.robertx22.mine_and_slash.uncommon.enumclasses.ModType;
import net.minecraft.item.Item;

import java.util.Arrays;
import java.util.List;

public class LifeRing extends BaseCurio {

    public static BaseGearType INSTANCE = new LifeRing();

    private LifeRing() {

    }

    @Override
    public StatRequirement getStatRequirements() {
        return new StatRequirement();
    }

    @Override
    public String GUID() {
        return "life_ring";
    }

    @Override
    public List<StatModifier> implicitStats() {
        return Arrays.asList();
    }

    @Override
    public List<StatModifier> baseStats() {
        return Arrays.asList(new StatModifier(1, 5, Health.getInstance(), ModType.FLAT));
    }

    @Override
    public Item getItem() {
        return ModRegistry.GEAR_ITEMS.LIFE_RING;
    }

    @Override
    public int Weight() {
        return super.Weight() * 2;
    }

    @Override
    public TagList getTags() {
        return new TagList(SlotTag.ring, SlotTag.jewelry_family);
    }

    @Override
    public String locNameForLangFile() {
        return "Life Ring";
    }
}
