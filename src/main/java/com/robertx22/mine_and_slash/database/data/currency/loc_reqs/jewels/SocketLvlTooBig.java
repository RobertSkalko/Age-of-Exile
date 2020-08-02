package com.robertx22.mine_and_slash.database.data.currency.loc_reqs.jewels;

import com.robertx22.mine_and_slash.database.data.currency.loc_reqs.BaseLocRequirement;
import com.robertx22.mine_and_slash.database.data.currency.loc_reqs.LocReqContext;
import com.robertx22.mine_and_slash.saveclasses.item_classes.GearItemData;
import com.robertx22.mine_and_slash.saveclasses.item_classes.JewelData;
import com.robertx22.mine_and_slash.uncommon.datasaving.Jewel;
import com.robertx22.mine_and_slash.uncommon.wrappers.SText;
import net.minecraft.text.MutableText;

public class SocketLvlTooBig extends BaseLocRequirement {

    @Override
    public MutableText getText() {
        return new SText("Jewel level can't be higher than gear level.");
    }

    @Override
    public boolean isAllowed(LocReqContext context) {

        JewelData jewel = Jewel.Load(context.Currency);
        GearItemData gear = (GearItemData) context.data;

        return gear.level >= jewel.affix.level;

    }
}

