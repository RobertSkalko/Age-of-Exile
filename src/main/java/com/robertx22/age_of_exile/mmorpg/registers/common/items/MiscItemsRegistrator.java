package com.robertx22.age_of_exile.mmorpg.registers.common.items;

import com.robertx22.age_of_exile.database.base.CreativeTabs;
import com.robertx22.age_of_exile.dimension.item.DungeonKeyItem;
import com.robertx22.age_of_exile.dimension.item.TeleportBackItem;
import com.robertx22.age_of_exile.mmorpg.ModRegistry;
import com.robertx22.age_of_exile.player_skills.items.backpacks.BackpackItem;
import com.robertx22.age_of_exile.player_skills.items.farming.ProduceItem;
import com.robertx22.age_of_exile.player_skills.items.foods.SkillItemTier;
import com.robertx22.age_of_exile.player_skills.items.inscribing.EssencePaperItem;
import com.robertx22.age_of_exile.player_skills.items.inscribing.ScrollBuffItem;
import com.robertx22.age_of_exile.saveclasses.stat_soul.StatSoulItem;
import com.robertx22.age_of_exile.vanilla_mc.items.MiscSeedItem;
import com.robertx22.age_of_exile.vanilla_mc.items.SimpleMatItem;
import com.robertx22.age_of_exile.vanilla_mc.items.favor.EmptyFavorItem;
import com.robertx22.age_of_exile.vanilla_mc.items.favor.FullFavorItem;
import com.robertx22.age_of_exile.vanilla_mc.items.loot_crate.LootCrateItem;
import com.robertx22.age_of_exile.vanilla_mc.items.misc.*;
import com.robertx22.age_of_exile.vanilla_mc.items.misc.reset_pots.RaceResetPotion;
import com.robertx22.age_of_exile.vanilla_mc.items.misc.reset_pots.ResetStatsPotion;
import com.robertx22.age_of_exile.vanilla_mc.items.misc.reset_pots.SingleTalentResetPotion;
import com.robertx22.age_of_exile.vanilla_mc.items.misc.reset_pots.TalentResetPotion;
import net.minecraft.block.Block;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.util.registry.Registry;

import java.util.HashMap;

public class MiscItemsRegistrator extends BaseItemRegistrator {

    public MiscItemsRegistrator() {
        for (DungeonKeyItem.KeyRarity rar : DungeonKeyItem.KeyRarity.values()) {
            DUNGEON_KEY_MAP.put(rar, item(new DungeonKeyItem(rar)));
        }
    }

    public StatSoulItem STAT_SOUL = item(new StatSoulItem());
    public LootCrateItem LOOT_CRATE = item(new LootCrateItem());

    public HashMap<DungeonKeyItem.KeyRarity, DungeonKeyItem> DUNGEON_KEY_MAP = new HashMap<>();

    public MiscSeedItem MANA_FLOWER_SEED = item(new MiscSeedItem("Mana Flower Seed", Items.LAPIS_LAZULI, ModRegistry.BLOCKS.MANA_PLANT), "seed/mana");
    public MiscSeedItem HP_FLOWER_SEED = item(new MiscSeedItem("Life Flower Seed", Items.APPLE, ModRegistry.BLOCKS.LIFE_PLANT), "seed/life");

    public ProjectileItem FIREBALL = item(new ProjectileItem("fireball"));
    public ProjectileItem SNOWBALL = item(new ProjectileItem("snowball"));
    public ProjectileItem SLIMEBALL = item(new ProjectileItem("slimeball"));

    public ScrollBuffItem SCROLL_BUFF = item(new ScrollBuffItem(), "scroll/buff");
    public EssencePaperItem ESSENCE_PAPER = item(new EssencePaperItem(), "scroll/paper");

    public TeleportBackItem TELEPORT_BACK = item(new TeleportBackItem(), "misc/teleport_back");

    public SalvagedDustItem T0_DUST() {
        return ModRegistry.TIERED.SALVAGED_ESSENCE_MAP.get(SkillItemTier.TIER0);
    }

