package com.robertx22.exiled_lib.events.base;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;

public class ExileEvents {

    public static ExileEventCaller<OnEntityTick> LIVING_ENTITY_TICK = new ExileEventCaller<>();
    public static ExileEventCaller<OnMobExpDrop> MOB_EXP_DROP = new ExileEventCaller<>();
    public static ExileEventCaller<OnMobDeath> MOB_DEATH = new ExileEventCaller<>();

    public static ExileEventCaller<OnDamageEntity> DAMAGE_BEFORE_CALC = new ExileEventCaller<>();
    public static ExileEventCaller<OnDamageEntity> DAMAGE_AFTER_CALC = new ExileEventCaller<>();

    public static class OnEntityTick extends ExileEvent {
        public LivingEntity entity;

        public OnEntityTick(LivingEntity entity) {
            this.entity = entity;
        }
    }

    public static class OnMobExpDrop extends ExileEvent {
        public LivingEntity mobKilled;
        public float exp;

        public OnMobExpDrop(LivingEntity mobKilled, float exp) {
            this.mobKilled = mobKilled;
            this.exp = exp;
        }
    }

    public static class OnMobDeath extends ExileEvent {
        public LivingEntity mob;

        public OnMobDeath(LivingEntity mob) {
            this.mob = mob;
        }
    }

    public static class OnDamageEntity extends ExileEvent {
        public DamageSource source;
        public float damage;
        public LivingEntity mob;

        public OnDamageEntity(DamageSource source, float damage, LivingEntity mob) {
            this.source = source;
            this.damage = damage;
            this.mob = mob;
        }
    }

}
