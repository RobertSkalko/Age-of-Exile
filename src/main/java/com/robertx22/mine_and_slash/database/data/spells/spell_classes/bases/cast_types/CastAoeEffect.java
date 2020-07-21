package com.robertx22.mine_and_slash.database.data.spells.spell_classes.bases.cast_types;

import com.google.common.base.Preconditions;
import com.robertx22.mine_and_slash.database.data.spells.spell_classes.bases.SpellCastContext;
import com.robertx22.mine_and_slash.database.data.spells.spell_classes.bases.configs.SC;
import com.robertx22.mine_and_slash.uncommon.utilityclasses.EntityFinder;
import com.robertx22.mine_and_slash.uncommon.utilityclasses.SoundUtils;
import com.robertx22.mine_and_slash.vanilla_mc.potion_effects.bases.PotionEffectUtils;
import net.minecraft.entity.LivingEntity;

public class CastAoeEffect extends SpellCastType {
    @Override
    public boolean cast(SpellCastContext ctx) {
        try {

            Preconditions.checkNotNull(ctx.spell.getImmutableConfigs()
                .potionEffect());

            float RADIUS = ctx.getConfigFor(ctx.spell)
                .get(SC.RADIUS)
                .get(ctx.skillGem);

            EntityFinder.start(ctx.caster, LivingEntity.class, ctx.caster.getPos())
                .radius(RADIUS)
                .searchFor(EntityFinder.SearchFor.ALL)
                .build()
                .forEach(x -> PotionEffectUtils.apply(ctx.spell.getImmutableConfigs()
                    .potionEffect(), ctx.caster, x));

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
