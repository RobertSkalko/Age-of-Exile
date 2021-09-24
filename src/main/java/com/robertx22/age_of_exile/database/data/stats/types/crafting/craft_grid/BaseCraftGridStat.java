package com.robertx22.age_of_exile.database.data.stats.types.crafting.craft_grid;

import com.robertx22.age_of_exile.database.data.stats.Stat;
import com.robertx22.age_of_exile.database.data.stats.StatScaling;
import com.robertx22.age_of_exile.database.data.stats.types.crafting.DoNotTransferToCraftedMarker;
import com.robertx22.age_of_exile.saveclasses.PointData;
import com.robertx22.age_of_exile.uncommon.enumclasses.Elements;
import net.minecraft.util.text.TextFormatting;

import java.util.List;

public abstract class BaseCraftGridStat extends Stat implements DoNotTransferToCraftedMarker {

    public BaseCraftGridStat() {
        this.min = -100;
        this.scaling = StatScaling.NONE;
        this.group = StatGroup.Misc;
        this.icon = "\u2748";
        this.format = TextFormatting.BLUE.getName();

        this.is_long = true;
    }

    public boolean affects(PointData thisplace, PointData placeOfIngredient) {
        return getIngredientsAffected(thisplace).contains(placeOfIngredient);
    }

    public abstract List<PointData> getIngredientsAffected(PointData point);

    @Override
    public String locDescForLangFile() {
        return "";
    }

    @Override
    public Elements getElement() {
        return Elements.Physical;
    }

    @Override
    public boolean IsPercent() {
        return true;
    }

}
