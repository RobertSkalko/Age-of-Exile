package com.robertx22.age_of_exile.database.data.spells.spell_classes;

import com.robertx22.age_of_exile.database.data.spells.components.EntityActivation;
import com.robertx22.age_of_exile.database.data.spells.entities.EntitySavedSpellData;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;

public class SpellCtx {

    // the entity the effect came from, player summons fireball. fireball hits enemy, dmg comes from fireball
    public Entity sourceEntity;

    public World world;
    public LivingEntity caster;

    @Nullable
    public LivingEntity target;

    public BlockPos pos;
    public Vec3d vecPos;

    public final EntityActivation activation;

    public EntitySavedSpellData calculatedSpellData;

    private SpellCtx(EntityActivation act, Entity sourceEntity, LivingEntity caster, LivingEntity target, BlockPos pos, Vec3d vec, EntitySavedSpellData calculatedSpellData) {
        this.sourceEntity = sourceEntity;
        this.caster = caster;
        this.target = target;
        this.pos = pos;
        this.calculatedSpellData = calculatedSpellData;
        this.world = caster.world;
        this.vecPos = vec;
        this.activation = act;

    }

    public static SpellCtx onCast(LivingEntity caster, EntitySavedSpellData data) {
        Objects.requireNonNull(caster);
        Objects.requireNonNull(data);
        return new SpellCtx(EntityActivation.ON_CAST, caster, caster, caster, caster.getBlockPos(), caster.getPos(), data);
    }

    public static SpellCtx onHit(LivingEntity caster, Entity sourceEntity, LivingEntity target, EntitySavedSpellData data) {
        Objects.requireNonNull(caster);
        Objects.requireNonNull(sourceEntity);
        Objects.requireNonNull(data);
        return new SpellCtx(EntityActivation.ON_HIT, sourceEntity, caster, target, target.getBlockPos(), target.getPos(), data);
    }

    public static SpellCtx onEntityHit(SpellCtx ctx, LivingEntity target) {
        Objects.requireNonNull(ctx);
        Objects.requireNonNull(target);
        return new SpellCtx(EntityActivation.PER_ENTITY_HIT, ctx.sourceEntity, ctx.caster, target, target.getBlockPos(), target.getPos(), ctx.calculatedSpellData);
    }

    public static SpellCtx onExpire(LivingEntity caster, Entity sourceEntity, EntitySavedSpellData data) {
        Objects.requireNonNull(caster);
        Objects.requireNonNull(sourceEntity);
        Objects.requireNonNull(data);
        return new SpellCtx(EntityActivation.ON_EXPIRE, sourceEntity, caster, null, sourceEntity.getBlockPos(), sourceEntity.getPos(), data);
    }


    public static SpellCtx onTick(LivingEntity caster, Entity sourceEntity, EntitySavedSpellData data) {
        Objects.requireNonNull(caster);
        Objects.requireNonNull(sourceEntity);
        Objects.requireNonNull(data);
        LivingEntity target = sourceEntity instanceof LivingEntity ? (LivingEntity) sourceEntity : null;
        return new SpellCtx(EntityActivation.ON_TICK, sourceEntity, caster, target, sourceEntity.getBlockPos(), sourceEntity.getPos(), data);
    }

}
