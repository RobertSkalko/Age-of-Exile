package com.robertx22.age_of_exile.mmorpg.registers.common.items;

import com.robertx22.age_of_exile.database.base.CreativeTabs;
import com.robertx22.age_of_exile.database.data.level_ranges.LevelRange;
import com.robertx22.age_of_exile.database.registrators.LevelRanges;
import com.robertx22.age_of_exile.dimension.item.TeleportBackItem;
import com.robertx22.age_of_exile.mmorpg.registers.common.SlashBlocks;
import com.robertx22.age_of_exile.mmorpg.registers.deferred_wrapper.Def;
import com.robertx22.age_of_exile.mmorpg.registers.deferred_wrapper.RegObj;
import com.robertx22.age_of_exile.player_skills.items.backpacks.BackpackItem;
import com.robertx22.age_of_exile.player_skills.items.backpacks.mat_pouch.MaterialBagItem;
import com.robertx22.age_of_exile.player_skills.items.foods.SkillItemTier;
import com.robertx22.age_of_exile.player_skills.items.inscribing.EssencePaperItem;
import com.robertx22.age_of_exile.saveclasses.stat_soul.StatSoulItem;
import com.robertx22.age_of_exile.vanilla_mc.items.*;
import com.robertx22.age_of_exile.vanilla_mc.items.favor.EmptyFavorItem;
import com.robertx22.age_of_exile.vanilla_mc.items.favor.FullFavorItem;
import com.robertx22.age_of_exile.vanilla_mc.items.gearitems.VanillaMaterial;
import com.robertx22.age_of_exile.vanilla_mc.items.gearitems.baubles.ItemNecklace;
import com.robertx22.age_of_exile.vanilla_mc.items.gearitems.baubles.ItemRing;
import com.robertx22.age_of_exile.vanilla_mc.items.gearitems.weapons.StaffWeapon;
import com.robertx22.age_of_exile.vanilla_mc.items.loot_crate.LootCrateItem;
import com.robertx22.age_of_exile.vanilla_mc.items.misc.*;
import com.robertx22.age_of_exile.vanilla_mc.items.misc.reset_pots.ResetStatsPotion;
import com.robertx22.age_of_exile.vanilla_mc.items.misc.reset_pots.SingleTalentResetPotion;
import com.robertx22.age_of_exile.vanilla_mc.items.misc.reset_pots.SpellResetPotion;
import com.robertx22.age_of_exile.vanilla_mc.items.misc.reset_pots.TalentResetPotion;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.function.Function;
import java.util.function.Supplier;

public class SlashItems {

    public static void init() {

    }

    public static RegObj<StatSoulItem> STAT_SOUL = Def.item(() -> new StatSoulItem());
    public static RegObj<RarityEssenceItem> RARITY_ESSENCE = Def.item(() -> new RarityEssenceItem());
    public static RegObj<RarityUpgradeStone> RARITY_UPGRADE = Def.item(() -> new RarityUpgradeStone());
    public static RegObj<LootCrateItem> LOOT_CRATE = Def.item(() -> new LootCrateItem());
    public static RegObj<SourceOfStrengthItem> SOURCE_OF_STRENGTH = Def.item(() -> new SourceOfStrengthItem());

    public static RegObj<ProjectileItem> FIREBALL = Def.item(() -> new ProjectileItem("fireball"));
    public static RegObj<ProjectileItem> SNOWBALL = Def.item(() -> new ProjectileItem("snowball"));
    public static RegObj<ProjectileItem> SLIMEBALL = Def.item(() -> new ProjectileItem("slimeball"));

    public static RegObj<EssencePaperItem> ESSENCE_PAPER = Def.item(() -> new EssencePaperItem(), "scroll/paper");

    public static RegObj<TeleportBackItem> TELEPORT_BACK = Def.item(() -> new TeleportBackItem(), "misc/teleport_back");

    public static SalvagedDustItem T0_DUST() {
        return ProfessionItems.SALVAGED_ESSENCE_MAP.get(SkillItemTier.TIER0)
            .get();
    }

    public static SalvagedDustItem T1_DUST() {
        return ProfessionItems.SALVAGED_ESSENCE_MAP.get(SkillItemTier.TIER1)
            .get();
    }

    public static SalvagedDustItem T2_DUST() {
        return ProfessionItems.SALVAGED_ESSENCE_MAP.get(SkillItemTier.TIER2)
            .get();
    }

    public static SalvagedDustItem T3_DUST() {
        return ProfessionItems.SALVAGED_ESSENCE_MAP.get(SkillItemTier.TIER3)
            .get();
    }

    public static SalvagedDustItem T4_DUST() {
        return ProfessionItems.SALVAGED_ESSENCE_MAP.get(SkillItemTier.TIER4)
            .get();
    }

