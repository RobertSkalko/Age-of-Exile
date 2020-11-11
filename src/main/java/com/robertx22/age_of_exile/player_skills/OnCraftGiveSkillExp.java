package com.robertx22.age_of_exile.player_skills;

import com.robertx22.age_of_exile.database.data.player_skills.ItemCraftExp;
import com.robertx22.age_of_exile.database.data.player_skills.PlayerSkill;
import com.robertx22.age_of_exile.database.registry.SlashRegistry;
import com.robertx22.age_of_exile.uncommon.datasaving.Load;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Optional;

public class OnCraftGiveSkillExp {
    public static void onCraft(ItemStack stack, World world, PlayerEntity player, int amount, CallbackInfo ci) {

        if (world.isClient || player == null || stack.isEmpty()) {
            return;
        }

        for (PlayerSkill skill : SlashRegistry.PlayerSkills()
            .getList()) {
            Optional<ItemCraftExp> opt = skill.item_craft_exp.stream()
                .filter(x -> x.getItem() == stack.getItem())
                .findAny();

            if (opt.isPresent()) {
                Load.playerSkills(player)
                    .addExp(skill.type_enum, (int) opt.get().exp);
            }

        }

    }
}
