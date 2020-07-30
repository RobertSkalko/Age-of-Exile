package com.robertx22.mine_and_slash.saveclasses.gearitem.gear_parts;

import com.robertx22.mine_and_slash.database.data.affixes.Affix;
import com.robertx22.mine_and_slash.database.data.rarities.GearRarity;
import com.robertx22.mine_and_slash.saveclasses.item_classes.GearItemData;
import com.robertx22.mine_and_slash.uncommon.utilityclasses.RandomUtils;
import info.loenwind.autosave.annotations.Storable;
import info.loenwind.autosave.annotations.Store;

import java.util.ArrayList;
import java.util.List;

@Storable
public class GearAffixesData {

    @Store
    public List<AffixData> suffixes = new ArrayList<>();
    @Store
    public List<AffixData> prefixes = new ArrayList<>();

    @Store
    public int emptySockets = 0;

    public boolean canGetMorePrefixSockets(GearItemData gear) {
        return emptySockets > 0 && prefixes.size() < gear.getRarity()
            .maxSockets() / 2;
    }

    public boolean canGetMoreSuffixSockets(GearItemData gear) {
        return emptySockets > 0 && suffixes.size() < gear.getRarity()
            .maxSockets() / 2;
    }

    public int getSocketedJewelsCount() {
        return (int) getAllAffixesAndSockets().stream()
            .filter(x -> x.is_socket && !x.isEmpty())
            .count();

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
            current = (int) prefixes.stream()
                .filter(x -> !x.is_socket)
                .count();
        } else {
            current = (int) suffixes.stream()
                .filter(x -> !x.is_socket)
                .count();
        }

        return current < getMaxAffixesPerType(gear);

    }

    public int getNumberOfPrefixes() {
        return prefixes.size();
    }

    public int getNumberOfSuffixes() {
        return suffixes.size();
    }

    public int getNumberOfEmptySockets() {
        return emptySockets;
    }

    public void randomize(GearItemData gear) {

        GearRarity rarity = gear.getRarity();

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

        emptySockets = 0;
        for (int i = 0; i < rarity.maxSockets(); i++) {
            if (RandomUtils.roll(rarity.socketChance())) {
                emptySockets++;
            }
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

}
