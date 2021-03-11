package com.robertx22.age_of_exile.mmorpg.registers.common.items;

import com.robertx22.age_of_exile.player_skills.items.fishing.ScribeInkItem;
import com.robertx22.age_of_exile.player_skills.items.foods.SkillItemTier;
import com.robertx22.age_of_exile.player_skills.items.inscribing.StatInfusionItem;
import com.robertx22.age_of_exile.player_skills.items.inscribing.teleports.DeathTeleportItem;
import com.robertx22.age_of_exile.player_skills.items.inscribing.teleports.RandomPlayerLevelTeleportItem;
import com.robertx22.age_of_exile.player_skills.items.inscribing.teleports.SpawnTeleportItem;
import com.robertx22.age_of_exile.player_skills.items.protection_tablets.BlankTabletItem;
import com.robertx22.age_of_exile.player_skills.items.protection_tablets.BlankTabletTier;
import com.robertx22.age_of_exile.player_skills.items.protection_tablets.ProtectionTabletItem;
import com.robertx22.age_of_exile.player_skills.items.protection_tablets.TabletTypes;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class InscribingItemRegister extends BaseItemRegistrator {

    public HashMap<SkillItemTier, ScribeInkItem> INK_TIER_MAP = new HashMap<>();
    public HashMap<SkillItemTier, StatInfusionItem> STAT_INFUSION_MAP = new HashMap<>();

    public List<ProtectionTabletItem> ALL_TABLETS = new ArrayList<>();

    public BlankTabletItem BLANK_TABLET = item(new BlankTabletItem(SkillItemTier.TIER0, BlankTabletTier.NORMAL), "tablet/blank_tablet0");
    public BlankTabletItem RARE_BLANK_TABLET = item(new BlankTabletItem(SkillItemTier.TIER4, BlankTabletTier.SUPREME), "tablet/blank_tablet1");

    public ProtectionTabletItem ANTI_FIRE = tablet(new ProtectionTabletItem(SkillItemTier.TIER0, TabletTypes.ANTI_FIRE));
    public ProtectionTabletItem ANTI_POISON = tablet(new ProtectionTabletItem(SkillItemTier.TIER0, TabletTypes.ANTI_POISON));
    public ProtectionTabletItem ANTI_HUNGER = tablet(new ProtectionTabletItem(SkillItemTier.TIER1, TabletTypes.ANTI_HUNGER));
    public ProtectionTabletItem ANTI_WITHER = tablet(new ProtectionTabletItem(SkillItemTier.TIER1, TabletTypes.ANTI_WITHER));
    public ProtectionTabletItem ANTI_GEAR_BREAK = tablet(new ProtectionTabletItem(SkillItemTier.TIER2, TabletTypes.ANTI_GEAR_BREAK));
    public ProtectionTabletItem ANTI_DEATH = tablet(new ProtectionTabletItem(SkillItemTier.TIER4, TabletTypes.ANTI_DEATH));
    public ProtectionTabletItem GEAR_REPAIR = tablet(new ProtectionTabletItem(SkillItemTier.TIER4, TabletTypes.GEAR_REPAIR));

    public DeathTeleportItem DEATH_TELEPORT = item(new DeathTeleportItem(), "scroll/death_teleport");
    public SpawnTeleportItem SPAWN_TELEPORT = item(new SpawnTeleportItem(), "scroll/spawn_teleport");
    public RandomPlayerLevelTeleportItem RANDOM_TELEPORT = item(new RandomPlayerLevelTeleportItem(), "scroll/random_teleport");

    public InscribingItemRegister() {
        for (SkillItemTier tier : SkillItemTier.values()) {
            INK_TIER_MAP.put(tier, item(new ScribeInkItem(tier)));
            STAT_INFUSION_MAP.put(tier, item(new StatInfusionItem(tier)));
        }

    }

    ProtectionTabletItem tablet(ProtectionTabletItem c) {
        ALL_TABLETS.add(c);
        return item(c);
    }
}
