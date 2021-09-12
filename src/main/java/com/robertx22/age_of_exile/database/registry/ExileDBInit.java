package com.robertx22.age_of_exile.database.registry;

import com.robertx22.age_of_exile.database.data.DimensionConfig;
import com.robertx22.age_of_exile.database.data.EntityConfig;
import com.robertx22.age_of_exile.database.data.crafting_req.CraftingReq;
import com.robertx22.age_of_exile.database.data.currency.OrbOfTransmutationItem;
import com.robertx22.age_of_exile.database.data.game_balance_config.GameBalanceConfig;
import com.robertx22.age_of_exile.database.data.gear_slots.GearSlot;
import com.robertx22.age_of_exile.database.data.mob_affixes.MobAffix;
import com.robertx22.age_of_exile.database.data.rarities.GearRarity;
import com.robertx22.age_of_exile.database.data.rarities.MobRarity;
import com.robertx22.age_of_exile.database.data.scroll_buff.ScrollBuff;
import com.robertx22.age_of_exile.database.data.set.GearSet;
import com.robertx22.age_of_exile.database.data.spells.components.Spell;
import com.robertx22.age_of_exile.database.data.tiers.base.Difficulty;
import com.robertx22.age_of_exile.database.data.value_calc.ValueCalculation;
import com.robertx22.age_of_exile.database.empty_entries.EmptyAffix;
import com.robertx22.age_of_exile.database.empty_entries.EmptyStat;
import com.robertx22.age_of_exile.database.registrators.CurrencyItems;
import com.robertx22.age_of_exile.database.registrators.StatsRegister;
import com.robertx22.age_of_exile.dimension.database.dungeon_mob_lists.DungeonMobList;
import com.robertx22.library_of_exile.registry.Database;
import com.robertx22.library_of_exile.registry.ExileRegistryContainer;
import net.minecraft.util.Formatting;

public class ExileDBInit {

    public static void registerAllItems() {
        try {
            registerAllNonDatapackEntries();
        } catch (ExceptionInInitializerError e) {
            // leave this, once this error happened and we don't know why. this is to know the cause if it happens again
            e.printStackTrace();
            e.getCause()
                .printStackTrace();
        }
    }

    private static void registerAllNonDatapackEntries() {
        new StatsRegister().registerAll();// STATS MUST BE INIT FIRST
        // should be at least
        new CurrencyItems().registerAll();
    }

