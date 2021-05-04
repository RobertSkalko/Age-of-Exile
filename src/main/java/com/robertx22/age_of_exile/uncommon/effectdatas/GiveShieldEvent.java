package com.robertx22.age_of_exile.uncommon.effectdatas;

import com.robertx22.age_of_exile.saveclasses.unit.AllShieldsData;
import com.robertx22.age_of_exile.uncommon.effectdatas.rework.EventData;
import net.minecraft.entity.LivingEntity;

public class GiveShieldEvent extends EffectEvent {

    public GiveShieldEvent(float amount, int seconds, LivingEntity caster, LivingEntity target) {
        super(amount, caster, target);
        this.data.getNumber(EventData.SECONDS).number = seconds;
    }

    @Override
    protected void activate() {

        if (this.data.isCanceled()) {
            return;
        }

        if (target.isAlive()) {
            int ticks = (int) (data.getNumber(EventData.SECONDS).number * 20);
            this.targetData.getResources()
                .shields.giveShield(new AllShieldsData.ShieldData(data.getNumber(), ticks));

        }
    }

    public static String ID = "on_absorb_damage";

    @Override
    public String GUID() {
        return ID;
    }
}
