package com.robertx22.age_of_exile.mmorpg.registers.common.items;

import com.robertx22.age_of_exile.vanilla_mc.items.protection_tablets.BlankTabletItem;
import com.robertx22.age_of_exile.vanilla_mc.items.protection_tablets.BlankTabletTier;
import com.robertx22.age_of_exile.vanilla_mc.items.protection_tablets.ProtectionTabletItem;
import com.robertx22.age_of_exile.vanilla_mc.items.protection_tablets.TabletTypes;

public class TabletItemRegister extends BaseItemRegistrator {

    public BlankTabletItem BLANK_TABLET = item(new BlankTabletItem(BlankTabletTier.NORMAL), "tablet/blank_tablet0");
    public BlankTabletItem RARE_BLANK_TABLET = item(new BlankTabletItem(BlankTabletTier.SUPREME), "tablet/blank_tablet1");

    public ProtectionTabletItem ANTI_FIRE = item(new ProtectionTabletItem(TabletTypes.ANTI_FIRE));
    public ProtectionTabletItem ANTI_HUNGER = item(new ProtectionTabletItem(TabletTypes.ANTI_HUNGER));
    public ProtectionTabletItem ANTI_GEAR_BREAK = item(new ProtectionTabletItem(TabletTypes.ANTI_GEAR_BREAK));
    public ProtectionTabletItem ANTI_DEATH = item(new ProtectionTabletItem(TabletTypes.ANTI_DEATH));

}
