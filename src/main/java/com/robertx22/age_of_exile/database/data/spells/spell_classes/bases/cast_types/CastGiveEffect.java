package com.robertx22.age_of_exile.database.data.spells.spell_classes.bases.cast_types;

import com.google.common.base.Preconditions;
import com.robertx22.age_of_exile.uncommon.utilityclasses.SoundUtils;
import com.robertx22.age_of_exile.database.data.spells.spell_classes.bases.SpellCastContext;
import com.robertx22.age_of_exile.vanilla_mc.potion_effects.bases.PotionEffectUtils;

public class CastGiveEffect extends SpellCastType {
    @Override
    public boolean cast(SpellCastContext ctx) {
        try {

            Preconditions.checkNotNull(ctx.spell.getImmutableConfigs()
                .potionEffect());

            PotionEffectUtils.applyToSelf(ctx.spell.getImmutableConfigs()
                .potionEffect(), ctx.caster);

            SoundUtils.playSound(ctx.caster, ctx.spell.getImmutableConfigs()
                .sound(), 1, 1);

            ctx.spell.spawnParticles(ctx);

            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }
}
