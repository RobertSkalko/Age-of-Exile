package com.robertx22.age_of_exile.saveclasses.stat_soul;

import com.robertx22.age_of_exile.database.data.gear_slots.GearSlot;
import com.robertx22.age_of_exile.database.data.rarities.GearRarity;
import com.robertx22.age_of_exile.database.registry.ExileDB;
import com.robertx22.age_of_exile.uncommon.datasaving.StackSaving;
import com.robertx22.age_of_exile.uncommon.utilityclasses.TooltipUtils;
import com.robertx22.age_of_exile.vanilla_mc.items.misc.AutoItem;
import com.robertx22.library_of_exile.utils.LoadSave;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.item.ItemStack;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Formatting;
import net.minecraft.world.World;

import java.util.List;

public class StatSoulItem extends AutoItem {

    public static String TAG = "stat_soul";

    @Override
    public Text getName(ItemStack stack) {

        MutableText txt = new TranslatableText(this.getTranslationKey());

        try {
            StatSoulData data = getSoul(stack);

            if (data == null) {
                return txt;
            } else {

                GearRarity rar = ExileDB.GearRarities()
                    .get(data.rar);
                GearSlot slot = ExileDB.GearSlots()
                    .get(data.slot);

                return rar.locName()
                    .append(" ")
                    .append(slot.locName())
                    .append(" ")
                    .append(txt)
                    .formatted(rar.textFormatting());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return txt;
    }

    public static void insertAsUnidentifiedOn(StatSoulData data, ItemStack stack) {
        LoadSave.Save(data, stack.getOrCreateTag(), TAG);
    }

    public static boolean hasSoul(ItemStack stack) {
        return stack.hasTag() && stack.getTag()
            .contains(TAG);
    }

    public static StatSoulData getSoul(ItemStack stack) {
        StatSoulData data = LoadSave.Load(StatSoulData.class, new StatSoulData(), stack.getOrCreateTag(), TAG);
        return data;
    }

    public StatSoulItem() {
        super(new Settings());
    }

    @Override
    @Environment(EnvType.CLIENT)
    public void appendTooltip(ItemStack stack, World world, List<Text> tooltip, TooltipContext context) {

        StatSoulData data = StackSaving.STAT_SOULS.loadFrom(stack);

        if (data != null) {
            tooltip.add(TooltipUtils.gearTier(data.tier));
            tooltip.add(ExileDB.GearSlots()
                .get(data.slot)
                .locName()
                .formatted(Formatting.BLUE));
            tooltip.add(TooltipUtils.gearRarity(ExileDB.GearRarities()
                .get(data.rar)));

        }

    }

    @Override
    public String locNameForLangFile() {
        return "Soul";
    }

    @Override
    public String GUID() {
        return "stat_soul/stat_soul";
    }
}
