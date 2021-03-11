package com.robertx22.age_of_exile.saveclasses.player_skills;

import com.robertx22.age_of_exile.database.data.game_balance_config.GameBalanceConfig;
import com.robertx22.age_of_exile.uncommon.datasaving.Load;
import com.robertx22.age_of_exile.uncommon.utilityclasses.LevelUtils;
import info.loenwind.autosave.annotations.Storable;
import info.loenwind.autosave.annotations.Store;
import net.minecraft.entity.player.PlayerEntity;

@Storable
public class PlayerSkillData {

    @Store
    private int lvl = 1;
    @Store
    private int exp = 0;

    static int MAX_LEVEL = 999;

    public boolean addExp(PlayerEntity player, int addExp) {

        if (lvl >= MAX_LEVEL) {
            return false;
        }
        if (GameBalanceConfig.get().MAX_LEVEL > lvl) {
            if (lvl >= Load.Unit(player)
                .getLevel() + 5) {
                return false; // don't allow leveling of skills if player level is not higher
                // edit: 10 lvl diff is fine (maybe)
            }
        }

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
        return (LevelUtils.getExpNeededForSkillLevel(lvl + 1));
    }
}
