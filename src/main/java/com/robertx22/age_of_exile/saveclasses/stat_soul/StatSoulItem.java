package com.robertx22.age_of_exile.saveclasses.stat_soul;

import com.robertx22.age_of_exile.database.base.CreativeTabs;
import com.robertx22.age_of_exile.database.data.gear_slots.GearSlot;
import com.robertx22.age_of_exile.database.data.rarities.GearRarity;
import com.robertx22.age_of_exile.database.registry.ExileDB;
import com.robertx22.age_of_exile.uncommon.datasaving.StackSaving;
import com.robertx22.age_of_exile.uncommon.localization.Words;
import com.robertx22.age_of_exile.uncommon.utilityclasses.TooltipUtils;
import com.robertx22.library_of_exile.registry.IGUID;
import com.robertx22.library_of_exile.utils.LoadSave;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.util.text.*;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.List;

public class StatSoulItem extends Item implements IGUID {

    public static String TAG = "stat_soul";

    public StatSoulItem() {
        super(new Properties().tab(CreativeTabs.GearSouls));
    }

    @Override
    public void fillItemCategory(ItemGroup group, NonNullList<ItemStack> stacks) {
        if (this.allowdedIn(group)) {

            for (GearRarity rarity : ExileDB.GearRarities()
                .getList()) {
                for (GearSlot slot : ExileDB.GearSlots()
                    .getList()) {
                    for (int i = 1; i < 6; i++) {
                        StatSoulData data = new StatSoulData();
                        data.tier = i;
                        data.rar = rarity.GUID();
                        data.slot = slot.GUID();

                        ItemStack stack = data.toStack();
                        stacks.add(stack);
                    }

                }
            }

        }
    }

    @Override
    public ITextComponent getName(ItemStack stack) {

        IFormattableTextComponent txt = new TranslationTextComponent(this.getDescriptionId());

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
                    .append(Words.Soul.locName())
                    .withStyle(rar.textFormatting());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return txt;
    }

    public static boolean hasSoul(ItemStack stack) {
        return stack.hasTag() && stack.getTag()
            .contains(TAG);
    }

    public static StatSoulData getSoul(ItemStack stack) {
        StatSoulData data = LoadSave.Load(StatSoulData.class, new StatSoulData(), stack.getOrCreateTag(), TAG);
        return data;
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void appendHoverText(ItemStack stack, World world, List<ITextComponent> tooltip, ITooltipFlag context) {

        StatSoulData data = StackSaving.STAT_SOULS.loadFrom(stack);

        if (data != null) {
            tooltip.add(TooltipUtils.gearTier(data.tier));
            tooltip.add(new StringTextComponent("Item Type: ").withStyle(TextFormatting.WHITE)
                .append(ExileDB.GearSlots()
                    .get(data.slot)
                    .locName()
                    .withStyle(TextFormatting.BLUE)));
            tooltip.add(TooltipUtils.gearRarity(ExileDB.GearRarities()
                .get(data.rar)));

        }

        tooltip.add(new StringTextComponent(""));

        tooltip.add(new StringTextComponent("Infuses stats into empty gear").withStyle(TextFormatting.AQUA));
        tooltip.add(TooltipUtils.dragOntoGearToUse());

    }

    @Override
    public String GUID() {
        return "stat_soul/stat_soul";
    }
}
