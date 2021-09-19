package com.robertx22.age_of_exile.player_skills.events;

import com.robertx22.age_of_exile.capability.player.RPGPlayerData;
import com.robertx22.age_of_exile.database.data.player_skills.PlayerSkill;
import com.robertx22.age_of_exile.database.registry.ExileDB;
import com.robertx22.age_of_exile.saveclasses.player_skills.PlayerSkillEnum;
import com.robertx22.age_of_exile.uncommon.datasaving.Load;
import com.robertx22.age_of_exile.uncommon.effectdatas.SkillDropEvent;
import com.robertx22.age_of_exile.uncommon.utilityclasses.LevelUtils;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.loot.LootContext;
import net.minecraft.loot.LootParameterSets;
import net.minecraft.loot.LootParameters;
import net.minecraft.loot.LootTable;
import net.minecraft.world.entity.projectile.FishingHook;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.List;

public class OnFishing {

    public static void run(LootTable lootTable, LootContext ctx, CallbackInfoReturnable<List<ItemStack>> ci) {

        try {

            if (!ctx.hasParam(LootParameters.THIS_ENTITY)) {
                return;
            }

            if (!lootTable.getParamSet()
                .equals(LootParameterSets.FISHING)) {
                return;
            }
            Entity en = ctx.getParamOrNull(LootParameters.THIS_ENTITY);
            PlayerEntity player = null;
            if (en instanceof FishingHook) {
                FishingHook bob = (FishingHook) en;
                player = bob.getPlayerOwner();
            }

            if (player == null) {
                return;
            }

            PlayerSkill skill = ExileDB.PlayerSkills()
                .get(PlayerSkillEnum.FISHING.id);

            RPGPlayerData skills = Load.playerRPGData(player);

            int exp = skill.getExpForAction(player);

            skills.professions.addExp(player, skill.type_enum, exp);

            List<ItemStack> list = skill.getExtraDropsFor(player, exp, LevelUtils.levelToSkillTier(skills.professions.getProfessionLevel(skill.type_enum)));

            SkillDropEvent effect = new SkillDropEvent(player, PlayerSkillEnum.FISHING, list);
            effect.Activate();

            ci.getReturnValue()
                .addAll(effect.extraDrops);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
