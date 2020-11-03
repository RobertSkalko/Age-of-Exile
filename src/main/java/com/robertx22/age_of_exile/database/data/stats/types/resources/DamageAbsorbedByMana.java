package com.robertx22.age_of_exile.database.data.stats.types.resources;

import com.robertx22.age_of_exile.database.data.stats.Stat;
import com.robertx22.age_of_exile.database.data.stats.StatScaling;
import com.robertx22.age_of_exile.database.data.stats.effects.game_changers.ManaBatteryEffect;
import com.robertx22.age_of_exile.uncommon.enumclasses.Elements;
import com.robertx22.age_of_exile.uncommon.interfaces.IStatEffect;
import com.robertx22.age_of_exile.uncommon.interfaces.IStatEffects;

public class DamageAbsorbedByMana extends Stat implements IStatEffects {
    public static String GUID = "mana_shield";

    private DamageAbsorbedByMana() {
        this.min_val = 0;
        this.scaling = StatScaling.NONE;
        this.statGroup = StatGroup.MAIN;

        this.add$To$toTooltip = false;
    }

    public static DamageAbsorbedByMana getInstance() {
        return DamageAbsorbedByMana.SingletonHolder.INSTANCE;
    }

    @Override
    public String locDescForLangFile() {
        return "Percent of damage is absorbed by mana. This effect only works if mana is not less than 50 percent capacity. Mana shield is applied after magic shield.";
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
        return true;
    }

    @Override
    public String locNameForLangFile() {
        return "Of Damage Absorbed By Mana";
    }

    @Override
    public IStatEffect getEffect() {
        return ManaBatteryEffect.INSTANCE;
    }

    private static class SingletonHolder {
        private static final DamageAbsorbedByMana INSTANCE = new DamageAbsorbedByMana();
    }
}
