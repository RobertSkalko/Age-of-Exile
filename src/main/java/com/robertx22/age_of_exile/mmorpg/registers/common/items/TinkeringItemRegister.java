package com.robertx22.age_of_exile.mmorpg.registers.common.items;

import com.robertx22.age_of_exile.player_skills.items.exploration.LockedChestItem;
import com.robertx22.age_of_exile.player_skills.items.foods.SkillItemTier;
import com.robertx22.age_of_exile.player_skills.items.mining.MiningStoneItem;
import com.robertx22.age_of_exile.player_skills.items.tinkering.ChestKeyItem;

import java.util.HashMap;

public class TinkeringItemRegister extends BaseItemRegistrator {

    public HashMap<SkillItemTier, ChestKeyItem> KEY_TIER_MAP = new HashMap<>();
    public HashMap<SkillItemTier, MiningStoneItem> STONE_TIER_MAP = new HashMap<>();
    public HashMap<SkillItemTier, LockedChestItem> LOCKED_CHEST_TIER_MAP = new HashMap<>();

    public TinkeringItemRegister() {

        for (SkillItemTier tier : SkillItemTier.values()) {
            KEY_TIER_MAP.put(tier, item(new ChestKeyItem(tier)));
            STONE_TIER_MAP.put(tier, item(new MiningStoneItem(tier)));
            LOCKED_CHEST_TIER_MAP.put(tier, item(new LockedChestItem(tier)));
        }
    }
}