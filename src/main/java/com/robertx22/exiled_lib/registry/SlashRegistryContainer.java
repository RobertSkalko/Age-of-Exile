package com.robertx22.exiled_lib.registry;

import com.robertx22.mine_and_slash.config.forge.ModConfig;
import com.robertx22.mine_and_slash.mmorpg.MMORPG;
import com.robertx22.mine_and_slash.uncommon.utilityclasses.RandomUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class SlashRegistryContainer<C extends ISlashRegistryEntry> {

    private List<String> registersErrorsAlertedFor = new ArrayList<>();
    private List<String> accessorErrosAletedFor = new ArrayList<>();
    private List<String> wrongRegistryNames = new ArrayList<>();
    private List<String> emptyRegistries = new ArrayList<>();

    private boolean dataPacksAreRegistered = true;

    public SlashRegistryContainer<C> isDatapack() {
        this.dataPacksAreRegistered = false;
        return this;
    }

    private HashMap<String, C> serializables = new HashMap<>();

    public List<C> getSerializable() {
        return new ArrayList<>(serializables.values());
    }

    public List<C> getFromDatapacks() {
        return getList().stream()
            .filter(x -> x.isFromDatapack())
            .collect(Collectors.toList());
    }

    public SlashRegistryType getType() {
        return type;
    }

    public static void logRegistryError(String text) {

        try {
            throw new Exception("[Mine and Slash Registry Error]: " + text);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private SlashRegistryType type;
    private C emptyDefault;

    protected C getDefault() {
        return this.emptyDefault;
    }

    private HashMap<String, C> map = new HashMap<>();
    private boolean errorIfEmpty = true;
    private boolean logAdditionsToRegistry = false;
    private boolean logMissingEntryOnAccess = true;

    public SlashRegistryContainer logAdditions() {
        this.logAdditionsToRegistry = true;
        return this;
    }

    public void unRegister(ISlashRegistryEntry entry) {
        map.remove(entry.GUID());
    }

    public SlashRegistryContainer dontErrorMissingEntriesOnAccess() {
        this.logMissingEntryOnAccess = false;
        return this;
    }

    public SlashRegistryContainer dontErrorIfEmpty() {
        this.errorIfEmpty = false;
        return this;
    }

    public int getSize() {
        return map.size();
    }

    public SlashRegistryContainer(SlashRegistryType type, C emptyDefault) {
        this.type = type;
        this.emptyDefault = emptyDefault;
    }

    public void setDefault(C c) {
        this.emptyDefault = c;
    }

    private void tryLogEmptyRegistry() {
        if (errorIfEmpty) {
            if (map.isEmpty()) {
                if (this.dataPacksAreRegistered) { // dont error for client side stuff if datapacks have yet to arrive from packets
                    if (emptyRegistries.contains(this.type.id)) {

                        emptyRegistries.add(this.type.id);
                        logRegistryError(
                            "Slash Registry of type: " + this.type.toString() + " is empty, this is really bad!");
                    }
                }
            }
        }
    }

    public HashMap<String, C> getAll() {

        tryLogEmptyRegistry();

        return map;
    }

    public List<C> getList() {
        if (!map.isEmpty()) {
            return new ArrayList<C>(map.values());
        }
        if (MMORPG.RUN_DEV_TOOLS && !serializables.isEmpty()) {
            return new ArrayList<C>(serializables.values());
            // if called before datapacks load, get serializables. This should only be used in dev enviroment to generate data
            // this should allow multiple registries to depend on each other before datapacks load while they're in serializables list
        } else {
            this.tryLogEmptyRegistry();
        }

        return Arrays.asList();
    }

    public List<C> getAllIncludingSeriazable() {
        List<C> list = new ArrayList<C>(map.values());
        list.addAll(serializables.values());
        return list;
    }

    public void unregisterAllEntriesFromDatapacks() {
        new HashMap<String, C>(map).entrySet()
            .forEach(x -> {
                if (x.getValue()
                    .isFromDatapack()) {
                    map.remove(x.getKey());
                }
            });
    }

    public C get(String guid) {

        if (map.isEmpty() && !serializables.isEmpty()) {
            try {
                throw new Exception("Accessed slash registry earlier than datapacks are loaded, returning empty: " + guid);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return this.emptyDefault;

        }

        tryLogEmptyRegistry();

        if (guid == null || guid.isEmpty()) {
            return getDefault();
        }
        if (map.containsKey(guid)) {
            return map.get(guid);
        } else {
            if (logMissingEntryOnAccess) {
                if (accessorErrosAletedFor.contains(guid) == false) {
                    logRegistryError(
                        "GUID Error: " + guid + " of type: " + type.toString() + " doesn't exist. This is either " + "a removed/renamed old registry, or robertx22 forgot to include it in an " + "update" + ".");
                    accessorErrosAletedFor.add(guid);
                }
            }
            return getDefault();
        }
    }

    public FilterListWrap<C> getWrapped() {
        return new FilterListWrap<C>(this.map.values());
    }

    public FilterListWrap<C> getFilterWrapped(Predicate<C> pred) {
        return new FilterListWrap<C>(getFiltered(pred));
    }

    // just do gearsThatCanDoThis.and() .or() etc. if need multiple
    public List<C> getFiltered(Predicate<C> predicate) {
        return this.getList()
            .stream()
            .filter(predicate)
            .collect(Collectors.toList());
    }

    public C random() {
        return RandomUtils.weightedRandom(this.getList());
    }

    public boolean isRegistered(C c) {
        return isRegistered(c.GUID());
    }

    public boolean isRegistered(String guid) {
        return map.containsKey(guid);
    }

    // for mod addon devs if they want to overwrite some of my stuff
    public void registerOverride(C c) {

        if (isRegistered(c)) {
            System.out.println("[Mine and Slash Registry Note]: Overriding: " + c.GUID());
        }

        map.put(c.GUID(), c);

    }

    public void register(C c) {

        if (isRegistered(c)) {

            if (registersErrorsAlertedFor.contains(c.GUID()) == false) {
                logRegistryError("Key: " + c.GUID() + " has already been registered to: " + c.getSlashRegistryType()
                    .toString() + " registry.");
                registersErrorsAlertedFor.add(c.GUID());
            }

        } else {
            //String id = IGUID.getformattedString(c.GUID());

            tryLogAddition(c);
            map.put(c.GUID(), c);
        }

    }

    private void tryLogAddition(C c) {
        if (logAdditionsToRegistry && ModConfig.INSTANCE.Server.LOG_REGISTRY_ENTRIES.get()) {
            System.out.println(
                "[Mine and Slash Registry Addition]: " + c.GUID() + " to " + type.toString() + " registry");
        }

    }

    public void addSerializable(C entry) {
        this.serializables.put(entry.GUID(), entry);

    }

    public boolean isEmpty() {
        return map.isEmpty();
    }
}
