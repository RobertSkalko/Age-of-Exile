package com.robertx22.age_of_exile.player_skills;

import com.robertx22.age_of_exile.uncommon.datasaving.Load;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.text.LiteralText;
import net.minecraft.util.Formatting;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

public class OnItemWithSkillLvlRestrictionCrafted {

    public static void onCraft(ItemStack stack, World world, PlayerEntity player, int amount, CallbackInfo ci) {

        if (true) {
            return; // todo delete
        }

        if (world.isClient || stack.isEmpty()) {
            return;
        }

        if (player == null) {
            stack.setCount(0); // this probably won't ever be called
            // item isn't given a tag that makes it usable, which makes it unusable!
            // auto-crafters won't give it the tag, so they are useless!
            return;
        }

        if (stack.getItem() instanceof IReqSkillLevel) {
            IReqSkillLevel req = (IReqSkillLevel) stack.getItem();

            if (req.getSkillLevelToCraft() > Load.playerSkills(player)
                .getLevel(req.getSkillTypeToCraft())) {

                stack.setCount(0);
                player.sendMessage(new LiteralText("Your skill level isn't high enough to craft that item.").formatted(Formatting.RED), false);
            }

        }

    }
}
