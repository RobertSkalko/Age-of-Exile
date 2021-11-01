package com.robertx22.age_of_exile.mmorpg.registers.common.items;

import com.robertx22.age_of_exile.database.data.currency.*;
import com.robertx22.age_of_exile.database.data.currency.base.CurrencyItem;
import com.robertx22.age_of_exile.database.data.currency.upgrades.PlusOneUpgradeItem;
import com.robertx22.age_of_exile.database.data.currency.upgrades.PlusThreeUpgradeItem;
import com.robertx22.age_of_exile.database.data.currency.upgrades.PlusTwoUpgradeItem;
import com.robertx22.age_of_exile.mmorpg.registers.deferred_wrapper.Def;
import com.robertx22.age_of_exile.mmorpg.registers.deferred_wrapper.RegObj;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.List;
import java.util.stream.Collectors;

public class CurrencyItems {

    public static void init() {

    }

    public static List<CurrencyItem> getAllCurrenciesFromRegistry() {
        return ForgeRegistries.ITEMS.getValues()
            .stream()
            .filter(x -> x instanceof CurrencyItem)
            .map(x -> (CurrencyItem) x)
            .collect(Collectors.toList());
    }

    public static RegObj<CurrencyItem> ORB_OF_TRANSMUTATION = Def.item(() -> new OrbOfTransmutationItem());
    public static RegObj<CurrencyItem> CLEAR_CORRUPTION = Def.item(() -> new PurifyItem());
    public static RegObj<CurrencyItem> CRYSTAL_OF_PURIFICATION = Def.item(() -> new CrystalOfPurificationItem());
    public static RegObj<CurrencyItem> CLEAR_RUNES = Def.item(() -> new ClearSocketsItem());
    public static RegObj<CurrencyItem> ORB_OF_DISORDER = Def.item(() -> new OrbOfDisorder());
    public static RegObj<CurrencyItem> ORB_OF_TURBULENCE = Def.item(() -> new OrbOfTurbulence());
    public static RegObj<CurrencyItem> STONE_OF_HOPE = Def.item(() -> new StoneOfHopeItem());
    public static RegObj<CurrencyItem> LEAF_OF_CHANGE = Def.item(() -> new LeafOfChangeItem());
    public static RegObj<CurrencyItem> ORB_OF_BLESSING = Def.item(() -> new OrbOfBlessingItem());
    public static RegObj<CurrencyItem> ORB_OF_UNIQUE_BLESSING = Def.item(() -> new OrbOfUniqueBlessingItem());
    public static RegObj<CurrencyItem> ORB_OF_INFINITY = Def.item(() -> new OrbOfInfinityItem());
    public static RegObj<CurrencyItem> CLEAR_INSTABILITY = Def.item(() -> new ClearInstabilityItem());
    public static RegObj<CurrencyItem> ORB_OF_CORRUPTION = Def.item(() -> new OrbOfCorruption());

    public static RegObj<CurrencyItem> PLUS_ONE_UPGRADE = Def.item(() -> new PlusOneUpgradeItem());
    public static RegObj<CurrencyItem> PLUS_TWO_UPGRADE = Def.item(() -> new PlusTwoUpgradeItem());
    public static RegObj<CurrencyItem> PLUS_THREE_UPGRADE = Def.item(() -> new PlusThreeUpgradeItem());

}
