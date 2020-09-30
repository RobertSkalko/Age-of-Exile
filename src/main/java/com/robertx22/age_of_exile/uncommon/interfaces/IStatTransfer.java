package com.robertx22.age_of_exile.uncommon.interfaces;

import com.robertx22.age_of_exile.database.data.IGUID;
import com.robertx22.age_of_exile.database.data.stats.TransferMethod;
import com.robertx22.age_of_exile.saveclasses.unit.StatData;
import com.robertx22.age_of_exile.saveclasses.unit.Unit;

import java.util.List;

public interface IStatTransfer extends IGUID {

    public abstract List<TransferMethod> Transfer();

    public default void transferStats(Unit copy, Unit unit, StatData data) {

        for (TransferMethod stat : this.Transfer()) {

            float val = copy.getStatInCalculation(stat.converted.GUID())
                .getFlatAverage() * data.getAverageValue() /* percent */ / 100;

            if (val != 0) {
                unit.getStatInCalculation(stat.converted)
                    .addAlreadyScaledFlat(-val);
                unit.getStatInCalculation(stat.statThatBenefits)
                    .addAlreadyScaledFlat(val);
            }
        }

    }

}
