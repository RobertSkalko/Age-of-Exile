package com.robertx22.age_of_exile.database.data.runewords;

import com.robertx22.age_of_exile.database.data.MinMax;
import com.robertx22.age_of_exile.database.data.StatModifier;
import com.robertx22.age_of_exile.database.data.gear_slots.GearSlot;
import com.robertx22.age_of_exile.database.data.unique_items.UniqueGear;
import com.robertx22.age_of_exile.database.registry.ExileDB;
import com.robertx22.age_of_exile.database.registry.ExileRegistryTypes;
import com.robertx22.age_of_exile.loot.blueprints.GearBlueprint;
import com.robertx22.age_of_exile.mmorpg.registers.common.items.SlashItems;
import com.robertx22.age_of_exile.saveclasses.gearitem.gear_bases.TooltipInfo;
import com.robertx22.age_of_exile.saveclasses.item_classes.GearItemData;
import com.robertx22.age_of_exile.uncommon.datasaving.Gear;
import com.robertx22.age_of_exile.uncommon.datasaving.Load;
import com.robertx22.library_of_exile.registry.ExileRegistryType;
import com.robertx22.library_of_exile.registry.IAutoGson;
import com.robertx22.library_of_exile.registry.JsonExileRegistry;
import com.robertx22.library_of_exile.text_wrapper.ModStringText;
import com.robertx22.library_of_exile.text_wrapper.TextBuilder;
import com.robertx22.library_of_exile.text_wrapper.TooltipBuilder;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextFormatting;

import java.util.ArrayList;
import java.util.List;

public class RuneWord implements IAutoGson<RuneWord>, JsonExileRegistry<RuneWord> {
    public static RuneWord SERIALIZER = new RuneWord();

    public String id = "";
    public String uniq_id = "";
    public String item_id = "";

    public int min_lvl = 0; // todo use these
    public int max_lvl = 100;

    public List<String> slots = new ArrayList<>();

    public UniqueGear getUnique() {
        return ExileDB.UniqueGears()
            .get(uniq_id);
    }

    // todo test
    public boolean isRunicSpell() {
        return getUnique().random_affixes > 0;
    }

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

    public ItemStack getStack() {
        ItemStack stack = new ItemStack(SlashItems.RUNEWORD.get());
        stack.getOrCreateTag()
            .putString("runeword", id);
        return stack;
    }

    public boolean canApplyOnItem(ItemStack stack) {
        if (Gear.has(stack)) {
            return false;
        }
        if (slots.stream()
            .noneMatch(e -> {
                return GearSlot.isItemOfThisSlot(ExileDB.GearSlots()
                    .get(e), stack.getItem());
            })) {
            return false;
        }

        return true;
    }

    public void useRuneWord(PlayerEntity player, ItemStack stack) {

        int lvl = MathHelper.clamp(Load.Unit(player)
            .getLevel(), min_lvl, max_lvl);

        GearBlueprint b = new GearBlueprint(lvl);
        UniqueGear uniq = ExileDB.UniqueGears()
            .get(uniq_id);
        b.level.override(lvl);
        b.uniquePart.set(uniq);
        b.rarity.set(uniq.getUniqueRarity());
        GearItemData gear = b.createData();
        gear.saveToStack(stack);

    }

    public TooltipBuilder getTooltip(int lvl) {

        TooltipBuilder tooltip = TooltipBuilder.of();

        UniqueGear uniq = ExileDB.UniqueGears()
            .get(uniq_id);

        tooltip.addEmptyLine();

        for (StatModifier stat : uniq.uniqueStats) {
            for (ITextComponent txt : stat.ToExactStat(100, lvl)
                .GetTooltipString(new TooltipInfo(new MinMax((int) stat.min, (int) stat.max)))) {
                tooltip.add(TextBuilder.of()
                    .append(txt));
            }
        }

        if (uniq.random_affixes > 0) {
            for (int i = 0; i < uniq.random_affixes; i++) {
                tooltip.add(TextBuilder.of()
                    .append(new ModStringText("[Random Affix]").format(TextFormatting.GREEN)));
            }
        }

        TextBuilder txt = TextBuilder.of()
            .append("Slots: ");
        for (String slotid : this.slots) {
            GearSlot slot = ExileDB.GearSlots()
                .get(slotid);
            txt.append(slot.ModlocName());
            txt.append(" ");
        }

        tooltip.addEmptyLine();

        tooltip.add(txt);
        tooltip.add(TextBuilder.of()
            .append("Level: " + min_lvl + " - " + max_lvl));

        return tooltip;
    }

    @Override
    public int Weight() {
        return 1000;
    }

}
