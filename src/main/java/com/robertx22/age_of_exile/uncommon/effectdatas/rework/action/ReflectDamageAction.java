package com.robertx22.age_of_exile.uncommon.effectdatas.rework.action;

import com.robertx22.age_of_exile.database.data.stats.Stat;
import com.robertx22.age_of_exile.saveclasses.unit.StatData;
import com.robertx22.age_of_exile.uncommon.effectdatas.DamageEvent;
import com.robertx22.age_of_exile.uncommon.effectdatas.EffectEvent;
import com.robertx22.age_of_exile.uncommon.effectdatas.EventBuilder;
import com.robertx22.age_of_exile.uncommon.effectdatas.rework.EventData;
import com.robertx22.age_of_exile.uncommon.effectdatas.rework.number_provider.NumberProvider;
import com.robertx22.age_of_exile.uncommon.enumclasses.AttackType;
import com.robertx22.age_of_exile.uncommon.interfaces.EffectSides;
import net.minecraft.entity.LivingEntity;

public class ReflectDamageAction extends StatEffect {

    NumberProvider num_provider = new NumberProvider();

    public ReflectDamageAction(String id, NumberProvider num) {
        super(id, "reflect_dmg");
        this.num_provider = num;
    }

    ReflectDamageAction() {
        super("", "reflect_dmg");
    }

    @Override
    public void activate(EffectEvent event, EffectSides statSource, StatData data, Stat stat) {

        LivingEntity en = event.getSide(statSource);
        LivingEntity target = event.source;

        float val = num_provider.getValue(event, en, data);

        DamageEvent dmg = EventBuilder.ofDamage(en, target, val)
            .setupDamage(AttackType.reflect, event.data.getWeaponType(), event.data.getStyle())
            .set(x -> x.data.setElement(event.data.getElement()))
            .build();

        dmg.data.setBoolean(EventData.DISABLE_KNOCKBACK, true);

        dmg.Activate();
    }

    @Override
    public Class<? extends StatEffect> getSerClass() {
        return ReflectDamageAction.class;
    }

}
