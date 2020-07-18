package com.robertx22.mine_and_slash.database.data.rarities.gears;

import com.robertx22.mine_and_slash.database.data.MinMax;
import com.robertx22.mine_and_slash.database.data.rarities.GearRarity;
import com.robertx22.mine_and_slash.database.data.rarities.base.BaseUnique;

public class UniqueGear extends BaseUnique implements GearRarity {

    private UniqueGear() {
    }

    public static UniqueGear getInstance() {
        return SingletonHolder.INSTANCE;
    }

    @Override
    public float unidentifiedChance() {
        return 100;
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
    public MinMax uniqueStatPercents() {
        return new MinMax(0, 100);
    }

    @Override
    public MinMax SpawnDurabilityHit() {
        return new MinMax(75, 90);
    }

    @Override
    public int Weight() {
        return 0;
    }

    @Override
    public int AffixChance() {
        return 0;
    }

    @Override
    public int minAffixes() {
        return 0;
    }

    @Override
    public MinMax StatPercents() {
        return new MinMax(50, 100);
    }

    @Override
    public float salvageLotteryWinChance() {
        return 50;
    }

    @Override
    public int maxAffixes() {
        return 0;
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
        return 3.5F;
    }

    private static class SingletonHolder {
        private static final UniqueGear INSTANCE = new UniqueGear();
    }
}