package com.robertx22.age_of_exile.aoe_data.database.spells.builders;

import com.robertx22.age_of_exile.database.data.spells.components.ComponentPart;
import com.robertx22.age_of_exile.uncommon.utilityclasses.AllyOrEnemy;

public abstract class BaseEffectActionBuilder<T extends BaseEffectActionBuilder> {

    AllyOrEnemy targetSelector = AllyOrEnemy.allies;
    float radius = 3;
    float seconds = 5;

    public T targetEnemies() {
        this.targetSelector = AllyOrEnemy.enemies;
        return (T) this;
    }

    public T radius(float rad) {
        this.radius = rad;
        return (T) this;
    }

    public T seconds(int seconds) {
        this.seconds = seconds;
        return (T) this;
    }

    public abstract ComponentPart build();

}
