package com.robertx22.age_of_exile.mmorpg.registers.common.items;

import com.robertx22.age_of_exile.database.base.CreativeTabs;
import com.robertx22.age_of_exile.mmorpg.ModRegistry;
import com.robertx22.age_of_exile.player_skills.items.foods.SkillItemTier;
import com.robertx22.age_of_exile.vanilla_mc.items.PlantProduceItem;
import com.robertx22.age_of_exile.vanilla_mc.items.PlantSeedItem;
import com.robertx22.age_of_exile.vanilla_mc.items.SimpleMatItem;
import com.robertx22.age_of_exile.vanilla_mc.items.favor.EmptyFavorItem;
import com.robertx22.age_of_exile.vanilla_mc.items.favor.FullFavorItem;
import com.robertx22.age_of_exile.vanilla_mc.items.gemrunes.RuneWordItem;
import com.robertx22.age_of_exile.vanilla_mc.items.misc.*;
import com.robertx22.age_of_exile.vanilla_mc.items.repair_hammers.RepairHammer0;
import com.robertx22.age_of_exile.vanilla_mc.items.repair_hammers.RepairHammer1;
import com.robertx22.age_of_exile.vanilla_mc.items.salvage_bag.CommonAutoSalvageBagItem;
import net.minecraft.block.Block;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.util.registry.Registry;

import java.util.HashMap;

public class MiscItemsRegistrator extends BaseItemRegistrator {

    public IdentifyTomeItem IDENTIFY_TOME = item(new IdentifyTomeItem(), "identify_tome");

    public HashMap<SkillItemTier, SalvagedDustItem> SALVAGED_ESSENCE_MAP = new HashMap<>();

    public Item PLANT1_SEED = item(new PlantSeedItem(ModRegistry.BLOCKS.PLANT1, Items.WHEAT, "Arcane Wheat Seed"), "seed/plant1");
    public Item PLANT2_SEED = item(new PlantSeedItem(ModRegistry.BLOCKS.PLANT2, Items.WHEAT_SEEDS, "Blood Wheat Seed"), "seed/plant2");

    public Item ARCANE_WHEAT = item(new PlantProduceItem("Arcane Wheat"), "plant/plant1");
    public Item BLOOD_WHEAT = item(new PlantProduceItem("Blood Wheat"), "plant/plant2");

    public SalvagedDustItem T0_DUST() {
        return SALVAGED_ESSENCE_MAP.get(SkillItemTier.TIER0);
    }

    public SalvagedDustItem T1_DUST() {
        return SALVAGED_ESSENCE_MAP.get(SkillItemTier.TIER1);
    }

    public SalvagedDustItem T2_DUST() {
        return SALVAGED_ESSENCE_MAP.get(SkillItemTier.TIER2);
    }

    public SalvagedDustItem T3_DUST() {
        return SALVAGED_ESSENCE_MAP.get(SkillItemTier.TIER3);
    }

    public SalvagedDustItem T4_DUST() {
        return SALVAGED_ESSENCE_MAP.get(SkillItemTier.TIER4);
    }

    public RepairHammer0 REPAIR_HAMMER_0 = item(new RepairHammer0(1250), "repair_hammers/0");
    public RepairHammer1 REPAIR_HAMMER_1 = item(new RepairHammer1(3750), "repair_hammers/1");

    public LootTableItem LOOT_TABLE_ITEM = item(new LootTableItem(), "loot_table_chest");

    public CommonAutoSalvageBagItem COMMON_SALVAGE_BAG = item(new CommonAutoSalvageBagItem(), "salvage_bag/0");

    public FullFavorItem FULL_FAVOR = item(new FullFavorItem(), "full_favor");
    public EmptyFavorItem EMPTY_FAVOR = item(new EmptyFavorItem(), "empty_favor");

    CustomLootCrateItem CUSTOM_CRATE = item(new CustomLootCrateItem(), "custom_crate");

    LootCrateItem COMMON_CRATE = item(new LootCrateItem(LootCrateItem.LootCrateType.COMMON), "loot_crate/common");
    LootCrateItem MAGIC_CRATE = item(new LootCrateItem(LootCrateItem.LootCrateType.MAGIC), "loot_crate/magic");
    LootCrateItem RARE_CRATE = item(new LootCrateItem(LootCrateItem.LootCrateType.RARE), "loot_crate/rare");
    LootCrateItem UNIQUE_CRATE = item(new LootCrateItem(LootCrateItem.LootCrateType.UNIQUE), "loot_crate/unique");
    LootCrateItem RUNE_CRATE = item(new LootCrateItem(LootCrateItem.LootCrateType.RUNE), "loot_crate/rune");
    LootCrateItem GEM_CRATE = item(new LootCrateItem(LootCrateItem.LootCrateType.GEM), "loot_crate/gem");

    public VanillaExperienceChestItem VANILLA_EXP_CHEST = item(new VanillaExperienceChestItem(), "loot_crate/ench_exp_bag");

    public Item NEWBIE_GEAR_BAG = item(new ItemNewbieGearBag(), "newbie_gear_bag");
    public Item INCRESE_MOB_RARITY = item(new ItemIncreaseRarityNearestEntity(), "increase_rarity_nearest_entity");
    public Item RUNEWORD = item(new RuneWordItem(), "runeword");

    public Item INFUSED_IRON = item(new SimpleMatItem(), "mat/infused_iron");
    public Item CRYSTALLIZED_ESSENCE = item(new SimpleMatItem(), "mat/crystallized_essence");
    public Item GOLDEN_ORB = item(new SimpleMatItem(), "mat/golden_orb");
    public Item MYTHIC_ESSENCE = item(new SimpleMatItem(), "mat/mythic_essence");

    public ResetAllPerksItem RESET_ALL_PERKS = item(new ResetAllPerksItem());
    public AddResetPerkPointsItem ADD_RESET_PERK_POINTS = item(new AddResetPerkPointsItem());

    public Item GEAR_MODIFY = blockItem(ModRegistry.BLOCKS.GEAR_MODIFY);
    public Item GEAR_REPAIR = blockItem(ModRegistry.BLOCKS.GEAR_REPAIR);
    public Item GEAR_SALVAGE = blockItem(ModRegistry.BLOCKS.GEAR_SALVAGE);
    public Item GEAR_SOCKET = blockItem(ModRegistry.BLOCKS.SOCKET_STATION);

    static Item.Settings stationProp = new Item.Settings().group(CreativeTabs.MyModTab);

    public MiscItemsRegistrator() {

        for (SkillItemTier tier : SkillItemTier.values()) {
            SALVAGED_ESSENCE_MAP.put(tier, item(new SalvagedDustItem("Tier " + (tier.tier + 1) + " Purified Essence", tier, tier.levelRange)));
        }
    }

    <T extends Block> Item blockItem(T block) {
        return item(new BlockItem(block, stationProp), Registry.BLOCK.getId(block)
            .getPath());
    }

}
