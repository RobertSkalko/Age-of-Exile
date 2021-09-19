package com.robertx22.age_of_exile.database.data.currency.loc_reqs.item_types;

import com.robertx22.age_of_exile.database.data.currency.loc_reqs.BaseLocRequirement;
import com.robertx22.age_of_exile.database.data.currency.loc_reqs.LocReqContext;
import com.robertx22.age_of_exile.saveclasses.item_classes.GearItemData;
import com.robertx22.age_of_exile.uncommon.localization.Words;
import net.minecraft.util.text.IFormattableTextComponent;

public class GearReq extends BaseLocRequirement {

    public static GearReq INSTANCE = new GearReq();

    @Override
    public IFormattableTextComponent getText() {
        return Words.MustBeGear.locName();
    }

    @Override
    public boolean isAllowed(LocReqContext context) {
        return context.data instanceof GearItemData;
    }
}
