package com.robertx22.mine_and_slash.database.data.stats.types.bonus_dmg_to_status_affected;

import com.robertx22.mine_and_slash.database.data.stats.Stat;
import com.robertx22.mine_and_slash.database.data.stats.effects.offense.BonusDmgToAffectedEffect;
import com.robertx22.mine_and_slash.database.data.stats.name_regex.StatNameRegex;
import com.robertx22.mine_and_slash.vanilla_mc.potion_effects.bases.BasePotionEffect;
import com.robertx22.mine_and_slash.vanilla_mc.potion_effects.druid.PoisonEffect;
import com.robertx22.mine_and_slash.vanilla_mc.potion_effects.ember_mage.BurnEffect;
import com.robertx22.mine_and_slash.vanilla_mc.potion_effects.ocean_mystic.FrostEffect;
import com.robertx22.mine_and_slash.vanilla_mc.potion_effects.shaman.StaticEffect;
import com.robertx22.mine_and_slash.uncommon.enumclasses.Elements;
import com.robertx22.mine_and_slash.uncommon.interfaces.IStatEffect;
import com.robertx22.mine_and_slash.uncommon.interfaces.IStatEffects;

public class BonusDmgToStatusAffected extends Stat implements IStatEffects {

    public static BonusDmgToStatusAffected FROST = new BonusDmgToStatusAffected(FrostEffect.INSTANCE, "Chilled");
    public static BonusDmgToStatusAffected BURN = new BonusDmgToStatusAffected(BurnEffect.INSTANCE, "Burning");
    public static BonusDmgToStatusAffected POISON = new BonusDmgToStatusAffected(PoisonEffect.INSTANCE, "Poisoned");
    public static BonusDmgToStatusAffected STATIC = new BonusDmgToStatusAffected(StaticEffect.INSTANCE, "Charged");

    BasePotionEffect effect;
    String affectedName;

    private BonusDmgToStatusAffected(BasePotionEffect effect, String affectedName) {
        this.effect = effect;
        this.affectedName = affectedName;
    }

    public StatNameRegex getStatNameRegex() {
        return StatNameRegex.STATUS_EFFECT;
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
        return "Increases dmg if enemy is affected by: " + effect.locNameForLangFile();
    }

    @Override
    public String locNameForLangFile() {
        return "Damage to " + affectedName + " Enemies";
    }

    @Override
    public String GUID() {
        return effect.GUID() + "_bonus_dmg";
    }

    @Override
    public IStatEffect getEffect() {
        return new BonusDmgToAffectedEffect(effect);
    }
}
