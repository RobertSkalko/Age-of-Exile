package com.robertx22.age_of_exile.saveclasses.gearitem.gear_parts;

import com.robertx22.age_of_exile.database.data.affixes.Affix;
import com.robertx22.age_of_exile.database.registry.Database;
import com.robertx22.age_of_exile.database.registry.FilterListWrap;
import com.robertx22.age_of_exile.saveclasses.ExactStatData;
import com.robertx22.age_of_exile.saveclasses.gearitem.gear_bases.*;
import com.robertx22.age_of_exile.saveclasses.item_classes.GearItemData;
import com.robertx22.age_of_exile.saveclasses.item_classes.tooltips.TooltipStatInfo;
import com.robertx22.age_of_exile.saveclasses.item_classes.tooltips.TooltipStatWithContext;
import com.robertx22.age_of_exile.uncommon.utilityclasses.RandomUtils;
import info.loenwind.autosave.annotations.Factory;
import info.loenwind.autosave.annotations.Storable;
import info.loenwind.autosave.annotations.Store;
import net.minecraft.text.Text;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Storable
public class AffixData implements IRerollable, IGearPartTooltip, IStatsContainer {

    // MAJOR NBT PROBLEM. RENAME ALL THESE

    @Store
    public Integer percent = 0;

    @Store
    public String baseAffix;

    @Store
    public Integer tier = 10;

    @Store
    public Affix.Type affixType;

    public AffixData(Affix.Type type) {
        this.affixType = type;
    }

    @Factory
    private AffixData() {
    }

    public boolean isEmpty() {
        return percent < 1;
    }

    public Affix.Type getAffixType() {
        return affixType;
    }

    @Override
    public List<Text> GetTooltipString(TooltipInfo info, GearItemData gear) {
        List<Text> list = new ArrayList<Text>();
        getAllStatsWithCtx(gear, info).forEach(x -> list.addAll(x.GetTooltipString(info)));
        return list;
    }

    public Affix getAffix() {
        return Database.Affixes()
            .get(this.baseAffix);
    }

    @Override
    public IGearPart.Part getPart() {
        return IGearPart.Part.AFFIX;
    }

    @Override
    public void RerollNumbers(GearItemData gear) {
        percent = getMinMax(gear)
            .random();

    }

    public final Affix BaseAffix() {
        return Database.Affixes()
            .get(baseAffix);
    }

    public List<TooltipStatWithContext> getAllStatsWithCtx(GearItemData gear, TooltipInfo info) {
        List<TooltipStatWithContext> list = new ArrayList<>();
        this.BaseAffix()
            .getTierStats(tier)
            .forEach(x -> {
                ExactStatData exact = x.ToExactStat(percent, gear.lvl);
                list.add(new TooltipStatWithContext(new TooltipStatInfo(exact, percent, info), x, gear.lvl));
            });
        return list;
    }

    public boolean isValid() {
        if (!Database.Affixes()
            .isRegistered(this.baseAffix)) {
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
            .getTierStats(tier)
            .stream()
            .map(x -> x.ToExactStat(percent, gear.lvl))
            .collect(Collectors.toList());

    }

    public void create(GearItemData gear, Affix suffix) {
        baseAffix = suffix.GUID();
        this.tier = RandomUtils.weightedRandom(suffix.tier_map.values()).tier;
        RerollNumbers(gear);
    }

    @Override
    public void RerollFully(GearItemData gear) {

        Affix affix = null;
        try {

            FilterListWrap<Affix> list = Database.Affixes()
                .getFilterWrapped(x -> x.type == getAffixType() && gear.canGetAffix(x));

            if (list.list.isEmpty()) {
                System.out.print("Gear Type: " + gear.gear_type + " affixtype: " + this.affixType.name());
            }

            affix = list
                .random();
        } catch (Exception e) {
            System.out.print("Gear Type: " + gear.gear_type + " affixtype: " + this.affixType.name());
            e.printStackTrace();
        }

        this.create(gear, affix);

    }
}
