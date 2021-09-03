package com.robertx22.age_of_exile.database.data.runewords;

import com.robertx22.age_of_exile.database.data.gear_types.bases.BaseGearType;
import com.robertx22.age_of_exile.database.registry.ExileDB;
import com.robertx22.age_of_exile.database.registry.ExileRegistryTypes;
import com.robertx22.library_of_exile.registry.ExileRegistryType;
import com.robertx22.library_of_exile.registry.IAutoGson;
import com.robertx22.library_of_exile.registry.JsonExileRegistry;
import net.minecraft.item.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class RuneWord implements IAutoGson<RuneWord>, JsonExileRegistry<RuneWord> {
    public static RuneWord SERIALIZER = new RuneWord();

    public String id = "";
    public String uniq_id = "";
    public List<String> runes = new ArrayList<>();
    public List<String> slots = new ArrayList<>();

    @Override
    public ExileRegistryType getExileRegistryType() {
        return ExileRegistryTypes.RUNEWORDS;
    }

    @Override
    public String GUID() {
        return id;
    }

    @Override
    public Class<RuneWord> getClassForSerialization() {
        return RuneWord.class;
    }

    public boolean canApplyOnItem(ItemStack stack) {

        if (slots.stream()
            .noneMatch(e -> {
                return BaseGearType.isGearOfThisType(ExileDB.GearSlots()
                    .get(e), stack.getItem());
            })) {
            return false;
        }

        return true;
    }

    public boolean runesCanActivateRuneWord(List<String> craftrunes) {

        List<String> copy = new ArrayList<>(craftrunes);

        boolean nope = false;
        for (String runeid : runes) {
            if (copy.contains(runeid)) {
                copy.remove(runeid);
            } else {
                nope = true;
            }
        }
        if (nope) {
            return false;
        }
        if (copy.isEmpty()) {
            return true;
        }
        return false;
    }

    @Override
    public int Weight() {
        return 1000;
    }
}
