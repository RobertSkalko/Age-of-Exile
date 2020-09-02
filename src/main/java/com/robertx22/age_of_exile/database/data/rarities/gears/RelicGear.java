package com.robertx22.age_of_exile.database.data.rarities.gears;

import com.robertx22.age_of_exile.database.data.MinMax;
import com.robertx22.age_of_exile.database.data.rarities.GearRarity;
import com.robertx22.age_of_exile.database.data.rarities.base.BaseRelic;

public class RelicGear extends BaseRelic implements GearRarity {
    private RelicGear() {
    }

    public static RelicGear getInstance() {
        return RelicGear.SingletonHolder.INSTANCE;
    }

    @Override
    public float unidentifiedChance() {
        return 15;
    }

    @Override
    public float statReqMulti() {
        return 1;
    }

    @Override
    public MinMax affixStatPercents() {
        return StatPercents();
    }

    @Override
    public MinMax SpawnDurabilityHit() {
        return new MinMax(75, 95);
    }

    @Override
    public MinMax uniqueStatPercents() {
        return StatPercents();
    }

    @Override
    public int AffixChance() {
        return 0;
    }

    @Override
    public MinMax StatPercents() {
        return new MinMax(0, 100);
    }

    @Override
    public float salvageLotteryWinChance() {
        return 4;
    }

    @Override
    public int minAffixes() {
        return 0;
    }

    @Override
    public int Weight() {
        return 200;
    }

    @Override
    public int maxAffixes() {
        return 0;
    }

    @Override
    public int maxSockets() {
        return 5;
    }

    @Override
    public int minSockets() {
        return 2;
    }

    @Override
    public float socketChance() {
        return 50;
    }

    @Override
    public float itemTierPower() {
        return 2;
    }

    private static class SingletonHolder {
        private static final RelicGear INSTANCE = new RelicGear();
    }
}

