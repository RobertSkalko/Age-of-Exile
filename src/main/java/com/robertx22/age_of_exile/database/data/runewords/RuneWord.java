package com.robertx22.age_of_exile.database.data.runewords;

import com.robertx22.age_of_exile.database.data.StatModifier;
import com.robertx22.age_of_exile.database.data.gear_slots.GearSlot;
import com.robertx22.age_of_exile.database.data.gear_types.bases.BaseGearType;
import com.robertx22.age_of_exile.database.data.runes.Rune;
import com.robertx22.age_of_exile.database.registry.ExileDB;
import com.robertx22.age_of_exile.database.registry.ExileRegistryTypes;
import com.robertx22.age_of_exile.mmorpg.Ref;
import com.robertx22.age_of_exile.saveclasses.gearitem.gear_parts.SocketData;
import com.robertx22.age_of_exile.saveclasses.item_classes.GearItemData;
import com.robertx22.age_of_exile.uncommon.interfaces.IAutoLocName;
import com.robertx22.age_of_exile.vanilla_mc.items.gemrunes.RuneItem;
import com.robertx22.library_of_exile.registry.DataGenKey;
import com.robertx22.library_of_exile.registry.ExileRegistryType;
import com.robertx22.library_of_exile.registry.IAutoGson;
import com.robertx22.library_of_exile.registry.JsonExileRegistry;
import com.robertx22.library_of_exile.registry.serialization.IByteBuf;
import net.minecraft.network.PacketByteBuf;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class RuneWord implements IByteBuf<RuneWord>, IAutoGson<RuneWord>, JsonExileRegistry<RuneWord>, IAutoLocName {
    public static RuneWord SERIALIZER = new RuneWord();

    public String identifier = "";
    public List<StatModifier> stats = new ArrayList<>();
    public List<String> runes_needed = new ArrayList<>();
    public BaseGearType.SlotFamily family = BaseGearType.SlotFamily.NONE;
    public List<String> gear_slots = new ArrayList<>();

    public transient String loc_name = "";

    @Override
    public RuneWord getFromBuf(PacketByteBuf buf) {
        RuneWord word = new RuneWord();

        word.identifier = buf.readString(500);

        int statAmount = buf.readInt();
        for (int i = 0; i < statAmount; i++) {
            word.stats.add(StatModifier.EMPTY.getFromBuf(buf));
        }
        int count = buf.readInt();
        for (int i = 0; i < count; i++) {
            word.runes_needed.add(buf.readString(10));
        }
        int c = buf.readInt();
        for (int i = 0; i < c; i++) {
            word.gear_slots.add(buf.readString(100));
        }
        word.family = BaseGearType.SlotFamily.valueOf(buf.readString(500));

        return word;
    }

    @Override
    public void toBuf(PacketByteBuf buf) {
        buf.writeString(identifier, 500);

        buf.writeInt(stats.size());
        stats.forEach(x -> x.toBuf(buf));
        buf.writeInt(runes_needed.size());
        runes_needed.forEach(x -> buf.writeString(x, 10));
        buf.writeInt(gear_slots.size());
        gear_slots.forEach(x -> buf.writeString(x, 100));

        buf.writeString(family.name(), 500);
    }

    public boolean containsRune(Rune rune) {
        return this.runes_needed.stream()
            .anyMatch(x -> x.equals(rune.identifier));
    }

    public boolean HasRuneWord(GearItemData gear) {

        if (!canItemHave(gear)) {
            return false;
        }

        int index = 0;
        for (SocketData socket : gear.sockets.sockets) {

            if (socket.rune.equals(runes_needed.get(index))) {
                index++;
                if (index >= runes_needed.size()) {
                    return true;
                }
            } else {
                if (index != 0) {
                    return false;
                }
            }

        }

        return false;
    }

    @Override
    public int Weight() {
        return 1000;
    }

    public boolean canItemHave(GearItemData gear) {

        try {
            int minlvl = runes_needed.stream()
                .map(x -> ExileDB.Runes()
                    .get(x))
                .min(Comparator.comparingInt(x -> x.getReqLevel()))
                .get()
                .getReqLevel();

            if (minlvl > gear.lvl) {
                return false;
            }

            List<String> runes = new ArrayList<>();

            gear.sockets.sockets.forEach(x -> {
                if (x.isRune()) {
                    runes.add(x.rune);
                }
            });

            int matches = 0;

            for (String rune : runes) {
                if (runes_needed.size() > matches) {
                    if (rune.equals(runes_needed.get(matches))) {
                        matches++;
                    } else {
                        matches = 0;
                    }
                }
            }

            if (matches + gear.sockets.getEmptySockets() < runes_needed.size()) {
                return false;
            }

            if (this.family != BaseGearType.SlotFamily.NONE) {
                return gear.GetBaseGearType()
                    .family() == family;
            } else {
                return gear_slots.stream()
                    .anyMatch(x -> {
                        return gear.GetBaseGearType().gear_slot.equals(x);
                    });
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public static RuneWord create(String id, String locname, BaseGearType.SlotFamily family, List<StatModifier> stats, List<RuneItem.RuneType> runes_needed) {
        RuneWord word = new RuneWord();
        word.identifier = id;
        word.stats = stats;
        word.loc_name = locname;
        word.family = family;
        word.runes_needed = runes_needed.stream()
            .map(x -> x.id)
            .collect(Collectors.toList());
        return word;
    }

    public static RuneWord create(String id, String locname, String slot, List<StatModifier> stats, List<RuneItem.RuneType> runes_needed) {
        RuneWord word = create(id, locname, BaseGearType.SlotFamily.NONE, stats, runes_needed);
        word.gear_slots.add(slot);
        return word;
    }

    public static RuneWord create(String id, String locname, List<DataGenKey<GearSlot>> slots, List<StatModifier> stats, List<RuneItem.RuneType> runes_needed) {
        RuneWord word = create(id, locname, BaseGearType.SlotFamily.NONE, stats, runes_needed);
        slots.forEach(x -> word.gear_slots.add(x.GUID()));
        return word;
    }

    @Override
    public Class<RuneWord> getClassForSerialization() {
        return RuneWord.class;
    }

    @Override
    public ExileRegistryType getExileRegistryType() {
        return ExileRegistryTypes.RUNEWORD;
    }

    @Override
    public String GUID() {
        return identifier;
    }

    @Override
    public AutoLocGroup locNameGroup() {
        return AutoLocGroup.Rune_Words;
    }

    @Override
    public String locNameLangFileGUID() {
        return Ref.MODID + ".runeword." + GUID();
    }

    @Override
    public String locNameForLangFile() {
        return loc_name;
    }

}
