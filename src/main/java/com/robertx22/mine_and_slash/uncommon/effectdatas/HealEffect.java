package com.robertx22.mine_and_slash.uncommon.effectdatas;

import com.robertx22.mine_and_slash.saveclasses.unit.ResourcesData;

public class HealEffect extends EffectData {

    public ResourcesData.Context healData;

    public HealEffect(ResourcesData.Context data) {
        super(data.source, data.target, data.sourceData, data.targetData);
        this.number = data.amount;
        this.healData = data;
    }

    @Override
    protected void activate() {

        if (this.canceled) {
            return;
        }

        if (target.isAlive()) {
            this.calculateEffects();

            //this.targetData.getResources().modify(this.healData);

            this.target.heal(getNumber());
        }
    }

    public float getNumber() {
        return number;
    }
}
