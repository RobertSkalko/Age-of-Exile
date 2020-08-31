package com.robertx22.age_of_exile.database.data.spells.spell_classes.bases.cast_types;

import com.robertx22.age_of_exile.database.data.spells.SpellUtils;
import com.robertx22.age_of_exile.database.data.spells.spell_classes.bases.SpellCastContext;
import com.robertx22.library_of_exile.utils.SoundUtils;

public class CastSelfHeal extends SpellCastType {

    @Override
    public boolean cast(SpellCastContext ctx) {

        SpellUtils.healCaster(ctx);
        SpellUtils.healCasterMagicShield(ctx);

        if (ctx.spell.getImmutableConfigs()
            .sound() != null) {
            SoundUtils.playSound(ctx.caster, ctx.spell.getImmutableConfigs()
                .sound(), 1, 1);
        }
        return true;
    }
}
