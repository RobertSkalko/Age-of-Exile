package com.robertx22.mine_and_slash.database.data.currency.loc_reqs.item_types;

import com.robertx22.mine_and_slash.database.data.currency.loc_reqs.BaseLocRequirement;
import com.robertx22.mine_and_slash.database.data.currency.loc_reqs.LocReqContext;
import com.robertx22.mine_and_slash.saveclasses.item_classes.GearItemData;
import com.robertx22.mine_and_slash.uncommon.localization.Words;
import net.minecraft.text.Text;

public class GearReq extends BaseLocRequirement {

    public static GearReq INSTANCE = new GearReq();

    @Override
    public Text getText() {
        return Words.MustBeGear.locName();
    }

    @Override
    public boolean isAllowed(LocReqContext context) {
        return context.data instanceof GearItemData;
    }
}
