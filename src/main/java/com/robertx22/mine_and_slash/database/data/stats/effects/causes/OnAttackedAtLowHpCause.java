package com.robertx22.mine_and_slash.database.data.stats.effects.causes;

import com.robertx22.mine_and_slash.uncommon.effectdatas.DamageEffect;
import com.robertx22.mine_and_slash.uncommon.effectdatas.EffectData;

public class OnAttackedAtLowHpCause extends BaseCause {

    public OnAttackedAtLowHpCause(int percenthp) {
        this.percenthp = percenthp;
    }

    private int percenthp = 10;

    @Override
    public boolean shouldActivate(EffectData Effect) {

        if (Effect instanceof DamageEffect) {

            DamageEffect dmgeffect = (DamageEffect) Effect;

            if (dmgeffect.targetUnit.health()
                .CurrentValue(dmgeffect.source, dmgeffect.sourceUnit) < dmgeffect.targetUnit
                .healthData()
                .getAverageValue() / 100 * percenthp) {
                return true;
            }

        }
        return false;
    }

}
