package com.robertx22.age_of_exile.mmorpg.registers.common.items;

import com.robertx22.age_of_exile.database.data.currency.*;
import com.robertx22.age_of_exile.database.data.currency.base.CurrencyItem;
import com.robertx22.age_of_exile.mmorpg.Ref;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;

import java.util.ArrayList;
import java.util.List;

public class CurrencyItemRegistrator extends BaseItemRegistrator {

    public List<CurrencyItem> currencies = new ArrayList<>();

    public CurrencyItem ORB_OF_TRANSMUTATION = of(new OrbOfTransmutationItem());
    public CurrencyItem CLEAR_CORRUPTION = of(new PurifyItem());
    public CurrencyItem CRYSTAL_OF_PURIFICATION = of(new CrystalOfPurificationItem());
    public CurrencyItem CRYSTAL_OF_TRUTH = of(new CrystalOfTruth());
    public CurrencyItem CLEAR_RUNES = of(new ClearSocketsItem());
    public CurrencyItem ORB_OF_DISORDER = of(new OrbOfDisorder());
    public CurrencyItem ORB_OF_TURBULENCE = of(new OrbOfTurbulence());
    public CurrencyItem STONE_OF_HOPE = of(new StoneOfHopeItem());
    public CurrencyItem LEAF_OF_CHANGE = of(new LeafOfChangeItem());
    public CurrencyItem ORB_OF_BLESSING = of(new OrbOfBlessingItem());
    public CurrencyItem ORB_OF_UNIQUE_BLESSING = of(new OrbOfUniqueBlessingItem());
    public CurrencyItem ORB_OF_INFINITY = of(new OrbOfInfinityItem());
    public CurrencyItem CLEAR_INSTABILITY = of(new ClearInstabilityItem());
    public CurrencyItem ORB_OF_CORRUPTION = of(new OrbOfCorruption());

    <T> T of(CurrencyItem c) {
        Registry.register(Registry.ITEM, new ResourceLocation(Ref.MODID, c.GUID()), c);

        currencies.add(c);

        return (T) c;

    }

}
