package com.robertx22.age_of_exile.capability.player;

import com.robertx22.age_of_exile.capability.bases.ICommonPlayerCap;
import com.robertx22.age_of_exile.mmorpg.Ref;
import com.robertx22.age_of_exile.saveclasses.player_skills.PlayerSkillData;
import com.robertx22.age_of_exile.saveclasses.player_skills.PlayerSkillEnum;
import com.robertx22.age_of_exile.saveclasses.player_skills.PlayerSkillsData;
import com.robertx22.age_of_exile.vanilla_mc.packets.sync_cap.PlayerCaps;
import com.robertx22.library_of_exile.utils.LoadSave;
import com.robertx22.library_of_exile.utils.SoundUtils;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.Identifier;

public class PlayerSkills implements ICommonPlayerCap {

    public static final Identifier RESOURCE = new Identifier(Ref.MODID, "player_skills");
    private static final String LOC = "data";

    PlayerEntity player;

    PlayerSkillsData data = new PlayerSkillsData();

    public void addExp(PlayerSkillEnum skill, int exp) {
        PlayerSkillData sd = data.getDataFor(skill);

        if (sd.addExp(exp)) {
            onLevelUp(skill);
        }
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

}