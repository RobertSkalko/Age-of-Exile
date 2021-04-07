package com.robertx22.age_of_exile.database.data.stats.types.bonus_dmg_to_status_affected;

import com.robertx22.age_of_exile.aoe_data.database.exile_effects.adders.NegativeEffects;
import com.robertx22.age_of_exile.database.data.stats.Stat;
import com.robertx22.age_of_exile.database.data.stats.StatScaling;
import com.robertx22.age_of_exile.database.data.stats.effects.base.BaseDamageIncreaseEffect;
import com.robertx22.age_of_exile.database.data.stats.name_regex.StatNameRegex;
import com.robertx22.age_of_exile.database.registry.Database;
import com.robertx22.age_of_exile.saveclasses.unit.StatData;
import com.robertx22.age_of_exile.uncommon.effectdatas.DamageEffect;
import com.robertx22.age_of_exile.uncommon.enumclasses.Elements;

public class BonusDmgToStatusAffected extends Stat {

    public static BonusDmgToStatusAffected FROST = new BonusDmgToStatusAffected(NegativeEffects.FROSTBURN, "Chilled", "chill");
    public static BonusDmgToStatusAffected BURN = new BonusDmgToStatusAffected(NegativeEffects.BURN, "Burning", "burn");
    public static BonusDmgToStatusAffected POISON = new BonusDmgToStatusAffected(NegativeEffects.POISON, "Poisoned", "poison");

    String affectedName;
    String id;

    private BonusDmgToStatusAffected(String effect, String affectedName, String id) {
        this.statEffect = new Effect(effect);
        this.affectedName = affectedName;
        this.id = id;
        this.scaling = StatScaling.NONE;
    }

    public StatNameRegex getStatNameRegex() {
        return StatNameRegex.VALUE_PLUS_NAME;
    }

    @Override
    public boolean IsPercent() {
        return true;
    }

    @Override
    public Elements getElement() {
        return null;
    }

    @Override
    public String locDescForLangFile() {
        return "Increases dmg if enemy is affected by: " + affectedName;
    }

    @Override
    public String locNameForLangFile() {
        return "Damage to " + affectedName + " Enemies";
    }

    @Override
    public String GUID() {
        return id + "_bonus_dmg";
    }

    private static class Effect extends BaseDamageIncreaseEffect {

        String eff;

        public Effect(String eff) {
            this.eff = eff;
        }

        @Override
        public boolean canActivate(DamageEffect effect, StatData data, Stat stat) {
            try {
                return effect.target.hasStatusEffect(Database.ExileEffects()
                    .get(eff)
                    .getStatusEffect());
            } catch (Exception e) {
                e.printStackTrace();
            }
            return false;
        }

    }

}
