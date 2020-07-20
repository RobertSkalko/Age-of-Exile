package com.robertx22.exiled_lib.registry;

import com.google.common.collect.Lists;
import com.robertx22.exiled_lib.registry.empty_entries.EmptyAffix;
import com.robertx22.exiled_lib.registry.empty_entries.EmptyBaseGearType;
import com.robertx22.exiled_lib.registry.empty_entries.EmptySpell;
import com.robertx22.exiled_lib.registry.empty_entries.EmptyStat;
import com.robertx22.mine_and_slash.capability.entity.EntityCap;
import com.robertx22.mine_and_slash.database.data.DimensionConfig;
import com.robertx22.mine_and_slash.database.data.EntityConfig;
import com.robertx22.mine_and_slash.database.data.affixes.Affix;
import com.robertx22.mine_and_slash.database.data.compatible_item.CompatibleItem;
import com.robertx22.mine_and_slash.database.data.currency.OrbOfTransmutationItem;
import com.robertx22.mine_and_slash.database.data.currency.base.CurrencyItem;
import com.robertx22.mine_and_slash.database.data.gearitemslots.bases.BaseGearType;
import com.robertx22.mine_and_slash.database.data.mob_affixes.base.MobAffix;
import com.robertx22.mine_and_slash.database.data.spells.spell_classes.bases.BaseSpell;
import com.robertx22.mine_and_slash.database.data.stats.Stat;
import com.robertx22.mine_and_slash.database.data.tiers.base.Tier;
import com.robertx22.mine_and_slash.database.data.tiers.impl.TierOne;
import com.robertx22.mine_and_slash.database.data.unique_items.IUnique;
import com.robertx22.mine_and_slash.database.registrators.*;
import com.robertx22.mine_and_slash.datapacks.bases.ISerializedRegistryEntry;
import com.robertx22.mine_and_slash.mmorpg.Packets;
import com.robertx22.mine_and_slash.uncommon.utilityclasses.MapManager;
import com.robertx22.mine_and_slash.vanilla_mc.packets.RegistryPacket;
import com.robertx22.mine_and_slash.vanilla_mc.potion_effects.bases.BasePotionEffect;
import net.minecraft.entity.LivingEntity;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.registry.Registry;
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
        String id = MapManager.getResourceLocation(world.getWorld())
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

    public static SlashRegistryContainer<BasePotionEffect> PotionEffects() {
        return getRegistry(SlashRegistryType.EFFECT);
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

        System.out.println("Starting Mine and Slash Registry auto validation.");

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
            System.out.println(invalid.size() + " Mine and Slash entries are INVALID!");
        }
    }

    private static void registerFromAllInits() {

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

    }

    private static void addRegistry(SlashRegistryContainer cont) {
        SERVER.put(cont.getType(), cont);
    }

    public static void initRegistries() {
        SERVER = new HashMap<>();

        // data pack ones
        addRegistry(new SlashRegistryContainer<BaseGearType>(SlashRegistryType.GEAR_TYPE, new EmptyBaseGearType()).isDatapack());
        addRegistry(new SlashRegistryContainer<Tier>(SlashRegistryType.TIER, new TierOne()).isDatapack());
        addRegistry(new SlashRegistryContainer<Affix>(SlashRegistryType.AFFIX, EmptyAffix.getInstance()).isDatapack());
        addRegistry(new SlashRegistryContainer<MobAffix>(SlashRegistryType.MOB_AFFIX, MobAffixes.EMPTY).isDatapack());
        addRegistry(new SlashRegistryContainer<IUnique>(SlashRegistryType.UNIQUE_GEAR, null).isDatapack());
        addRegistry(new SlashRegistryContainer<CompatibleItem>(SlashRegistryType.COMPATIBLE_ITEM,
            CompatibleItem.EMPTY).dontErrorIfEmpty()
            .isDatapack()
            .logAdditions());
        addRegistry(new SlashRegistryContainer<DimensionConfig>(SlashRegistryType.DIMENSION_CONFIGS, DimensionConfig.DefaultExtra()
            ).logAdditions()
                .isDatapack()
                .dontErrorMissingEntriesOnAccess()
        );
        addRegistry(new SlashRegistryContainer<EntityConfig>(SlashRegistryType.ENTITY_CONFIGS, new EntityConfig("", 0)).logAdditions()
            .isDatapack());

        // data pack ones
        addRegistry(new SlashRegistryContainer<Stat>(SlashRegistryType.STAT, EmptyStat.getInstance()));
        addRegistry(new SlashRegistryContainer<BaseSpell>(SlashRegistryType.SPELL, new EmptySpell()));
        addRegistry(new SlashRegistryContainer<CurrencyItem>(SlashRegistryType.CURRENCY_ITEMS, new OrbOfTransmutationItem()));
        addRegistry(new SlashRegistryContainer<BasePotionEffect>(SlashRegistryType.EFFECT, null));
    }

}
