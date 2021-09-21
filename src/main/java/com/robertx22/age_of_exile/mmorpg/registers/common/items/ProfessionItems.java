package com.robertx22.age_of_exile.mmorpg.registers.common.items;

import com.robertx22.age_of_exile.mmorpg.registers.common.SlashBlocks;
import com.robertx22.age_of_exile.mmorpg.registers.deferred_wrapper.Def;
import com.robertx22.age_of_exile.mmorpg.registers.deferred_wrapper.RegObj;
import com.robertx22.age_of_exile.player_skills.items.SmeltedEssenceItem;
import com.robertx22.age_of_exile.player_skills.items.alchemy.CondensedEssenceItem;
import com.robertx22.age_of_exile.player_skills.items.exploration.LockedChestItem;
import com.robertx22.age_of_exile.player_skills.items.fishing.ScribeInkItem;
import com.robertx22.age_of_exile.player_skills.items.foods.SkillItemTier;
import com.robertx22.age_of_exile.player_skills.items.inscribing.EssenceInkItem;
import com.robertx22.age_of_exile.player_skills.items.mining.MiningStoneItem;
import com.robertx22.age_of_exile.player_skills.items.tinkering.ChestKeyItem;
import com.robertx22.age_of_exile.vanilla_mc.items.PlantProduceItem;
import com.robertx22.age_of_exile.vanilla_mc.items.PlantSeedItem;
import com.robertx22.age_of_exile.vanilla_mc.items.misc.SalvagedDustItem;

import java.util.HashMap;

public class ProfessionItems {

    public static HashMap<SkillItemTier, RegObj<CondensedEssenceItem>> CONDENSED_ESSENCE_MAP = new HashMap<>();
    public static HashMap<SkillItemTier, RegObj<SmeltedEssenceItem>> SMELTED_ESSENCE = new HashMap<>();
    public static HashMap<SkillItemTier, RegObj<ScribeInkItem>> INK_TIER_MAP = new HashMap<>();
    public static HashMap<SkillItemTier, RegObj<SalvagedDustItem>> SALVAGED_ESSENCE_MAP = new HashMap<>();
    public static HashMap<SkillItemTier, RegObj<PlantSeedItem>> FARMING_SEEDS = new HashMap<>();
    public static HashMap<SkillItemTier, RegObj<PlantProduceItem>> FARMING_PRODUCE = new HashMap<>();
    public static HashMap<SkillItemTier, RegObj<ChestKeyItem>> KEY_TIER_MAP = new HashMap<>();
    public static HashMap<SkillItemTier, RegObj<MiningStoneItem>> STONE_TIER_MAP = new HashMap<>();
    public static HashMap<SkillItemTier, RegObj<LockedChestItem>> LOCKED_CHEST_TIER_MAP = new HashMap<>();
    public static HashMap<SkillItemTier, RegObj<EssenceInkItem>> ESSENCE_INK = new HashMap<>();

    public static void init() {

        for (SkillItemTier tier : SkillItemTier.values()) {

            INK_TIER_MAP.put(tier, Def.item(() -> new ScribeInkItem(tier)));

            SMELTED_ESSENCE.put(tier, Def.item(() -> new SmeltedEssenceItem(tier)));
            ESSENCE_INK.put(tier, Def.item(() -> new EssenceInkItem(tier)));
            CONDENSED_ESSENCE_MAP.put(tier, Def.item(() -> new CondensedEssenceItem(tier)));
            SALVAGED_ESSENCE_MAP.put(tier, Def.item(() -> new SalvagedDustItem("Tier " + (tier.tier + 1) + " Purified Essence", tier, tier.levelRange)));
            FARMING_SEEDS.put(tier, Def.item(() -> new PlantSeedItem(tier, SlashBlocks.FARMING_PLANTS.get(tier)
                .get()), "seed/plant" + (tier.tier + 1)));
            FARMING_PRODUCE.put(tier, Def.item(() -> new PlantProduceItem(tier), "plant/plant" + (tier.tier + 1)));

            KEY_TIER_MAP.put(tier, Def.item(() -> new ChestKeyItem(tier)));
            STONE_TIER_MAP.put(tier, Def.item(() -> new MiningStoneItem(tier)));
            LOCKED_CHEST_TIER_MAP.put(tier, Def.item(() -> new LockedChestItem(tier)));

        }

    }
}
