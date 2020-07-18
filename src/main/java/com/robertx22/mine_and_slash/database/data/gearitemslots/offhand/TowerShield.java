package com.robertx22.mine_and_slash.database.data.gearitemslots.offhand;

import com.robertx22.mine_and_slash.database.data.StatModifier;
import com.robertx22.mine_and_slash.database.data.gearitemslots.bases.BaseGearType;
import com.robertx22.mine_and_slash.database.data.gearitemslots.bases.BaseOffHand;
import com.robertx22.mine_and_slash.database.data.stats.types.defense.Armor;
import com.robertx22.mine_and_slash.mmorpg.registers.common.ModItems;
import com.robertx22.mine_and_slash.saveclasses.gearitem.gear_bases.StatRequirement;
import com.robertx22.mine_and_slash.uncommon.enumclasses.ModType;
import net.minecraft.item.Item;

import java.util.Arrays;
import java.util.List;

public class TowerShield extends BaseOffHand {
    public static BaseGearType INSTANCE = new TowerShield();

    private TowerShield() {

    }

    @Override
    public List<StatModifier> baseStats() {
        return Arrays.asList(new StatModifier(20, 80, Armor.getInstance(), ModType.FLAT));
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
        return Arrays.asList(SlotTag.Shield, SlotTag.Plate);
    }

    @Override
    public Item getItem() {
        return ModItems.TOWER_SHIELD.get();
    }

    @Override
    public String GUID() {
        return "tower_shield";
    }

    @Override
    public String locNameForLangFile() {
        return "Tower Shield";
    }
}
