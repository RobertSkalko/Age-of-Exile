package com.robertx22.age_of_exile.uncommon.effectdatas;

public class EffectUtils {

    public static boolean isProjectileAttack(DamageEffect effect) {

        if (effect.weaponType.isProjectile) {
            return true;
        }

        if (effect instanceof SpellDamageEffect) {
            SpellDamageEffect spell = (SpellDamageEffect) effect;
            if (spell.getSpell()
                .getConfig()
                .isProjectile()) {
                return true;
            }
        }
        return false;

    }

}
