package com.robertx22.age_of_exile.database.data.player_skills;

import com.robertx22.age_of_exile.database.OptScaleExactStat;
import com.robertx22.age_of_exile.database.data.game_balance_config.GameBalanceConfig;
import com.robertx22.age_of_exile.database.registry.ExileRegistryTypes;
import com.robertx22.age_of_exile.mmorpg.SlashRef;
import com.robertx22.age_of_exile.player_skills.items.foods.SkillItemTier;
import com.robertx22.age_of_exile.saveclasses.gearitem.gear_bases.ITooltipList;
import com.robertx22.age_of_exile.saveclasses.gearitem.gear_bases.TooltipInfo;
import com.robertx22.age_of_exile.saveclasses.player_skills.PlayerSkillEnum;
import com.robertx22.age_of_exile.uncommon.datasaving.Load;
import com.robertx22.age_of_exile.uncommon.localization.Words;
import com.robertx22.age_of_exile.uncommon.utilityclasses.TooltipUtils;
import com.robertx22.library_of_exile.registry.ExileRegistryType;
import com.robertx22.library_of_exile.registry.IAutoGson;
import com.robertx22.library_of_exile.registry.JsonExileRegistry;
import com.robertx22.library_of_exile.utils.RandomUtils;
import net.minecraft.block.Block;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;

import java.util.*;
import java.util.stream.Collectors;

public class PlayerSkill implements JsonExileRegistry<PlayerSkill>, IAutoGson<PlayerSkill>, ITooltipList {
    public static PlayerSkill SERIALIZER = new PlayerSkill();

    public PlayerSkillEnum type_enum = PlayerSkillEnum.NONE;
    public String id;
    public int exp_per_action = 0;
    public int order = 0;

    public List<SkillDropTable> dropTables = new ArrayList<>();

    public List<SkillStatReward> stat_rewards = new ArrayList<>();
    public List<MasteryStatReward> mastery_stat_reward = new ArrayList<>();

    public List<BlockBreakExp> block_break_exp = new ArrayList<>();

    public List<ItemCraftExp> item_craft_exp = new ArrayList<>();
    public List<ItemCraftExp> item_smelt_exp = new ArrayList<>();

    List<SkillStatReward> cachedStatRewards = null;

    public ResourceLocation getIcon() {
        return SlashRef.id("textures/gui/skills/icons/" + id + ".png");
    }

    public int getExpForCraft(ItemStack stack, PlayerEntity player) {

        Optional<ItemCraftExp> opt = item_craft_exp.stream()
            .filter(x -> x.getItem() == stack.getItem())
            .findAny();

        if (opt.isPresent()) {
            return opt.get().exp;
        }
        return 0;
    }

    public List<SkillStatReward> getStatRewards() {
        if (cachedStatRewards == null) {
            List<SkillStatReward> list = new ArrayList<>(stat_rewards);
            mastery_stat_reward.forEach(x -> list.addAll(x.getStatRewards()));
            cachedStatRewards = list;
        }
        return cachedStatRewards;

    }

    public int getExpForAction(PlayerEntity player) {

        int exp = exp_per_action;

        return exp;

    }

    public List<SkillStatReward> getClaimedStats(int lvl) {
        return getStatRewards().stream()
            .filter(r -> lvl >= r.lvl_req)
            .collect(Collectors.toList());
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

    public List<ItemStack> getExtraDropsFor(PlayerEntity player, int expForAction, SkillItemTier tierContext) {
        if (expForAction == 0) {
            return Arrays.asList();
        }

        List<ItemStack> list = new ArrayList<>();

        for (SkillDropTable dropTable : dropTables) {

            if (dropTable.tier == tierContext) {

                if (dropTable.req.isAllowed(player.level, player.blockPosition())) {
                    float chance = dropTable.loot_chance_per_action_exp * expForAction;

                    if (RandomUtils.roll(chance)) {
                        List<SkillDropReward> possible = dropTable.drop_rewards;

                        if (!possible.isEmpty()) {
                            list.add(RandomUtils.weightedRandom(possible)
                                .getRewardStack());
                        }
                    }
                }
            }
        }

        return list;

    }

    @Override
    public ExileRegistryType getExileRegistryType() {
        return ExileRegistryTypes.PLAYER_SKILLS;
    }

    @Override
    public String GUID() {
        return id;
    }

    @Override
    public Class<PlayerSkill> getClassForSerialization() {
        return PlayerSkill.class;
    }

    @Override
    public List<ITextComponent> GetTooltipString(TooltipInfo info) {

        List<ITextComponent> list = new ArrayList<>();

        int lvl = Load.playerRPGData(info.player).professions
            .getProfessionLevel(type_enum);

        list.add(this.type_enum.word.locName()
            .withStyle(type_enum.format));

        list.add(new StringTextComponent(""));

        List<OptScaleExactStat> stats = new ArrayList<>();

        for (SkillStatReward x : getClaimedStats(lvl)) {
            stats.addAll(x.stats);
        }
        OptScaleExactStat.combine(stats);

        if (!stats.isEmpty()) {
            list.add(Words.Stats.locName()
                .append(": "));
            stats.forEach(x -> list.addAll(x.GetTooltipString(info)));
        }

        Optional<SkillStatReward> opt = getStatRewards().stream()
            .filter(x -> x.lvl_req > lvl)
            .sorted(Comparator.comparingInt(x -> x.lvl_req))
            .findFirst();

        if (opt.isPresent()) {

            List<SkillStatReward> nextstatrewards = new ArrayList<>();

            for (SkillStatReward next : getStatRewards()) {
                if (next.lvl_req == opt.get().lvl_req) {
                    nextstatrewards.add(next);
                }
            }
            List<OptScaleExactStat> nextStats = new ArrayList<>();
            nextstatrewards.forEach(x -> nextStats.addAll(x.stats));
            OptScaleExactStat.combine(nextStats);

            list.add(new StringTextComponent(""));
            list.add(new StringTextComponent("Level " + opt.get().lvl_req + " unlocks:"));
            nextStats.forEach(x -> list.addAll(x.GetTooltipString(info)));
        }

        if (lvl >= GameBalanceConfig.get().MAX_LEVEL) {

        } else {
            if (lvl >= Load.Unit(info.player)
                .getLevel()) {
                if (lvl < GameBalanceConfig.get().MAX_LEVEL) {
                    list.add(new StringTextComponent(""));
                    list.add(new StringTextComponent("Skill level Capped to Combat level.").withStyle(TextFormatting.RED));
                    list.add(new StringTextComponent("Level your Combat level to progress further.").withStyle(TextFormatting.RED));
                }
            }
        }

        TooltipUtils.removeDoubleBlankLines(list);

        return list;

    }

    @Override
    public int Weight() {
        return 1000;
    }
}
