package com.robertx22.age_of_exile.saveclasses.gearitem.gear_parts;

import com.robertx22.age_of_exile.saveclasses.gearitem.gear_bases.TooltipInfo;
import com.robertx22.age_of_exile.saveclasses.gearitem.rework.IStatContainer;
import com.robertx22.age_of_exile.saveclasses.gearitem.rework.StatModifierInfo;
import com.robertx22.age_of_exile.saveclasses.gearitem.rework.StatsWithContext;
import com.robertx22.age_of_exile.saveclasses.item_classes.GearItemData;
import com.robertx22.library_of_exile.utils.RandomUtils;
import info.loenwind.autosave.annotations.Storable;
import info.loenwind.autosave.annotations.Store;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;

import java.util.ArrayList;
import java.util.List;

@Storable
public class AllReforgesData implements IStatContainer {

    @Store
    public List<ReforgeData> reforges = new ArrayList<>();

    public void tryAddReforge(ReforgeData data) {

        int current = reforges.size();

        if (current >= 3) {
            reforges.clear();
        } else {
            int chanceToDelete = 25 * reforges.size();
            if (RandomUtils.roll(chanceToDelete)) {
                reforges.clear();
            }
        }

        reforges.add(data);
    }

    public List<ITextComponent> GetTooltipString(TooltipInfo info, GearItemData gear) {
        List<ITextComponent> list = new ArrayList<>();

        list.add(new StringTextComponent(" "));
        for (ReforgeData reforge : reforges) {
            list.addAll(reforge.GetTooltipString(info, gear));
        }
        list.add(new StringTextComponent(" "));

        return list;

    }

    @Override
    public StatsWithContext getStatsWithContext(GearItemData gear) {
        List<StatModifierInfo> list = new ArrayList<>();

        try {
            for (ReforgeData reforge : reforges) {
                list.addAll(reforge.getStatsWithContext(gear).list);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return new StatsWithContext(list);
    }
}
