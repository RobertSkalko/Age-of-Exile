package com.robertx22.age_of_exile.database.data.spells.spell_classes;

import com.robertx22.age_of_exile.database.data.spells.spell_classes.bases.SpellPredicate;
import com.robertx22.age_of_exile.database.data.spells.spell_classes.bases.SpellPredicates;

public enum CastingWeapon {
    MAGE_WEAPON(SpellPredicates.REQUIRE_MAGE_WEAPON),
    MELEE_WEAPON(SpellPredicates.REQUIRE_MELEE),
    NON_MAGE_WEAPON(SpellPredicates.NON_MAGE_WEAPON),
    ANY_WEAPON(SpellPredicates.ANY_ITEM),
    RANGED(SpellPredicates.REQUIRE_SHOOTABLE);

    public SpellPredicate predicate;

    CastingWeapon(SpellPredicate predicate) {
        this.predicate = predicate;

    }

}
