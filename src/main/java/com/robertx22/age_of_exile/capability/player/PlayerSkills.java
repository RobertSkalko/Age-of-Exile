package com.robertx22.age_of_exile.capability.player;

import com.robertx22.age_of_exile.capability.bases.ICommonPlayerCap;
import com.robertx22.age_of_exile.capability.entity.EntityCap;
import com.robertx22.age_of_exile.mmorpg.Ref;
import com.robertx22.age_of_exile.saveclasses.gearitem.gear_bases.IApplyableStats;
import com.robertx22.age_of_exile.saveclasses.player_skills.PlayerSkillData;
import com.robertx22.age_of_exile.saveclasses.player_skills.PlayerSkillEnum;
import com.robertx22.age_of_exile.saveclasses.player_skills.PlayerSkillsData;
import com.robertx22.age_of_exile.uncommon.utilityclasses.LevelUtils;
import com.robertx22.age_of_exile.vanilla_mc.packets.SkillLevelUpToClient;
import com.robertx22.age_of_exile.vanilla_mc.packets.sync_cap.PlayerCaps;
import com.robertx22.library_of_exile.main.Packets;
import com.robertx22.library_of_exile.utils.LoadSave;
import com.robertx22.library_of_exile.utils.SoundUtils;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.Identifier;

public class PlayerSkills implements ICommonPlayerCap, IApplyableStats {

    static Identifier LOW_LVL_TEX = Ref.id("textures/gui/skills/skill_level/low.png");
    static Identifier MID_LVL_TEX = Ref.id("textures/gui/skills/skill_level/mid.png");
    static Identifier HIGH_LVL_TEX = Ref.id("textures/gui/skills/skill_level/high.png");

    public static final Identifier RESOURCE = new Identifier(Ref.MODID, "player_skills");
    private static final String LOC = "data";

    public PlayerEntity player;

    PlayerSkillsData data = new PlayerSkillsData();

    public void addExp(PlayerSkillEnum skill, int exp) {
        PlayerSkillData sd = data.getDataFor(skill);

        if (sd.addExp(player, exp)) {
            onLevelUp(skill);
        }
    }

    public PlayerSkillData getDataFor(PlayerSkillEnum skill) {
        return this.data.getDataFor(skill);
    }

    public Identifier getBackGroundTextureFor(PlayerSkillEnum se) {
        int lvl = getLevel(se);
        float multi = LevelUtils.getMaxLevelMultiplier(lvl);

        if (multi < 0.25F) {
            return LOW_LVL_TEX;
        } else if (multi < 0.75F) {
            return MID_LVL_TEX;
        }
        return HIGH_LVL_TEX;

    }

    public int getLevel(PlayerSkillEnum se) {
        return data.getDataFor(se)
            .getLvl();
    }

    public void onLevelUp(PlayerSkillEnum skill) {

        SoundUtils.ding(player.world, player.getBlockPos());

        player.sendMessage(skill.word.locName()
            .append(" leveled up!")
            .formatted(skill.format), false);

        Packets.sendToClient(player, new SkillLevelUpToClient(skill));
    }

    public PlayerSkills(PlayerEntity player) {
        this.player = player;
    }

    @Override
    public CompoundTag toTag(CompoundTag nbt) {
        LoadSave.Save(data, nbt, LOC);
        return nbt;
    }

    @Override
    public void fromTag(CompoundTag nbt) {
        this.data = LoadSave.Load(PlayerSkillsData.class, new PlayerSkillsData(), nbt, LOC);
    }

    @Override
    public PlayerCaps getCapType() {
        return PlayerCaps.PLAYER_SKILLS;
    }

    @Override
    public void applyStats(EntityCap.UnitData data) {
        this.data.applyStats(data);
    }
}