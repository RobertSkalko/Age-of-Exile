package com.robertx22.mine_and_slash.database.data.gearitemslots.offhand;

import com.robertx22.mine_and_slash.database.data.StatModifier;
import com.robertx22.mine_and_slash.database.data.gearitemslots.bases.BaseGearType;
import com.robertx22.mine_and_slash.database.data.gearitemslots.bases.BaseOffHand;
import com.robertx22.mine_and_slash.database.data.stats.types.offense.SpellDamage;
import com.robertx22.mine_and_slash.database.data.stats.types.resources.MagicShield;
import com.robertx22.mine_and_slash.mmorpg.registers.common.ModItems;
import com.robertx22.mine_and_slash.saveclasses.gearitem.gear_bases.StatRequirement;
import com.robertx22.mine_and_slash.uncommon.enumclasses.ModType;
import net.minecraft.item.Item;

import java.util.Arrays;
import java.util.List;

public class SpiritShield extends BaseOffHand {
    public static BaseGearType INSTANCE = new SpiritShield();

    private SpiritShield() {

    }

    @Override
    public List<StatModifier> baseStats() {
        return Arrays.asList(new StatModifier(5, 15, MagicShield.getInstance(), ModType.FLAT));
    }

    @Override
    public List<StatModifier> implicitStats() {
        return Arrays.asList(new StatModifier(3, 8, SpellDamage.getInstance(), ModType.FLAT));
    }

    @Override
    public StatRequirement getStatRequirements() {
        return new StatRequirement().intelligence(0.5F);
    }

    @Override
    public List<SlotTag> getTags() {
        return Arrays.asList(SlotTag.Shield, SlotTag.Cloth);
    }

    @Override
    public Item getItem() {
        return ModItems.SPIRIT_SHIELD.get();
    }

    @Override
    public String GUID() {
        return "spirit_shield";
    }

    @Override
    public String locNameForLangFile() {
        return "Spirit Shield";
    }
}
