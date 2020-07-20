package com.robertx22.mine_and_slash.event_hooks.entity.damage;

import com.robertx22.mine_and_slash.database.data.spells.spell_classes.bases.MyDamageSource;
import com.robertx22.mine_and_slash.uncommon.effectdatas.DamageEffect;
import net.minecraft.entity.damage.DamageSource;

public class DmgSourceUtils {

    public static boolean isMyDmgSource(DamageSource source) {
        return source instanceof MyDamageSource || source.getName()
            .equals(DamageEffect.dmgSourceName);
    }

}
