package com.robertx22.age_of_exile.database.data.runewords;

import com.robertx22.age_of_exile.database.data.IAutoGson;
import com.robertx22.age_of_exile.database.data.StatModifier;
import com.robertx22.age_of_exile.database.data.gear_types.bases.BaseGearType;
import com.robertx22.age_of_exile.database.registry.SlashRegistry;
import com.robertx22.age_of_exile.database.registry.SlashRegistryType;
import com.robertx22.age_of_exile.datapacks.bases.ISerializedRegistryEntry;
import com.robertx22.age_of_exile.saveclasses.gearitem.gear_parts.SocketData;
import com.robertx22.age_of_exile.saveclasses.item_classes.GearItemData;
import com.robertx22.age_of_exile.vanilla_mc.items.gemrunes.RuneItem;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class RuneWord implements IAutoGson<RuneWord>, ISerializedRegistryEntry<RuneWord> {
    public static RuneWord SERIALIZER = new RuneWord();

    public String identifier = "";

    List<StatModifier> stats = new ArrayList<>();

    List<String> runes_needed = new ArrayList<>();

    public BaseGearType.SlotFamily family;

    public String loc_name = "";

    public boolean HasRuneWord(GearItemData gear) {

        int index = 0;
        for (SocketData socket : gear.sockets.sockets) {

            if (socket.rune_id.equals(runes_needed.get(index))) {
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
            .map(x -> SlashRegistry.Runes()
                .get(x))
            .min(Comparator.comparingInt(x -> x.getReqLevel()))
            .get()
            .getReqLevel();

        if (minlvl > gear.level) {
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
}
