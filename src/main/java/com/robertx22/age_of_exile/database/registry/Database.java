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
import com.robertx22.age_of_exile.database.empty_entries.EmptyAffix;
import com.robertx22.age_of_exile.database.empty_entries.EmptyStat;
import com.robertx22.age_of_exile.database.registrators.CurrencyItems;
import com.robertx22.age_of_exile.database.registrators.StatsRegister;
import com.robertx22.age_of_exile.dimension.database.dungeon_mob_lists.DungeonMobList;
import com.robertx22.age_of_exile.uncommon.effectdatas.rework.action.StatEffect;
import com.robertx22.age_of_exile.uncommon.effectdatas.rework.condition.StatCondition;
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

    private static HashMap<ExileRegistryTypes, ExileRegistryContainer> SERVER = new HashMap<>();
    private static HashMap<ExileRegistryTypes, ExileRegistryContainer> BACKUP = new HashMap<>();

    public static boolean areDatapacksLoaded(World world) {
        return ExileRegistryTypes.getInRegisterOrder(SyncTime.ON_LOGIN)
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

    public static ExileRegistryContainer<GearSlot> GearSlots() {
        return getRegistry(ExileRegistryTypes.GEAR_SLOT);
    }

    public static ExileRegistryContainer<UniqueGear> UniqueGears() {
        return getRegistry(ExileRegistryTypes.UNIQUE_GEAR);
    }

    public static ExileRegistryContainer<GearRarityGroup> GearRarityGroups() {
        return getRegistry(ExileRegistryTypes.GEAR_RARITY_GROUP);
    }

    public static ExileRegistryContainer<CurrencyItem> CurrencyItems() {
        return getRegistry(ExileRegistryTypes.CURRENCY_ITEMS);
    }

    public static ExileRegistryContainer<DimensionConfig> DimensionConfigs() {
        return getRegistry(ExileRegistryTypes.DIMENSION_CONFIGS);
    }

    public static ExileRegistryContainer<CompatibleItem> CompatibleItems() {
        return getRegistry(ExileRegistryTypes.COMPATIBLE_ITEM);
    }

    public static ExileRegistryContainer<SalvageOutput> SalvageOutputs() {
        return getRegistry(ExileRegistryTypes.SALVAGE_OUTPUT);
    }

    public static ExileRegistryContainer<StatCondition> StatConditions() {
        return getRegistry(ExileRegistryTypes.STAT_CONDITION);
    }

    public static ExileRegistryContainer<StatEffect> StatEffects() {
        return getRegistry(ExileRegistryTypes.STAT_EFFECT);
    }

    public static ExileRegistryContainer<Gem> Gems() {
        return getRegistry(ExileRegistryTypes.GEM);
    }

    public static ExileRegistryContainer<ExileEffect> ExileEffects() {
        return getRegistry(ExileRegistryTypes.EXILE_EFFECT);
    }

    public static ExileRegistryContainer<SalvageRecipe> SalvageRecipes() {
        return getRegistry(ExileRegistryTypes.SALVAGE_RECIPE);
    }

    public static ExileRegistryContainer<SkillGemRarity> SkillGemRarities() {
        return getRegistry(ExileRegistryTypes.SKILL_GEM_RARITY);
    }

    public static ExileRegistryContainer<FavorRank> FavorRanks() {
        return getRegistry(ExileRegistryTypes.FAVOR_RANK);
    }

    public static ExileRegistryContainer<SpellSchool> SpellSchools() {
        return getRegistry(ExileRegistryTypes.SPELL_SCHOOL);
    }

    public static ExileRegistryContainer<GearSet> Sets() {
        return getRegistry(ExileRegistryTypes.GEAR_SET);
    }

    public static ExileRegistryContainer<Perk> Perks() {
        return getRegistry(ExileRegistryTypes.PERK);
    }

    public static ExileRegistryContainer<PlayerRace> Races() {
        return getRegistry(ExileRegistryTypes.RACES);
    }

    public static ExileRegistryContainer<Rune> Runes() {
        return getRegistry(ExileRegistryTypes.RUNE);
    }

    public static ExileRegistryContainer<RandomSkillGemStats> RandomSkilLGemStats() {
        return getRegistry(ExileRegistryTypes.RANDOM_SKILL_GEM_STATS);
    }

    public static ExileRegistryContainer<Affix> Affixes() {
        return getRegistry(ExileRegistryTypes.AFFIX);
    }

    public static RarityRegistryContainer<GearRarity> GearRarities() {
        return (RarityRegistryContainer<GearRarity>) getRegistry(ExileRegistryTypes.GEAR_RARITY);
    }

    public static RarityRegistryContainer<MobRarity> MobRarities() {
        return (RarityRegistryContainer<MobRarity>) getRegistry(ExileRegistryTypes.MOB_RARITY);
    }

    public static ExileRegistryContainer<Tier> Tiers() {
        return getRegistry(ExileRegistryTypes.TIER);
    }

    public static ExileRegistryContainer<SkillGem> SkillGems() {
        return getRegistry(ExileRegistryTypes.SKILL_GEM);
    }

    public static ExileRegistryContainer<BaseGearType> GearTypes() {
        return getRegistry(ExileRegistryTypes.GEAR_TYPE);
    }

    public static ExileRegistryContainer<ScrollBuff> ScrollBuffs() {
        return getRegistry(ExileRegistryTypes.SCROLL_BUFFS);
    }

    public static ExileRegistryContainer<DungeonMobList> DungeonMobLists() {
        return getRegistry(ExileRegistryTypes.DUNGEON_MOB_LIST);
    }

    public static ExileRegistryContainer<Spell> Spells() {
        return getRegistry(ExileRegistryTypes.SPELL);
    }

    public static ExileRegistryContainer<MobAffix> MobAffixes() {
        return getRegistry(ExileRegistryTypes.MOB_AFFIX);
    }

    public static ExileRegistryContainer<RuneWord> Runewords() {
        return getRegistry(ExileRegistryTypes.RUNEWORD);
    }

    public static ExileRegistryContainer<ValueCalculation> ValueCalculations() {
        return getRegistry(ExileRegistryTypes.VALUE_CALC);
    }

    public static ExileRegistryContainer<EntityConfig> EntityConfigs() {
        return getRegistry(ExileRegistryTypes.ENTITY_CONFIGS);
    }

    public static ExileRegistryContainer<PlayerSkill> PlayerSkills() {
        return getRegistry(ExileRegistryTypes.PLAYER_SKILLS);
    }

    public static ExileRegistryContainer<CraftingReq> ItemCraftReq() {
        return getRegistry(ExileRegistryTypes.CRAFTING_REQ);
    }

    public static ExileRegistryContainer<Stat> Stats() {
        return getRegistry(ExileRegistryTypes.STAT);
    }

    public static ExileRegistryContainer<BaseStatsConfig> BaseStats() {
        return getRegistry(ExileRegistryTypes.BASE_STATS);
    }

    public static List<ExileRegistryContainer> getAllRegistries() {
        return new ArrayList<>(SERVER.values());
    }

    public static ExileRegistryContainer getRegistry(ExileRegistryTypes type) {
        return SERVER.get(type);
    }

    public static ExileRegistry get(ExileRegistryTypes type, String guid) {
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

        List<ExileRegistryTypes> list = ExileRegistryTypes.getInRegisterOrder(sync);

        list.forEach(x -> getRegistry(x).sendUpdatePacket(player));
    }

    public static void checkGuidValidity() {

        SERVER.values()
            .forEach(c -> c.getAllIncludingSeriazable()
                .forEach(x -> {
                    ExileRegistry entry = (ExileRegistry) x;
                    if (!entry.isGuidFormattedCorrectly()) {
                        throw new RuntimeException(entry.getInvalidGuidMessage());
                    }
                }));

    }

    public static void unregisterInvalidEntries() {

        System.out.println("Starting Age of Exile Registry auto validation.");

        List<ExileRegistry> invalid = new ArrayList<>();

        SERVER.values()
            .forEach(c -> c.getList()
                .forEach(x -> {
                    ExileRegistry entry = (ExileRegistry) x;
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
        new StatsRegister().registerAll();// STATS MUST BE INIT FIRST
        // should be at least
        new CurrencyItems().registerAll();
    }

    private static void addRegistry(ExileRegistryContainer cont) {
        SERVER.put(cont.getType(), cont);
    }

    public static void initRegistries() {
        SERVER = new HashMap<>();

        ExileRegistryTypes.init();

        // data pack ones
        addRegistry(new RarityRegistryContainer<>(ExileRegistryTypes.GEAR_RARITY, new GearRarity()).setIsDatapack());
        addRegistry(new RarityRegistryContainer<MobRarity>(ExileRegistryTypes.MOB_RARITY, null).setIsDatapack());
        addRegistry(new ExileRegistryContainer<>(ExileRegistryTypes.GEAR_SLOT, new GearSlot("", 0)).setIsDatapack());
        addRegistry(new ExileRegistryContainer<>(ExileRegistryTypes.GEAR_RARITY_GROUP, null).setIsDatapack());
        addRegistry(new ExileRegistryContainer<>(ExileRegistryTypes.GEAR_TYPE, null).setIsDatapack());
        addRegistry(new ExileRegistryContainer<>(ExileRegistryTypes.EXILE_EFFECT, null).setIsDatapack());
        addRegistry(new ExileRegistryContainer<>(ExileRegistryTypes.TIER, new Tier(0)).setIsDatapack());
        addRegistry(new ExileRegistryContainer<>(ExileRegistryTypes.AFFIX, EmptyAffix.getInstance()).setIsDatapack());
        addRegistry(new ExileRegistryContainer<>(ExileRegistryTypes.MOB_AFFIX, new MobAffix("empty", "empty", Formatting.AQUA)).setIsDatapack());
        addRegistry(new ExileRegistryContainer<>(ExileRegistryTypes.UNIQUE_GEAR, null).setIsDatapack());
        addRegistry(new ExileRegistryContainer<>(ExileRegistryTypes.GEM, null).setIsDatapack());
        addRegistry(new ExileRegistryContainer<>(ExileRegistryTypes.RUNE, null).setIsDatapack());
        addRegistry(new ExileRegistryContainer<>(ExileRegistryTypes.RUNEWORD, null).setIsDatapack());
        addRegistry(new ExileRegistryContainer<>(ExileRegistryTypes.SPELL, Spell.SERIALIZER).setIsDatapack());
        addRegistry(new ExileRegistryContainer<>(ExileRegistryTypes.PERK, null).setIsDatapack());
        addRegistry(new ExileRegistryContainer<>(ExileRegistryTypes.SPELL_SCHOOL, null).setIsDatapack());
        addRegistry(new ExileRegistryContainer<>(ExileRegistryTypes.FAVOR_RANK, null).setIsDatapack());
        addRegistry(new ExileRegistryContainer<>(ExileRegistryTypes.SALVAGE_OUTPUT, null).setIsDatapack());
        addRegistry(new ExileRegistryContainer<>(ExileRegistryTypes.PLAYER_SKILLS, null).setIsDatapack());
        addRegistry(new ExileRegistryContainer<>(ExileRegistryTypes.BASE_STATS, null).setIsDatapack());
        addRegistry(new ExileRegistryContainer<>(ExileRegistryTypes.SKILL_GEM, null).setIsDatapack());
        addRegistry(new ExileRegistryContainer<>(ExileRegistryTypes.SKILL_GEM_RARITY, null).setIsDatapack());
        addRegistry(new ExileRegistryContainer<>(ExileRegistryTypes.RANDOM_SKILL_GEM_STATS, null).setIsDatapack());
        addRegistry(new ExileRegistryContainer<>(ExileRegistryTypes.GAME_BALANCE, new GameBalanceConfig()).setIsDatapack());
        addRegistry(new ExileRegistryContainer<>(ExileRegistryTypes.SALVAGE_RECIPE, null).setIsDatapack());
        addRegistry(new ExileRegistryContainer<>(ExileRegistryTypes.RACES, new PlayerRace()).setIsDatapack());
        addRegistry(new ExileRegistryContainer<>(ExileRegistryTypes.CRAFTING_REQ, new CraftingReq()).setIsDatapack());
        addRegistry(new ExileRegistryContainer<>(ExileRegistryTypes.SCROLL_BUFFS, new ScrollBuff()).setIsDatapack());
        addRegistry(new ExileRegistryContainer<>(ExileRegistryTypes.VALUE_CALC, new ValueCalculation()).setIsDatapack());
        addRegistry(new ExileRegistryContainer<>(ExileRegistryTypes.DUNGEON_MOB_LIST, new DungeonMobList()).setIsDatapack());
        addRegistry(new ExileRegistryContainer<>(ExileRegistryTypes.STAT_EFFECT, null).setIsDatapack());
        addRegistry(new ExileRegistryContainer<>(ExileRegistryTypes.STAT_CONDITION, null).setIsDatapack());
        addRegistry(new ExileRegistryContainer<>(ExileRegistryTypes.GEAR_SET, new GearSet()).setIsDatapack());
        addRegistry(new ExileRegistryContainer<>(ExileRegistryTypes.COMPATIBLE_ITEM,
            CompatibleItem.EMPTY).dontErrorIfEmpty()
            .setIsDatapack()
            .logAdditions());
        addRegistry(new ExileRegistryContainer<>(ExileRegistryTypes.DIMENSION_CONFIGS, DimensionConfig.DefaultExtra()
            ).logAdditions()
                .setIsDatapack()
                .dontErrorMissingEntriesOnAccess()
        );
        addRegistry(new ExileRegistryContainer<>(ExileRegistryTypes.ENTITY_CONFIGS, new EntityConfig("", 0)).logAdditions()
            .setIsDatapack());

        addRegistry(new ExileRegistryContainer<>(ExileRegistryTypes.STAT, EmptyStat.getInstance()));
        addRegistry(new ExileRegistryContainer<>(ExileRegistryTypes.CURRENCY_ITEMS, new OrbOfTransmutationItem()));
    }

}
