package com.robertx22.age_of_exile.mmorpg.registers.common.items;

import com.robertx22.age_of_exile.database.data.currency.*;
import com.robertx22.age_of_exile.database.data.currency.base.CurrencyItem;
import com.robertx22.age_of_exile.mmorpg.registers.deferred_wrapper.Def;
import com.robertx22.age_of_exile.mmorpg.registers.deferred_wrapper.RegObj;

import java.util.ArrayList;
import java.util.List;

public class CurrencyItems {

    public static void init() {

    }

    public static List<RegObj<CurrencyItem>> currencies = new ArrayList<>();

    public static RegObj<CurrencyItem> ORB_OF_TRANSMUTATION = of(new OrbOfTransmutationItem());
    public static RegObj<CurrencyItem> CLEAR_CORRUPTION = of(new PurifyItem());
    public static RegObj<CurrencyItem> CRYSTAL_OF_PURIFICATION = of(new CrystalOfPurificationItem());
    public static RegObj<CurrencyItem> CRYSTAL_OF_TRUTH = of(new CrystalOfTruth());
    public static RegObj<CurrencyItem> CLEAR_RUNES = of(new ClearSocketsItem());
    public static RegObj<CurrencyItem> ORB_OF_DISORDER = of(new OrbOfDisorder());
    public static RegObj<CurrencyItem> ORB_OF_TURBULENCE = of(new OrbOfTurbulence());
    public static RegObj<CurrencyItem> STONE_OF_HOPE = of(new StoneOfHopeItem());
    public static RegObj<CurrencyItem> LEAF_OF_CHANGE = of(new LeafOfChangeItem());
    public static RegObj<CurrencyItem> ORB_OF_BLESSING = of(new OrbOfBlessingItem());
    public static RegObj<CurrencyItem> ORB_OF_UNIQUE_BLESSING = of(new OrbOfUniqueBlessingItem());
    public static RegObj<CurrencyItem> ORB_OF_INFINITY = of(new OrbOfInfinityItem());
    public static RegObj<CurrencyItem> CLEAR_INSTABILITY = of(new ClearInstabilityItem());
    public static RegObj<CurrencyItem> ORB_OF_CORRUPTION = of(new OrbOfCorruption());

    static RegObj<CurrencyItem> of(CurrencyItem c) {

        RegObj<CurrencyItem> def = Def.item(() -> c);

        currencies.add(def);

        return def;

    }

}
