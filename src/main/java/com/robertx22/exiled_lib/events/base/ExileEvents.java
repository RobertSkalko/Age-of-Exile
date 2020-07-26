package com.robertx22.exiled_lib.events.base;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;

public class ExileEvents {

    public static ExileEventCaller<OnEntityTick, NothingData> LIVING_ENTITY_TICK = new ExileEventCaller<>();
    public static ExileEventCaller<OnMobExpDrop, ExpData> MOB_EXP_DROP = new ExileEventCaller<>();
    public static ExileEventCaller<OnMobDeath, NothingData> MOB_DEATH = new ExileEventCaller<>();

    public static ExileEventCaller<OnDamageEntity, DamageData> DAMAGE_BEFORE_CALC = new ExileEventCaller<>();
    public static ExileEventCaller<OnDamageEntity, DamageData> DAMAGE_AFTER_CALC = new ExileEventCaller<>();

    public static abstract class OnEntityTick extends ExileEvent {

        public abstract void onTick(LivingEntity entity);
    }

    public static abstract class OnMobExpDrop extends ExileEvent {

        public abstract void onExp(LivingEntity mobKilled);
    }

    public static abstract class OnMobDeath extends ExileEvent {

        public abstract void onDeath(LivingEntity mob);
    }

    public static abstract class OnDamageEntity extends ExileEvent {

        public abstract void onDamage(LivingEntity mob, float damage, DamageSource source, DamageData data);
    }

    public static class ExpData {

        public float exp;

        public ExpData(float exp) {
            this.exp = exp;
        }
    }

    public static class DamageData {

        public float damage;

        public DamageData(float damage) {
            this.damage = damage;
        }
    }

    public static class NothingData {

    }

}
