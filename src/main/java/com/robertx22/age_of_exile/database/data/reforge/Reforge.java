package com.robertx22.age_of_exile.database.data.reforge;

import com.robertx22.age_of_exile.database.all_keys.GearSlotKeys;
import com.robertx22.age_of_exile.database.all_keys.base.GearSlotKey;
import com.robertx22.age_of_exile.database.data.StatModifier;
import com.robertx22.age_of_exile.database.data.gear_slots.GearSlot;
import com.robertx22.age_of_exile.database.data.rarities.GearRarity;
import com.robertx22.age_of_exile.database.registry.ExileDB;
import com.robertx22.age_of_exile.database.registry.ExileRegistryTypes;
import com.robertx22.age_of_exile.mmorpg.SlashRef;
import com.robertx22.age_of_exile.mmorpg.registers.common.items.SlashItems;
import com.robertx22.age_of_exile.saveclasses.item_classes.GearItemData;
import com.robertx22.age_of_exile.uncommon.datasaving.Gear;
import com.robertx22.age_of_exile.uncommon.interfaces.IAutoLocName;
import com.robertx22.age_of_exile.uncommon.interfaces.data_items.IRarity;
import com.robertx22.library_of_exile.registry.ExileRegistryType;
import com.robertx22.library_of_exile.registry.IAutoGson;
import com.robertx22.library_of_exile.registry.JsonExileRegistry;
import com.robertx22.library_of_exile.text_wrapper.TextBuilder;
import com.robertx22.library_of_exile.text_wrapper.TooltipBuilder;
import com.robertx22.library_of_exile.text_wrapper.VanillaText;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.ITextComponent;

import java.util.ArrayList;
import java.util.List;

public class Reforge implements IAutoGson<Reforge>, JsonExileRegistry<Reforge>, IAutoLocName {
    public static Reforge SERIALIZER = new Reforge();

    public int weight = 1000;
    public String id = "";
    public String rarity = "";
    public List<StatModifier> stats = new ArrayList<>();
    public List<String> gear_slots = new ArrayList<>();

    public transient String locname = "";

    public ItemStack getStack() {
        ItemStack stack = new ItemStack(SlashItems.REFORGE.get());
        stack.getOrCreateTag()
            .putString("reforge", id);
        return stack;
    }

    public boolean canApplyOnItem(ItemStack stack) {
        if (!Gear.has(stack)) {
            return false;
        }
        if (gear_slots.stream()
            .noneMatch(e -> GearSlot.isItemOfThisSlot(ExileDB.GearSlots()
                .get(e), stack.getItem()))) {
            return false;
        }

        return true;
    }

    public GearRarity getRarity() {
        return ExileDB.GearRarities()
            .get(rarity);
    }

    @Override
    public ExileRegistryType getExileRegistryType() {
        return ExileRegistryTypes.REFORGE;
    }

    @Override
    public String GUID() {
        return id;
    }

    @Override
    public int Weight() {
        return weight;
    }

    @Override
    public Class<Reforge> getClassForSerialization() {
        return Reforge.class;
    }

    @Override
    public AutoLocGroup locNameGroup() {
        return AutoLocGroup.Reforges;
    }

    @Override
    public String locNameLangFileGUID() {
        return SlashRef.MODID + ".reforge." + id;
    }

    @Override
    public String locNameForLangFile() {
        return locname;
    }

    public void useReforgeStone(ItemStack stack, GearItemData data) {
        data.reforge.reforge = GUID();

        data.reforge.rarity = ExileDB.GearRarities()
            .getFilterWrapped(x -> !x.is_unique_item)
            .random()
            .GUID();

        data.saveToStack(stack);
    }

    public TooltipBuilder getTooltip(int lvl) {

        TooltipBuilder tooltip = TooltipBuilder.of();

        tooltip.addEmptyLine();

        tooltip.add(TextBuilder.of()
            .append("Stats: "));
        for (StatModifier stat : stats) {
            for (ITextComponent text : stat.getEstimationTooltip(lvl)) {
                tooltip.add(new VanillaText(text));
            }
        }

        TextBuilder txt = TextBuilder.of()
            .append("Slots: ");
        for (String slotid : this.gear_slots) {
            GearSlot slot = ExileDB.GearSlots()
                .get(slotid);
            txt.append(slot.ModlocName());
            txt.append(" ");
        }

        tooltip.addEmptyLine();

        tooltip.add(txt);

        tooltip.addEmptyLine();

        tooltip.add(TextBuilder.of()
            .append(getRarity().ModlocName()
                .format(getRarity().textFormatting())));

        tooltip.addEmptyLine();
        tooltip.add(TextBuilder.of()
            .append("Can Apply To Gear"));

        return tooltip;
    }

    public static class Builder {

        Reforge object = new Reforge();

        public static Builder of(String id, String locname) {

            Builder b = new Builder();
            b.object.id = id;
            b.object.locname = locname;
            return b;
        }

        public Builder stat(StatModifier stat) {
            this.object.stats.add(stat);
            return this;
        }

        public Builder gearSlot(GearSlotKey key) {
            this.object.gear_slots.add(key.id);
            return this;
        }

        public Builder armors() {
            gearSlot(GearSlotKeys.HELMET);
            gearSlot(GearSlotKeys.CHEST);
            gearSlot(GearSlotKeys.PANTS);
            gearSlot(GearSlotKeys.BOOTS);
            return this;
        }

        public Builder weapons() {
            gearSlot(GearSlotKeys.SWORD);
            gearSlot(GearSlotKeys.STAFF);
            gearSlot(GearSlotKeys.BOW);
            return this;
        }

        public Builder rarity(String rar) {
            this.object.rarity = rar;

            if (rar.equals(IRarity.LEGENDARY_ID)) {
                object.weight = 200;
            }
            if (rar.equals(IRarity.EPIC_ID)) {
                object.weight = 400;
            }
            if (rar.equals(IRarity.RARE_ID)) {
                object.weight = 600;
            }

            return this;
        }

        public void build() {
            if (object.rarity.isEmpty()) {
                throw new RuntimeException("rar empty");
            }
            if (object.stats.isEmpty()) {
                throw new RuntimeException("stats empty");
            }
            if (object.gear_slots.isEmpty()) {
                throw new RuntimeException("slots empty");
            }
            this.object.addToSerializables();
        }

    }
}
