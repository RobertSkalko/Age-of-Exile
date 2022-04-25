package com.robertx22.age_of_exile.database.data.currency.loc_reqs;

import com.robertx22.age_of_exile.database.data.groups.GearRarityGroups;
import com.robertx22.age_of_exile.database.registry.ExileDB;
import com.robertx22.age_of_exile.saveclasses.item_classes.GearItemData;
import com.robertx22.age_of_exile.uncommon.interfaces.data_items.IRarity;
import com.robertx22.age_of_exile.uncommon.localization.Words;
import net.minecraft.util.text.IFormattableTextComponent;

import java.util.Comparator;
import java.util.function.Predicate;

public class SimpleGearLocReq extends BaseLocRequirement {

    public static final SimpleGearLocReq HAS_HIGHER_RARITY = new SimpleGearLocReq(
        x -> x.getRarity()
            .hasHigherRarity(), Words.HasHigherRarity.locName());

    public static final SimpleGearLocReq IS_COMMON = new SimpleGearLocReq(
        x -> x.rarity.equals(IRarity.COMMON_ID), Words.IsCommon.locName());

    public static final SimpleGearLocReq IS_NOT_HIGHEST_RARITY = new SimpleGearLocReq(
        x -> ExileDB.GearRarityGroups()
            .get(GearRarityGroups.NON_UNIQUE_ID)
            .getRarities()

            .stream()
            .max(Comparator.comparingDouble(r -> r.itemTierPower()))
            .get()
            .isHigherThan(x.getRarity())
        , Words.IsNotMaxRarity.locName());
    public static final SimpleGearLocReq IS_NOT_UNIQUE = new SimpleGearLocReq(
        x -> !x.isUnique(), Words.isNotUnique.locName());
    public static final SimpleGearLocReq IS_UNIQUE = new SimpleGearLocReq(
        x -> x.getRarity().is_unique_item, Words.isUnique.locName());
    public static final BaseLocRequirement HAS_PRIMARY_STATS = new SimpleGearLocReq(
        x -> x.baseStats != null, Words.hasSet.locName());
    public static final BaseLocRequirement HAS_UNIQUE_STATS = new SimpleGearLocReq(
        x -> x.uniqueStats != null, Words.hasUniqueStats.locName());

    public SimpleGearLocReq(Predicate<GearItemData> pred, IFormattableTextComponent text) {
        this.text = text;
        this.gearsThatCanDoThis = pred;
    }

    Predicate<GearItemData> gearsThatCanDoThis;
    IFormattableTextComponent text;

    @Override
    public IFormattableTextComponent getText() {

        return text;
    }

    boolean returnIfIsntGear = false; // for tools

    public SimpleGearLocReq setTrueIfNotGear() {
        this.returnIfIsntGear = true;
        return this;
    }

    @Override
    public boolean isAllowed(LocReqContext context) {

        if (context.data instanceof GearItemData) {
            GearItemData gear = (GearItemData) context.data;
            return gearsThatCanDoThis.test(gear);
        }

        return returnIfIsntGear;
    }

}
