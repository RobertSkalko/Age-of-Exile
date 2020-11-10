package com.robertx22.age_of_exile.database.data.player_skills;

import com.robertx22.age_of_exile.aoe_data.datapacks.bases.ISerializedRegistryEntry;
import com.robertx22.age_of_exile.database.data.IAutoGson;
import com.robertx22.age_of_exile.database.registry.SlashRegistryType;
import com.robertx22.age_of_exile.saveclasses.player_skills.PlayerSkillEnum;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class PlayerSkill implements ISerializedRegistryEntry<PlayerSkill>, IAutoGson<PlayerSkill> {
    public static PlayerSkill SERIALIZER = new PlayerSkill();

    public PlayerSkillEnum type_enum = PlayerSkillEnum.MINING;
    public String id;
    public int exp_per_action = 0;

    public HashMap<Integer, SkillDropReward> drop_rewards = new HashMap<>();
    public HashMap<Integer, SkillStatReward> stat_rewards = new HashMap<>();

    public List<BlockBreakExp> block_break_exp = new ArrayList<>();

    @Override
    public SlashRegistryType getSlashRegistryType() {
        return SlashRegistryType.PLAYER_SKILLS;
    }

    @Override
    public String GUID() {
        return id;
    }

    @Override
    public Class<PlayerSkill> getClassForSerialization() {
        return PlayerSkill.class;
    }
}
