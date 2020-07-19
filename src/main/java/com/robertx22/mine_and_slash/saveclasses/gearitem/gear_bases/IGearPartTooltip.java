package com.robertx22.mine_and_slash.saveclasses.gearitem.gear_bases;

import com.robertx22.mine_and_slash.database.data.MinMax;
import com.robertx22.mine_and_slash.saveclasses.item_classes.GearItemData;
import java.util.List;
import net.minecraft.text.MutableText;

public interface IGearPartTooltip extends IGearPart {

    public List<MutableText> GetTooltipString(TooltipInfo info, GearItemData gear);

    default MinMax getMinMax(GearItemData gear) {
        return gear.getRarity()
            .getStatPercentsFor(getPart());
    }
}


