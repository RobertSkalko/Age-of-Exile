package com.robertx22.age_of_exile.vanilla_mc.packets;

import com.robertx22.age_of_exile.database.registry.ExileDB;
import com.robertx22.age_of_exile.mmorpg.SlashRef;
import com.robertx22.age_of_exile.mmorpg.SyncedToClientValues;
import com.robertx22.age_of_exile.saveclasses.player_skills.PlayerSkillEnum;
import com.robertx22.library_of_exile.main.MyPacket;
import com.robertx22.library_of_exile.packets.ExilePacketContext;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.ResourceLocation;

public class SkillLevelUpToClient extends MyPacket<SkillLevelUpToClient> {

    public String skill;

    public SkillLevelUpToClient() {

    }

    public SkillLevelUpToClient(PlayerSkillEnum skill) {
        this.skill = skill.id;
    }

    @Override
    public ResourceLocation getIdentifier() {
        return new ResourceLocation(SlashRef.MODID, "skill_lvl");
    }

    @Override
    public void loadFromData(PacketBuffer tag) {
        skill = tag.readUtf(500);
    }

    @Override
    public void saveToData(PacketBuffer tag) {
        tag.writeUtf(skill, 500);
    }

    @Override
    public void onReceived(ExilePacketContext ctx) {
        try {
            SyncedToClientValues.skillJustLeveled = ExileDB.PlayerSkills()
                .get(skill).type_enum;
            SyncedToClientValues.ticksToShowSkillLvled = 120;
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public MyPacket<SkillLevelUpToClient> newInstance() {
        return new SkillLevelUpToClient();
    }
}

