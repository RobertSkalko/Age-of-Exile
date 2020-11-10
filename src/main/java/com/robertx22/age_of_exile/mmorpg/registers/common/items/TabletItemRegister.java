package com.robertx22.age_of_exile.mmorpg.registers.common.items;

import com.robertx22.age_of_exile.vanilla_mc.items.protection_tablets.ProtectionTabletItem;
import com.robertx22.age_of_exile.vanilla_mc.items.protection_tablets.TabletTypes;

public class TabletItemRegister extends BaseItemRegistrator {

    public ProtectionTabletItem ANTI_FIRE = item(new ProtectionTabletItem(TabletTypes.ANTI_FIRE));
    public ProtectionTabletItem ANTI_HUNGER = item(new ProtectionTabletItem(TabletTypes.ANTI_HUNGER));
    public ProtectionTabletItem ANTI_GEAR_BREAK = item(new ProtectionTabletItem(TabletTypes.ANTI_GEAR_BREAK));

}
