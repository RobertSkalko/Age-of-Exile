package com.robertx22.age_of_exile.damage_hooks.util;

import com.robertx22.age_of_exile.database.data.spells.spell_classes.bases.MyDamageSource;
import com.robertx22.age_of_exile.uncommon.effectdatas.DamageEvent;
import net.minecraft.util.DamageSource;

public class DmgSourceUtils {

    public static boolean isMyDmgSource(DamageSource source) {
        return source instanceof MyDamageSource || source.getMsgId()
            .equals(DamageEvent.dmgSourceName);
    }

}
