package com.robertx22.age_of_exile.vanilla_mc.items.misc;

import com.robertx22.age_of_exile.database.data.currency.base.ICurrencyItemEffect;
import com.robertx22.age_of_exile.database.data.currency.loc_reqs.BaseLocRequirement;
import com.robertx22.age_of_exile.database.data.currency.loc_reqs.LocReqContext;
import com.robertx22.age_of_exile.database.data.currency.loc_reqs.item_types.GearReq;
import com.robertx22.age_of_exile.database.data.currency.loc_reqs.jewels.ItemCanHaveAffixJewelReq;
import com.robertx22.age_of_exile.database.data.currency.loc_reqs.jewels.NeedsEmptySocket;
import com.robertx22.age_of_exile.database.data.currency.loc_reqs.jewels.SocketLvlTooBig;
import com.robertx22.age_of_exile.datapacks.models.IAutoModel;
import com.robertx22.age_of_exile.datapacks.models.ItemModelManager;
import com.robertx22.age_of_exile.saveclasses.item_classes.GearItemData;
import com.robertx22.age_of_exile.saveclasses.item_classes.JewelData;
import com.robertx22.age_of_exile.uncommon.datasaving.Gear;
import com.robertx22.age_of_exile.uncommon.datasaving.Jewel;
import com.robertx22.age_of_exile.uncommon.interfaces.IAutoLocName;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.registry.Registry;

import java.util.Arrays;
import java.util.List;

public class JewelItem extends Item implements IAutoModel, ICurrencyItemEffect, IAutoLocName {

    public JewelItem() {
        super(new Item.Settings().maxCount(1)
            .maxDamage(0));
    }

    @Override
    public void generateModel(ItemModelManager manager) {
        manager.generated(this);
    }

    @Override
    public ItemStack ModifyItem(ItemStack stack, ItemStack currency) {

        GearItemData gear = Gear.Load(stack);

        JewelData jewel = Jewel.Load(currency);

        gear.insertJewel(jewel);

        Gear.Save(stack, gear);

        return stack;
    }

    @Override
    public final boolean canItemBeModified(LocReqContext context) {

        if (Gear.Load(context.stack) == null || Jewel.Load(context.Currency) == null) {
            return false;
        }

        for (BaseLocRequirement req : requirements()) {
            if (req.isNotAllowed(context)) {
                return false;
            }

        }
        return true;
    }

    @Override
    public List<BaseLocRequirement> requirements() {
        return Arrays.asList(GearReq.INSTANCE, new SocketLvlTooBig(), new NeedsEmptySocket(), new ItemCanHaveAffixJewelReq());
    }

    @Override
    public AutoLocGroup locNameGroup() {
        return AutoLocGroup.Misc;
    }

    @Override
    public String locNameLangFileGUID() {
        return Registry.ITEM.getId(this)
            .toString();
    }

    @Override
    public String locNameForLangFile() {
        return "Jewel";
    }

    @Override
    public String GUID() {
        return "";
    }
}
