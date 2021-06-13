package com.robertx22.age_of_exile.database.data.spells.components.actions.vanity;

import com.robertx22.age_of_exile.database.data.spells.components.MapHolder;
import com.robertx22.age_of_exile.database.data.spells.components.actions.SpellAction;
import com.robertx22.age_of_exile.database.data.spells.spell_classes.SpellCtx;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.mob.EvokerFangsEntity;
import net.minecraft.util.math.Vec3d;

import java.util.Arrays;
import java.util.Collection;

public class SummonEvokerFangsAction extends SpellAction {

    public SummonEvokerFangsAction() {
        super(Arrays.asList());
    }

    @Override
    public void tryActivate(Collection<LivingEntity> targets, SpellCtx ctx, MapHolder data) {
        if (!ctx.world.isClient) {
            targets.forEach(t -> {
                Vec3d p = t.getPos();
                t.world.spawnEntity(new EvokerFangsEntity(t.world, p.x, p.y, p.z, 0F, 0, ctx.caster));
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

