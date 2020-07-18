package com.robertx22.mine_and_slash.database.data.rarities.gears;

import com.robertx22.mine_and_slash.database.data.MinMax;
import com.robertx22.mine_and_slash.database.data.rarities.GearRarity;
import com.robertx22.mine_and_slash.database.data.rarities.base.BaseRare;

public class RareGear extends BaseRare implements GearRarity {
    private RareGear() {
    }

    public static RareGear getInstance() {
        return SingletonHolder.INSTANCE;
    }

    @Override
    public float unidentifiedChance() {
        return 25;
    }

    @Override
    public float statReqMulti() {
        return 0.8F;
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
        return 20;
    }

    @Override
    public MinMax StatPercents() {
        return new MinMax(0, 100);
    }

    @Override
    public float salvageLotteryWinChance() {
        return 2.5F;
    }

    @Override
    public int minAffixes() {
        return 3;
    }

    @Override
    public int Weight() {
        return 100;
    }

    @Override
    public int maxAffixes() {
        return 6;
    }

    @Override
    public int maxSockets() {
        return 0;
    }

    @Override
    public float socketChance() {
        return 0;
    }

    @Override
    public float itemTierPower() {
        return 1.5F;
    }

    private static class SingletonHolder {
        private static final RareGear INSTANCE = new RareGear();
    }
}


