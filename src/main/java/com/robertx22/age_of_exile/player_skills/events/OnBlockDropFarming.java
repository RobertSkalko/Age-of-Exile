package com.robertx22.age_of_exile.player_skills.events;

import com.robertx22.age_of_exile.capability.player.RPGPlayerData;
import com.robertx22.age_of_exile.database.data.player_skills.PlayerSkill;
import com.robertx22.age_of_exile.database.registry.ExileDB;
import com.robertx22.age_of_exile.player_skills.items.foods.SkillItemTier;
import com.robertx22.age_of_exile.saveclasses.player_skills.PlayerSkillEnum;
import com.robertx22.age_of_exile.uncommon.datasaving.Load;
import com.robertx22.age_of_exile.uncommon.effectdatas.SkillDropEvent;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.CropsBlock;
import net.minecraft.block.StemGrownBlock;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.loot.LootContext;
import net.minecraft.loot.LootParameters;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.List;

public class OnBlockDropFarming {

    public static void run(LootContext ctx, CallbackInfoReturnable<List<ItemStack>> ci) {

        try {
            if (!ctx.hasParam(LootParameters.BLOCK_STATE)) {
                return;
            }
            if (!ctx.hasParam(LootParameters.ORIGIN)) {
                return;
            }
            if (!ctx.hasParam(LootParameters.THIS_ENTITY)) {
                return;
            }

            BlockState state = ctx.getParamOrNull(LootParameters.BLOCK_STATE);

            Block block = state
                .getBlock();

            if (block instanceof CropsBlock) {
                CropsBlock crop = (CropsBlock) block;
                if (!crop.isMaxAge(state)) {
                    return;
                }
            } else if (block instanceof StemGrownBlock) {

            } else {
                return;
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
                .get(PlayerSkillEnum.FARMING.id);

            RPGPlayerData data = Load.playerRPGData(player);

            int exp = skill.getExpForBlockBroken(block);

            if (exp > 0) {

                data.professions.addExp(player, skill.type_enum, exp);
                List<ItemStack> list = skill.getExtraDropsFor(player, exp, SkillItemTier.TIER0); // todo tier

                SkillDropEvent effect = new SkillDropEvent(player, PlayerSkillEnum.FARMING, list);
                effect.Activate();

                ci.getReturnValue()
                    .addAll(effect.extraDrops);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}


