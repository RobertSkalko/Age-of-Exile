package com.robertx22.age_of_exile.database.data.exile_effects;

import org.apache.commons.lang3.StringUtils;

public enum EffectTags {
    immobilizing, offensive, defensive, positive, negative;

    public String getLocName() {
        return StringUtils.capitalize(name());
    }
}
