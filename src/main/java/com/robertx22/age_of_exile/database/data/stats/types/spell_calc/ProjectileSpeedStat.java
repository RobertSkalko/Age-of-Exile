package com.robertx22.age_of_exile.database.data.stats.types.spell_calc;

import com.robertx22.age_of_exile.database.data.stats.Stat;
import com.robertx22.age_of_exile.database.data.stats.effects.spell_calc.ProjectileSpeedEffect;
import com.robertx22.age_of_exile.uncommon.enumclasses.Elements;
import com.robertx22.age_of_exile.uncommon.interfaces.IStatEffect;
import com.robertx22.age_of_exile.uncommon.interfaces.IStatEffects;

public class ProjectileSpeedStat extends Stat implements IStatEffects {

    private ProjectileSpeedStat() {
        this.max_val = 200;
    }

    public static ProjectileSpeedStat getInstance() {
        return ProjectileSpeedStat.SingletonHolder.INSTANCE;
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
        return "Makes spell projectiles faster";
    }

    @Override
    public String locNameForLangFile() {
        return "Faster Projectiles";
    }

    @Override
    public String GUID() {
        return "faster_projectiles";
    }

    @Override
    public IStatEffect getEffect() {
        return new ProjectileSpeedEffect();
    }

    private static class SingletonHolder {
        private static final ProjectileSpeedStat INSTANCE = new ProjectileSpeedStat();
    }
}

