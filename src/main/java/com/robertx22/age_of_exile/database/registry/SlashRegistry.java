package com.robertx22.age_of_exile.database.registry;

import com.google.common.collect.Lists;
import com.robertx22.age_of_exile.aoe_data.exile_effects.adders.ExileEffects;
import com.robertx22.age_of_exile.capability.entity.EntityCap;
import com.robertx22.age_of_exile.database.IByteBuf;
import com.robertx22.age_of_exile.database.data.DimensionConfig;
import com.robertx22.age_of_exile.database.data.EntityConfig;
import com.robertx22.age_of_exile.database.data.affixes.Affix;
import com.robertx22.age_of_exile.database.data.compatible_item.CompatibleItem;
import com.robertx22.age_of_exile.database.data.currency.OrbOfTransmutationItem;
import com.robertx22.age_of_exile.database.data.currency.base.CurrencyItem;
import com.robertx22.age_of_exile.database.data.exile_effects.ExileEffect;
import com.robertx22.age_of_exile.database.data.gear_slots.GearSlot;
import com.robertx22.age_of_exile.database.data.gear_types.bases.BaseGearType;
import com.robertx22.age_of_exile.database.data.gems.Gem;
import com.robertx22.age_of_exile.database.data.mob_affixes.MobAffix;
import com.robertx22.age_of_exile.database.data.perks.Perk;
import com.robertx22.age_of_exile.database.data.runes.Rune;
import com.robertx22.age_of_exile.database.data.runewords.RuneWord;
import com.robertx22.age_of_exile.database.data.spell_modifiers.SpellModifier;
import com.robertx22.age_of_exile.database.data.spell_schools.SpellSchool;
import com.robertx22.age_of_exile.database.data.spells.components.Spell;
import com.robertx22.age_of_exile.database.data.stats.Stat;
import com.robertx22.age_of_exile.database.data.tiers.base.Tier;
import com.robertx22.age_of_exile.database.data.tiers.impl.TierOne;
import com.robertx22.age_of_exile.database.data.unique_items.UniqueGear;
import com.robertx22.age_of_exile.database.data.unique_items.UniqueGearReg;
import com.robertx22.age_of_exile.database.registrators.*;
import com.robertx22.age_of_exile.database.registry.empty_entries.EmptyAffix;
import com.robertx22.age_of_exile.database.registry.empty_entries.EmptyBaseGearType;
import com.robertx22.age_of_exile.database.registry.empty_entries.EmptyStat;
import com.robertx22.age_of_exile.datapacks.bases.ISerializable;
import com.robertx22.age_of_exile.datapacks.bases.ISerializedRegistryEntry;
import com.robertx22.age_of_exile.mmorpg.MMORPG;
import com.robertx22.age_of_exile.saveclasses.ListStringData;
import com.robertx22.age_of_exile.uncommon.utilityclasses.MapManager;
import com.robertx22.age_of_exile.vanilla_mc.packets.registry.EfficientRegistryPacket;
import com.robertx22.age_of_exile.vanilla_mc.packets.registry.RegistryPacket;
import com.robertx22.library_of_exile.main.Packets;
import net.minecraft.entity.LivingEntity;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

public class SlashRegistry {

    private static HashMap<SlashRegistryType, SlashRegistryContainer> SERVER = new HashMap<>();
    private static HashMap<SlashRegistryType, SlashRegistryContainer> BACKUP = new HashMap<>();

    public static boolean areDatapacksLoaded(World world) {
        return SlashRegistryType.getInRegisterOrder(SyncTime.ON_LOGIN)
            .stream()
            .allMatch(x -> SlashRegistry.getRegistry(x)
                .isRegistrationDone());
    }

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

