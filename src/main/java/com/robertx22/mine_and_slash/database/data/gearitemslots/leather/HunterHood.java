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

public class HunterHood extends BaseChest {
    public static BaseGearType INSTANCE = new HunterHood();

    private HunterHood() {

    }

    @Override
    public List<StatModifier> baseStats() {
        return Arrays.asList(
            new StatModifier(20, 50, DodgeRating.getInstance(), ModType.FLAT)
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
        return Arrays.asList(SlotTag.Leather, SlotTag.Helmet);
    }

    @Override
    public Item getItem() {
        return ModItems.HUNTER_HOOD.get();
    }

    @Override
    public String GUID() {
        return "hunter_hood";
    }

    @Override
    public String locNameForLangFile() {
        return "Hunter Hood";
    }

}
