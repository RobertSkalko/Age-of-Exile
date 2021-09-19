package com.robertx22.age_of_exile.database.data.spells.components.actions.vanity;

import com.robertx22.age_of_exile.database.data.spells.components.MapHolder;
import com.robertx22.age_of_exile.database.data.spells.components.actions.SpellAction;
import com.robertx22.age_of_exile.database.data.spells.spell_classes.SpellCtx;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.entity.projectile.EvokerFangs;

import java.util.Arrays;
import java.util.Collection;

public class SummonEvokerFangsAction extends SpellAction {

    public SummonEvokerFangsAction() {
        super(Arrays.asList());
    }

    @Override
    public void tryActivate(Collection<LivingEntity> targets, SpellCtx ctx, MapHolder data) {
        if (!ctx.world.isClientSide) {
            targets.forEach(t -> {
                Vector3d p = t.position();
                t.level.addFreshEntity(new EvokerFangs(t.level, p.x, p.y, p.z, 0F, 0, ctx.caster));
            });
        }
    }

    public MapHolder create() {
        MapHolder dmg = new MapHolder();
        dmg.type = GUID();
        return dmg;
    }

    @Override
    public String GUID() {
        return "summon_evoker_fang";
    }
}

