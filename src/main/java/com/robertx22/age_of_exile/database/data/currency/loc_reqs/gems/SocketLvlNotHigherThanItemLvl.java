package com.robertx22.age_of_exile.database.data.currency.loc_reqs.gems;

import com.robertx22.age_of_exile.database.data.currency.loc_reqs.BaseLocRequirement;
import com.robertx22.age_of_exile.database.data.currency.loc_reqs.LocReqContext;
import com.robertx22.age_of_exile.database.data.gems.Gem;
import com.robertx22.age_of_exile.saveclasses.item_classes.GearItemData;
import com.robertx22.age_of_exile.uncommon.localization.Words;
import com.robertx22.age_of_exile.vanilla_mc.items.gemrunes.GemItem;
import net.minecraft.text.MutableText;

public class SocketLvlNotHigherThanItemLvl extends BaseLocRequirement {
    @Override
    public MutableText getText() {
        return Words.SocketLvlIsntHigherThanItemLvl.locName();
    }

    @Override
    public boolean isAllowed(LocReqContext ctx) {

        if (ctx.Currency.getItem() instanceof GemItem) {

            GemItem gitem = (GemItem) ctx.Currency.getItem();

            Gem gem = gitem.getGem();

            GearItemData gear = (GearItemData) ctx.data;

            return gear.level >= gem.getReqLevel();

        }

        return false;
    }
}
