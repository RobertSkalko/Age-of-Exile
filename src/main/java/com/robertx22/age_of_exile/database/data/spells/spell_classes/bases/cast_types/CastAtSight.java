package com.robertx22.age_of_exile.database.data.spells.spell_classes.bases.cast_types;

import com.robertx22.age_of_exile.database.data.spells.SpellUtils;
import com.robertx22.age_of_exile.database.data.spells.spell_classes.bases.SpellCastContext;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class CastAtSight extends SpellCastType {

    @Override
    public boolean cast(SpellCastContext ctx) {
        World world = ctx.caster.world;

        HitResult ray = ctx.caster.rayTrace(10D, 0.0F, false);

        Vec3d pos = ray.getPos();

        if (ctx.caster instanceof PlayerEntity == false) {
            pos = ctx.caster.getPos();
        }

        Entity en = SpellUtils.getSpellEntity(ctx.configForSummonedEntities, ctx.spell.getImmutableConfigs()
            .newEntitySummoner()
            .apply(world), ctx.skillGem, ctx.caster);

        en.updatePosition(pos.x, pos.y, pos.z);

        ctx.caster.world.spawnEntity(en);
        return true;
    }
}
