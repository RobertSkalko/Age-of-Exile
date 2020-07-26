package com.robertx22.exiled_lib.events.base;

import net.minecraft.entity.LivingEntity;

public class ExileEvents {

    public static ExileEventCaller<OnEntityTick> LIVING_ENTITY_TICK = new ExileEventCaller<>();

    public static abstract class OnEntityTick extends ExileEvent {

        public abstract void onTick(LivingEntity entity);
    }
}
