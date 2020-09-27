package com.robertx22.age_of_exile.database.data.stats.types.bonus_dmg_to_status_affected;

import com.robertx22.age_of_exile.aoe_data.database.exile_effects.adders.NegativeEffects;
import com.robertx22.age_of_exile.database.data.stats.Stat;
import com.robertx22.age_of_exile.database.data.stats.StatScaling;
import com.robertx22.age_of_exile.database.data.stats.effects.offense.DamageToAffectedEffect;
import com.robertx22.age_of_exile.database.data.stats.name_regex.StatNameRegex;
import com.robertx22.age_of_exile.database.registry.SlashRegistry;
import com.robertx22.age_of_exile.uncommon.enumclasses.Elements;
import com.robertx22.age_of_exile.uncommon.interfaces.IStatEffect;
import com.robertx22.age_of_exile.uncommon.interfaces.IStatEffects;

public class BonusDmgToStatusAffected extends Stat implements IStatEffects {

    public static BonusDmgToStatusAffected FROST = new BonusDmgToStatusAffected(NegativeEffects.CHILL, "Chilled", "chill");
    public static BonusDmgToStatusAffected BURN = new BonusDmgToStatusAffected(NegativeEffects.BURN, "Burning", "burn");
    public static BonusDmgToStatusAffected POISON = new BonusDmgToStatusAffected(NegativeEffects.THORNS, "Poisoned", "poison");
    public static BonusDmgToStatusAffected STATIC = new BonusDmgToStatusAffected(NegativeEffects.STATIC, "Charged", "static");

    String effect;
    String affectedName;
    String id;

    private BonusDmgToStatusAffected(String effect, String affectedName, String id) {
        this.effect = effect;
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

    @Override
    public IStatEffect getEffect() {
        return new DamageToAffectedEffect(SlashRegistry.ExileEffects()
            .get(effect));
    }
}
