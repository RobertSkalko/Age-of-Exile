package com.robertx22.age_of_exile.database.data.currency.safes;

import com.robertx22.age_of_exile.database.data.currency.GearBlessingType;
import com.robertx22.age_of_exile.database.data.currency.base.CurrencyItem;
import com.robertx22.age_of_exile.database.data.currency.base.ICurrencyItemEffect;
import com.robertx22.age_of_exile.database.data.currency.loc_reqs.BaseLocRequirement;
import com.robertx22.age_of_exile.database.data.currency.loc_reqs.LocReqContext;
import com.robertx22.age_of_exile.database.data.currency.loc_reqs.item_types.GearReq;
import com.robertx22.age_of_exile.saveclasses.item_classes.GearItemData;
import com.robertx22.age_of_exile.uncommon.datasaving.Gear;
import com.robertx22.age_of_exile.uncommon.interfaces.data_items.IRarity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.TextFormatting;

import java.util.Arrays;
import java.util.List;

public class LegendarySafeScroll extends CurrencyItem implements ICurrencyItemEffect {

    public LegendarySafeScroll() {
        super("currency/legendary_safe_scroll");
    }

    @Override
    public String GUID() {
        return "currency/legendary_safe_scroll";
    }

    @Override
    public int getWeight() {
        return 0;
    }

    @Override
    public int getTier() {
        return 0;
    }

    @Override
    public ItemStack internalModifyMethod(LocReqContext ctx, ItemStack stack, ItemStack Currency) {
        GearItemData gear = Gear.Load(stack);
        gear.up.bless = GearBlessingType.DESTROY_PROTECT;
        Gear.Save(stack, gear);
        return stack;
    }

    @Override
    public List<BaseLocRequirement> requirements() {
        return Arrays.asList(GearReq.INSTANCE);
    }

    @Override
    public String getRarityRank() {
        return IRarity.EPIC_ID;
    }

    @Override
    public float getInstability() {
        return 25;
    }

    @Override
    public String locNameForLangFile() {
        return TextFormatting.LIGHT_PURPLE + "" + TextFormatting.GOLD + "Legendary Upgrade Protection Scroll";
    }

    @Override
    public String locDescForLangFile() {
        return "Blesses the gear, not allowing upgrades to destroy gear";
    }

}
