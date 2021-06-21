package com.robertx22.age_of_exile.vanilla_mc.packets;

import com.robertx22.age_of_exile.database.registry.ExileDB;
import com.robertx22.age_of_exile.mmorpg.Ref;
import com.robertx22.age_of_exile.mmorpg.SyncedToClientValues;
import com.robertx22.age_of_exile.saveclasses.player_skills.PlayerSkillEnum;
import com.robertx22.library_of_exile.main.MyPacket;
import net.fabricmc.fabric.api.network.PacketContext;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.util.Identifier;

public class SkillLevelUpToClient extends MyPacket<SkillLevelUpToClient> {

    public String skill;

    public SkillLevelUpToClient() {

    }

    public SkillLevelUpToClient(PlayerSkillEnum skill) {
        this.skill = skill.id;
    }

    @Override
    public Identifier getIdentifier() {
        return new Identifier(Ref.MODID, "skill_lvl");
    }

    @Override
    public void loadFromData(PacketByteBuf tag) {
        skill = tag.readString();
    }

    @Override
    public void saveToData(PacketByteBuf tag) {
        tag.writeString(skill, 500);
    }

    @Override
    public void onReceived(PacketContext ctx) {
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

