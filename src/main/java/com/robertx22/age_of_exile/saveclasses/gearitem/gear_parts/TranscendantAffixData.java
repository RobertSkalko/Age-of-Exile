package com.robertx22.age_of_exile.saveclasses.gearitem.gear_parts;

import com.robertx22.age_of_exile.database.data.transc_affix.TranscendentAffix;
import com.robertx22.age_of_exile.database.registry.ExileDB;
import com.robertx22.age_of_exile.saveclasses.ExactStatData;
import com.robertx22.age_of_exile.saveclasses.gearitem.gear_bases.IGearPartTooltip;
import com.robertx22.age_of_exile.saveclasses.gearitem.gear_bases.IStatsContainer;
import com.robertx22.age_of_exile.saveclasses.gearitem.gear_bases.TooltipInfo;
import com.robertx22.age_of_exile.saveclasses.item_classes.GearItemData;
import info.loenwind.autosave.annotations.Storable;
import info.loenwind.autosave.annotations.Store;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextFormatting;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Storable
public class TranscendantAffixData implements IStatsContainer, IGearPartTooltip {

    @Store
    public String id = "";
    @Store
    public int perc = 0;

    @Override
    public List<ExactStatData> GetAllStats(GearItemData gear) {
        return ExileDB.TranscendentAffixes()
            .get(id).stats.stream()
            .map(x -> x.ToExactStat(perc, gear.getEffectiveLevel()))
            .collect(Collectors.toList());
    }

    @Override
    public Part getPart() {
        return Part.OTHER;
    }

    @Override
    public List<ITextComponent> GetTooltipString(TooltipInfo info, GearItemData gear) {
        List<ITextComponent> list = new ArrayList<>();
        TranscendentAffix affix = ExileDB.TranscendentAffixes()
            .get(id);

        list.add(affix.locName()
            .withStyle(TextFormatting.BLUE));

        GetAllStats(gear).forEach(x -> x.GetTooltipString(info)
            .forEach(e -> list.add(e)));

        return list;
    }
}
