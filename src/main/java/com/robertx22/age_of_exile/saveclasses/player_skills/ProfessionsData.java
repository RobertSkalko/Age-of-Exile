package com.robertx22.age_of_exile.saveclasses.player_skills;

import com.robertx22.age_of_exile.database.data.player_skills.PlayerSkill;
import com.robertx22.age_of_exile.database.data.stats.types.professions.all.BonusSkillExp;
import com.robertx22.age_of_exile.database.registry.ExileDB;
import com.robertx22.age_of_exile.mmorpg.SlashRef;
import com.robertx22.age_of_exile.saveclasses.ExactStatData;
import com.robertx22.age_of_exile.saveclasses.gearitem.gear_bases.IApplyableStats;
import com.robertx22.age_of_exile.saveclasses.unit.stat_ctx.MiscStatCtx;
import com.robertx22.age_of_exile.saveclasses.unit.stat_ctx.StatContext;
import com.robertx22.age_of_exile.uncommon.datasaving.Load;
import com.robertx22.age_of_exile.uncommon.utilityclasses.LevelUtils;
import com.robertx22.age_of_exile.vanilla_mc.packets.SkillLevelUpToClient;
import com.robertx22.age_of_exile.vanilla_mc.packets.sync_cap.PlayerCaps;
import com.robertx22.age_of_exile.vanilla_mc.packets.sync_cap.SyncCapabilityToClient;
import com.robertx22.library_of_exile.main.Packets;
import com.robertx22.library_of_exile.utils.SoundUtils;
import info.loenwind.autosave.annotations.Storable;
import info.loenwind.autosave.annotations.Store;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

@Storable
public class ProfessionsData implements IApplyableStats {
    static ResourceLocation LOW_LVL_TEX = SlashRef.id("textures/gui/skills/skill_level/low.png");
    static ResourceLocation MID_LVL_TEX = SlashRef.id("textures/gui/skills/skill_level/mid.png");
    static ResourceLocation HIGH_LVL_TEX = SlashRef.id("textures/gui/skills/skill_level/high.png");

    @Store
    private HashMap<String, PlayerSkillData> map = new HashMap<>();

    public PlayerSkillData getDataFor(String id) {

        PlayerSkillEnum skill = PlayerSkillEnum.NONE;

        for (PlayerSkill x : ExileDB.PlayerSkills()
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
                    if (ExileDB.PlayerSkills()
                        .isRegistered(x.getKey())) {
                        PlayerSkill skill = ExileDB.PlayerSkills()
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

    public void addExp(PlayerEntity player, PlayerSkillEnum skill, int exp) {

        exp *= Load.Unit(player)
            .getUnit()
            .getCalculatedStat(new BonusSkillExp(skill))
            .getMultiplier(); // bonus exp needs to happen here because otherwise it counts towards droprates

        PlayerSkillData sd = getDataFor(skill);

        if (sd.addExp(player, exp)) {
            onLevelUp(player, skill);
        }
    }

    public ResourceLocation getBackGroundTextureFor(PlayerSkillEnum se) {
        int lvl = getProfessionLevel(se);
        float multi = LevelUtils.getMaxLevelMultiplier(lvl);

        if (multi < 0.25F) {
            return LOW_LVL_TEX;
        } else if (multi < 0.75F) {
            return MID_LVL_TEX;
        }
        return HIGH_LVL_TEX;

    }

    public int getProfessionLevel(PlayerSkillEnum se) {
        return getDataFor(se)
            .getLvl();
    }

    public float getExpDividedByNeededToLevelMulti(PlayerSkillEnum skill) {

        int exp = this.getDataFor(skill)
            .getExp();
        int need = getDataFor(skill).getExpNeededToLevel();

        return MathHelper.clamp((float) exp / (float) need, 0F, 1F);
    }

    public void onLevelUp(PlayerEntity player, PlayerSkillEnum skill) {

        Packets.sendToClient(player, new SyncCapabilityToClient(player, PlayerCaps.PLAYER_RPG_DATA));

        SoundUtils.ding(player.level, player.blockPosition());

        player.displayClientMessage(skill.word.locName()
            .append(" leveled up!")
            .withStyle(skill.format), false);

        Packets.sendToClient(player, new SkillLevelUpToClient(skill));
    }
}
