package com.robertx22.age_of_exile.vanilla_mc.items.repair_hammers;

import com.robertx22.age_of_exile.mmorpg.registers.common.ModItemTags;
import net.minecraft.item.Item;
import net.minecraft.tag.Tag;

public class RepairHammer0 extends RepairHammerItem {

    public RepairHammer0(int repairAmount) {
        super(repairAmount);
    }

    @Override
    public Tag.Identified<Item> getGemTag() {
        return ModItemTags.CHIPPED_GEMS;
    }

    @Override
    public String locNameForLangFile() {
        return "Common Repair Hammer";
    }
}
