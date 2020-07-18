package com.robertx22.mine_and_slash.saveclasses.gearitem.gear_parts;

import com.robertx22.mine_and_slash.database.data.affixes.Affix;
import com.robertx22.exiled_lib.registry.SlashRegistry;
import com.robertx22.mine_and_slash.saveclasses.ExactStatData;
import com.robertx22.mine_and_slash.saveclasses.gearitem.gear_bases.*;
import com.robertx22.mine_and_slash.saveclasses.item_classes.GearItemData;
import com.robertx22.mine_and_slash.uncommon.utilityclasses.RandomUtils;
import info.loenwind.autosave.annotations.Factory;
import info.loenwind.autosave.annotations.Storable;
import info.loenwind.autosave.annotations.Store;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import net.minecraft.text.Text;

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

        Affix affix = BaseAffix();

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
        this.tier = RandomUtils.weightedRandom(suffix.tierMap.values()).tier;
        this.level = gear.level;
        RerollNumbers(gear);
    }

    @Override
    public void RerollFully(GearItemData gear) {

        Affix affix = SlashRegistry.Affixes()
            .getFilterWrapped(x -> x.type == getAffixType() && !gear.affixes.containsAffix(x))
            .allThatMeetRequirement(gear)
            .random();

        this.create(gear, affix);

    }
}
