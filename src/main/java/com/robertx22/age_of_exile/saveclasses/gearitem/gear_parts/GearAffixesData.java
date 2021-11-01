package com.robertx22.age_of_exile.saveclasses.gearitem.gear_parts;

import com.robertx22.age_of_exile.database.data.affixes.Affix;
import com.robertx22.age_of_exile.database.data.rarities.GearRarity;
import com.robertx22.age_of_exile.saveclasses.gearitem.gear_bases.IGearPartTooltip;
import com.robertx22.age_of_exile.saveclasses.gearitem.gear_bases.TooltipInfo;
import com.robertx22.age_of_exile.saveclasses.item_classes.GearItemData;
import com.robertx22.age_of_exile.saveclasses.item_classes.tooltips.TooltipStatWithContext;
import com.robertx22.library_of_exile.utils.RandomUtils;
import info.loenwind.autosave.annotations.Storable;
import info.loenwind.autosave.annotations.Store;
import net.minecraft.util.text.ITextComponent;

import java.util.ArrayList;
import java.util.List;

@Storable
public class GearAffixesData implements IGearPartTooltip {

    @Store
    public List<AffixData> suf = new ArrayList<>();
    @Store
    public List<AffixData> pre = new ArrayList<>();
    @Store
    public List<AffixData> cor = new ArrayList<>();

    public boolean canCorrupt() {
        return cor.isEmpty();
    }

    public void addCorruptAffix(GearItemData gear) {
        // todo currently just gives a normal affix

        if (RandomUtils.roll(50)) {
            AffixData suffix = new AffixData(Affix.Type.suffix);
            suffix.RerollFully(gear);
            cor.add(suffix);
        } else {
            AffixData prefix = new AffixData(Affix.Type.prefix);
            prefix.RerollFully(gear);
            cor.add(prefix);
        }

    }

    public List<TooltipStatWithContext> getAllStatsWithCtx(GearItemData gear, TooltipInfo info) {
        List<TooltipStatWithContext> list = new ArrayList<>();
        this.suf.forEach(x -> list.addAll(x.getAllStatsWithCtx(gear, info)));
        this.pre.forEach(x -> list.addAll(x.getAllStatsWithCtx(gear, info)));
        this.cor.forEach(x -> list.addAll(x.getAllStatsWithCtx(gear, info)));
        return list;
    }

    @Override
    public List<ITextComponent> GetTooltipString(TooltipInfo info, GearItemData gear) {
        List<ITextComponent> list = new ArrayList<ITextComponent>();
        getAllStatsWithCtx(gear, info).forEach(x -> list.addAll(x.GetTooltipString(info)));
        return list;
    }

    public int getMaxAffixesPerType(GearItemData gear) {
        int affixes = gear.getRarity()
            .maximumOfOneAffixType();

        return affixes;
    }

    public void add(AffixData affix) {
        if (affix.type.isSuffix()) {
            suf.add(affix);
        } else {
            pre.add(affix);
        }
    }

    public boolean canGetMore(Affix.Type type, GearItemData gear) {

        if (gear.hasCraftedStats()) {
            return false;
        }

        int current;
        if (type == Affix.Type.prefix) {
            current = pre
                .size();
        } else {
            current = suf.size();
        }

        return current < getMaxAffixesPerType(gear);

    }

    public int getNumberOfPrefixes() {
        return pre.size();
    }

    public int getNumberOfSuffixes() {
        return suf.size();
    }

    public void randomize(GearItemData gear) {

        GearRarity rar = gear.getRarity();

        for (int i = 0; i < rar
            .maximumOfOneAffixType(); i++) {

            AffixData suffix = new AffixData(Affix.Type.suffix);
            suffix.RerollFully(gear);
            suf.add(suffix);

            AffixData prefix = new AffixData(Affix.Type.prefix);
            prefix.RerollFully(gear);
            pre.add(prefix);

        }

        int minaffixes = rar
            .affixes;
        int affixesToGen = minaffixes - (this.getNumberOfAffixes());

        while (affixesToGen > 0) {
            addOneRandomAffix(gear);
            affixesToGen--;

        }
    }

    public void addOneRandomAffix(GearItemData gear) {
        if (getNumberOfPrefixes() > getNumberOfSuffixes()) {
            AffixData suffix = new AffixData(Affix.Type.suffix);
            suffix.RerollFully(gear);
            suf.add(suffix);
        } else {
            AffixData prefix = new AffixData(Affix.Type.prefix);
            prefix.RerollFully(gear);
            pre.add(prefix);
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

        list.addAll(pre);
        list.addAll(suf);
        list.addAll(cor);

        return list;
    }

    public boolean containsAffix(Affix affix) {
        return containsAffix(affix.GUID());
    }

    public boolean containsAffix(String id) {
        return getAllAffixesAndSockets().stream()
            .anyMatch(x -> x.affix.equals(id));
    }

    public int getNumberOfAffixes() {
        return getNumberOfPrefixes() + getNumberOfSuffixes();
    }

    @Override
    public Part getPart() {
        return Part.AFFIX;
    }
}
