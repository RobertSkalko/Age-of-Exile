package com.robertx22.age_of_exile.saveclasses.gearitem.gear_parts;

import com.robertx22.age_of_exile.database.data.affixes.Affix;
import com.robertx22.age_of_exile.database.registry.FilterListWrap;
import com.robertx22.age_of_exile.database.registry.SlashRegistry;
import com.robertx22.age_of_exile.saveclasses.ExactStatData;
import com.robertx22.age_of_exile.saveclasses.gearitem.gear_bases.*;
import com.robertx22.age_of_exile.saveclasses.item_classes.GearItemData;
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

    @Store
    public Integer percent = 0;

    @Store
    public Integer level = 0;

    @Store
    public String baseAffix;

    @Store
    public Integer tier = 10;

    @Store
    public Affix.Type affixType;

    @Store
    public boolean is_socket = false;

    public AffixData(Affix.Type type) {
        this.affixType = type;
    }

    @Factory
    private AffixData() {
    }

    public boolean isSocketAndEmpty() {
        return this.is_socket && percent < 1;
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
        GetAllStats(gear).forEach(x -> list.addAll(x.GetTooltipString(info)));
        return list;
    }

    public Affix getAffix() {
        return SlashRegistry.Affixes()
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
        return SlashRegistry.Affixes()
            .get(baseAffix);
    }

    @Override
    public List<ExactStatData> GetAllStats(GearItemData gear) {

        if (!SlashRegistry.Affixes()
            .isRegistered(this.baseAffix)) {
            return Arrays.asList();
        }
        if (this.isSocketAndEmpty()) {
            return Arrays.asList();
        }

        return this.BaseAffix()
            .getTierStats(tier)
            .stream()
            .map(x -> x.ToExactStat(percent, this.level))
            .collect(Collectors.toList());

    }

    public void create(GearItemData gear, Affix suffix) {
        baseAffix = suffix.GUID();
        this.tier = RandomUtils.weightedRandom(suffix.tier_map.values()).tier;
        this.level = gear.level;
        RerollNumbers(gear);
    }

    @Override
    public void RerollFully(GearItemData gear) {

        Affix affix = null;
        try {

            FilterListWrap<Affix> list = SlashRegistry.Affixes()
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
