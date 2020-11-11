package com.robertx22.age_of_exile.saveclasses.player_skills;

import com.robertx22.age_of_exile.uncommon.utilityclasses.LevelUtils;
import info.loenwind.autosave.annotations.Storable;
import info.loenwind.autosave.annotations.Store;

@Storable
public class PlayerSkillData {

    @Store
    private int lvl = 1;
    @Store
    private int exp = 0;

    public boolean addExp(int addExp) {
        this.exp += addExp;

        int needed = getExpNeededToLevel();

        if (this.exp >= needed) {
            this.exp -= needed;
            lvl++;
            return true;
        }

        return false;
    }

    public int getLvl() {
        return lvl;
    }

    public int getExp() {
        return exp;
    }

    public int getExpNeededToLevel() {
        return (int) (LevelUtils.getExpRequiredForLevel(lvl + 1) * 0.25F);
    }
}
