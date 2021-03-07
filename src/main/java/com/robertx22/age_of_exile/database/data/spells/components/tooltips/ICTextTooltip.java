package com.robertx22.age_of_exile.database.data.spells.components.tooltips;

import com.robertx22.age_of_exile.database.data.spells.components.MapHolder;
import com.robertx22.age_of_exile.saveclasses.gearitem.gear_bases.TooltipInfo;
import com.robertx22.age_of_exile.saveclasses.item_classes.CalculatedSpellData;
import net.minecraft.text.MutableText;

public interface ICTextTooltip {
    MutableText getText(TooltipInfo info, MapHolder holder, CalculatedSpellData spelldata);
}
