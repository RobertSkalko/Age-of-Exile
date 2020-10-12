package com.robertx22.age_of_exile.saveclasses.item_classes.tooltips;

import com.robertx22.age_of_exile.database.data.StatModifier;
import com.robertx22.age_of_exile.saveclasses.gearitem.gear_bases.ITooltipList;
import com.robertx22.age_of_exile.saveclasses.gearitem.gear_bases.TooltipInfo;
import com.robertx22.age_of_exile.saveclasses.item_classes.GearItemData;
import net.minecraft.text.Text;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class TooltipStatWithContext implements ITooltipList {

    public TooltipStatInfo statinfo;
    public @Nullable StatModifier mod;
    public @Nullable GearItemData gear;

    public TooltipStatWithContext(TooltipStatInfo statinfo, @Nullable StatModifier mod, GearItemData gear) {
        this.statinfo = statinfo;
        this.mod = mod;
        this.gear = gear;
    }

    @Override
    public List<Text> GetTooltipString(TooltipInfo info) {
        return statinfo.stat.getTooltipList(this);
    }

    public boolean showStatRanges() {
        if (statinfo.useInDepthStats()) {
            if (mod != null) {
                if (gear != null) {
                    return true;
                }
            }
        }
        return false;
    }
}
