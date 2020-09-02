package com.robertx22.age_of_exile.database.data.currency.loc_reqs.gems;

import com.robertx22.age_of_exile.database.data.currency.loc_reqs.BaseLocRequirement;
import com.robertx22.age_of_exile.database.data.currency.loc_reqs.LocReqContext;
import com.robertx22.age_of_exile.database.data.runes.Rune;
import com.robertx22.age_of_exile.saveclasses.item_classes.GearItemData;
import com.robertx22.age_of_exile.uncommon.localization.Words;
import com.robertx22.age_of_exile.vanilla_mc.items.gemrunes.RuneItem;
import net.minecraft.text.MutableText;

public class NoDuplicateRunes extends BaseLocRequirement {
    @Override
    public MutableText getText() {
        return Words.NoDuplicateSockets.locName();
    }

    @Override
    public boolean isAllowed(LocReqContext ctx) {

        if (ctx.Currency.getItem() instanceof RuneItem) {

            RuneItem gitem = (RuneItem) ctx.Currency.getItem();

            Rune gem = gitem.getRune();

            GearItemData gear = (GearItemData) ctx.data;

            return gear.sockets.sockets.stream()
                .noneMatch(x -> x.getRune() != null && x.rune_id
                    .equals(gem.identifier));

        }

        return false;
    }
}

