package com.robertx22.age_of_exile.uncommon.effectdatas.builders;

import com.ibm.icu.impl.Assert;
import com.robertx22.age_of_exile.uncommon.effectdatas.DamageEvent;
import com.robertx22.age_of_exile.uncommon.effectdatas.EventBuilder;
import com.robertx22.age_of_exile.uncommon.effectdatas.rework.EventData;
import com.robertx22.age_of_exile.uncommon.enumclasses.AttackType;
import com.robertx22.age_of_exile.uncommon.enumclasses.PlayStyle;
import com.robertx22.age_of_exile.uncommon.enumclasses.WeaponTypes;

public class DamageBuilder extends EventBuilder<DamageEvent> {

    boolean isSetup = false;

    public DamageBuilder setupDamage(AttackType effectType, WeaponTypes weptype, PlayStyle style) {
        event.data.setString(EventData.STYLE, style.name());
        event.data.setString(EventData.WEAPON_TYPE, weptype.name());
        event.data.setString(EventData.ATTACK_TYPE, effectType.name());
        return this;
    }

    public DamageBuilder setIsBasicAttack() {
        event.data.setBoolean(EventData.IS_BASIC_ATTACK, true);
        return this;
    }

    @Override
    protected void buildCheck() {
        Assert.assrt(isSetup);
    }

}
