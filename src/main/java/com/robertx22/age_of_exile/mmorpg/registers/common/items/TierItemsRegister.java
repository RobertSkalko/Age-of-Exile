package com.robertx22.age_of_exile.mmorpg.registers.common.items;

import com.robertx22.age_of_exile.mmorpg.ModRegistry;
import com.robertx22.age_of_exile.player_skills.items.alchemy.CondensedSalvageEssence;
import com.robertx22.age_of_exile.player_skills.items.backpacks.BackpackItem;
import com.robertx22.age_of_exile.player_skills.items.backpacks.BackpackType;
import com.robertx22.age_of_exile.player_skills.items.exploration.LockedChestItem;
import com.robertx22.age_of_exile.player_skills.items.fishing.ScribeInkItem;
import com.robertx22.age_of_exile.player_skills.items.foods.SkillItemTier;
import com.robertx22.age_of_exile.player_skills.items.inscribing.EssenceInkItem;
import com.robertx22.age_of_exile.player_skills.items.inscribing.StatInfusionItem;
import com.robertx22.age_of_exile.player_skills.items.mining.MiningStoneItem;
import com.robertx22.age_of_exile.player_skills.items.tinkering.ChestKeyItem;
import com.robertx22.age_of_exile.player_skills.items.tinkering.MysteriousLeatherItem;
import com.robertx22.age_of_exile.vanilla_mc.items.PlantProduceItem;
import com.robertx22.age_of_exile.vanilla_mc.items.PlantSeedItem;
import com.robertx22.age_of_exile.vanilla_mc.items.misc.SalvagedDustItem;

import java.util.HashMap;

public class TierItemsRegister extends BaseItemRegistrator {

    public HashMap<SkillItemTier, CondensedSalvageEssence> CONDENSED_ESSENCE_MAP = new HashMap<>();
    public HashMap<SkillItemTier, ScribeInkItem> INK_TIER_MAP = new HashMap<>();
    public HashMap<SkillItemTier, StatInfusionItem> STAT_INFUSION_MAP = new HashMap<>();
    public HashMap<SkillItemTier, SalvagedDustItem> SALVAGED_ESSENCE_MAP = new HashMap<>();
    public HashMap<SkillItemTier, PlantSeedItem> FARMING_SEEDS = new HashMap<>();
    public HashMap<SkillItemTier, PlantProduceItem> FARMING_PRODUCE = new HashMap<>();
    public HashMap<SkillItemTier, ChestKeyItem> KEY_TIER_MAP = new HashMap<>();
    public HashMap<SkillItemTier, MiningStoneItem> STONE_TIER_MAP = new HashMap<>();
    public HashMap<SkillItemTier, MysteriousLeatherItem> LEATHER_TIER_MAP = new HashMap<>();
    public HashMap<SkillItemTier, LockedChestItem> LOCKED_CHEST_TIER_MAP = new HashMap<>();

    public HashMap<SkillItemTier, BackpackItem> VALUABLES_BACKPACKS_TIER_MAP = new HashMap<>();
    public HashMap<SkillItemTier, BackpackItem> NORMAL_BACKPACKS_TIER_MAP = new HashMap<>();

    public HashMap<SkillItemTier, EssenceInkItem> ESSENCE_INK = new HashMap<>();

    public TierItemsRegister() {

        for (SkillItemTier tier : SkillItemTier.values()) {

            INK_TIER_MAP.put(tier, item(new ScribeInkItem(tier)));
            ESSENCE_INK.put(tier, item(new EssenceInkItem(tier)));
            STAT_INFUSION_MAP.put(tier, item(new StatInfusionItem(tier)));
            CONDENSED_ESSENCE_MAP.put(tier, item(new CondensedSalvageEssence(tier)));
            SALVAGED_ESSENCE_MAP.put(tier, item(new SalvagedDustItem("Tier " + (tier.tier + 1) + " Purified Essence", tier, tier.levelRange)));
            FARMING_SEEDS.put(tier, item(new PlantSeedItem(tier, ModRegistry.BLOCKS.FARMING_PLANTS.get(tier)), "seed/plant" + (tier.tier + 1)));
            FARMING_PRODUCE.put(tier, item(new PlantProduceItem(tier), "plant/plant" + (tier.tier + 1)));

            KEY_TIER_MAP.put(tier, item(new ChestKeyItem(tier)));
            STONE_TIER_MAP.put(tier, item(new MiningStoneItem(tier)));
            LOCKED_CHEST_TIER_MAP.put(tier, item(new LockedChestItem(tier)));
            LEATHER_TIER_MAP.put(tier, item(new MysteriousLeatherItem(tier)));

            VALUABLES_BACKPACKS_TIER_MAP.put(tier, item(new BackpackItem(BackpackType.VALUABLES, tier), "backpack/valuables/" + tier.tier));
            NORMAL_BACKPACKS_TIER_MAP.put(tier, item(new BackpackItem(BackpackType.NORMAL, tier), "backpack/normal/" + tier.tier));

        }

    }
}