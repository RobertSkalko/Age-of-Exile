package com.robertx22.mine_and_slash.database.data.stats.types.offense;

import com.robertx22.mine_and_slash.database.data.stats.Stat;
import com.robertx22.mine_and_slash.uncommon.enumclasses.Elements;

public class AttackSpeed extends Stat {

    public static String GUID = "attack_speed";

    private AttackSpeed() {
        this.BaseFlat = 100; // todo unsure
    }

    public static AttackSpeed getInstance() {
        return SingletonHolder.INSTANCE;
    }

    @Override
    public StatGroup statGroup() {
        return StatGroup.Damage;
    }

    @Override
    public String locDescForLangFile() {
        return "Lowers cooldown of melee attacks.";
    }

    @Override
    public String getIconPath() {
        return "attack_speed";
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
        return "Attack Speed";
    }

    private static class SingletonHolder {
        private static final AttackSpeed INSTANCE = new AttackSpeed();
    }
}
