package com.robertx22.age_of_exile.vanilla_mc.items.gemrunes;

import com.robertx22.age_of_exile.aoe_data.datapacks.models.IAutoModel;
import com.robertx22.age_of_exile.aoe_data.datapacks.models.ItemModelManager;
import com.robertx22.age_of_exile.database.base.CreativeTabs;
import com.robertx22.age_of_exile.database.data.StatModifier;
import com.robertx22.age_of_exile.database.data.gear_types.bases.BaseGearType;
import com.robertx22.age_of_exile.database.data.runewords.RuneWord;
import com.robertx22.age_of_exile.database.registry.Database;
import com.robertx22.age_of_exile.mmorpg.ModRegistry;
import com.robertx22.age_of_exile.uncommon.datasaving.Load;
import com.robertx22.age_of_exile.uncommon.interfaces.IAutoLocName;
import com.robertx22.age_of_exile.uncommon.utilityclasses.ClientOnly;
import com.robertx22.age_of_exile.uncommon.utilityclasses.TooltipUtils;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.text.LiteralText;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Formatting;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.World;

import java.util.Comparator;
import java.util.List;
import java.util.Locale;

public class RuneWordItem extends Item implements IAutoModel, IAutoLocName {

    public RuneWordItem() {
        super(new Settings().group(CreativeTabs.Runewords));
    }

    public static ItemStack createStack(RuneWord word) {
        CompoundTag tag = new CompoundTag();
        tag.putString("runeword", word.GUID());
        ItemStack stack = new ItemStack(ModRegistry.MISC_ITEMS.RUNEWORD);
        stack.setTag(tag);
        return stack;
    }

    @Override
    public Text getName(ItemStack stack) {
        MutableText txt = new TranslatableText(this.getTranslationKey());
        try {
            txt.append(": ")
                .append(get(stack).locName())
                .formatted(Formatting.GOLD);
        } catch (Exception e) {
        }
        return txt;

    }

    public static RuneWord get(ItemStack stack) {

        if (Database.Runewords()
            .isEmpty()) {
            return Database.Runewords()
                .getSerializable()
                .get(0);
        }

        RuneWord word = Database.Runewords()
            .get(stack.getTag()
                .getString("runeword"));

        if (word == null) {
            word = Database.Runewords()
                .getList()
                .get(0);
        }
        return word;
    }

    @Override
    public void appendStacks(ItemGroup group, DefaultedList<ItemStack> stacks) {
        if (this.isIn(group)) {
            Database.Runewords()
                .getList()
                .forEach(x -> stacks.add(createStack(x)));
        }
    }

    @Override
    @Environment(EnvType.CLIENT)
    public void appendTooltip(ItemStack stack, World world, List<Text> tooltip, TooltipContext context) {

        try {

            if (Database.Runewords()
                .isEmpty()) {
                return;
            }

            RuneWord word = get(stack);

            tooltip.add(new LiteralText("To create the runeword: ").formatted(Formatting.BLUE));

            tooltip.add(new LiteralText("insert these runes in an item with sockets").formatted(Formatting.BLUE));
            tooltip.add(new LiteralText(""));

            String runes = "";

            for (String x : word.runes_needed) {
                if (!runes.isEmpty()) {
                    runes += " - ";
                }
                runes += x.toUpperCase(Locale.ROOT);
            }

            tooltip.add(new LiteralText(runes).formatted(Formatting.GOLD));
            tooltip.add(new LiteralText(""));

            tooltip.add(new LiteralText("Runeword added stats:").formatted(Formatting.RED));
            for (StatModifier x : word.stats) {
                tooltip.addAll(x.getEstimationTooltip(Load.Unit(ClientOnly.getPlayer())
                    .getLevel()));
            }

            tooltip.add(new LiteralText(""));

            tooltip.add(TooltipUtils.level(word.runes_needed.stream()
                .map(x -> Database.Runes()
                    .get(x))
                .max(Comparator.comparingInt(y -> y.getReqLevel()))
                .get()
                .getReqLevel()));

            tooltip.add(new LiteralText(""));

            if (word.family != BaseGearType.SlotFamily.NONE) {
                tooltip.add(new LiteralText(Formatting.GREEN + "Can be used on: " + word.family.name()));
            } else {
                String usedOn = "";
                for (String x : word.gear_slots) {
                    usedOn += x;
                }
                tooltip.add(new LiteralText(Formatting.GREEN + "Can be used on: " + usedOn));
            }

            tooltip.add(new LiteralText(""));

            tooltip.add(new LiteralText("Socketing is done in the Socketing Station").formatted(Formatting.BLUE));

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public void generateModel(ItemModelManager manager) {
        manager.generated(this);
    }

    @Override
    public AutoLocGroup locNameGroup() {
        return AutoLocGroup.Misc;
    }

    @Override
    public String locNameLangFileGUID() {
        return Registry.ITEM.getId(this)
            .toString();
    }

    @Override
    public String locNameForLangFile() {
        return "Runeword";
    }

    @Override
    public String GUID() {
        return "";
    }
}
