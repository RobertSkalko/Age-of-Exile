package com.robertx22.age_of_exile.database.data.stats.types.offense;

import com.robertx22.age_of_exile.database.data.stats.Stat;
import com.robertx22.age_of_exile.database.data.stats.StatScaling;
import com.robertx22.age_of_exile.database.data.stats.effects.base.BaseDamageIncreaseEffect;
import com.robertx22.age_of_exile.saveclasses.unit.StatData;
import com.robertx22.age_of_exile.uncommon.effectdatas.DamageEffect;
import com.robertx22.age_of_exile.uncommon.effectdatas.EffectUtils;
import com.robertx22.age_of_exile.uncommon.enumclasses.Elements;
import net.minecraft.util.Formatting;

public class ProjectileDamage extends Stat {

    public static String GUID = "projectile_damage";

    private ProjectileDamage() {
        this.scaling = StatScaling.NONE;
        this.statEffect = new Effect();

        this.textIcon = "\u27B9";
        this.textFormat = Formatting.RED;
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

    private static class Effect extends BaseDamageIncreaseEffect {

        @Override
        public boolean canActivate(DamageEffect effect, StatData data, Stat stat) {
            return EffectUtils.isProjectileAttack(effect);
        }

    }

    private static class SingletonHolder {
        private static final ProjectileDamage INSTANCE = new ProjectileDamage();
    }
}
