package com.robertx22.age_of_exile.database.data.stats.types.resources;

import com.robertx22.age_of_exile.database.data.stats.Stat;
import com.robertx22.age_of_exile.database.data.stats.StatScaling;
import com.robertx22.age_of_exile.saveclasses.unit.Unit;
import com.robertx22.age_of_exile.uncommon.enumclasses.Elements;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.Formatting;

public class Health extends Stat {
    public static String GUID = "health";

    private Health() {
        this.minimumValue = 1;
    }

    public static Health getInstance() {
        return SingletonHolder.INSTANCE;
    }

    @Override
    public StatScaling getScaling() {
        return StatScaling.SCALING;
    }

    @Override
    public Formatting getIconFormat() {
        return Formatting.RED;
    }

    @Override
    public String getIcon() {
        return "\u2764";
    }

    @Override
    public String getIconPath() {
        return "resource/hp";
    }

    @Override
    public StatGroup statGroup() {
        return StatGroup.Main;
    }

    @Override
    public String locDescForLangFile() {
        return "Increases your total hearts amount.";
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

    public int CurrentValue(LivingEntity entity, Unit unit) {

        return (int) entity.getHealth();

    }

    @Override
    public String locNameForLangFile() {
        return "Maximum Health";
    }

    private static class SingletonHolder {
        private static final Health INSTANCE = new Health();
    }
}
