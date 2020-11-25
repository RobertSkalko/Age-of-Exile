package com.robertx22.age_of_exile.uncommon.effectdatas;

public class EffectUtils {

    public static boolean isConsideredAWeaponAttack(DamageEffect effect) {

        if (effect.effectType == EffectData.EffectTypes.BASIC_ATTACK) {
            return true;
        }

        if (effect instanceof SpellDamageEffect) {
            SpellDamageEffect sdmg = (SpellDamageEffect) effect;
            if (sdmg.getSpell()
                .getConfig().style == AttackPlayStyle.MELEE) {
                return true;
            }
            if (sdmg.getSpell()
                .getConfig().style == AttackPlayStyle.RANGED) {
                return true;
            }

        }

        return false;
    }

    public static boolean isConsideredASpellAttack(DamageEffect effect) {

        if (effect instanceof SpellDamageEffect) {
            SpellDamageEffect sdmg = (SpellDamageEffect) effect;
            if (sdmg.getSpell()
                .getConfig().style == AttackPlayStyle.MAGIC) {
                return true;
            }

        }

        return false;
    }

}
