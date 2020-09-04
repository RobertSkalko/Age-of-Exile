package com.robertx22.age_of_exile.database.registry;

import com.google.common.collect.Lists;
import com.robertx22.age_of_exile.capability.entity.EntityCap;
import com.robertx22.age_of_exile.database.data.DimensionConfig;
import com.robertx22.age_of_exile.database.data.EntityConfig;
import com.robertx22.age_of_exile.database.data.affixes.Affix;
import com.robertx22.age_of_exile.database.data.compatible_item.CompatibleItem;
import com.robertx22.age_of_exile.database.data.currency.OrbOfTransmutationItem;
import com.robertx22.age_of_exile.database.data.currency.base.CurrencyItem;
import com.robertx22.age_of_exile.database.data.gear_slots.GearSlot;
import com.robertx22.age_of_exile.database.data.gear_types.bases.BaseGearType;
import com.robertx22.age_of_exile.database.data.gems.Gem;
import com.robertx22.age_of_exile.database.data.mob_affixes.base.MobAffix;
import com.robertx22.age_of_exile.database.data.perks.Perk;
import com.robertx22.age_of_exile.database.data.runes.Rune;
import com.robertx22.age_of_exile.database.data.runewords.RuneWord;
import com.robertx22.age_of_exile.database.data.spell_schools.SpellSchool;
import com.robertx22.age_of_exile.database.data.spells.spell_classes.bases.BaseSpell;
import com.robertx22.age_of_exile.database.data.stats.Stat;
import com.robertx22.age_of_exile.database.data.tiers.base.Tier;
import com.robertx22.age_of_exile.database.data.tiers.impl.TierOne;
import com.robertx22.age_of_exile.database.data.unique_items.IUnique;
import com.robertx22.age_of_exile.database.registrators.*;
import com.robertx22.age_of_exile.database.registry.empty_entries.EmptyAffix;
import com.robertx22.age_of_exile.database.registry.empty_entries.EmptyBaseGearType;
import com.robertx22.age_of_exile.database.registry.empty_entries.EmptySpell;
import com.robertx22.age_of_exile.database.registry.empty_entries.EmptyStat;
import com.robertx22.age_of_exile.datapacks.bases.ISerializedRegistryEntry;
import com.robertx22.age_of_exile.uncommon.utilityclasses.MapManager;
import com.robertx22.age_of_exile.vanilla_mc.packets.RegistryPacket;
import com.robertx22.library_of_exile.main.Packets;
import net.minecraft.entity.LivingEntity;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class SlashRegistry {

    private static HashMap<SlashRegistryType, SlashRegistryContainer> SERVER = new HashMap<>();
    private static HashMap<SlashRegistryType, SlashRegistryContainer> BACKUP = new HashMap<>();

    public static void backup() {
        BACKUP = new HashMap<>(SERVER);
    }

    public static void restoreBackup() {
        SERVER = new HashMap<>(BACKUP);
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

    public static SlashRegistryContainer<IUnique> UniqueGears() {
        return getRegistry(SlashRegistryType.UNIQUE_GEAR);
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

    public static SlashRegistryContainer<Gem> Gems() {
        return getRegistry(SlashRegistryType.GEM);
    }

    public static SlashRegistryContainer<SpellSchool> SpellSchools() {
        return getRegistry(SlashRegistryType.SPELL_SCHOOL);
    }

    public static SlashRegistryContainer<Perk> Perks() {
        return getRegistry(SlashRegistryType.PERK);
    }

    public static SlashRegistryContainer<Rune> Runes() {
        return getRegistry(SlashRegistryType.RUNE);
    }

    public static SlashRegistryContainer<Affix> Affixes() {
        return getRegistry(SlashRegistryType.AFFIX);
    }

    public static SlashRegistryContainer<Tier> Tiers() {
        return getRegistry(SlashRegistryType.TIER);
    }

    public static SlashRegistryContainer<BaseGearType> GearTypes() {
        return getRegistry(SlashRegistryType.GEAR_TYPE);
    }

    public static SlashRegistryContainer<BaseSpell> Spells() {
        return getRegistry(SlashRegistryType.SPELL);
    }

    public static SlashRegistryContainer<MobAffix> MobAffixes() {
        return getRegistry(SlashRegistryType.MOB_AFFIX);
    }

    public static SlashRegistryContainer<RuneWord> Runewords() {
        return getRegistry(SlashRegistryType.RUNEWORD);
    }

    public static SlashRegistryContainer<EntityConfig> EntityConfigs() {
        return getRegistry(SlashRegistryType.ENTITY_CONFIGS);
    }

    public static SlashRegistryContainer<Stat> Stats() {
        return getRegistry(SlashRegistryType.STAT);
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
            registerFromAllInits();
        } catch (ExceptionInInitializerError e) {
            // leave this, once this error happened and we don't know why. this is to know the cause if it happens again
            e.printStackTrace();
            e.getCause()
                .printStackTrace();
        }
    }

    public static void sendAllPacketsToClientOnLogin(ServerPlayerEntity player) {

        getAllRegistries()
            .forEach(x -> {
                if (x.getType()
                    .getSerializer() != null) {
                    try {

                        List<ISerializedRegistryEntry> list = x.getFromDatapacks();

                        if (list.size() == 0) {
                            throw new Exception("Registry empty: " + x.getType()
                                .name());
                        } else if (list.size() < 100) {
                            Packets.sendToClient(player, new RegistryPacket(x.getType(), list));
                        } else {
                            for (List<ISerializedRegistryEntry> part : Lists.partition(list, 100)) {
                                Packets.sendToClient(player, new RegistryPacket(x.getType(), part));
                            }

                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });

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
            System.out.println("All Mine and Slash registries appear valid.");
        } else {
            System.out.println(invalid.size() + " Age of Exile entries are INVALID!");
        }
    }

    private static void registerFromAllInits() {

        new GearSlots().registerAll();
        new Tiers().registerAll();

        new Spells().registerAll(); // some stats are based on spells, so spells go first
        new PotionEffects().registerAll();// some stats are based on effects ,so they go first

        new Stats().registerAll();// STATS MUST BE INIT before STATMODS  cus statmods ARE DERIVED FROM STATS, or
        // should be at least

        new BaseGearTypes().registerAll();

        new Prefixes().registerAll();
        new Suffixes().registerAll();

        new UniqueGears().registerAll();

        new MobAffixes().registerAll();
        new DimConfigs().registerAll();
        new EntityConfigs().registerAll();

        new CurrencyItems().registerAll();
        new Gems().registerAll();
        new Runes().registerAll();
        new Runewords().registerAll();

    }

    private static void addRegistry(SlashRegistryContainer cont) {
        SERVER.put(cont.getType(), cont);
    }

    public static void initRegistries() {
        SERVER = new HashMap<>();

        // data pack ones
        addRegistry(new SlashRegistryContainer<>(SlashRegistryType.GEAR_SLOT, new GearSlot("", 0)).isDatapack());
        addRegistry(new SlashRegistryContainer<>(SlashRegistryType.GEAR_TYPE, new EmptyBaseGearType()).isDatapack());
        addRegistry(new SlashRegistryContainer<>(SlashRegistryType.TIER, new TierOne()).isDatapack());
        addRegistry(new SlashRegistryContainer<>(SlashRegistryType.AFFIX, EmptyAffix.getInstance()).isDatapack());
        addRegistry(new SlashRegistryContainer<>(SlashRegistryType.MOB_AFFIX, MobAffixes.EMPTY).isDatapack());
        addRegistry(new SlashRegistryContainer<>(SlashRegistryType.UNIQUE_GEAR, null).isDatapack());
        addRegistry(new SlashRegistryContainer<>(SlashRegistryType.GEM, null).isDatapack());
        addRegistry(new SlashRegistryContainer<>(SlashRegistryType.RUNE, null).isDatapack());
        addRegistry(new SlashRegistryContainer<>(SlashRegistryType.RUNEWORD, null).isDatapack());
        addRegistry(new SlashRegistryContainer<>(SlashRegistryType.PERK, null).isDatapack());
        addRegistry(new SlashRegistryContainer<>(SlashRegistryType.SPELL_SCHOOL, null).isDatapack());
        addRegistry(new SlashRegistryContainer<>(SlashRegistryType.COMPATIBLE_ITEM,
            CompatibleItem.EMPTY).dontErrorIfEmpty()
            .isDatapack()
            .logAdditions());
        addRegistry(new SlashRegistryContainer<>(SlashRegistryType.DIMENSION_CONFIGS, DimensionConfig.DefaultExtra()
            ).logAdditions()
                .isDatapack()
                .dontErrorMissingEntriesOnAccess()
        );
        addRegistry(new SlashRegistryContainer<>(SlashRegistryType.ENTITY_CONFIGS, new EntityConfig("", 0)).logAdditions()
            .isDatapack());

        // data pack ones
        addRegistry(new SlashRegistryContainer<>(SlashRegistryType.STAT, EmptyStat.getInstance()));
        addRegistry(new SlashRegistryContainer<>(SlashRegistryType.SPELL, new EmptySpell()));
        addRegistry(new SlashRegistryContainer<>(SlashRegistryType.CURRENCY_ITEMS, new OrbOfTransmutationItem()));
        addRegistry(new SlashRegistryContainer<>(SlashRegistryType.EFFECT, null));
    }

}
