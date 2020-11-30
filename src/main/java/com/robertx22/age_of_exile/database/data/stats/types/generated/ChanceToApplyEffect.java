package com.robertx22.age_of_exile.database.data.stats.types.generated;

import com.robertx22.age_of_exile.aoe_data.database.exile_effects.adders.NegativeEffects;
import com.robertx22.age_of_exile.database.data.stats.Stat;
import com.robertx22.age_of_exile.database.data.stats.effects.offense.ChanceToApplyPotionEffect;
import com.robertx22.age_of_exile.database.registry.SlashRegistry;
import com.robertx22.age_of_exile.uncommon.effectdatas.AttackType;
import com.robertx22.age_of_exile.uncommon.enumclasses.Elements;
import com.robertx22.age_of_exile.uncommon.interfaces.IStatEffect;
import com.robertx22.age_of_exile.uncommon.interfaces.IStatEffects;

public class ChanceToApplyEffect extends Stat implements IStatEffects {

    public static ChanceToApplyEffect BURN = new ChanceToApplyEffect(NegativeEffects.BURN, "Burn", "burn");
    public static ChanceToApplyEffect CHILL = new ChanceToApplyEffect(NegativeEffects.FROSTBURN, "Frostburn", "chill");
    public static ChanceToApplyEffect POISON = new ChanceToApplyEffect(NegativeEffects.THORNS, "Thorns", "poison");
    public static ChanceToApplyEffect STATIC = new ChanceToApplyEffect(NegativeEffects.SHOCK, "Static", "static");

    String effect;
    String locname;
    String id;

    public ChanceToApplyEffect(String effect, String locname, String id) {
        this.effect = effect;
        this.locname = locname;
        this.id = id;

        this.add$To$toTooltip = false;
        this.add$plusminus$toTooltip = false;
        this.min_val = 0;
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
        return "Chance to apply effect on weapon hits.";
    }

    @Override
    public String locNameForLangFile() {
        return "Chance to Apply " + locname;
    }

    @Override
    public String GUID() {
        return "chance_of_" + id;
    }

    @Override
    public IStatEffect getEffect() {

        return new ChanceToApplyPotionEffect(SlashRegistry.ExileEffects()
            .get(effect), AttackType.ATTACK);
    }
}
