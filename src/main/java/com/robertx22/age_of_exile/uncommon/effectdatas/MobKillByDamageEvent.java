package com.robertx22.age_of_exile.uncommon.effectdatas;

public class MobKillByDamageEvent extends EffectData {

    public static String ID = "on_mob_kill";

    @Override
    public String GUID() {
        return ID;
    }

    public DamageEffect damageEvent;

    public MobKillByDamageEvent(DamageEffect damageEvent) {
        super(damageEvent.source, damageEvent.target);
        this.damageEvent = damageEvent;
    }

    @Override
    protected void activate() {

    }
}

