package com.robertx22.mine_and_slash.saveclasses.gearitem.gear_bases;

import com.robertx22.mine_and_slash.capability.entity.EntityCap.UnitData;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import java.util.List;

public class TooltipContext {

    public TooltipContext(ItemStack stack, List<Text> tooltip, UnitData data) {
        this.stack = stack;
        this.tooltip = tooltip;
        this.data = data;
    }

    public ItemStack stack;
    public List<Text> tooltip;
    public UnitData data;

}
