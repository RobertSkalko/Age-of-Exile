package com.robertx22.age_of_exile.database.data.currency.loc_reqs;

import com.robertx22.age_of_exile.database.data.affixes.Affix;
import com.robertx22.age_of_exile.database.registry.Database;
import com.robertx22.age_of_exile.saveclasses.item_classes.GearItemData;
import com.robertx22.age_of_exile.uncommon.interfaces.data_items.IRarity;
import com.robertx22.age_of_exile.uncommon.localization.Words;
import net.minecraft.text.MutableText;

import java.util.Comparator;
import java.util.function.Predicate;

public class SimpleGearLocReq extends BaseLocRequirement {

    public static final SimpleGearLocReq HAS_HIGHER_RARITY = new SimpleGearLocReq(
        x -> x.getRarity()
            .hasHigherRarity(), Words.HasHigherRarity.locName());

    public static final SimpleGearLocReq HAS_EMPTY_SOCKETS = new SimpleGearLocReq(
        x -> x.sockets.getEmptySockets() > 0, Words.HasEmptySockets.locName());

    public static final SimpleGearLocReq CAN_GET_MORE_SOCKETS = new SimpleGearLocReq(
        x -> x.sockets.max_sockets < x.getRarity()
            .maxSockets(), Words.CanGetMoreSockets.locName());
    public static final SimpleGearLocReq IS_SEALED = new SimpleGearLocReq(
        x -> x.sealed, Words.IsSealed.locName());

    public static final SimpleGearLocReq CAN_GET_MORE_AFFIXES = new SimpleGearLocReq(
        x -> x.affixes.canGetMore(Affix.Type.prefix, x) || x.affixes.canGetMore(Affix.Type.suffix, x), Words.CantGetMoreAffixes.locName());

    public static final SimpleGearLocReq IS_COMMON = new SimpleGearLocReq(
        x -> x.rarity == IRarity.Common, Words.IsCommon.locName());
    public static final SimpleGearLocReq IS_NOT_HIGHEST_RARITY = new SimpleGearLocReq(
        x -> x.rarity < Database.GearRarities()
            .getAllRarities()
            .stream()
            .filter(t -> t.Rank() != IRarity.Unique)
            .max(Comparator.comparingInt(r -> r.Rank()))
            .get()
            .Rank(), Words.IsNotMaxRarity.locName());
    public static final SimpleGearLocReq IS_NOT_UNIQUE = new SimpleGearLocReq(
        x -> !x.isUnique(), Words.isNotUnique.locName());
    public static final SimpleGearLocReq IS_UNIQUE = new SimpleGearLocReq(
        x -> x.isUnique(), Words.isUnique.locName());
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
