package com.robertx22.age_of_exile.mmorpg.registers.common.items;

import com.robertx22.age_of_exile.mmorpg.registers.deferred_wrapper.Def;
import com.robertx22.age_of_exile.mmorpg.registers.deferred_wrapper.RegObj;
import com.robertx22.age_of_exile.player_skills.items.foods.SkillItemTier;
import com.robertx22.age_of_exile.player_skills.items.inscribing.teleports.DeathTeleportItem;
import com.robertx22.age_of_exile.player_skills.items.inscribing.teleports.SpawnTeleportItem;
import com.robertx22.age_of_exile.player_skills.items.protection_tablets.BlankTabletItem;
import com.robertx22.age_of_exile.player_skills.items.protection_tablets.BlankTabletTier;
import com.robertx22.age_of_exile.player_skills.items.protection_tablets.ProtectionTabletItem;
import com.robertx22.age_of_exile.player_skills.items.protection_tablets.TabletTypes;

import java.util.ArrayList;
import java.util.List;

public class TabletItems {

    public static void init() {

    }

    public static List<ProtectionTabletItem> ALL_TABLETS = new ArrayList<ProtectionTabletItem>();

    public static RegObj<BlankTabletItem> BLANK_TABLET = Def.item(new BlankTabletItem(SkillItemTier.TIER1, BlankTabletTier.NORMAL), "tablet/blank_tablet0");
    public static RegObj<BlankTabletItem> RARE_BLANK_TABLET = Def.item(new BlankTabletItem(SkillItemTier.TIER4, BlankTabletTier.SUPREME), "tablet/blank_tablet1");

    public static RegObj<ProtectionTabletItem> ANTI_FIRE = tablet(new ProtectionTabletItem(SkillItemTier.TIER0, TabletTypes.ANTI_FIRE));
    public static RegObj<ProtectionTabletItem> ANTI_POISON = tablet(new ProtectionTabletItem(SkillItemTier.TIER0, TabletTypes.ANTI_POISON));
    public static RegObj<ProtectionTabletItem> ANTI_HUNGER = tablet(new ProtectionTabletItem(SkillItemTier.TIER1, TabletTypes.ANTI_HUNGER));
    public static RegObj<ProtectionTabletItem> ANTI_WITHER = tablet(new ProtectionTabletItem(SkillItemTier.TIER1, TabletTypes.ANTI_WITHER));
    public static RegObj<ProtectionTabletItem> ANTI_GEAR_BREAK = tablet(new ProtectionTabletItem(SkillItemTier.TIER2, TabletTypes.ANTI_GEAR_BREAK));
    public static RegObj<ProtectionTabletItem> ANTI_DEATH = tablet(new ProtectionTabletItem(SkillItemTier.TIER4, TabletTypes.ANTI_DEATH));
    public static RegObj<ProtectionTabletItem> GEAR_REPAIR = tablet(new ProtectionTabletItem(SkillItemTier.TIER4, TabletTypes.GEAR_REPAIR));

    public static RegObj<DeathTeleportItem> DEATH_TELEPORT = Def.item(new DeathTeleportItem(), "scroll/death_teleport");
    public static RegObj<SpawnTeleportItem> SPAWN_TELEPORT = Def.item(new SpawnTeleportItem(), "scroll/spawn_teleport");

    static RegObj<ProtectionTabletItem> tablet(ProtectionTabletItem c) {
        RegObj<ProtectionTabletItem> d = Def.item(c);
        ALL_TABLETS.add(d.get());
        return d;
    }
}
