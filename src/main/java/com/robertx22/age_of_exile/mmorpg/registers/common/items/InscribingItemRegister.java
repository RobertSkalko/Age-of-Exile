package com.robertx22.age_of_exile.mmorpg.registers.common.items;

import com.robertx22.age_of_exile.player_skills.enchants.EnchantsEnum;
import com.robertx22.age_of_exile.player_skills.items.fishing.ScribeInkItem;
import com.robertx22.age_of_exile.player_skills.items.foods.SkillItemTier;
import com.robertx22.age_of_exile.player_skills.items.inscribing.EnchantmentScrollItem;
import com.robertx22.age_of_exile.player_skills.items.inscribing.teleports.DeathTeleportItem;
import com.robertx22.age_of_exile.player_skills.items.inscribing.teleports.RandomPlayerLevelTeleportItem;
import com.robertx22.age_of_exile.player_skills.items.inscribing.teleports.SpawnTeleportItem;
import com.robertx22.age_of_exile.player_skills.items.protection_tablets.BlankTabletItem;
import com.robertx22.age_of_exile.player_skills.items.protection_tablets.BlankTabletTier;
import com.robertx22.age_of_exile.player_skills.items.protection_tablets.ProtectionTabletItem;
import com.robertx22.age_of_exile.player_skills.items.protection_tablets.TabletTypes;
import com.robertx22.age_of_exile.saveclasses.player_skills.PlayerSkillEnum;
import org.apache.commons.lang3.tuple.ImmutableTriple;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class InscribingItemRegister extends BaseItemRegistrator {

    public HashMap<ImmutableTriple<PlayerSkillEnum, EnchantsEnum, SkillItemTier>, EnchantmentScrollItem> ENCHANT_SCROLL_MAP = new HashMap<>();

    public HashMap<SkillItemTier, ScribeInkItem> INK_TIER_MAP = new HashMap<>();

    public List<ProtectionTabletItem> ALL_TABLETS = new ArrayList<>();

    public BlankTabletItem BLANK_TABLET = item(new BlankTabletItem(BlankTabletTier.NORMAL), "tablet/blank_tablet0");
    public BlankTabletItem RARE_BLANK_TABLET = item(new BlankTabletItem(BlankTabletTier.SUPREME), "tablet/blank_tablet1");

    public ProtectionTabletItem ANTI_FIRE = tablet(new ProtectionTabletItem(SkillItemTier.SPIRITUAL, TabletTypes.ANTI_FIRE));
    public ProtectionTabletItem ANTI_HUNGER = tablet(new ProtectionTabletItem(SkillItemTier.CELESTIAL, TabletTypes.ANTI_HUNGER));
    public ProtectionTabletItem ANTI_GEAR_BREAK = tablet(new ProtectionTabletItem(SkillItemTier.EMPYREAN, TabletTypes.ANTI_GEAR_BREAK));
    public ProtectionTabletItem ANTI_DEATH = tablet(new ProtectionTabletItem(SkillItemTier.DIVINE, TabletTypes.ANTI_DEATH));

    public DeathTeleportItem DEATH_TELEPORT = item(new DeathTeleportItem(), "scroll/death_teleport");
    public SpawnTeleportItem SPAWN_TELEPORT = item(new SpawnTeleportItem(), "scroll/spawn_teleport");
    public RandomPlayerLevelTeleportItem RANDOM_TELEPORT = item(new RandomPlayerLevelTeleportItem(), "scroll/random_teleport");

    public InscribingItemRegister() {
        for (SkillItemTier tier : SkillItemTier.values()) {
            INK_TIER_MAP.put(tier, item(new ScribeInkItem(tier)));
        }

        for (SkillItemTier tier : SkillItemTier.values()) {
            for (EnchantsEnum ench : EnchantsEnum.values()) {
                for (PlayerSkillEnum skill : ench.getSkillsUsedFor()) {
                    ENCHANT_SCROLL_MAP.put(ImmutableTriple.of(skill, ench, tier), item(new EnchantmentScrollItem(skill, ench, tier)));
                }
            }
        }
    }

    ProtectionTabletItem tablet(ProtectionTabletItem c) {
        ALL_TABLETS.add(c);
        return item(c);
    }
}
