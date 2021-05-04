package com.robertx22.age_of_exile.database.data.stats.effects.base;

import com.robertx22.age_of_exile.uncommon.effectdatas.ExilePotionEvent;

public abstract class BasePotionEffect extends InCodeStatEffect<ExilePotionEvent> {
    public BasePotionEffect() {
        super(ExilePotionEvent.class);
    }
}