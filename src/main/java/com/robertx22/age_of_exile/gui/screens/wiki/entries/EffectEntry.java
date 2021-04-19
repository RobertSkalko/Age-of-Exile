package com.robertx22.age_of_exile.gui.screens.wiki.entries;

import com.robertx22.age_of_exile.database.data.exile_effects.ExileEffect;
import com.robertx22.age_of_exile.gui.screens.wiki.WikiEntry;
import com.robertx22.age_of_exile.saveclasses.gearitem.gear_bases.TooltipInfo;
import com.robertx22.age_of_exile.saveclasses.item_classes.CalculatedSpellData;
import com.robertx22.library_of_exile.utils.CLOC;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;

import java.util.ArrayList;
import java.util.List;

public class EffectEntry extends WikiEntry {

    ExileEffect effect;

    public EffectEntry(ExileEffect effect) {
        this.effect = effect;
    }

    @Override
    public ItemStack createMainStack() {
        ItemStack stack = new ItemStack(Items.POTION);
        return stack;
    }

    @Override
    public List<Text> getExtraInfo() {
        List<Text> list = new ArrayList<>();

        TooltipInfo info = new TooltipInfo();
        list.addAll(effect.GetTooltipString(info, new CalculatedSpellData()));
        return list;
    }

    @Override
    public String getName() {
        return CLOC.translate(effect.locName());
    }

    @Override
    public Formatting getFormat() {
        return Formatting.AQUA;
    }
}
