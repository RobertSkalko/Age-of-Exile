package com.robertx22.addon.divine_missions.data_gen;

import com.robertx22.divine_missions.database.RewardTypeIds;
import com.robertx22.divine_missions.database.WorthTypeIds;
import com.robertx22.divine_missions.database.db_types.Reward;

public class DMRewardsAdder {

    public static Reward FAVOR = favor();
    public static Reward AOE_EXP = aoeExp();

    public static void init() {

    }

    private static Reward favor() {

        Reward favor = new Reward();
        favor.id = "favor";
        favor.weight = 5000;
        favor.type = RewardTypeIds.FAVOR;
        favor.worth_type = WorthTypeIds.AGE_OF_EXILE;
        favor.min = 5;
        favor.max = 15;
        favor.worth = 25;
        favor.addToSerializables();

        return favor;
    }

    private static Reward aoeExp() {

        Reward aoeExp = new Reward();
        aoeExp.id = "aoe_exp";
        aoeExp.type = RewardTypeIds.AOE_EXP;
        aoeExp.min = 20;
        aoeExp.max = 50;
        aoeExp.worth = 4;
        aoeExp.addToSerializables();

        return aoeExp;
    }

}