    public SalvagedDustItem T1_DUST() {
        return ModRegistry.TIERED.SALVAGED_ESSENCE_MAP.get(SkillItemTier.TIER1);
    }

    public SalvagedDustItem T2_DUST() {
        return ModRegistry.TIERED.SALVAGED_ESSENCE_MAP.get(SkillItemTier.TIER2);
    }

    public SalvagedDustItem T3_DUST() {
        return ModRegistry.TIERED.SALVAGED_ESSENCE_MAP.get(SkillItemTier.TIER3);
    }

    public SalvagedDustItem T4_DUST() {
        return ModRegistry.TIERED.SALVAGED_ESSENCE_MAP.get(SkillItemTier.TIER4);
    }

    public LootTableItem LOOT_TABLE_ITEM = item(new LootTableItem(), "loot_table_chest");

    public FullFavorItem FULL_FAVOR = item(new FullFavorItem(), "full_favor");
    public EmptyFavorItem EMPTY_FAVOR = item(new EmptyFavorItem(), "empty_favor");

    CustomLootCrateItem CUSTOM_CRATE = item(new CustomLootCrateItem(), "custom_crate");

    public VanillaExperienceChestItem VANILLA_EXP_CHEST = item(new VanillaExperienceChestItem(), "loot_crate/ench_exp_bag");

    public Item NEWBIE_GEAR_BAG = item(new ItemNewbieGearBag(), "newbie_gear_bag");
    public Item INCRESE_MOB_RARITY = item(new ItemIncreaseRarityNearestEntity(), "increase_rarity_nearest_entity");

    public Item INFUSED_IRON = item(new SimpleMatItem(), "mat/infused_iron");
    public Item CRYSTALLIZED_ESSENCE = item(new SimpleMatItem(), "mat/crystallized_essence");
    public Item GOLDEN_ORB = item(new SimpleMatItem(), "mat/golden_orb");
    public Item MYTHIC_ESSENCE = item(new SimpleMatItem(), "mat/mythic_essence");

    public TalentResetPotion RESET_ALL_PERKS = item(new TalentResetPotion());
    public SingleTalentResetPotion ADD_RESET_PERK_POINTS = item(new SingleTalentResetPotion());
    public ResetStatsPotion RESET_STATS = item(new ResetStatsPotion());
    public RaceResetPotion RESET_RACE = item(new RaceResetPotion());
    public FunnyDeathPotion DEATH_POTION = item(new FunnyDeathPotion());

    public BackpackItem BACKPACK = item(new BackpackItem(), "backpack/normal");

    public Item GEAR_MODIFY = blockItem(ModRegistry.BLOCKS.GEAR_MODIFY);
    public Item GEAR_REPAIR = blockItem(ModRegistry.BLOCKS.GEAR_REPAIR);
    public Item GEAR_SALVAGE = blockItem(ModRegistry.BLOCKS.GEAR_SALVAGE);
    public Item GEAR_SOCKET = blockItem(ModRegistry.BLOCKS.SOCKET_STATION);
    public Item SCRIBE_BUFF_BLOCK = blockItem(ModRegistry.BLOCKS.SCRIBE_BUFF);
    public Item COOKING_STATION = blockItem(ModRegistry.BLOCKS.COOKING_STATION);
    public Item TABLET_STATION = blockItem(ModRegistry.BLOCKS.TABLET_STATION);
    public Item ALCHEMY_STATION = blockItem(ModRegistry.BLOCKS.ALCHEMY_STATION);
    public Item SMITHING_STATION = blockItem(ModRegistry.BLOCKS.SMITHING_STATION);
    public Item TELEPORTER_BLOCK = blockItem(ModRegistry.BLOCKS.TELEPORTER);

    public Item MANA_PLANT = item(new ProduceItem("Astral Fruit"), "plant/mana");
    public Item LIFE_PLANT = item(new ProduceItem("Life Berry"), "plant/life");

    static Item.Settings stationProp = new Item.Settings().group(CreativeTabs.MyModTab);

    <T extends Block> Item blockItem(T block) {
        return item(new BlockItem(block, stationProp), Registry.BLOCK.getId(block)
            .getPath());
    }

}
