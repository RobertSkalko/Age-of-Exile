package com.robertx22.age_of_exile.mixins;

import com.robertx22.age_of_exile.saveclasses.player_skills.PlayerSkillEnum;
import com.robertx22.age_of_exile.uncommon.datasaving.Load;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.FrameType;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.server.PlayerAdvancements;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(PlayerAdvancements.class)
public abstract class AdvancementMixin {

    @Accessor(value = "owner")
    public abstract ServerPlayerEntity getOwner();

    @Inject(method = "grantCriterion(Lnet/minecraft/advancement/Advancement;Ljava/lang/String;)Z", at = @At(value = "INVOKE", target = "Lnet/minecraft/advancement/AdvancementRewards;apply(Lnet/minecraft/server/network/ServerPlayerEntity;)V"))
    public void onGiveRewardsMixin(Advancement adv, String criterionName, CallbackInfoReturnable<Boolean> ci) {
        try {
            //    PlayerAdvancementTracker tracker = (PlayerAdvancementTracker) (Object) this;
            ServerPlayerEntity player = getOwner();

            if (adv != null) {

                float xp = 5;

                if (adv.getDisplay() != null) {

                    FrameType frame = adv.getDisplay()
                        .getFrame();

                    if (frame == FrameType.CHALLENGE) {
                        xp = 10;
                    }

                    // System.out.print(" giving " + xp + " xp for " + adv.getId()
                    //   .toString());

                    Load.playerRPGData(player).professions
                        .getDataFor(PlayerSkillEnum.EXPLORATION)
                        .addExp(player, (int) xp);

                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
