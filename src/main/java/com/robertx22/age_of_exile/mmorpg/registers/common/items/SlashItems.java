package com.robertx22.age_of_exile.mmorpg.registers.common.items;

import com.robertx22.age_of_exile.database.base.CreativeTabs;
import com.robertx22.age_of_exile.dimension.item.TeleportBackItem;
import com.robertx22.age_of_exile.mmorpg.ModRegistry;
import com.robertx22.age_of_exile.mmorpg.registers.deferred_wrapper.Def;
import com.robertx22.age_of_exile.mmorpg.registers.deferred_wrapper.RegObj;
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
import com.robertx22.age_of_exile.vanilla_mc.items.misc.reset_pots.ResetStatsPotion;
import com.robertx22.age_of_exile.vanilla_mc.items.misc.reset_pots.SingleTalentResetPotion;
import com.robertx22.age_of_exile.vanilla_mc.items.misc.reset_pots.SpellResetPotion;
import com.robertx22.age_of_exile.vanilla_mc.items.misc.reset_pots.TalentResetPotion;
import net.minecraft.block.Block;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.Items;

public class SlashItems {

    public static void init() {

    }

    public static RegObj<StatSoulItem> STAT_SOUL = Def.item(new StatSoulItem());
    public static RegObj<LootCrateItem> LOOT_CRATE = Def.item(new LootCrateItem());

    public static RegObj<MiscSeedItem> MANA_FLOWER_SEED = Def.item(new MiscSeedItem("Mana Flower Seed", Items.LAPIS_LAZULI, ModRegistry.BLOCKS.MANA_PLANT.get()), "seed/mana");
    public static RegObj<MiscSeedItem> HP_FLOWER_SEED = Def.item(new MiscSeedItem("Life Flower Seed", Items.APPLE, ModRegistry.BLOCKS.LIFE_PLANT.get()), "seed/life");

    public static RegObj<ProjectileItem> FIREBALL = Def.item(new ProjectileItem("fireball"));
    public static RegObj<ProjectileItem> SNOWBALL = Def.item(new ProjectileItem("snowball"));
    public static RegObj<ProjectileItem> SLIMEBALL = Def.item(new ProjectileItem("slimeball"));

    public static RegObj<ScrollBuffItem> SCROLL_BUFF = Def.item(new ScrollBuffItem(), "scroll/buff");
    public static RegObj<EssencePaperItem> ESSENCE_PAPER = Def.item(new EssencePaperItem(), "scroll/paper");

    public static RegObj<TeleportBackItem> TELEPORT_BACK = Def.item(new TeleportBackItem(), "misc/teleport_back");

    public static SalvagedDustItem T0_DUST() {
        return ModRegistry.TIERED.SALVAGED_ESSENCE_MAP.get(SkillItemTier.TIER0);
    }

    public static SalvagedDustItem T1_DUST() {
        return ModRegistry.TIERED.SALVAGED_ESSENCE_MAP.get(SkillItemTier.TIER1);
    }

    public static SalvagedDustItem T2_DUST() {
        return ModRegistry.TIERED.SALVAGED_ESSENCE_MAP.get(SkillItemTier.TIER2);
    }

    public static SalvagedDustItem T3_DUST() {
        return ModRegistry.TIERED.SALVAGED_ESSENCE_MAP.get(SkillItemTier.TIER3);
    }

    public static SalvagedDustItem T4_DUST() {
        return ModRegistry.TIERED.SALVAGED_ESSENCE_MAP.get(SkillItemTier.TIER4);
    }

    public static RegObj<LootTableItem> LOOT_TABLE_ITEM = Def.item(new LootTableItem(), "loot_table_chest");

    public static RegObj<FullFavorItem> FULL_FAVOR = Def.item(new FullFavorItem(), "full_favor");
    public static RegObj<EmptyFavorItem> EMPTY_FAVOR = Def.item(new EmptyFavorItem(), "empty_favor");

    public static RegObj<CustomLootCrateItem> CUSTOM_CRATE = Def.item(new CustomLootCrateItem(), "custom_crate");

    public static RegObj<Item> NEWBIE_GEAR_BAG = Def.item(new ItemNewbieGearBag(), "newbie_gear_bag");

    public static RegObj<Item> INFUSED_IRON = Def.item(new SimpleMatItem(), "mat/infused_iron");
    public static RegObj<Item> CRYSTALLIZED_ESSENCE = Def.item(new SimpleMatItem(), "mat/crystallized_essence");
    public static RegObj<Item> GOLDEN_ORB = Def.item(new SimpleMatItem(), "mat/golden_orb");
    public static RegObj<Item> MYTHIC_ESSENCE = Def.item(new SimpleMatItem(), "mat/mythic_essence");

    public static RegObj<TalentResetPotion> RESET_ALL_PERKS = Def.item(new TalentResetPotion());
    public static RegObj<SingleTalentResetPotion> ADD_RESET_PERK_POINTS = Def.item(new SingleTalentResetPotion());
    public static RegObj<SpellResetPotion> RESET_SPELLS = Def.item(new SpellResetPotion());
    public static RegObj<ResetStatsPotion> RESET_STATS = Def.item(new ResetStatsPotion());
    public static RegObj<FunnyDeathPotion> DEATH_POTION = Def.item(new FunnyDeathPotion());

    public static RegObj<BackpackItem> BACKPACK = Def.item(new BackpackItem(), "backpack/normal");

    public static RegObj<Item> GEAR_SALVAGE = blockItem(ModRegistry.BLOCKS.GEAR_SALVAGE.get());
    public static RegObj<Item> RUNEWORD_STATION = blockItem(ModRegistry.BLOCKS.RUNEWORD.get());
    public static RegObj<Item> SCRIBE_BUFF_BLOCK = blockItem(ModRegistry.BLOCKS.SCRIBE_BUFF.get());
    public static RegObj<Item> COOKING_STATION = blockItem(ModRegistry.BLOCKS.COOKING_STATION.get());
    public static RegObj<Item> TABLET_STATION = blockItem(ModRegistry.BLOCKS.TABLET_STATION.get());
    public static RegObj<Item> ALCHEMY_STATION = blockItem(ModRegistry.BLOCKS.ALCHEMY_STATION.get());
    public static RegObj<Item> SMITHING_STATION = blockItem(ModRegistry.BLOCKS.SMITHING_STATION.get());
    public static RegObj<Item> TELEPORTER_BLOCK = blockItem(ModRegistry.BLOCKS.TELEPORTER.get());

    public static RegObj<Item> MANA_PLANT = Def.item(new ProduceItem("Astral Fruit"), "plant/mana");
    public static RegObj<Item> LIFE_PLANT = Def.item(new ProduceItem("Life Berry"), "plant/life");

    static Item.Properties stationProp = new Item.Properties().tab(CreativeTabs.MyModTab);

    static <T extends Block> RegObj<Item> blockItem(T block) {
        return Def.item(block.getRegistryName()
            .getPath(), new BlockItem(block, stationProp));
    }

}
