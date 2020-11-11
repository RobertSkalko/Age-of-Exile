package com.robertx22.age_of_exile.database.data.player_skills;

import com.robertx22.age_of_exile.aoe_data.datapacks.bases.ISerializedRegistryEntry;
import com.robertx22.age_of_exile.capability.player.PlayerSkills;
import com.robertx22.age_of_exile.database.data.IAutoGson;
import com.robertx22.age_of_exile.database.registry.SlashRegistryType;
import com.robertx22.age_of_exile.mmorpg.Ref;
import com.robertx22.age_of_exile.saveclasses.player_skills.PlayerSkillEnum;
import com.robertx22.age_of_exile.uncommon.utilityclasses.RandomUtils;
import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class PlayerSkill implements ISerializedRegistryEntry<PlayerSkill>, IAutoGson<PlayerSkill> {
    public static PlayerSkill SERIALIZER = new PlayerSkill();

    public PlayerSkillEnum type_enum = PlayerSkillEnum.MINING;
    public String id;
    public int exp_per_action = 0;

    public float loot_chance_per_action_exp = 0.1F;

    public List<SkillDropReward> drop_rewards = new ArrayList<>();
    public List<SkillStatReward> stat_rewards = new ArrayList<>();

    public List<BlockBreakExp> block_break_exp = new ArrayList<>();

    public List<ItemCraftExp> item_craft_exp = new ArrayList<>();

    public Identifier getIcon() {
        return Ref.id("textures/gui/skills/icons/" + id + ".png");
    }

    public int getExpForBlockBroken(Block block) {

        Optional<BlockBreakExp> opt = block_break_exp.stream()
            .filter(x -> x.getBlock() == block)
            .findFirst();

        if (opt.isPresent()) {
            return (int) opt.get().exp;
        } else {
            return 0;
        }

    }

    public List<ItemStack> getExtraDropsFor(PlayerSkills skills, int expForAction) {

        List<ItemStack> list = new ArrayList<>();

        float chance = loot_chance_per_action_exp * expForAction;

        if (RandomUtils.roll(chance)) {
            List<SkillDropReward> possible = drop_rewards
                .stream()
                .filter(x -> skills.getLevel(type_enum) >= x.lvl_req)
                .collect(Collectors.toList());

            if (!possible.isEmpty()) {
                list.add(RandomUtils.weightedRandom(possible)
                    .getRewardStack());
            }
        }

        return list;

    }

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
