package com.robertx22.age_of_exile.saveclasses.player_skills;

import com.robertx22.age_of_exile.database.data.player_skills.PlayerSkill;
import com.robertx22.age_of_exile.database.registry.Database;
import com.robertx22.age_of_exile.saveclasses.ExactStatData;
import com.robertx22.age_of_exile.saveclasses.gearitem.gear_bases.IApplyableStats;
import com.robertx22.age_of_exile.saveclasses.unit.stat_ctx.MiscStatCtx;
import com.robertx22.age_of_exile.saveclasses.unit.stat_ctx.StatContext;
import com.robertx22.age_of_exile.uncommon.datasaving.Load;
import info.loenwind.autosave.annotations.Storable;
import info.loenwind.autosave.annotations.Store;
import net.minecraft.entity.LivingEntity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

@Storable
public class PlayerSkillsData implements IApplyableStats {

    @Store
    private HashMap<String, PlayerSkillData> map = new HashMap<>();

    public PlayerSkillData getDataFor(String id) {

        PlayerSkillEnum skill = PlayerSkillEnum.NONE;

        for (PlayerSkill x : Database.PlayerSkills()
            .getList()) {
            if (x.id.equals(id)) {
                skill = x.type_enum;
            }
        }
        return getDataFor(skill);

    }

    public PlayerSkillData getDataFor(PlayerSkillEnum skill) {

        if (!map.containsKey(skill.id)) {
            map.put(skill.id, new PlayerSkillData());
        }

        return map.get(skill.id);
    }

    @Override
    public List<StatContext> getStatAndContext(LivingEntity en) {
        List<ExactStatData> stats = new ArrayList<>();

        int lvl = Load.Unit(en)
            .getLevel();

        try {
            map.entrySet()
                .forEach(x -> {
                    if (Database.PlayerSkills()
                        .isRegistered(x.getKey())) {
                        PlayerSkill skill = Database.PlayerSkills()
                            .get(x.getKey());

                        if (skill != null) {
                            skill.getClaimedStats(x.getValue()
                                .getLvl())
                                .forEach(s -> {
                                    s.stats.forEach(e -> stats.add(e.toExactStat(lvl)));
                                });
                        }
                    }
                });
        } catch (Exception e) {
            e.printStackTrace();
        }

        return Arrays.asList(new MiscStatCtx(stats));
    }

}
