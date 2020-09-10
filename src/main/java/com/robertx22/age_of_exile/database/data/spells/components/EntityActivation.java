package com.robertx22.age_of_exile.database.data.spells.components;

import com.robertx22.age_of_exile.uncommon.localization.Words;

public enum EntityActivation {
    ON_CAST(null), ON_TICK(null), ON_HIT(Words.OnHit), ON_EXPIRE(Words.OnExpire), PER_ENTITY_HIT(Words.PerEntityHit);

    EntityActivation(Words word) {

    }
}
