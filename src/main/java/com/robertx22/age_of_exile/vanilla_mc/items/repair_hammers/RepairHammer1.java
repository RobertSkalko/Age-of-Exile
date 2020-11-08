package com.robertx22.age_of_exile.vanilla_mc.items.repair_hammers;

import com.robertx22.age_of_exile.mmorpg.registers.common.ModItemTags;
import net.minecraft.item.Item;
import net.minecraft.tag.Tag;

public class RepairHammer1 extends RepairHammerItem {

    public RepairHammer1(int repairAmount) {
        super(repairAmount);
    }

    @Override
    public Tag.Identified<Item> getGemTag() {
        return ModItemTags.FLAWED_GEMS;
    }

    @Override
    public String locNameForLangFile() {
        return "Magical Repair Hammer";
    }
}
