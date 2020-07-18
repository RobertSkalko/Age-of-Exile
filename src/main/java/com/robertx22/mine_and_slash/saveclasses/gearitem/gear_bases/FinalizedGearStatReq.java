package com.robertx22.mine_and_slash.saveclasses.gearitem.gear_bases;

import com.robertx22.mine_and_slash.capability.entity.EntityCap;
import com.robertx22.mine_and_slash.database.data.stats.types.core_stats.Dexterity;
import com.robertx22.mine_and_slash.database.data.stats.types.core_stats.Intelligence;
import com.robertx22.mine_and_slash.database.data.stats.types.core_stats.Strength;
import com.robertx22.mine_and_slash.database.data.stats.types.reduced_req.FlatIncreasedReq;
import com.robertx22.mine_and_slash.database.data.stats.types.reduced_req.ReducedAllStatReqOnItem;
import com.robertx22.mine_and_slash.saveclasses.ExactStatData;
import com.robertx22.mine_and_slash.saveclasses.item_classes.GearItemData;

import java.util.List;

public class FinalizedGearStatReq {

    public int dexterity;
    public int intelligence;
    public int strength;

    public FinalizedGearStatReq(int dexterity, int intelligence, int strength) {
        this.dexterity = dexterity;
        this.intelligence = intelligence;
        this.strength = strength;
    }

    public void calculate(GearItemData gear) {

        List<ExactStatData> stats = gear.GetAllStats(false, false);

        // first apply flat +x str req, then x% reduced requirements

        stats.forEach(x -> {
            if (x.getStat() instanceof FlatIncreasedReq) {
                FlatIncreasedReq reduce = (FlatIncreasedReq) x.getStat();

                dexterity = (int) reduce.getModifiedRequirement(Dexterity.INSTANCE, dexterity, x);

                intelligence = (int) reduce.getModifiedRequirement(Intelligence.INSTANCE, intelligence, x);

                strength = (int) reduce.getModifiedRequirement(Strength.INSTANCE, strength, x);

            }
        });

        stats
            .forEach(x -> {
                if (x.getStat() instanceof ReducedAllStatReqOnItem) {
                    ReducedAllStatReqOnItem reduce = (ReducedAllStatReqOnItem) x.getStat();

                    if (dexterity > 0) {
                        dexterity = (int) reduce.getModifiedRequirement(dexterity, x);
                    }
                    if (intelligence > 0) {
                        intelligence = (int) reduce.getModifiedRequirement(intelligence, x);
                    }
                    if (strength > 0) {
                        strength = (int) reduce.getModifiedRequirement(strength, x);
                    }
                }
            });
    }

    public boolean passesStatRequirements(EntityCap.UnitData data, GearItemData gear) {

        if (data.getUnit()
            .peekAtStat(Dexterity.INSTANCE)
            .getAverageValue() < dexterity) {
            return false;
        }
        if (data.getUnit()
            .peekAtStat(Intelligence.INSTANCE)
            .getAverageValue() < intelligence) {
            return false;
        }
        if (data.getUnit()
            .peekAtStat(Strength.INSTANCE)
            .getAverageValue() < strength) {
            return false;
        }

        return true;
    }

}
