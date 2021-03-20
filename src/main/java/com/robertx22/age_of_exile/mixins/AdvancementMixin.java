package com.robertx22.age_of_exile.mixins;

import com.robertx22.age_of_exile.saveclasses.player_skills.PlayerSkillEnum;
import com.robertx22.age_of_exile.uncommon.datasaving.Load;
import net.minecraft.advancement.Advancement;
import net.minecraft.advancement.AdvancementFrame;
import net.minecraft.advancement.PlayerAdvancementTracker;
import net.minecraft.server.network.ServerPlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(PlayerAdvancementTracker.class)
public abstract class AdvancementMixin {

    @Accessor(value = "owner")
    public abstract ServerPlayerEntity getOwner();

    @Inject(method = "grantCriterion(Lnet/minecraft/advancement/Advancement;Ljava/lang/String;)Z", at = @At(value = "INVOKE", target = "Lnet/minecraft/advancement/AdvancementRewards;apply(Lnet/minecraft/server/network/ServerPlayerEntity;)V"))
    public void onGiveRewardsMixin(Advancement adv, String criterionName, CallbackInfoReturnable<Boolean> ci) {
        try {
            PlayerAdvancementTracker tracker = (PlayerAdvancementTracker) (Object) this;
            ServerPlayerEntity player = getOwner();

            if (adv != null) {

                float xp = 5;

                if (adv.getDisplay() != null) {

                    AdvancementFrame frame = adv.getDisplay()
                        .getFrame();

                    if (frame == AdvancementFrame.CHALLENGE) {
                        xp = 10;
                    }

                    System.out.print(" giving " + xp + " xp for " + adv.getId()
                        .toString());

                    Load.playerSkills(player)
                        .getDataFor(PlayerSkillEnum.EXPLORATION)
                        .addExp(player, (int) xp);

                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