    public static RegObj<LootTableItem> LOOT_TABLE_ITEM = Def.item(() -> new LootTableItem(), "loot_table_chest");

    public static RegObj<FullFavorItem> FULL_FAVOR = Def.item(() -> new FullFavorItem(), "full_favor");
    public static RegObj<EmptyFavorItem> EMPTY_FAVOR = Def.item(() -> new EmptyFavorItem(), "empty_favor");

    public static RegObj<CustomLootCrateItem> CUSTOM_CRATE = Def.item(() -> new CustomLootCrateItem(), "custom_crate");

    public static RegObj<Item> NEWBIE_GEAR_BAG = Def.item(() -> new ItemNewbieGearBag(), "newbie_gear_bag");
    public static RegObj<Item> SALVAGE_HAMMER = Def.item(() -> new SalvageHammerItem(), "salvage_hammer");

    public static RegObj<Item> INFUSED_IRON = Def.item(() -> new SimpleMatItem(), "mat/infused_iron");
    public static RegObj<Item> CRYSTALLIZED_ESSENCE = Def.item(() -> new SimpleMatItem(), "mat/crystallized_essence");
    public static RegObj<Item> GOLDEN_ORB = Def.item(() -> new SimpleMatItem(), "mat/golden_orb");
    public static RegObj<Item> MYTHIC_ESSENCE = Def.item(() -> new SimpleMatItem(), "mat/mythic_essence");

    public static RegObj<TalentResetPotion> RESET_ALL_PERKS = Def.item(() -> new TalentResetPotion());
    public static RegObj<SingleTalentResetPotion> ADD_RESET_PERK_POINTS = Def.item(() -> new SingleTalentResetPotion());
    public static RegObj<SpellResetPotion> RESET_SPELLS = Def.item(() -> new SpellResetPotion());
    public static RegObj<ResetStatsPotion> RESET_STATS = Def.item(() -> new ResetStatsPotion());
    public static RegObj<FunnyDeathPotion> DEATH_POTION = Def.item(() -> new FunnyDeathPotion());

    public static RegObj<BackpackItem> BACKPACK = Def.item(() -> new BackpackItem(), "backpack/normal");
    public static RegObj<MaterialBagItem> MATERIAL_POUCH = Def.item(() -> new MaterialBagItem(), "backpack/material_pouch");

    static Item.Properties stationProp = new Item.Properties().tab(CreativeTabs.MyModTab);

    public static RegObj<Item> TELEPORTER_BLOCK = Def.item("teleporter", () -> new BlockItem(SlashBlocks.TELEPORTER.get(), stationProp));
    public static RegObj<Item> RUNEWORD_STATION = Def.item("runeword_station", () -> new BlockItem(SlashBlocks.RUNEWORD.get(), stationProp));
    public static RegObj<Item> PORTAL = Def.item("portal", () -> new BlockItem(SlashBlocks.PORTAL.get(), stationProp));

    public static class GearItems {

        public static void init() {

        }

        public static HashMap<VanillaMaterial, RegObj<Item>> STAFFS = of("weapon/staff/",
            Arrays.asList(VanillaMaterial.DIAMOND, VanillaMaterial.GOLD, VanillaMaterial.IRON, VanillaMaterial.WOOD),
            x -> new StaffWeapon(x));

        public static HashMap<VanillaMaterial, RegObj<Item>> RINGS = of("jewelry/ring/", Arrays.asList(
            VanillaMaterial.DIAMOND, VanillaMaterial.GOLD, VanillaMaterial.IRON
        ), x -> new ItemRing(x));
        public static HashMap<VanillaMaterial, RegObj<Item>> NECKLACES = of("jewelry/necklace/", Arrays.asList(
            VanillaMaterial.DIAMOND, VanillaMaterial.GOLD, VanillaMaterial.IRON
        ), x -> new ItemNecklace(x));

        private static HashMap<VanillaMaterial, RegObj<Item>> of(String idprefix, List<VanillaMaterial> list, Function<VanillaMaterial, Item> item) {
            HashMap<VanillaMaterial, RegObj<Item>> map = new HashMap<VanillaMaterial, RegObj<Item>>();
            list
                .forEach(x -> {
                    map.put(x, Def.item(idprefix + x.id, () -> item.apply(x)));
                });
            return map;
        }

        private static HashMap<LevelRange, RegObj<Item>> of(String idprefix, Supplier<Item> item) {
            HashMap<LevelRange, RegObj<Item>> map = new HashMap<LevelRange, RegObj<Item>>();
            LevelRanges.allNormal()
                .forEach(x -> {
                    map.put(x, Def.item(idprefix + x.id_suffix, () -> item.get()));
                });
            return map;
        }

    }

}
