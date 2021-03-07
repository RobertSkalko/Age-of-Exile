package com.robertx22.age_of_exile.database.data.runewords;

import com.robertx22.age_of_exile.aoe_data.datapacks.bases.ISerializedRegistryEntry;
import com.robertx22.age_of_exile.database.IByteBuf;
import com.robertx22.age_of_exile.database.data.IAutoGson;
import com.robertx22.age_of_exile.database.data.StatModifier;
import com.robertx22.age_of_exile.database.data.gear_types.bases.BaseGearType;
import com.robertx22.age_of_exile.database.data.runes.Rune;
import com.robertx22.age_of_exile.database.registry.Database;
import com.robertx22.age_of_exile.database.registry.SlashRegistryType;
import com.robertx22.age_of_exile.mmorpg.Ref;
import com.robertx22.age_of_exile.saveclasses.gearitem.gear_parts.SocketData;
import com.robertx22.age_of_exile.saveclasses.item_classes.GearItemData;
import com.robertx22.age_of_exile.uncommon.interfaces.IAutoLocName;
import com.robertx22.age_of_exile.vanilla_mc.items.gemrunes.RuneItem;
import net.minecraft.network.PacketByteBuf;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class RuneWord implements IByteBuf<RuneWord>, IAutoGson<RuneWord>, ISerializedRegistryEntry<RuneWord>, IAutoLocName {
    public static RuneWord SERIALIZER = new RuneWord();

    public String identifier = "";
    public List<StatModifier> stats = new ArrayList<>();
    public List<String> runes_needed = new ArrayList<>();
    public BaseGearType.SlotFamily family = BaseGearType.SlotFamily.NONE;

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

    public boolean canItemHave(GearItemData gear) {

        int minlvl = runes_needed.stream()
            .map(x -> Database.Runes()
                .get(x))
            .min(Comparator.comparingInt(x -> x.getReqLevel()))
            .get()
            .getReqLevel();

        if (minlvl > gear.lvl) {
            return false;
        }

        List<String> runes = new ArrayList<>();

        gear.sockets.sockets.forEach(x -> runes.add(x.rune));

        int matches = 0;

        for (String rune : runes) {
            if (rune.equals(runes_needed.get(matches))) {
                matches++;
            } else {
                matches = 0;
            }
        }

        if (matches + gear.sockets.getEmptySockets() < runes_needed.size()) {
            return false;
        }

        return gear.GetBaseGearType()
            .family()
            .equals(this.family);
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

    @Override
    public Class<RuneWord> getClassForSerialization() {
        return RuneWord.class;
    }

    @Override
    public SlashRegistryType getSlashRegistryType() {
        return SlashRegistryType.RUNEWORD;
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
        return Ref.MODID + ".runeword." + formattedGUID();
    }

    @Override
    public String locNameForLangFile() {
        return loc_name;
    }

}
