package com.robertx22.age_of_exile.database.data.stats.types.offense;

import com.robertx22.age_of_exile.database.data.stats.Stat;
import com.robertx22.age_of_exile.database.data.stats.StatScaling;
import com.robertx22.age_of_exile.database.data.stats.effects.offense.damage_increase.ProjectileDamageEffect;
import com.robertx22.age_of_exile.uncommon.enumclasses.Elements;
import com.robertx22.age_of_exile.uncommon.interfaces.IStatEffect;
import com.robertx22.age_of_exile.uncommon.interfaces.IStatEffects;

public class ProjectileDamage extends Stat implements IStatEffects {

    public static String GUID = "projectile_damage";

    private ProjectileDamage() {
        this.scaling = StatScaling.NONE;
    }

    public static ProjectileDamage getInstance() {
        return ProjectileDamage.SingletonHolder.INSTANCE;
    }

    @Override
    public String locDescForLangFile() {
        return "Affects projectile damage, includes projectile spells like fireballs, and ranged basic attacks.";
    }

    @Override
    public String GUID() {
        return GUID;
    }

    @Override
    public Elements getElement() {
        return Elements.Physical;
    }

    @Override
    public boolean IsPercent() {
        return true;
    }

    @Override
    public String locNameForLangFile() {
        return "Projectile Damage";
    }

    @Override
    public IStatEffect getEffect() {
        return ProjectileDamageEffect.getInstance();
    }

    private static class SingletonHolder {
        private static final ProjectileDamage INSTANCE = new ProjectileDamage();
    }
}
