package com.robertx22.age_of_exile.saveclasses.item_classes.tooltips;

import com.robertx22.age_of_exile.database.data.StatModifier;
import com.robertx22.age_of_exile.saveclasses.gearitem.gear_bases.ITooltipList;
import com.robertx22.age_of_exile.saveclasses.gearitem.gear_bases.TooltipInfo;
import net.minecraft.util.text.ITextComponent;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class TooltipStatWithContext implements ITooltipList {

    public TooltipStatInfo statinfo;
    public @Nullable
    StatModifier mod;
    public @Nullable
    Integer level;

    public TooltipStatWithContext(TooltipStatInfo statinfo, @Nullable StatModifier mod, Integer level) {
        this.statinfo = statinfo;
        this.mod = mod;
        this.level = level;
    }

    @Override
    public List<ITextComponent> GetTooltipString(TooltipInfo info) {
        return statinfo.stat.getTooltipList(this);
    }

    public boolean showStatRanges() {
        if (statinfo.useInDepthStats()) {
            if (mod != null) {
                if (level != null) {
                    return true;
                }
            }
        }
        return false;
    }
}
