package com.robertx22.age_of_exile.database.data.spells.contexts;

import com.robertx22.age_of_exile.saveclasses.item_classes.CalculatedSpellData;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.math.BlockPos;

import java.util.Objects;

public class SpellCtx {

    // the entity the effect came from, player summons fireball. fireball hits enemy, dmg comes from fireball
    public Entity sourceEntity;

    public LivingEntity caster;
    public LivingEntity target;

    public BlockPos pos;

    private SpellCtx(Entity sourceEntity, LivingEntity caster, LivingEntity target, BlockPos pos, CalculatedSpellData calculatedSpellData) {
        this.sourceEntity = sourceEntity;
        this.caster = caster;
        this.target = target;
        this.pos = pos;
        this.calculatedSpellData = calculatedSpellData;
    }

    public static SpellCtx onCast(LivingEntity caster, CalculatedSpellData data) {
        Objects.requireNonNull(caster);
        Objects.requireNonNull(data);
        return new SpellCtx(caster, caster, caster, caster.getBlockPos(), data);
    }

    public static SpellCtx onHit(LivingEntity caster, Entity sourceEntity, LivingEntity target, CalculatedSpellData data) {
        Objects.requireNonNull(caster);
        Objects.requireNonNull(sourceEntity);
        Objects.requireNonNull(target);
        Objects.requireNonNull(data);
        return new SpellCtx(sourceEntity, caster, target, target.getBlockPos(), data);
    }

    public static SpellCtx onTick(LivingEntity caster, Entity sourceEntity, CalculatedSpellData data) {
        Objects.requireNonNull(caster);
        Objects.requireNonNull(sourceEntity);
        Objects.requireNonNull(data);
        return new SpellCtx(sourceEntity, caster, null, sourceEntity.getBlockPos(), data);
    }

    public CalculatedSpellData calculatedSpellData;

}
