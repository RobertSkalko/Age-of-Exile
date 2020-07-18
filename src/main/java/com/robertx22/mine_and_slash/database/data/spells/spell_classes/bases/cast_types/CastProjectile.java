package com.robertx22.mine_and_slash.database.data.spells.spell_classes.bases.cast_types;

import com.robertx22.mine_and_slash.database.data.spells.ProjectileCastOptions;
import com.robertx22.mine_and_slash.database.data.spells.spell_classes.bases.SpellCastContext;
import com.robertx22.mine_and_slash.database.data.spells.spell_classes.bases.configs.SC;
import net.minecraft.sound.SoundCategory;
import net.minecraft.world.World;

public class CastProjectile extends SpellCastType {

    @Override
    public boolean cast(SpellCastContext ctx) {
        World world = ctx.caster.world;

        ProjectileCastOptions builder = new ProjectileCastOptions(ctx);

        builder.projectilesAmount = (int) ctx.getConfigFor(ctx.spell)
            .get(SC.PROJECTILE_COUNT)
            .get(ctx.spellsCap, ctx.spell);

        builder.shootSpeed = ctx.getConfigFor(ctx.spell)
            .get(SC.SHOOT_SPEED)
            .get(ctx.spellsCap, ctx.spell);

        builder.apart = 75;
        builder.cast();

        if (ctx.spell.getImmutableConfigs()
            .sound() != null) {
            ctx.caster.world.playSoundFromEntity(null, ctx.caster, ctx.spell.getImmutableConfigs()
                .sound(), SoundCategory.HOSTILE, 1.0F, 1.0F);
        }
        return true;
    }
}
