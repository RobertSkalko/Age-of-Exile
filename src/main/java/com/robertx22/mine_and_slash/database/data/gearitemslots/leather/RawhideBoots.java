package com.robertx22.mine_and_slash.database.data.gearitemslots.leather;

import com.robertx22.mine_and_slash.database.data.StatModifier;
import com.robertx22.mine_and_slash.database.data.gearitemslots.bases.BaseGearType;
import com.robertx22.mine_and_slash.database.data.gearitemslots.bases.armor.BaseChest;
import com.robertx22.mine_and_slash.database.data.stats.types.defense.DodgeRating;
import com.robertx22.mine_and_slash.mmorpg.registers.common.ModItems;
import com.robertx22.mine_and_slash.saveclasses.gearitem.gear_bases.StatRequirement;
import com.robertx22.mine_and_slash.uncommon.enumclasses.ModType;
import net.minecraft.item.Item;

import java.util.Arrays;
import java.util.List;

public class RawhideBoots extends BaseChest {
    public static BaseGearType INSTANCE = new RawhideBoots();

    private RawhideBoots() {

    }

    @Override
    public List<StatModifier> baseStats() {
        return Arrays.asList(
            new StatModifier(15, 40, DodgeRating.getInstance(), ModType.FLAT)
        );
    }

    @Override
    public StatRequirement getStatRequirements() {
        return new StatRequirement().dexterity(0.5F);
    }

    @Override
    public List<StatModifier> implicitStats() {
        return Arrays.asList();
    }

    @Override
    public List<SlotTag> getTags() {
        return Arrays.asList(SlotTag.Leather, SlotTag.Boots);
    }

    @Override
    public Item getItem() {
        return ModItems.RAWHIDE_BOOTS.get();
    }

    @Override
    public String GUID() {
        return "rawhide_boots";
    }

    @Override
    public String locNameForLangFile() {
        return "Rawhide Boots";
    }

}
