package com.robertx22.age_of_exile.player_skills.events;

import com.robertx22.age_of_exile.capability.player.PlayerSkills;
import com.robertx22.age_of_exile.database.data.player_skills.PlayerSkill;
import com.robertx22.age_of_exile.database.registry.ExileDB;
import com.robertx22.age_of_exile.player_skills.items.fishing.FishingLureItem;
import com.robertx22.age_of_exile.saveclasses.player_skills.PlayerSkillEnum;
import com.robertx22.age_of_exile.uncommon.datasaving.Load;
import com.robertx22.age_of_exile.uncommon.effectdatas.SkillDropEvent;
import com.robertx22.age_of_exile.uncommon.utilityclasses.LevelUtils;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.FishingBobberEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.loot.LootTable;
import net.minecraft.loot.context.LootContext;
import net.minecraft.loot.context.LootContextParameters;
import net.minecraft.loot.context.LootContextTypes;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.List;

public class OnFishing {

    public static void run(LootTable lootTable, LootContext ctx, CallbackInfoReturnable<List<ItemStack>> ci) {

        try {

            if (!ctx.hasParameter(LootContextParameters.THIS_ENTITY)) {
                return;
            }

            if (!lootTable.getType()
                .equals(LootContextTypes.FISHING)) {
                return;
            }
            Entity en = ctx.get(LootContextParameters.THIS_ENTITY);
            PlayerEntity player = null;
            if (en instanceof FishingBobberEntity) {
                FishingBobberEntity bob = (FishingBobberEntity) en;
                player = bob.getPlayerOwner();
            }

            if (player == null) {
                return;
            }

            PlayerSkill skill = ExileDB.PlayerSkills()
                .get(PlayerSkillEnum.FISHING.id);

            PlayerSkills skills = Load.playerSkills(player);

            int exp = skill.getExpForAction(player);

            skills.addExp(skill.type_enum, exp);

            List<ItemStack> list = skill.getExtraDropsFor(player, exp, LevelUtils.levelToTier(skills.getLevel(skill.type_enum)));

            SkillDropEvent effect = new SkillDropEvent(player, PlayerSkillEnum.FISHING, list);
            effect.Activate();

            FishingLureItem.spendLure(player);

            ci.getReturnValue()
                .addAll(effect.extraDrops);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
