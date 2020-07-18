package com.robertx22.mine_and_slash.database.data.stats.effects.causes;

import com.robertx22.mine_and_slash.uncommon.effectdatas.DamageEffect;
import com.robertx22.mine_and_slash.uncommon.effectdatas.EffectData;

public class OnAttackDodgedCause extends BaseCause {

    @Override
    public boolean shouldActivate(EffectData Effect) {

        if (Effect instanceof DamageEffect) {

            DamageEffect dmgeffect = (DamageEffect) Effect;

            if (dmgeffect.isDodged) {
                return true;
            }

        }
        return false;
    }

}

