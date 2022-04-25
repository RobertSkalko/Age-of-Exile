package com.robertx22.age_of_exile.database.data.spells.components.actions;

import com.robertx22.age_of_exile.database.data.spells.components.MapHolder;
import com.robertx22.age_of_exile.database.data.spells.spell_classes.SpellCtx;
import com.robertx22.age_of_exile.uncommon.utilityclasses.AllyOrEnemy;
import com.robertx22.age_of_exile.uncommon.utilityclasses.BoxUtils;
import com.robertx22.age_of_exile.uncommon.utilityclasses.LookUtils;
import com.robertx22.age_of_exile.uncommon.utilityclasses.PlayerUtils;
import net.minecraft.entity.EntityPredicate;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.network.play.server.SEntityVelocityPacket;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.vector.Vector3d;

import java.util.Arrays;
import java.util.Collection;

public class HomeInOnNearestTargetAction extends SpellAction {

    public HomeInOnNearestTargetAction() {
        super(Arrays.asList());
    }

    @Override
    public void tryActivate(Collection<LivingEntity> targets, SpellCtx ctx, MapHolder data) {

        if (!ctx.world.isClientSide) {

            EntityPredicate pred = new EntityPredicate()
                .selector(x -> {
                    if (!ctx.caster.canSee(x)) {
                        return false;
                    }
                    return AllyOrEnemy.enemies.is(ctx.caster, x);
                });

            RayTraceResult hit = LookUtils.raycast(ctx.caster, 15D);

            Vector3d pos = hit.getLocation();

            LivingEntity target = ctx.world.getNearestEntity(LivingEntity.class,
                pred,
                null,
                pos.x(), pos.y(), pos.z(),
                BoxUtils.createBoxOfRadius(new BlockPos(pos.x, pos.y, pos.z), 2)
                    .inflate(10)
            );

            if (target != null) {

                ctx.sourceEntity.setDeltaMovement(BoxUtils.positionToVelocity(
                    ctx.sourceEntity.position(),
                    target.getEyePosition(1)
                ));

                PlayerUtils.getNearbyPlayers(ctx.world, ctx.pos, 100)
                    .forEach(p -> {
                        ((ServerPlayerEntity) p).connection.send(new SEntityVelocityPacket(ctx.sourceEntity));
                        ctx.sourceEntity.hurtMarked = false;
                    });

            }
        }

    }

    public MapHolder create() {
        MapHolder d = new MapHolder();
        d.type = GUID();
        return d;
    }

    @Override
    public String GUID() {
        return "home_in_on_nearest_target";
    }
}
