package com.robertx22.age_of_exile.database.data.stats.types.core_stats;

import com.robertx22.age_of_exile.capability.entity.EntityData;
import com.robertx22.age_of_exile.database.data.stats.Stat;
import com.robertx22.age_of_exile.database.data.stats.StatScaling;
import com.robertx22.age_of_exile.database.data.stats.types.core_stats.base.ITransferToOtherStats;
import com.robertx22.age_of_exile.database.registry.ExileDB;
import com.robertx22.age_of_exile.saveclasses.unit.InCalcStatData;
import com.robertx22.age_of_exile.uncommon.enumclasses.Elements;
import net.minecraft.util.Formatting;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class AllAttributes extends Stat implements ITransferToOtherStats {

    public static String INT_ID = "intelligence";
    public static String STR_ID = "strength";
    public static String DEX_ID = "dexterity";
    public static String VIT_ID = "vitality";
    public static String WIS_ID = "wisdom";
    public static String AGI_ID = "agility";

    private AllAttributes() {
        this.scaling = StatScaling.CORE_STAT;
        this.format = Formatting.LIGHT_PURPLE.getName();
    }

    public static AllAttributes getInstance() {
        return SingletonHolder.INSTANCE;
    }

    @Override
    public boolean IsPercent() {
        return false;
    }

    @Override
    public Elements getElement() {
        return null;
    }

    public List<Stat> coreStatsThatBenefit() {
        return coreStatsThatBenefitIDS().stream()
            .map(x -> ExileDB.Stats()
                .get(x))
            .collect(Collectors.toList());
    }

    public List<String> coreStatsThatBenefitIDS() {
        return Arrays.asList(AllAttributes.AGI_ID, AllAttributes.WIS_ID, AllAttributes.VIT_ID, AllAttributes.INT_ID, AllAttributes.STR_ID, AllAttributes.DEX_ID);
    }

    @Override
    public String locDescForLangFile() {
        return "Adds to DEX, INT STR, VIT, WIL, AGI.";
    }

    @Override
    public String locNameForLangFile() {
        return "All Attributes";
    }

    @Override
    public String GUID() {
        return "all_attributes";
    }

    @Override
    public void transferStats(EntityData unit, InCalcStatData thisstat) {
        for (Stat ele : coreStatsThatBenefit()) {
            thisstat.addFullyTo(unit.getUnit()
                .getStatInCalculation(ele));
        }
        thisstat.clear();
    }

    private static class SingletonHolder {
        private static final AllAttributes INSTANCE = new AllAttributes();
    }
}



