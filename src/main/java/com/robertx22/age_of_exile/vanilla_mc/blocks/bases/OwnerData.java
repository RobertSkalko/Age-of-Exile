package com.robertx22.age_of_exile.vanilla_mc.blocks.bases;

import com.robertx22.age_of_exile.saveclasses.player_skills.PlayerSkillEnum;
import com.robertx22.age_of_exile.uncommon.datasaving.Load;
import info.loenwind.autosave.annotations.Storable;
import info.loenwind.autosave.annotations.Store;
import net.minecraft.entity.player.PlayerEntity;

import java.util.HashMap;

@Storable
public class OwnerData {

    @Store
    public HashMap<String, Integer> lvls = new HashMap<>();

    public OwnerData(PlayerEntity p) {
        for (PlayerSkillEnum sk : PlayerSkillEnum.values()) {
            lvls.put(sk.id, Load.playerSkills(p)
                .getLevel(sk));
        }
    }

    public OwnerData() {
    }
}
