package com.robertx22.age_of_exile.saveclasses.gearitem.gear_parts;

import com.robertx22.age_of_exile.database.data.affixes.Affix;
import com.robertx22.age_of_exile.database.registry.ExileDB;
import com.robertx22.age_of_exile.saveclasses.ExactStatData;
import com.robertx22.age_of_exile.saveclasses.gearitem.gear_bases.*;
import com.robertx22.age_of_exile.saveclasses.item_classes.GearItemData;
import com.robertx22.age_of_exile.saveclasses.item_classes.tooltips.TooltipStatInfo;
import com.robertx22.age_of_exile.saveclasses.item_classes.tooltips.TooltipStatWithContext;
import com.robertx22.library_of_exile.registry.FilterListWrap;
import info.loenwind.autosave.annotations.Factory;
import info.loenwind.autosave.annotations.Storable;
import info.loenwind.autosave.annotations.Store;
import net.minecraft.util.text.ITextComponent;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Storable
public class AffixData implements IRerollable, IGearPartTooltip, IStatsContainer {

    // MAJOR NBT PROBLEM. RENAME ALL THESE

    @Store
    public Integer perc = 0;

    @Store
    public String affix;

    @Store
    public Affix.Type type;

    public AffixData(Affix.Type type) {
        this.type = type;
    }

    @Factory
    private AffixData() {
    }

    public boolean isEmpty() {
        return perc < 1;
    }

    public Affix.Type getAffixType() {
        return type;
    }

    @Override
    public List<ITextComponent> GetTooltipString(TooltipInfo info, GearItemData gear) {
        List<ITextComponent> list = new ArrayList<ITextComponent>();
        getAllStatsWithCtx(gear, info).forEach(x -> list.addAll(x.GetTooltipString(info)));
        return list;
    }

    public Affix getAffix() {
        return ExileDB.Affixes()
            .get(this.affix);
    }

    @Override
    public IGearPart.Part getPart() {
        return IGearPart.Part.AFFIX;
    }

    @Override
    public void RerollNumbers(GearItemData gear) {
        perc = getMinMax(gear)
            .random();
    }

    public final Affix BaseAffix() {
        return ExileDB.Affixes()
            .get(affix);
    }

    public List<TooltipStatWithContext> getAllStatsWithCtx(GearItemData gear, TooltipInfo info) {
        List<TooltipStatWithContext> list = new ArrayList<>();
        this.BaseAffix()
            .getStats()
            .forEach(x -> {
                ExactStatData exact = x.ToExactStat(perc, gear.getEffectiveLevel());
                list.add(new TooltipStatWithContext(new TooltipStatInfo(exact, perc, info), x, (int) gear.getEffectiveLevel()));
            });
        return list;
    }

    public boolean isValid() {
        if (!ExileDB.Affixes()
            .isRegistered(this.affix)) {
            return false;
        }
        if (this.isEmpty()) {
            return false;
        }

        return true;
    }

    @Override
    public List<ExactStatData> GetAllStats(GearItemData gear) {

        if (!isValid()) {
            return Arrays.asList();
        }

        return this.BaseAffix()
            .getStats()
            .stream()
            .map(x -> x.ToExactStat(perc, gear.getEffectiveLevel()))
            .collect(Collectors.toList());

    }

    public void create(GearItemData gear, Affix suffix) {
        affix = suffix.GUID();
        RerollNumbers(gear);
    }

    @Override
    public void RerollFully(GearItemData gear) {

        Affix affix = null;
        try {

            FilterListWrap<Affix> list = ExileDB.Affixes()
                .getFilterWrapped(x -> x.type == getAffixType() && gear.canGetAffix(x));

            if (list.list.isEmpty()) {
                System.out.print("Gear Type: " + gear.gear_type + " affixtype: " + this.type.name());
            }

            affix = list
                .random();
        } catch (Exception e) {
            System.out.print("Gear Type: " + gear.gear_type + " affixtype: " + this.type.name());
            e.printStackTrace();
        }

        this.create(gear, affix);

    }
}
