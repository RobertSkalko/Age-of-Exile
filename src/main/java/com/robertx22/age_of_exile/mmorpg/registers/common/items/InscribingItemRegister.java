package com.robertx22.age_of_exile.mmorpg.registers.common.items;

import com.robertx22.age_of_exile.player_skills.items.fishing.ScribeInkItem;
import com.robertx22.age_of_exile.player_skills.items.foods.SkillItemTier;
import com.robertx22.age_of_exile.player_skills.items.protection_tablets.BlankTabletItem;
import com.robertx22.age_of_exile.player_skills.items.protection_tablets.BlankTabletTier;
import com.robertx22.age_of_exile.player_skills.items.protection_tablets.ProtectionTabletItem;
import com.robertx22.age_of_exile.player_skills.items.protection_tablets.TabletTypes;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class InscribingItemRegister extends BaseItemRegistrator {

    public HashMap<SkillItemTier, ScribeInkItem> INK_TIER_MAP = new HashMap<>();

    public List<ProtectionTabletItem> ALL_TABLETS = new ArrayList<>();

    public BlankTabletItem BLANK_TABLET = item(new BlankTabletItem(BlankTabletTier.NORMAL), "tablet/blank_tablet0");
    public BlankTabletItem RARE_BLANK_TABLET = item(new BlankTabletItem(BlankTabletTier.SUPREME), "tablet/blank_tablet1");

    public ProtectionTabletItem ANTI_FIRE = tablet(new ProtectionTabletItem(SkillItemTier.SPIRITUAL, TabletTypes.ANTI_FIRE));
    public ProtectionTabletItem ANTI_HUNGER = tablet(new ProtectionTabletItem(SkillItemTier.CELESTIAL, TabletTypes.ANTI_HUNGER));
    public ProtectionTabletItem ANTI_GEAR_BREAK = tablet(new ProtectionTabletItem(SkillItemTier.EMPYREAN, TabletTypes.ANTI_GEAR_BREAK));
    public ProtectionTabletItem ANTI_DEATH = tablet(new ProtectionTabletItem(SkillItemTier.DIVINE, TabletTypes.ANTI_DEATH));

    public InscribingItemRegister() {
        for (SkillItemTier tier : SkillItemTier.values()) {
            INK_TIER_MAP.put(tier, item(new ScribeInkItem(tier)));
        }
    }

    ProtectionTabletItem tablet(ProtectionTabletItem c) {
        ALL_TABLETS.add(c);
        return item(c);
    }
}
