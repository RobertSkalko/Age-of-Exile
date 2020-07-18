package com.robertx22.mine_and_slash.saveclasses.gearitem.gear_parts;

import com.robertx22.mine_and_slash.database.data.affixes.Affix;
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
    public List<AffixData> prefix_sockets = new ArrayList<>();
    @Store
    public List<AffixData> suffix_sockets = new ArrayList<>();

    public int getSocketedJewelsCount() {
        return (int) getAllAffixesAndSockets().stream()
            .filter(x -> x.is_socket && !x.isEmpty())
            .count();

    }

    public int getMaxAffixesPerType(GearItemData gear) {
        return gear.getRarity()
            .maximumOfOneAffixType();
    }

    public int getNumberOfPrefixes() {
        return prefixes.size();
    }

    public int getNumberOfSuffixes() {
        return suffixes.size();
    }

    public int getNumberOfEmptySockets() {
        return (int) (prefix_sockets.stream()
            .filter(x -> x.isSocketAndEmpty())
            .count() + suffix_sockets.stream()
            .filter(x -> x.isSocketAndEmpty())
            .count());
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

        for (int i = 0; i < gear.getRarity()
            .maxSockets() / 2; i++) {
            if (RandomUtils.roll(gear.getRarity()
                .socketChance())) {
                AffixData socket = new AffixData(Affix.Type.prefix);
                socket.is_socket = true;
                this.prefix_sockets.add(socket);
            }
            if (RandomUtils.roll(gear.getRarity()
                .socketChance())) {
                AffixData socket = new AffixData(Affix.Type.suffix);
                socket.is_socket = true;
                this.suffix_sockets.add(socket);
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
        list.addAll(prefix_sockets);
        list.addAll(suffix_sockets);

        return list;
    }

    public boolean containsAffix(Affix affix) {
        return containsAffix(affix.GUID());
    }

    public boolean containsAffix(String id) {
        return getAllAffixesAndSockets().stream()
            .anyMatch(x -> x.baseAffix == id);
    }

    public int getNumberOfAffixes() {
        return getNumberOfPrefixes() + getNumberOfSuffixes();
    }

}