    public static void initRegistries() {
        // data pack ones
        Database.addRegistry(new RarityRegistryContainer<>(ExileRegistryTypes.GEAR_RARITY, new GearRarity()).setIsDatapack());
        Database.addRegistry(new RarityRegistryContainer<MobRarity>(ExileRegistryTypes.MOB_RARITY, null).setIsDatapack());
        Database.addRegistry(new ExileRegistryContainer<>(ExileRegistryTypes.GEAR_SLOT, new GearSlot("", "", 0, -1, 0)).setIsDatapack());
        Database.addRegistry(new ExileRegistryContainer<>(ExileRegistryTypes.GEAR_RARITY_GROUP, null).setIsDatapack());
        Database.addRegistry(new ExileRegistryContainer<>(ExileRegistryTypes.GEAR_TYPE, null).setIsDatapack());
        Database.addRegistry(new ExileRegistryContainer<>(ExileRegistryTypes.RUNEWORDS, null).setIsDatapack());
        Database.addRegistry(new ExileRegistryContainer<>(ExileRegistryTypes.EXILE_EFFECT, null).setIsDatapack());
        Database.addRegistry(new ExileRegistryContainer<>(ExileRegistryTypes.TIER, new Difficulty()).setIsDatapack());
        Database.addRegistry(new ExileRegistryContainer<>(ExileRegistryTypes.AFFIX, EmptyAffix.getInstance()).setIsDatapack());
        Database.addRegistry(new ExileRegistryContainer<>(ExileRegistryTypes.MOB_AFFIX, new MobAffix("empty", "empty", Formatting.AQUA)).setIsDatapack());
        Database.addRegistry(new ExileRegistryContainer<>(ExileRegistryTypes.UNIQUE_GEAR, null).setIsDatapack());
        Database.addRegistry(new ExileRegistryContainer<>(ExileRegistryTypes.GEM, null).setIsDatapack());
        Database.addRegistry(new ExileRegistryContainer<>(ExileRegistryTypes.RUNE, null).setIsDatapack());
        Database.addRegistry(new ExileRegistryContainer<>(ExileRegistryTypes.SPELL, Spell.SERIALIZER).setIsDatapack());
        Database.addRegistry(new ExileRegistryContainer<>(ExileRegistryTypes.PERK, null).setIsDatapack());
        Database.addRegistry(new ExileRegistryContainer<>(ExileRegistryTypes.TALENT_TREE, null).setIsDatapack());
        Database.addRegistry(new ExileRegistryContainer<>(ExileRegistryTypes.FAVOR_RANK, null).setIsDatapack());
        Database.addRegistry(new ExileRegistryContainer<>(ExileRegistryTypes.SALVAGE_OUTPUT, null).setIsDatapack());
        Database.addRegistry(new ExileRegistryContainer<>(ExileRegistryTypes.PLAYER_SKILLS, null).setIsDatapack());
        Database.addRegistry(new ExileRegistryContainer<>(ExileRegistryTypes.SPELL_SCHOOL, null).setIsDatapack());
        Database.addRegistry(new ExileRegistryContainer<>(ExileRegistryTypes.BASE_STATS, null).setIsDatapack());
        Database.addRegistry(new ExileRegistryContainer<>(ExileRegistryTypes.GAME_BALANCE, new GameBalanceConfig()).setIsDatapack());
        Database.addRegistry(new ExileRegistryContainer<>(ExileRegistryTypes.SALVAGE_RECIPE, null).setIsDatapack());
        Database.addRegistry(new ExileRegistryContainer<>(ExileRegistryTypes.CRAFTING_REQ, new CraftingReq()).setIsDatapack());
        Database.addRegistry(new ExileRegistryContainer<>(ExileRegistryTypes.SCROLL_BUFFS, new ScrollBuff()).setIsDatapack());
        Database.addRegistry(new ExileRegistryContainer<>(ExileRegistryTypes.VALUE_CALC, new ValueCalculation()).setIsDatapack());
        Database.addRegistry(new ExileRegistryContainer<>(ExileRegistryTypes.DUNGEON_MOB_LIST, new DungeonMobList()).setIsDatapack());
        Database.addRegistry(new ExileRegistryContainer<>(ExileRegistryTypes.STAT_EFFECT, null).setIsDatapack());
        Database.addRegistry(new ExileRegistryContainer<>(ExileRegistryTypes.STAT_CONDITION, null).setIsDatapack());
        Database.addRegistry(new ExileRegistryContainer<>(ExileRegistryTypes.GEAR_SET, new GearSet()).setIsDatapack());
        Database.addRegistry(new ExileRegistryContainer<>(ExileRegistryTypes.DIMENSION_CONFIGS, DimensionConfig.DefaultExtra()
            ).logAdditions()
                .setIsDatapack()
                .dontErrorMissingEntriesOnAccess()
        );
        Database.addRegistry(new ExileRegistryContainer<>(ExileRegistryTypes.ENTITY_CONFIGS, new EntityConfig("", 0)).logAdditions()
            .setIsDatapack());

        Database.addRegistry(new ExileRegistryContainer<>(ExileRegistryTypes.STAT, EmptyStat.getInstance()));
        Database.addRegistry(new ExileRegistryContainer<>(ExileRegistryTypes.CURRENCY_ITEMS, new OrbOfTransmutationItem()));
    }
}
