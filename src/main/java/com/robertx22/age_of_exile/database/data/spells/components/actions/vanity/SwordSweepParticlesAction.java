package com.robertx22.age_of_exile.database.data.spells.components.actions.vanity;

import com.robertx22.age_of_exile.database.data.spells.components.MapHolder;
import com.robertx22.age_of_exile.database.data.spells.components.actions.SpellAction;
import com.robertx22.age_of_exile.database.data.spells.spell_classes.SpellCtx;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;

import java.util.Arrays;
import java.util.Collection;

public class SwordSweepParticlesAction extends SpellAction {

    public SwordSweepParticlesAction() {
        super(Arrays.asList());
    }

    @Override
    public void tryActivate(Collection<LivingEntity> targets, SpellCtx ctx, MapHolder data) {
        if (!ctx.world.isClient) {

            if (ctx.caster instanceof PlayerEntity) {
                PlayerEntity p = (PlayerEntity) ctx.caster;
                p.spawnSweepAttackParticles();
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
        return "sword_sweep_particles";
    }
}
