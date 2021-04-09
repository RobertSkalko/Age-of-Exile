package com.robertx22.age_of_exile.database.data.stats.effects.base;

public abstract class BaseSpecialStatDamageEffect extends BaseDamageEffect {

    @Override
    public int GetPriority() {
        return Priority.Last.priority;
    }
}
