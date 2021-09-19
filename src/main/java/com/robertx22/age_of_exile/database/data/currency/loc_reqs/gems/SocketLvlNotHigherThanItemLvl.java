package com.robertx22.age_of_exile.database.data.currency.loc_reqs.gems;

import com.robertx22.age_of_exile.database.data.currency.loc_reqs.BaseLocRequirement;
import com.robertx22.age_of_exile.database.data.currency.loc_reqs.LocReqContext;
import com.robertx22.age_of_exile.saveclasses.item_classes.GearItemData;
import com.robertx22.age_of_exile.uncommon.localization.Words;
import com.robertx22.age_of_exile.vanilla_mc.items.gemrunes.BaseGemRuneItem;
import net.minecraft.util.text.IFormattableTextComponent;

public class SocketLvlNotHigherThanItemLvl extends BaseLocRequirement {
    @Override
    public IFormattableTextComponent getText() {
        return Words.SocketLvlIsntHigherThanItemLvl.locName();
    }

    @Override
    public boolean isAllowed(LocReqContext ctx) {

        if (ctx.Currency.getItem() instanceof BaseGemRuneItem) {
            BaseGemRuneItem gitem = (BaseGemRuneItem) ctx.Currency.getItem();
            GearItemData gear = (GearItemData) ctx.data;
            return gear.lvl >= gitem.getBaseRuneGem()
                .getReqLevel();

        }

        return false;
    }
}
