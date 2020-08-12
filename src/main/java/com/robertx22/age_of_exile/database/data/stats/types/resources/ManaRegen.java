package com.robertx22.age_of_exile.database.data.stats.types.resources;

import com.robertx22.age_of_exile.database.data.stats.StatScaling;
import net.minecraft.util.Formatting;

public class ManaRegen extends BaseRegenClass {
    public static String GUID = "mana_regen";

    public static ManaRegen getInstance() {
        return SingletonHolder.INSTANCE;
    }

    @Override
    public StatScaling getScaling() {
        return StatScaling.SCALING;
    }

    @Override
    public Formatting getIconFormat() {
        return Formatting.AQUA;
    }

    @Override
    public String getIcon() {
        return "\u0E51";
    }

    @Override
    public String getIconPath() {
        return "regen/mana_regen";
    }

    @Override
    public StatGroup statGroup() {
        return StatGroup.Main;
    }

    private ManaRegen() {
        this.minimumValue = 0;
    }

    @Override
    public String GUID() {
        return GUID;
    }

    @Override
    public String locNameForLangFile() {
        return "Mana Regen";
    }

    private static class SingletonHolder {
        private static final ManaRegen INSTANCE = new ManaRegen();
    }
}
