package com.robertx22.age_of_exile.database.all_keys;

import com.robertx22.age_of_exile.database.all_keys.base.SpellKey;

public interface SpellKeys {

    SpellKey MAGIC_PROJECTILE = new SpellKey("magic_projectile");
    SpellKey METEOR = new SpellKey("meteor");
    SpellKey ICE_SHIELD = new SpellKey("ice_shield");
    SpellKey ICE_NOVA = new SpellKey("ice_nova");
    SpellKey NATURE_BALM = new SpellKey("nature_balm");
    SpellKey POISON_CLOUD = new SpellKey("poison_cloud");

    public static void init() {

    }
}
