package com.robertx22.age_of_exile.database.data.rarities.serialization;

import com.robertx22.age_of_exile.database.data.IAutoGson;
import com.robertx22.age_of_exile.database.data.MinMax;
import com.robertx22.age_of_exile.database.data.rarities.IGearRarity;

public final class GearRarity extends BaseRarity implements IGearRarity, IAutoGson<GearRarity> {
    public static GearRarity SERIALIZER = new GearRarity();

    public GearRarity() {
        super(RarityType.GEAR);
    }

    @Override
    public Class<GearRarity> getClassForSerialization() {
        return GearRarity.class;
    }

    public static class Part {
        public int min_amount;
        public int max_amount;
        public int chance_for_more;

        public Part(int min_amount, int max_amount, int chance_for_more) {
            this.min_amount = min_amount;
            this.max_amount = max_amount;
            this.chance_for_more = chance_for_more;
        }
    }

    public Part affixes;
    public Part sockets;

    public MinMax default_stat_percents = new MinMax(0, 100);
    public MinMax secondaryStatPercents = new MinMax(0, 100);
    public MinMax affix_stat_percents = new MinMax(0, 100);
    public MinMax unique_stat_percents = new MinMax(0, 100);

    public float item_tier_power;
    public float item_value_multi;
    public float stat_req_multi;
    public int unidentified_chance;
    public MinMax spawn_durability_hit;

    @Override
    public MinMax SpawnDurabilityHit() {
        return spawn_durability_hit;
    }

    @Override
    public float statReqMulti() {
        return this.stat_req_multi;
    }

    @Override
    public MinMax affixStatPercents() {
        return affix_stat_percents;
    }

    @Override
    public float valueMulti() {
        return this.item_value_multi;
    }

    @Override
    public MinMax uniqueStatPercents() {
        return unique_stat_percents;
    }

    @Override
    public int AffixChance() {
        return affixes.chance_for_more;
    }

    @Override
    public int maxAffixes() {
        return affixes.max_amount;
    }

    @Override
    public int maxSockets() {
        return this.sockets.max_amount;
    }

    @Override
    public int minSockets() {
        return this.sockets.min_amount;
    }

    @Override
    public float socketChance() {
        return this.sockets.chance_for_more;
    }

    @Override
    public int minAffixes() {
        return affixes.min_amount;
    }

    @Override
    public float itemTierPower() {
        return item_tier_power;
    }

    @Override
    public float unidentifiedChance() {
        return unidentified_chance;
    }

    @Override
    public MinMax StatPercents() {
        return default_stat_percents;
    }

}
