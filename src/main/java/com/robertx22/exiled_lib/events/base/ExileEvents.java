package com.robertx22.exiled_lib.events.base;

import net.minecraft.entity.LivingEntity;

public class ExileEvents {

    public static ExileEventCaller<OnEntityTick, NothingData> LIVING_ENTITY_TICK = new ExileEventCaller<>();
    public static ExileEventCaller<OnMobExpDrop, ExpData> MOB_EXP_DROP = new ExileEventCaller<>();
    public static ExileEventCaller<OnMobDeath, NothingData> MOB_DEATH = new ExileEventCaller<>();

    public static abstract class OnEntityTick extends ExileEvent {

        public abstract void onTick(LivingEntity entity);
    }

    public static abstract class OnMobExpDrop extends ExileEvent {

        public abstract void onExp(LivingEntity mobKilled);
    }

    public static abstract class OnMobDeath extends ExileEvent {

        public abstract void onDeath(LivingEntity mob);
    }

    public static class ExpData {

        public float exp;

        public ExpData(float exp) {
            this.exp = exp;
        }
    }

    public static class NothingData {

    }

}
