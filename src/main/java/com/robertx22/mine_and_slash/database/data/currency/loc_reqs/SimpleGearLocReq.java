package com.robertx22.mine_and_slash.database.data.currency.loc_reqs;

import com.robertx22.mine_and_slash.database.base.Rarities;
import com.robertx22.mine_and_slash.saveclasses.item_classes.GearItemData;
import com.robertx22.mine_and_slash.uncommon.interfaces.data_items.IRarity;
import com.robertx22.mine_and_slash.uncommon.localization.Words;
import java.util.Comparator;
import java.util.function.Predicate;

import net.minecraft.text.MutableText;

public class SimpleGearLocReq extends BaseLocRequirement {

    public static final SimpleGearLocReq NO_PREFIX = new SimpleGearLocReq(
        x -> x.affixes.prefixes.size() == 0, Words.NoPrefix.locName());
    public static final SimpleGearLocReq NO_SUFFIX = new SimpleGearLocReq(
        x -> x.affixes.suffixes.size() == 0l, Words.NoSuffix.locName());
    public static final SimpleGearLocReq IS_COMMON = new SimpleGearLocReq(
        x -> x.rarity == IRarity.Common, Words.IsCommon.locName());
    public static final SimpleGearLocReq IS_NOT_HIGHEST_RARITY = new SimpleGearLocReq(
        x -> x.rarity < Rarities.Gears.getAllRarities()
            .stream()
            .filter(t -> t.Rank() != IRarity.Unique)
            .max(Comparator.comparingInt(r -> r.Rank()))
            .get()
            .Rank(), Words.IsNotMaxRarity.locName());
    public static final SimpleGearLocReq IS_NOT_UNIQUE = new SimpleGearLocReq(
        x -> !x.isUnique(), Words.isNotUnique.locName());
    public static final SimpleGearLocReq IS_UNIQUE = new SimpleGearLocReq(
        x -> x.isUnique(), Words.isUnique.locName());

    public static final BaseLocRequirement HAS_PREFIX = new SimpleGearLocReq(
        x -> x.affixes.prefixes.size() > 0, Words.hasPrefix.locName());
    public static final BaseLocRequirement HAS_SUFFIX = new SimpleGearLocReq(
        x -> x.affixes.suffixes.size() > 0, Words.hasSuffix.locName());
    public static final BaseLocRequirement HAS_PRIMARY_STATS = new SimpleGearLocReq(
        x -> x.baseStats != null, Words.hasSet.locName());
    public static final BaseLocRequirement HAS_UNIQUE_STATS = new SimpleGearLocReq(
        x -> x.uniqueStats != null, Words.hasUniqueStats.locName());

    private SimpleGearLocReq(Predicate<GearItemData> pred, MutableText text) {
        this.text = text;
        this.gearsThatCanDoThis = pred;
    }

    Predicate<GearItemData> gearsThatCanDoThis;
    MutableText text;

    @Override
    public MutableText getText() {

        return text;
    }

    @Override
    public boolean isAllowed(LocReqContext context) {

        if (context.data instanceof GearItemData) {
            GearItemData gear = (GearItemData) context.data;
            return gearsThatCanDoThis.test(gear);
        }

        return false;
    }

}
