package com.robertx22.age_of_exile.aoe_data.database.player_skills;

import com.robertx22.age_of_exile.player_skills.IReqSkillLevel;
import com.robertx22.age_of_exile.uncommon.datasaving.Load;
import com.robertx22.age_of_exile.uncommon.utilityclasses.OnScreenMessageUtils;
import com.robertx22.library_of_exile.utils.SoundUtils;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.network.packet.s2c.play.TitleS2CPacket;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.LiteralText;
import net.minecraft.util.Formatting;
import org.jetbrains.annotations.Nullable;

public class IsSkillItemUsableUtil {

    public static boolean canUseItem(@Nullable PlayerEntity player, ItemStack stack, Boolean announce) {

        if (stack.getItem() instanceof IReqSkillLevel) {
            if (player == null) {
                return false;
            }

            IReqSkillLevel req = (IReqSkillLevel) stack.getItem();
            if (Load.playerSkills(player)
                .getLevel(req.getSkillTypeToCraft()) >= req.getSkillLevelToCraft()) {
                return true;
            } else {

                if (announce) {
                    if (player instanceof ServerPlayerEntity) {
                        SoundUtils.playSound(player, SoundEvents.ENTITY_VILLAGER_NO, 1, 1);
                        OnScreenMessageUtils.sendMessage((ServerPlayerEntity) player, new LiteralText("Skill level not high enough to use that item.").formatted(Formatting.RED), TitleS2CPacket.Action.ACTIONBAR);
                    }
                }
                return false;
            }
        } else {
            return true;
        }

    }

}
