package com.robertx22.age_of_exile.database.registry;

import com.robertx22.age_of_exile.capability.entity.EntityCap;
import com.robertx22.age_of_exile.database.data.DimensionConfig;
import com.robertx22.age_of_exile.database.data.EntityConfig;
import com.robertx22.age_of_exile.database.data.affixes.Affix;
import com.robertx22.age_of_exile.database.data.base_stats.BaseStatsConfig;
import com.robertx22.age_of_exile.database.data.compatible_item.CompatibleItem;
import com.robertx22.age_of_exile.database.data.crafting_req.CraftingReq;
import com.robertx22.age_of_exile.database.data.currency.OrbOfTransmutationItem;
import com.robertx22.age_of_exile.database.data.currency.base.CurrencyItem;
import com.robertx22.age_of_exile.database.data.exile_effects.ExileEffect;
import com.robertx22.age_of_exile.database.data.favor.FavorRank;
import com.robertx22.age_of_exile.database.data.game_balance_config.GameBalanceConfig;
import com.robertx22.age_of_exile.database.data.gear_slots.GearSlot;
import com.robertx22.age_of_exile.database.data.gear_types.bases.BaseGearType;
import com.robertx22.age_of_exile.database.data.gems.Gem;
import com.robertx22.age_of_exile.database.data.groups.GearRarityGroup;
import com.robertx22.age_of_exile.database.data.mob_affixes.MobAffix;
import com.robertx22.age_of_exile.database.data.perks.Perk;
import com.robertx22.age_of_exile.database.data.player_skills.PlayerSkill;
import com.robertx22.age_of_exile.database.data.races.PlayerRace;
import com.robertx22.age_of_exile.database.data.random_skill_gem_stats.RandomSkillGemStats;
import com.robertx22.age_of_exile.database.data.rarities.GearRarity;
import com.robertx22.age_of_exile.database.data.rarities.MobRarity;
import com.robertx22.age_of_exile.database.data.rarities.SkillGemRarity;
import com.robertx22.age_of_exile.database.data.runes.Rune;
import com.robertx22.age_of_exile.database.data.runewords.RuneWord;
import com.robertx22.age_of_exile.database.data.salvage_outputs.SalvageOutput;
import com.robertx22.age_of_exile.database.data.salvage_recipes.SalvageRecipe;
import com.robertx22.age_of_exile.database.data.scroll_buff.ScrollBuff;
import com.robertx22.age_of_exile.database.data.set.GearSet;
import com.robertx22.age_of_exile.database.data.skill_gem.SkillGem;
import com.robertx22.age_of_exile.database.data.spell_schools.SpellSchool;
import com.robertx22.age_of_exile.database.data.spells.components.Spell;
import com.robertx22.age_of_exile.database.data.stats.Stat;
import com.robertx22.age_of_exile.database.data.tiers.base.Tier;
import com.robertx22.age_of_exile.database.data.unique_items.UniqueGear;
import com.robertx22.age_of_exile.database.data.value_calc.ValueCalculation;
import com.robertx22.age_of_exile.database.registrators.CurrencyItems;
import com.robertx22.age_of_exile.database.registrators.Stats;
import com.robertx22.age_of_exile.database.registry.empty_entries.EmptyAffix;
import com.robertx22.age_of_exile.database.registry.empty_entries.EmptyStat;
import com.robertx22.age_of_exile.dimension.database.dungeon_mob_lists.DungeonMobList;
import com.robertx22.age_of_exile.uncommon.utilityclasses.MapManager;
import net.minecraft.entity.LivingEntity;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Formatting;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Database {

    private static HashMap<SlashRegistryType, SlashRegistryContainer> SERVER = new HashMap<>();
    private static HashMap<SlashRegistryType, SlashRegistryContainer> BACKUP = new HashMap<>();

    public static boolean areDatapacksLoaded(World world) {
        return SlashRegistryType.getInRegisterOrder(SyncTime.ON_LOGIN)
            .stream()
            .allMatch(x -> Database.getRegistry(x)
                .isRegistrationDone());
    }

    public static void backup() {
        BACKUP = new HashMap<>(SERVER);
    }

    public static void restoreBackup() {
        System.out.print("Restoring registry backup, this should never happen!");
        SERVER = new HashMap<>(BACKUP); // this doesnt appear to be EVER called.. But unsure if good idea to remove
    }

    public static void restoreFromBackupifEmpty() {
        if (Affixes().isEmpty()) {
            restoreBackup();
        }
    }

    public static DimensionConfig getDimensionConfig(WorldAccess world) {
        String id = MapManager.getResourceLocation((World) world)
            .toString();
        return DimensionConfigs().get(id);
    }

    public static EntityConfig getEntityConfig(LivingEntity entity, EntityCap.UnitData data) {

        String monster_id = Registry.ENTITY_TYPE.getId(entity.getType())
            .toString();
        String mod_id = Registry.ENTITY_TYPE.getId(entity.getType())
            .getNamespace();

        EntityConfig config = null;

        if (EntityConfigs().isRegistered(monster_id)) {
            config = EntityConfigs().get(monster_id);
            if (config != null) {
                return config;
            }
        } else {
            if (EntityConfigs().isRegistered(mod_id)) {
                config = EntityConfigs().get(mod_id);

                if (config != null) {
                    return config;
                }

            } else {
                config = EntityConfigs().get(data.getType().id);

                if (config != null) {
                    return config;
                }
            }
        }

        return EntityConfigs().getDefault();

    }

    public static SlashRegistryContainer<GearSlot> GearSlots() {
        return getRegistry(SlashRegistryType.GEAR_SLOT);
    }

    public static SlashRegistryContainer<UniqueGear> UniqueGears() {
        return getRegistry(SlashRegistryType.UNIQUE_GEAR);
    }

    public static SlashRegistryContainer<GearRarityGroup> GearRarityGroups() {
        return getRegistry(SlashRegistryType.GEAR_RARITY_GROUP);
    }

    public static SlashRegistryContainer<CurrencyItem> CurrencyItems() {
        return getRegistry(SlashRegistryType.CURRENCY_ITEMS);
    }

    public static SlashRegistryContainer<DimensionConfig> DimensionConfigs() {
        return getRegistry(SlashRegistryType.DIMENSION_CONFIGS);
    }

    public static SlashRegistryContainer<CompatibleItem> CompatibleItems() {
        return getRegistry(SlashRegistryType.COMPATIBLE_ITEM);
    }

    public static SlashRegistryContainer<SalvageOutput> SalvageOutputs() {
        return getRegistry(SlashRegistryType.SALVAGE_OUTPUT);
    }

    public static SlashRegistryContainer<Gem> Gems() {
        return getRegistry(SlashRegistryType.GEM);
    }

    public static SlashRegistryContainer<ExileEffect> ExileEffects() {
        return getRegistry(SlashRegistryType.EXILE_EFFECT);
    }

    public static SlashRegistryContainer<SalvageRecipe> SalvageRecipes() {
        return getRegistry(SlashRegistryType.SALVAGE_RECIPE);
    }

    public static SlashRegistryContainer<SkillGemRarity> SkillGemRarities() {
        return getRegistry(SlashRegistryType.SKILL_GEM_RARITY);
    }

    public static SlashRegistryContainer<FavorRank> FavorRanks() {
        return getRegistry(SlashRegistryType.FAVOR_RANK);
    }

    public static SlashRegistryContainer<SpellSchool> SpellSchools() {
        return getRegistry(SlashRegistryType.SPELL_SCHOOL);
    }

    public static SlashRegistryContainer<GearSet> Sets() {
        return getRegistry(SlashRegistryType.GEAR_SET);
    }

    public static SlashRegistryContainer<Perk> Perks() {
        return getRegistry(SlashRegistryType.PERK);
    }

    public static SlashRegistryContainer<PlayerRace> Races() {
        return getRegistry(SlashRegistryType.RACES);
    }

    public static SlashRegistryContainer<Rune> Runes() {
        return getRegistry(SlashRegistryType.RUNE);
    }

    public static SlashRegistryContainer<RandomSkillGemStats> RandomSkilLGemStats() {
        return getRegistry(SlashRegistryType.RANDOM_SKILL_GEM_STATS);
    }

    public static SlashRegistryContainer<Affix> Affixes() {
        return getRegistry(SlashRegistryType.AFFIX);
    }

    public static RarityRegistryContainer<GearRarity> GearRarities() {
        return (RarityRegistryContainer<GearRarity>) getRegistry(SlashRegistryType.GEAR_RARITY);
    }

    public static RarityRegistryContainer<MobRarity> MobRarities() {
        return (RarityRegistryContainer<MobRarity>) getRegistry(SlashRegistryType.MOB_RARITY);
    }

    public static SlashRegistryContainer<Tier> Tiers() {
        return getRegistry(SlashRegistryType.TIER);
    }

    public static SlashRegistryContainer<SkillGem> SkillGems() {
        return getRegistry(SlashRegistryType.SKILL_GEM);
    }

    public static SlashRegistryContainer<BaseGearType> GearTypes() {
        return getRegistry(SlashRegistryType.GEAR_TYPE);
    }

    public static SlashRegistryContainer<ScrollBuff> ScrollBuffs() {
        return getRegistry(SlashRegistryType.SCROLL_BUFFS);
    }

    public static SlashRegistryContainer<DungeonMobList> DungeonMobLists() {
        return getRegistry(SlashRegistryType.DUNGEON_MOB_LIST);
    }

    public static SlashRegistryContainer<Spell> Spells() {
        return getRegistry(SlashRegistryType.SPELL);
    }

    public static SlashRegistryContainer<MobAffix> MobAffixes() {
        return getRegistry(SlashRegistryType.MOB_AFFIX);
    }

    public static SlashRegistryContainer<RuneWord> Runewords() {
        return getRegistry(SlashRegistryType.RUNEWORD);
    }

    public static SlashRegistryContainer<ValueCalculation> ValueCalculations() {
        return getRegistry(SlashRegistryType.VALUE_CALC);
    }

    public static SlashRegistryContainer<EntityConfig> EntityConfigs() {
        return getRegistry(SlashRegistryType.ENTITY_CONFIGS);
    }

    public static SlashRegistryContainer<PlayerSkill> PlayerSkills() {
        return getRegistry(SlashRegistryType.PLAYER_SKILLS);
    }

    public static SlashRegistryContainer<CraftingReq> ItemCraftReq() {
        return getRegistry(SlashRegistryType.CRAFTING_REQ);
    }

    public static SlashRegistryContainer<Stat> Stats() {
        return getRegistry(SlashRegistryType.STAT);
    }

    public static SlashRegistryContainer<BaseStatsConfig> BaseStats() {
        return getRegistry(SlashRegistryType.BASE_STATS);
    }

    public static List<SlashRegistryContainer> getAllRegistries() {
        return new ArrayList<>(SERVER.values());
    }

    public static SlashRegistryContainer getRegistry(SlashRegistryType type) {
        return SERVER.get(type);
    }

    public static ISlashRegistryEntry get(SlashRegistryType type, String guid) {
        return getRegistry(type).get(guid);

    }

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

    public static void sendPacketsToClient(ServerPlayerEntity player, SyncTime sync) {

        List<SlashRegistryType> list = SlashRegistryType.getInRegisterOrder(sync);

        list.forEach(x -> getRegistry(x).sendUpdatePacket(player));
    }

    public static void checkGuidValidity() {

        SERVER.values()
            .forEach(c -> c.getAllIncludingSeriazable()
                .forEach(x -> {
                    ISlashRegistryEntry entry = (ISlashRegistryEntry) x;
                    if (!entry.isGuidFormattedCorrectly()) {
                        throw new RuntimeException(entry.getInvalidGuidMessage());
                    }
                }));

    }

    public static void unregisterInvalidEntries() {

        System.out.println("Starting Age of Exile Registry auto validation.");

        List<ISlashRegistryEntry> invalid = new ArrayList<>();

        SERVER.values()
            .forEach(c -> c.getList()
                .forEach(x -> {
                    ISlashRegistryEntry entry = (ISlashRegistryEntry) x;
                    if (!entry.isRegistryEntryValid()) {
                        invalid.add(entry);
                    }
                }));

        invalid.forEach(x -> x.unregisterDueToInvalidity());

        if (invalid.isEmpty()) {
            System.out.println("All Age of Exile registries appear valid.");
        } else {
            System.out.println(invalid.size() + " Age of Exile entries are INVALID!");
        }

    }

    private static void registerAllNonDatapackEntries() {
        new Stats().registerAll();// STATS MUST BE INIT FIRST
        // should be at least
        new CurrencyItems().registerAll();
    }

    private static void addRegistry(SlashRegistryContainer cont) {
        SERVER.put(cont.getType(), cont);
    }

    public static void initRegistries() {
        SERVER = new HashMap<>();

        SlashRegistryType.init();

        // data pack ones
        addRegistry(new RarityRegistryContainer<GearRarity>(SlashRegistryType.GEAR_RARITY, new GearRarity()).setIsDatapack());
        addRegistry(new RarityRegistryContainer<MobRarity>(SlashRegistryType.MOB_RARITY, null).setIsDatapack());
        addRegistry(new SlashRegistryContainer<>(SlashRegistryType.GEAR_SLOT, new GearSlot("", 0)).setIsDatapack());
        addRegistry(new SlashRegistryContainer<>(SlashRegistryType.GEAR_RARITY_GROUP, null).setIsDatapack());
        addRegistry(new SlashRegistryContainer<>(SlashRegistryType.GEAR_TYPE, null).setIsDatapack());
        addRegistry(new SlashRegistryContainer<>(SlashRegistryType.EXILE_EFFECT, null).setIsDatapack());
        addRegistry(new SlashRegistryContainer<>(SlashRegistryType.TIER, new Tier(0)).setIsDatapack());
        addRegistry(new SlashRegistryContainer<>(SlashRegistryType.AFFIX, EmptyAffix.getInstance()).setIsDatapack());
        addRegistry(new SlashRegistryContainer<>(SlashRegistryType.MOB_AFFIX, new MobAffix("empty", "empty", Formatting.AQUA)).setIsDatapack());
        addRegistry(new SlashRegistryContainer<>(SlashRegistryType.UNIQUE_GEAR, null).setIsDatapack());
        addRegistry(new SlashRegistryContainer<>(SlashRegistryType.GEM, null).setIsDatapack());
        addRegistry(new SlashRegistryContainer<>(SlashRegistryType.RUNE, null).setIsDatapack());
        addRegistry(new SlashRegistryContainer<>(SlashRegistryType.RUNEWORD, null).setIsDatapack());
        addRegistry(new SlashRegistryContainer<>(SlashRegistryType.SPELL, Spell.SERIALIZER).setIsDatapack());
        addRegistry(new SlashRegistryContainer<>(SlashRegistryType.PERK, null).setIsDatapack());
        addRegistry(new SlashRegistryContainer<>(SlashRegistryType.SPELL_SCHOOL, null).setIsDatapack());
        addRegistry(new SlashRegistryContainer<>(SlashRegistryType.FAVOR_RANK, null).setIsDatapack());
        addRegistry(new SlashRegistryContainer<>(SlashRegistryType.SALVAGE_OUTPUT, null).setIsDatapack());
        addRegistry(new SlashRegistryContainer<>(SlashRegistryType.PLAYER_SKILLS, null).setIsDatapack());
        addRegistry(new SlashRegistryContainer<>(SlashRegistryType.BASE_STATS, null).setIsDatapack());
        addRegistry(new SlashRegistryContainer<>(SlashRegistryType.SKILL_GEM, null).setIsDatapack());
        addRegistry(new SlashRegistryContainer<>(SlashRegistryType.SKILL_GEM_RARITY, null).setIsDatapack());
        addRegistry(new SlashRegistryContainer<>(SlashRegistryType.RANDOM_SKILL_GEM_STATS, null).setIsDatapack());
        addRegistry(new SlashRegistryContainer<>(SlashRegistryType.GAME_BALANCE, new GameBalanceConfig()).setIsDatapack());
        addRegistry(new SlashRegistryContainer<>(SlashRegistryType.SALVAGE_RECIPE, null).setIsDatapack());
        addRegistry(new SlashRegistryContainer<>(SlashRegistryType.RACES, new PlayerRace()).setIsDatapack());
        addRegistry(new SlashRegistryContainer<>(SlashRegistryType.CRAFTING_REQ, new CraftingReq()).setIsDatapack());
        addRegistry(new SlashRegistryContainer<>(SlashRegistryType.SCROLL_BUFFS, new ScrollBuff()).setIsDatapack());
        addRegistry(new SlashRegistryContainer<>(SlashRegistryType.VALUE_CALC, new ValueCalculation()).setIsDatapack());
        addRegistry(new SlashRegistryContainer<>(SlashRegistryType.DUNGEON_MOB_LIST, new DungeonMobList()).setIsDatapack());
        addRegistry(new SlashRegistryContainer<>(SlashRegistryType.GEAR_SET, new GearSet()).setIsDatapack());
        addRegistry(new SlashRegistryContainer<>(SlashRegistryType.COMPATIBLE_ITEM,
            CompatibleItem.EMPTY).dontErrorIfEmpty()
            .setIsDatapack()
            .logAdditions());
        addRegistry(new SlashRegistryContainer<>(SlashRegistryType.DIMENSION_CONFIGS, DimensionConfig.DefaultExtra()
            ).logAdditions()
                .setIsDatapack()
                .dontErrorMissingEntriesOnAccess()
        );
        addRegistry(new SlashRegistryContainer<>(SlashRegistryType.ENTITY_CONFIGS, new EntityConfig("", 0)).logAdditions()
            .setIsDatapack());

        addRegistry(new SlashRegistryContainer<>(SlashRegistryType.STAT, EmptyStat.getInstance()));
        addRegistry(new SlashRegistryContainer<>(SlashRegistryType.CURRENCY_ITEMS, new OrbOfTransmutationItem()));
    }

}
