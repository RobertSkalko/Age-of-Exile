package com.robertx22.age_of_exile.player_skills.events;

import com.robertx22.age_of_exile.capability.player.RPGPlayerData;
import com.robertx22.age_of_exile.database.data.player_skills.PlayerSkill;
import com.robertx22.age_of_exile.database.registry.ExileDB;
import com.robertx22.age_of_exile.saveclasses.player_skills.PlayerSkillEnum;
import com.robertx22.age_of_exile.uncommon.datasaving.Load;
import com.robertx22.age_of_exile.uncommon.effectdatas.SkillDropEvent;
import com.robertx22.age_of_exile.uncommon.utilityclasses.LevelUtils;
import net.minecraft.block.Block;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.loot.LootContext;
import net.minecraft.loot.LootParameters;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.List;

public class OnBlockDropMining {

    public static void run(LootContext ctx, CallbackInfoReturnable<List<ItemStack>> ci) {

        try {
            if (!ctx.hasParam(LootParameters.BLOCK_STATE)) {
                return;
            }
            if (!ctx.hasParam(LootParameters.TOOL)) {
                return;
            }
            if (!ctx.hasParam(LootParameters.ORIGIN)) {
                return;
            }
            if (!ctx.hasParam(LootParameters.THIS_ENTITY)) {
                return;
            }
            ItemStack stack = ctx.getParamOrNull(LootParameters.TOOL);
            if (EnchantmentHelper.getItemEnchantmentLevel(Enchantments.SILK_TOUCH, stack) != 0) {
                return;
            }
            Block block = ctx.getParamOrNull(LootParameters.BLOCK_STATE)
                .getBlock();

            if (ci.getReturnValue()
                .stream()
                .anyMatch(x -> x.getItem() == block.asItem())) {
                return; // if a diamond ore is broken and drops diamond ore, don't give exp and loot
            }

            Entity en = ctx.getParamOrNull(LootParameters.THIS_ENTITY);

            PlayerEntity player = null;
            if (en instanceof PlayerEntity) {
                player = (PlayerEntity) en;
            } else {
                return;
            }
            if (player.level.isClientSide) {
                return;
            }
            PlayerSkill skill = ExileDB.PlayerSkills()
                .get(PlayerSkillEnum.MINING.id);

            RPGPlayerData data = Load.playerRPGData(player);

            int exp = skill.getExpForBlockBroken(block);

            if (exp > 0) {

                data.professions.addExp(player, skill.type_enum, exp);

                List<ItemStack> list = skill.getExtraDropsFor(player, exp, LevelUtils.levelToSkillTier(data.professions.getProfessionLevel(skill.type_enum)));

                SkillDropEvent effect = new SkillDropEvent(player, PlayerSkillEnum.MINING, list);
                effect.Activate();

                ci.getReturnValue()
                    .addAll(effect.extraDrops);

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
