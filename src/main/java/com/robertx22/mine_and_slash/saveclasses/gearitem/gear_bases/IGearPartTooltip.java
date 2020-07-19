package com.robertx22.mine_and_slash.saveclasses.gearitem.gear_bases;

import com.robertx22.mine_and_slash.database.data.MinMax;
import com.robertx22.mine_and_slash.saveclasses.item_classes.GearItemData;
import net.minecraft.text.Text;

import java.util.List;

public interface IGearPartTooltip extends IGearPart {

    public List<Text> GetTooltipString(TooltipInfo info, GearItemData gear);

    default MinMax getMinMax(GearItemData gear) {
        return gear.getRarity()
            .getStatPercentsFor(getPart());
    }
}


