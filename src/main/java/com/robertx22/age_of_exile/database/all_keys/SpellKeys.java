package com.robertx22.age_of_exile.database.all_keys;

import com.robertx22.age_of_exile.database.all_keys.base.SpellKey;

public interface SpellKeys {

    SpellKey MAGIC_PROJECTILE = new SpellKey("magic_projectile");
    SpellKey METEOR = new SpellKey("meteor");
    SpellKey ICE_SHIELD = new SpellKey("ice_shield");
    SpellKey ICE_NOVA = new SpellKey("ice_nova");

    public static void init() {

    }
}
