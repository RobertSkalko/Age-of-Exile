package com.robertx22.mine_and_slash.database.data.stats.types.resources;

import com.robertx22.mine_and_slash.database.data.stats.StatScaling;
import com.robertx22.mine_and_slash.uncommon.enumclasses.Elements;
import net.minecraft.util.Formatting;

public class MagicShieldRegen extends BaseRegenClass {

    public static String GUID = "magic_shield_regen";

    private MagicShieldRegen() {

    }

    @Override
    public StatScaling getScaling() {
        return StatScaling.SCALING;
    }

    public static MagicShieldRegen getInstance() {
        return SingletonHolder.INSTANCE;
    }

    @Override
    public String getIconPath() {
        return "regen/magic_shield_regen";
    }

    @Override
    public Formatting getIconFormat() {
        return Formatting.BLUE;
    }

    @Override
    public String getIcon() {
        return "\u0E51";
    }

    @Override
    public StatGroup statGroup() {
        return StatGroup.Main;
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
        return "Magic Shield Regen";
    }

    private static class SingletonHolder {
        private static final MagicShieldRegen INSTANCE = new MagicShieldRegen();
    }
}
