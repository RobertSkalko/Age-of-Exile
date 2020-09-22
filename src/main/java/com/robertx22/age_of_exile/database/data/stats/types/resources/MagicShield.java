package com.robertx22.age_of_exile.database.data.stats.types.resources;

import com.robertx22.age_of_exile.database.data.stats.ILocalStat;
import com.robertx22.age_of_exile.database.data.stats.Stat;
import com.robertx22.age_of_exile.database.data.stats.StatScaling;
import com.robertx22.age_of_exile.database.data.stats.effects.defense.MagicShieldEffect;
import com.robertx22.age_of_exile.uncommon.enumclasses.Elements;
import com.robertx22.age_of_exile.uncommon.interfaces.IStatEffect;
import com.robertx22.age_of_exile.uncommon.interfaces.IStatEffects;

public class MagicShield extends Stat implements IStatEffects, ILocalStat {
    public static String GUID = "magic_shield";

    private MagicShield() {
        this.min_val = 0;
        this.scaling = StatScaling.SCALING;
    }

    public static MagicShield getInstance() {
        return SingletonHolder.INSTANCE;
    }

    @Override
    public String getIconPath() {
        return "resource/magic_shield";
    }

    @Override
    public StatGroup statGroup() {
        return StatGroup.Main;
    }

    @Override
    public String locDescForLangFile() {
        return "Works like extra health. But regen is separate.";
    }

    @Override
    public String GUID() {
        return GUID;
    }

    @Override
    public Elements getElement() {
        return null;
    }

    @Override
    public boolean IsPercent() {
        return false;
    }

    @Override
    public String locNameForLangFile() {
        return "Magic Shield";
    }

    @Override
    public IStatEffect getEffect() {
        return MagicShieldEffect.INSTANCE;
    }

    private static class SingletonHolder {
        private static final MagicShield INSTANCE = new MagicShield();
    }
}
