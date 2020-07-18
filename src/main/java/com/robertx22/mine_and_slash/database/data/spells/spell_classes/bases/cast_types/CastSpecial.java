package com.robertx22.mine_and_slash.database.data.spells.spell_classes.bases.cast_types;

import com.robertx22.mine_and_slash.database.data.spells.spell_classes.bases.SpellCastContext;

public class CastSpecial extends SpellCastType {

    @Override
    public boolean cast(SpellCastContext ctx) {
        return false;
    }
}
