package com.robertx22.mine_and_slash.uncommon.interfaces;

import com.robertx22.mine_and_slash.database.data.IGUID;
import com.robertx22.mine_and_slash.database.data.stats.TransferMethod;
import com.robertx22.mine_and_slash.saveclasses.unit.StatData;
import com.robertx22.mine_and_slash.saveclasses.unit.Unit;

import java.util.List;

public interface IStatTransfer extends IGUID {

    public abstract List<TransferMethod> Transfer();

    public default void transferStats(Unit copy, Unit unit, StatData data) {

        for (TransferMethod stat : this.Transfer()) {

            float val = copy.peekAtStat(stat.converted.GUID())
                .getFlatAverage() * data.getAverageValue() /* percent */ / 100;

            if (val != 0) {
                unit.getCreateStat(stat.converted)
                    .addAlreadyScaledFlat(-val);
                unit.getCreateStat(stat.statThatBenefits)
                    .addAlreadyScaledFlat(val);
            }
        }

    }

}