    public static SlashRegistryContainer<UniqueGear> UniqueGears() {
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

    public static SlashRegistryContainer<ExileEffect> ExileEffects() {
        return getRegistry(SlashRegistryType.EXILE_EFFECT);
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

    public static SlashRegistryContainer<Spell> Spells() {
        return getRegistry(SlashRegistryType.SPELL);
    }

    public static SlashRegistryContainer<MobAffix> MobAffixes() {
        return getRegistry(SlashRegistryType.MOB_AFFIX);
    }

    public static SlashRegistryContainer<RuneWord> Runewords() {
        return getRegistry(SlashRegistryType.RUNEWORD);
    }

    public static SlashRegistryContainer<SpellModifier> SpellModifiers() {
        return getRegistry(SlashRegistryType.SPELL_MODIFIER);
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

    static HashMap<SlashRegistryType, List<ListStringData>> cachedRegistryPackets = new HashMap<>();

    private static List<ListStringData> getDataFor(SlashRegistryType type) {
        if (!cachedRegistryPackets.containsKey(type)) {

            SlashRegistryContainer reg = SlashRegistry.getRegistry(type);

            if (reg.isEmpty()) {
                SlashRegistry.restoreBackup();
            }

            List<ISerializedRegistryEntry> items = reg.getFromDatapacks();

            if (items.isEmpty()) {
                throw new RuntimeException(type.name() + " Registry is empty on the server when trying to send registry packet!");
            }

            new ListStringData(items
                .stream()
                .map(x -> ((ISerializable) x).toJsonString())
                .collect(Collectors.toList()));

            List<ListStringData> list = new ArrayList<>();
            if (items.size() < 100) {
                list.add(new ListStringData(items
                    .stream()
                    .map(x -> ((ISerializable) x).toJsonString())
                    .collect(Collectors.toList())));
            } else {
                for (List<ISerializedRegistryEntry> part : Lists.partition(items, 100)) {
                    list.add(new ListStringData(part
                        .stream()
                        .map(x -> ((ISerializable) x).toJsonString())
                        .collect(Collectors.toList())));
                }
            }
            cachedRegistryPackets.put(type, list);
        }

        return cachedRegistryPackets.get(type);
    }

    public static void sendPacketsToClient(ServerPlayerEntity player, SyncTime sync) {
        SlashRegistryType.getInRegisterOrder(sync)
            .forEach(x -> {
                if (x.getLoader() != null && x.ser != null) {
                    try {
                        if (x.ser instanceof IByteBuf) {
                            Packets.sendToClient(player, new EfficientRegistryPacket(x, SlashRegistry.getRegistry(x)
                                .getFromDatapacks()));
                        } else {
                            getDataFor(x).forEach(d -> {
                                Packets.sendToClient(player, new RegistryPacket(x, d));
                            });
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

        new Stats().registerAll();// STATS MUST BE INIT FIRST
        // should be at least
        new CurrencyItems().registerAll();

        new BaseGearTypes().registerAll();// used cus i register items based on seralizables..
        new UniqueGearReg().registerAll();// used cus i register items based on seralizables..

        if (MMORPG.RUN_DEV_TOOLS) {
            // as these only add serizables.
            // They shouldn't be needed at all to play the game.
            // If it errors without them, then that means i hardcoded something i shouldn't have

            new GearSlots().registerAll();
            new ExileEffects().registerAll();

            new Tiers().registerAll();

            new Spells().registerAll(); // some stats are based on spells, so spells go first

            new Prefixes().registerAll();
            new Suffixes().registerAll();

            new MobAffixes().registerAll();
            new DimConfigs().registerAll();
            new EntityConfigs().registerAll();

            new Gems().registerAll();
            new Runes().registerAll();
            new Runewords().registerAll();
            new SpellModifiers().registerAll();
            new Perks().registerAll();
        }

    }

    private static void addRegistry(SlashRegistryContainer cont) {
        SERVER.put(cont.getType(), cont);
    }

    public static void initRegistries() {
        SERVER = new HashMap<>();

        SlashRegistryType.init();

        // data pack ones
        addRegistry(new SlashRegistryContainer<>(SlashRegistryType.GEAR_SLOT, new GearSlot("", 0)).setIsDatapack());
        addRegistry(new SlashRegistryContainer<>(SlashRegistryType.GEAR_TYPE, new EmptyBaseGearType()).setIsDatapack());
        addRegistry(new SlashRegistryContainer<>(SlashRegistryType.EXILE_EFFECT, null).setIsDatapack());
        addRegistry(new SlashRegistryContainer<>(SlashRegistryType.TIER, new TierOne()).setIsDatapack());
        addRegistry(new SlashRegistryContainer<>(SlashRegistryType.AFFIX, EmptyAffix.getInstance()).setIsDatapack());
        addRegistry(new SlashRegistryContainer<>(SlashRegistryType.MOB_AFFIX, MobAffixes.EMPTY).setIsDatapack());
        addRegistry(new SlashRegistryContainer<>(SlashRegistryType.UNIQUE_GEAR, null).setIsDatapack());
        addRegistry(new SlashRegistryContainer<>(SlashRegistryType.GEM, null).setIsDatapack());
        addRegistry(new SlashRegistryContainer<>(SlashRegistryType.RUNE, null).setIsDatapack());
        addRegistry(new SlashRegistryContainer<>(SlashRegistryType.RUNEWORD, null).setIsDatapack());
        addRegistry(new SlashRegistryContainer<>(SlashRegistryType.SPELL, Spell.SERIALIZER).setIsDatapack());
        addRegistry(new SlashRegistryContainer<>(SlashRegistryType.PERK, null).setIsDatapack());
        addRegistry(new SlashRegistryContainer<>(SlashRegistryType.SPELL_MODIFIER, null).setIsDatapack());
        addRegistry(new SlashRegistryContainer<>(SlashRegistryType.SPELL_SCHOOL, null).setIsDatapack());
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
