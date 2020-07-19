package com.robertx22.mine_and_slash.event_hooks.entity.damage;

import com.robertx22.mine_and_slash.database.data.spells.spell_classes.bases.MyDamageSource;
import com.robertx22.mine_and_slash.uncommon.effectdatas.DamageEffect;
import net.minecraft.entity.damage.DamageSource;

public class DmgSourceUtils {

    private static String MARKER = "dmgismineandconverted";

    public static boolean isMyDmgSource(DamageSource source) {
        return source instanceof MyDamageSource || source.getName()
            .equals(DamageEffect.dmgSourceName) || source.name.contains(MARKER);
    }

    public static void markSourceAsMine(DamageSource source) {
        if (!(source instanceof MyDamageSource)) {
            if (!source.name.contains(MARKER)) {
                //  TODO source.name = source.name + MARKER;
            }
        }
    }

    public static void removeSourceMarker(DamageSource source) {
        // TODO source.name = source.name.replaceAll(MARKER, "");

    }

}
