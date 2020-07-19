package com.robertx22.mine_and_slash.database.data.requirements;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.robertx22.mine_and_slash.data_generation.JsonUtils;
import com.robertx22.mine_and_slash.database.data.gearitemslots.bases.BaseGearType;
import com.robertx22.mine_and_slash.database.data.requirements.bases.BaseRequirement;
import com.robertx22.mine_and_slash.database.data.requirements.bases.GearRequestedFor;
import com.robertx22.mine_and_slash.database.data.stats.Stat;
import com.robertx22.exiled_lib.registry.SlashRegistry;
import com.robertx22.mine_and_slash.saveclasses.gearitem.gear_bases.TooltipInfo;
import com.robertx22.mine_and_slash.uncommon.wrappers.SText;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import net.minecraft.text.MutableText;
import net.minecraft.util.Formatting;

public class SlotRequirement extends BaseRequirement<SlotRequirement> {

    public List<BaseGearType> slots = new ArrayList<>();

    public SlotRequirement() {

    }

    private SlotRequirement(BaseGearType slot) {
        this.slots.add(slot);
    }

    private SlotRequirement(List<BaseGearType> slots) {
        this.slots.addAll(slots);
    }

    @Override
    public boolean meetsRequierment(GearRequestedFor requested) {

        for (BaseGearType slot : slots) {
            if (requested.forSlot.GUID()
                .equals(slot.GUID())) {
                return true;
            }
        }
        return false;

    }

    public static SlotRequirement everythingBesides(BaseGearType.SlotFamily type) {
        return new SlotRequirement(SlashRegistry.GearTypes()
            .getFiltered(x -> x.family() != type));

    }

    public static SlotRequirement of(BaseGearType.SlotFamily type) {
        return new SlotRequirement(SlashRegistry.GearTypes()
            .getFiltered(x -> x.family() == type));

    }

    public SlotRequirement plus(Predicate<BaseGearType> pred) {
        this.slots.addAll(SlashRegistry.GearTypes()
            .getFilterWrapped(pred).list);
        return this;
    }

    public static SlotRequirement of(Predicate<BaseGearType> pred) {
        return new SlotRequirement(SlashRegistry.GearTypes()
            .getFilterWrapped(pred).list);
    }

    public static SlotRequirement hasBaseStat(Stat stat) {
        return new SlotRequirement(SlashRegistry.GearTypes()
            .getFiltered(x -> x.baseStats()
                .stream()
                .anyMatch(s -> s.stat == stat.GUID())));

    }

    public static SlotRequirement of(BaseGearType.SlotTag tag) {
        return new SlotRequirement(SlashRegistry.GearTypes()
            .getFiltered(x -> x.getTags()
                .contains(tag)));

    }

    @Override
    public String getJsonID() {
        return "slot_req";
    }

    @Override
    public JsonObject toJson() {
        JsonObject json = new JsonObject();
        json.add(
            "slots",
            JsonUtils.stringListToJsonArray(slots.stream()
                .map(x -> x.GUID())
                .collect(Collectors.toList()))
        );
        return json;
    }

    @Override
    public SlotRequirement fromJson(JsonObject json) {

        try {
            SlotRequirement newobj = new SlotRequirement();

            JsonArray array = json.getAsJsonArray("slots");

            newobj.slots = JsonUtils.jsonArrayToStringList(array)
                .stream()
                .map(x -> SlashRegistry.GearTypes()
                    .get(x))
                .collect(Collectors.toList());

            return newobj;
        } catch (Exception e) {
            return null;
        }

    }

    @Override
    public List<MutableText> GetTooltipString(TooltipInfo info) {

        List<MutableText> list = new ArrayList<>();

        list.add(new SText(Formatting.GREEN + "Allowed on: "));

        List<BaseGearType> copy = new ArrayList<>(this.slots);

        MutableText comp = new SText(Formatting.RED + "");

        List<BaseGearType> armors = SlashRegistry.GearTypes()
            .getFiltered(x -> x.family()
                .equals(BaseGearType.SlotFamily.Armor));
        if (copy.containsAll(armors)) {
            copy.removeIf(x -> x.family()
                .equals(BaseGearType.SlotFamily.Armor));
            comp.append(" ")
                .append(new SText("All Armors"));
        }

        List<BaseGearType> weapons = SlashRegistry.GearTypes()
            .getFiltered(x -> x.family()
                .equals(BaseGearType.SlotFamily.Weapon));
        if (copy.containsAll(weapons)) {
            copy.removeIf(x -> x.family()
                .equals(BaseGearType.SlotFamily.Weapon));
            comp.append(" ")
                .append(new SText("All Weapons"));
        }

        List<BaseGearType> jewerly = SlashRegistry.GearTypes()
            .getFiltered(x -> x.family()
                .equals(BaseGearType.SlotFamily.Jewelry));
        if (copy.containsAll(jewerly)) {
            copy.removeIf(x -> x.family()
                .equals(BaseGearType.SlotFamily.Jewelry));
            comp.append(" ")
                .append(new SText("All Jewerly"));
        }
        copy.forEach(x -> {
            comp.append(" ")
                .append(x.locName());

        });

        list.add(comp);

        return list;
    }
}
