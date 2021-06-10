package com.robertx22.age_of_exile.uncommon.effectdatas;

public class OnMobKilledByDamageEvent extends EffectEvent {

    public static String ID = "on_mob_kill";

    @Override
    public String GUID() {
        return ID;
    }

    public DamageEvent damageEvent;

    public OnMobKilledByDamageEvent(DamageEvent damageEvent) {
        super(damageEvent.source, damageEvent.target);
        this.damageEvent = damageEvent;

        this.data = damageEvent.data;
    }

    @Override
    protected void activate() {

    }
}

