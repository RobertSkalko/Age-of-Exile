package com.robertx22.age_of_exile.saveclasses.gearitem.gear_parts;

import com.robertx22.age_of_exile.database.data.affixes.Affix;
import com.robertx22.age_of_exile.saveclasses.gearitem.gear_bases.IGearPartTooltip;
import com.robertx22.age_of_exile.saveclasses.gearitem.gear_bases.TooltipInfo;
import com.robertx22.age_of_exile.saveclasses.item_classes.GearItemData;
import com.robertx22.age_of_exile.saveclasses.item_classes.tooltips.TooltipStatWithContext;
import com.robertx22.age_of_exile.uncommon.utilityclasses.RandomUtils;
import info.loenwind.autosave.annotations.Storable;
import info.loenwind.autosave.annotations.Store;
import net.minecraft.text.Text;

import java.util.ArrayList;
import java.util.List;

@Storable
public class GearAffixesData implements IGearPartTooltip {

    @Store
    public List<AffixData> suffixes = new ArrayList<>();
    @Store
    public List<AffixData> prefixes = new ArrayList<>();

    public List<TooltipStatWithContext> getAllStatsWithCtx(GearItemData gear, TooltipInfo info) {
        List<TooltipStatWithContext> list = new ArrayList<>();
        this.suffixes.forEach(x -> list.addAll(x.getAllStatsWithCtx(gear, info)));
        this.prefixes.forEach(x -> list.addAll(x.getAllStatsWithCtx(gear, info)));
        return list;
    }

    @Override
    public List<Text> GetTooltipString(TooltipInfo info, GearItemData gear) {
        List<Text> list = new ArrayList<Text>();
        getAllStatsWithCtx(gear, info).forEach(x -> list.addAll(x.GetTooltipString(info)));
        return list;
    }

    public int getMaxAffixesPerType(GearItemData gear) {
        int affixes = gear.getRarity()
            .maximumOfOneAffixType();

        return affixes;
    }

    public void add(AffixData affix) {
        if (affix.affixType.isSuffix()) {
            suffixes.add(affix);
        } else {
            prefixes.add(affix);
        }
    }

    public boolean canGetMore(Affix.Type type, GearItemData gear) {

        int current;
        if (type == Affix.Type.prefix) {
            current = prefixes
                .size();
        } else {
            current = suffixes.size();
        }

        return current < getMaxAffixesPerType(gear);

    }

    public int getNumberOfPrefixes() {
        return prefixes.size();
    }

    public int getNumberOfSuffixes() {
        return suffixes.size();
    }

    public void randomize(GearItemData gear) {

        for (int i = 0; i < gear.getRarity()
            .maximumOfOneAffixType(); i++) {

            if (RandomUtils.roll(gear.getRarity()
                .AffixChance())) {
                AffixData suffix = new AffixData(Affix.Type.suffix);
                suffix.RerollFully(gear);
                suffixes.add(suffix);
            }

            if (RandomUtils.roll(gear.getRarity()
                .AffixChance())) {
                AffixData prefix = new AffixData(Affix.Type.prefix);
                prefix.RerollFully(gear);
                prefixes.add(prefix);
            }
        }

        int minaffixes = gear.getRarity()
            .minAffixes();
        int affixesToGen = minaffixes - getNumberOfAffixes();

        while (affixesToGen > 0) {
            if (getNumberOfPrefixes() > getNumberOfSuffixes()) {
                AffixData suffix = new AffixData(Affix.Type.suffix);
                suffix.RerollFully(gear);
                suffixes.add(suffix);
            } else {
                AffixData prefix = new AffixData(Affix.Type.prefix);
                prefix.RerollFully(gear);
                prefixes.add(prefix);
            }

            affixesToGen--;
        }

    }

    public boolean hasSuffix() {
        return getNumberOfSuffixes() > 0;
    }

    public boolean hasPrefix() {
        return getNumberOfPrefixes() > 0;
    }

    public List<AffixData> getAllAffixesAndSockets() {
        List<AffixData> list = new ArrayList<>();

        list.addAll(prefixes);
        list.addAll(suffixes);

        return list;
    }

    public boolean containsAffix(Affix affix) {
        return containsAffix(affix.GUID());
    }

    public boolean containsAffix(String id) {
        return getAllAffixesAndSockets().stream()
            .anyMatch(x -> x.baseAffix.equals(id));
    }

    public int getNumberOfAffixes() {
        return getNumberOfPrefixes() + getNumberOfSuffixes();
    }

    @Override
    public Part getPart() {
        return Part.AFFIX;
    }
}
